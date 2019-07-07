package realworld.processors;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import java.util.Optional;
import realworld.core.auth.Constants;
import realworld.core.dao.ArticleDao;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.GetArticleRequest;
import realworld.proto.Article;
import realworld.proto.ArticleResponse;
import realworld.proto.Profile;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbUser;

class GetArticleRequestProcessor implements GrpcRequestProcessor<GetArticleRequest, ArticleResponse> {

  private final UserDao userDao;
  private final ArticleDao articleDao;

  @Inject
  GetArticleRequestProcessor(UserDao userDao, ArticleDao articleDao) {
    this.userDao = userDao;
    this.articleDao = articleDao;
  }

  @Override
  public ListenableFuture<ArticleResponse> execute(GetArticleRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    Optional<DbArticle> dbArticle = articleDao.getArticleBySlug(request.getSlug());
    if (!dbArticle.isPresent()) {
      throw ExceptionUtil.notFound("article not found.");
    }
    Optional<DbUser> author = userDao.getUser(dbArticle.get().getAuthorId());
    if (!author.isPresent()) {
      throw ExceptionUtil.notFound("article not found.");
    }
    return Futures.immediateFuture(ArticleResponse.newBuilder()
        .setArticle(toArticle(dbArticle.get(), author.get(), articleDao, userDao, userId))
        .build());
  }

  private static Article toArticle(
      DbArticle article, DbUser author, ArticleDao articleDao, UserDao userDao, String userId) {
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
