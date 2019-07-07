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
import realworld.proto.ListCommentsRequest;
import realworld.proto.Comment;
import realworld.proto.CommentsResponse;
import realworld.proto.Profile;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbComment;
import realworld.proto.internal.DbUser;

class ListCommentsRequestProcessor implements GrpcRequestProcessor<ListCommentsRequest, CommentsResponse> {

  private static final Logger logger = Logger.getLogger(ListCommentsRequestProcessor.class.getName());

  private final UserDao userDao;
  private final ArticleDao articleDao;

  @Inject
  ListCommentsRequestProcessor(UserDao userDao, ArticleDao articleDao) {
    this.userDao = userDao;
    this.articleDao = articleDao;
  }

  @Override
  public ListenableFuture<CommentsResponse> execute(ListCommentsRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();

    Optional<DbArticle> dbArticle = articleDao.getArticleBySlug(request.getSlug());
    if (!dbArticle.isPresent()) {
      throw ExceptionUtil.badRequest("article not found.");
    }

    List<Comment> comments = articleDao.listComments(dbArticle.get().getId())
      .stream()
      .map(comment -> toComment(comment, articleDao, userDao, userId))
      .collect(Collectors.toList());
    return Futures.immediateFuture(CommentsResponse.newBuilder()
        .addAllComments(comments)
        .build());
  }

  private static Comment toComment(
      DbComment comment, ArticleDao articleDao, UserDao userDao, String userId) {
    DbUser author = userDao.getUser(comment.getAuthorId()).get();
    return Comment.newBuilder()
          .setId(comment.getId())
          .setBody(comment.getBody())
          .setCreatedAt(comment.getCreatedAt())
          .setUpdatedAt(comment.getUpdatedAt())
          .setAuthor(Profile.newBuilder()
              .setUsername(author.getUsername())
              .setBio(StringValue.of(author.getBio()))
              .setImage(StringValue.of(author.getImage()))
              .setFollowing(BoolValue.of(
                  Strings.isNullOrEmpty(userId) ? false : userDao.isFollowingUser(userId, author.getId()))))
          .build();
  }
}
