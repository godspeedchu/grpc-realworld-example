package realworld.processors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.Empty;
import java.util.Optional;
import realworld.core.auth.Constants;
import realworld.core.dao.ArticleDao;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.DeleteCommentRequest;
import realworld.proto.Article;
import realworld.proto.ArticleResponse;
import realworld.proto.Profile;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbUser;

class DeleteCommentRequestProcessor implements GrpcRequestProcessor<DeleteCommentRequest, Empty> {

  private final UserDao userDao;
  private final ArticleDao articleDao;

  @Inject
  DeleteCommentRequestProcessor(UserDao userDao, ArticleDao articleDao) {
    this.userDao = userDao;
    this.articleDao = articleDao;
  }

  @Override
  public ListenableFuture<Empty> execute(DeleteCommentRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    if (userId.isEmpty()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
    Optional<DbUser> callerOptional = userDao.getUser(userId);
    if (!callerOptional.isPresent()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }

    Optional<DbArticle> dbArticle = articleDao.getArticleBySlug(request.getSlug());
    if (!dbArticle.isPresent()) {
      throw ExceptionUtil.notFound("article not found.");
    } else if (!dbArticle.get().getAuthorId().equals(userId)) {
      throw ExceptionUtil.permissionDenied("delete article not allowed.");
    }
    articleDao.deleteComment(dbArticle.get().getId(), request.getId());
    return Futures.immediateFuture(Empty.getDefaultInstance());
  }
}
