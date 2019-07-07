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
import realworld.proto.AddCommentRequest;
import realworld.proto.Comment;
import realworld.proto.CommentResponse;
import realworld.proto.Profile;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbComment;
import realworld.proto.internal.DbUser;

class AddCommentRequestProcessor implements GrpcRequestProcessor<AddCommentRequest, CommentResponse> {

  private final UserDao userDao;
  private final ArticleDao articleDao;

  @Inject
  AddCommentRequestProcessor(UserDao userDao, ArticleDao articleDao) {
    this.userDao = userDao;
    this.articleDao = articleDao;
  }

  @Override
  public ListenableFuture<CommentResponse> execute(AddCommentRequest request) {
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
      throw ExceptionUtil.badRequest("article not found.");
    }
    DbComment dbComment = articleDao.createComment(dbArticle.get().getId(), DbComment.newBuilder()
      .setBody(request.getComment().getBody())
      .setAuthorId(userId)
      .build());
    return Futures.immediateFuture(CommentResponse.newBuilder()
        .setComment(toComment(dbComment, callerOptional.get()))
        .build());
  }

  private static Comment toComment(DbComment comment, DbUser author) {
    return Comment.newBuilder()
          .setId(comment.getId())
          .setBody(comment.getBody())
          .setCreatedAt(comment.getCreatedAt())
          .setUpdatedAt(comment.getUpdatedAt())
          .setAuthor(Profile.newBuilder()
              .setUsername(author.getUsername())
              .setBio(StringValue.of(author.getBio()))
              .setImage(StringValue.of(author.getImage()))
              .setFollowing(BoolValue.of(false)))
          .build();
  }
}
