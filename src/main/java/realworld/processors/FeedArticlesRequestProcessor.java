package realworld.processors;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import realworld.core.auth.Constants;
import realworld.core.dao.ArticleDao;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.FeedArticlesRequest;
import realworld.proto.Article;
import realworld.proto.ArticlesResponse;
import realworld.proto.Profile;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbUser;

class FeedArticlesRequestProcessor implements GrpcRequestProcessor<FeedArticlesRequest, ArticlesResponse> {

  private static final Logger logger = Logger.getLogger(FeedArticlesRequestProcessor.class.getName());

  private final UserDao userDao;
  private final ArticleDao articleDao;

  @Inject
  FeedArticlesRequestProcessor(UserDao userDao, ArticleDao articleDao) {
    this.userDao = userDao;
    this.articleDao = articleDao;
  }

  @Override
  public ListenableFuture<ArticlesResponse> execute(FeedArticlesRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    if (userId.isEmpty()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
    Optional<DbUser> callerOptional = userDao.getUser(userId);
    if (!callerOptional.isPresent()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }

    List<Article> articles = articleDao.feedArticles(userId)
      .stream()
      .map(article -> toArticle(article, articleDao, userDao, userId))
      .collect(Collectors.toList());
    return Futures.immediateFuture(ArticlesResponse.newBuilder()
        .addAllArticles(articles)
        .setArticlesSize(Int32Value.of(articles.size()))
        .build());
  }

  private static Article toArticle(
      DbArticle article, ArticleDao articleDao, UserDao userDao, String userId) {
    DbUser author = userDao.getUser(article.getAuthorId()).get();
    return Article.newBuilder()
          .setSlug(article.getSlug())
          .setTitle(article.getTitle())
          .setDescription(article.getDescription())
          .setBody(article.getBody())
          .addAllTagList(article.getTagListList())
          .setCreatedAt(article.getCreatedAt())
          .setUpdatedAt(article.getUpdatedAt())
          .setFavorited(BoolValue.of(
                Strings.isNullOrEmpty(userId)
                    ? false
                    : articleDao.isFavoriteArticle(userId, article.getId())))
          .setFavoritesCount(Int32Value.of(articleDao.getArticleFavoriteCount(article.getId())))
          .setAuthor(Profile.newBuilder()
              .setUsername(author.getUsername())
              .setBio(StringValue.of(author.getBio()))
              .setImage(StringValue.of(author.getImage()))
              .setFollowing(BoolValue.of(
                  Strings.isNullOrEmpty(userId) ? false : userDao.isFollowingUser(userId, author.getId()))))
          .build();
  }
}
