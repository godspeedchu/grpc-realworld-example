package realworld.processors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import java.util.Optional;
import realworld.core.auth.Constants;
import realworld.core.auth.DummyJwtUtil;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.User;
import realworld.proto.UserResponse;
import realworld.proto.internal.DbUser;

class GetUserRequestProcessor implements GrpcRequestProcessor<Empty, UserResponse> {

  private final UserDao userDao;

  @Inject
  GetUserRequestProcessor(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<UserResponse> execute(Empty request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    if (userId.isEmpty()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
    Optional<DbUser> userOptional = userDao.getUser(userId);
    if (!userOptional.isPresent()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
    DbUser user = userOptional.get();
    return Futures.immediateFuture(UserResponse.newBuilder()
        .setUser(User.newBuilder()
          .setEmail(StringValue.of(user.getEmail()))
          .setToken(DummyJwtUtil.toToken(user.getId()))
          .setUsername(StringValue.of(user.getUsername()))
          .setBio(StringValue.of(user.getBio()))
          .setImage(StringValue.of(user.getImage())))
        .build());
  }
}
