package realworld.processors;

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
import realworld.proto.CreateArticleRequest;
import realworld.proto.Article;
import realworld.proto.ArticleResponse;
import realworld.proto.Profile;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbUser;

class CreateArticleRequestProcessor implements GrpcRequestProcessor<CreateArticleRequest, ArticleResponse> {

  private final UserDao userDao;
  private final ArticleDao articleDao;

  @Inject
  CreateArticleRequestProcessor(UserDao userDao, ArticleDao articleDao) {
    this.userDao = userDao;
    this.articleDao = articleDao;
  }

  @Override
  public ListenableFuture<ArticleResponse> execute(CreateArticleRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    if (userId.isEmpty()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
    Optional<DbUser> callerOptional = userDao.getUser(userId);
    if (!callerOptional.isPresent()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }

    Article article = request.getArticle();
    DbArticle dbArticle = articleDao.createArticle(DbArticle.newBuilder()
      .setTitle(article.getTitle())
      .setDescription(article.getDescription())
      .setBody(article.getBody())
      .addAllTagList(article.getTagListList())
      .setAuthorId(userId)
      .build());
    return Futures.immediateFuture(ArticleResponse.newBuilder()
        .setArticle(toArticle(dbArticle, callerOptional.get(), articleDao))
        .build());
  }

  private static Article toArticle(DbArticle article, DbUser author, ArticleDao articleDao) {
    return Article.newBuilder()
          .setSlug(article.getSlug())
          .setTitle(article.getTitle())
          .setDescription(article.getDescription())
          .setBody(article.getBody())
          .addAllTagList(article.getTagListList())
          .setCreatedAt(article.getCreatedAt())
          .setUpdatedAt(article.getUpdatedAt())
          .setFavorited(BoolValue.of(false))
          .setFavoritesCount(Int32Value.of(articleDao.getArticleFavoriteCount(article.getId())))
          .setAuthor(Profile.newBuilder()
              .setUsername(author.getUsername())
              .setBio(StringValue.of(author.getBio()))
              .setImage(StringValue.of(author.getImage()))
              .setFollowing(BoolValue.of(false)))
          .build();
  }
}
