package realworld.processors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.StringValue;
import realworld.core.auth.DummyJwtUtil;
import realworld.core.dao.UserDao;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.RegisterRequest;
import realworld.proto.User;
import realworld.proto.UserResponse;
import realworld.proto.internal.DbUser;

class RegisterUserRequestProcessor implements GrpcRequestProcessor<RegisterRequest, UserResponse> {

  private final UserDao userDao;

  @Inject
  RegisterUserRequestProcessor(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<UserResponse> execute(RegisterRequest request) {
    User user = request.getUser();
    DbUser dbUser = userDao.createUser(DbUser.newBuilder()
      .setEmail(user.getEmail().getValue())
      .setUsername(user.getUsername().getValue())
      .setPasswordHash(hashFunc(user.getPassword().getValue()))
      .setBio(user.getBio().getValue())
      .setImage(user.getImage().getValue())
      .build());
    return Futures.immediateFuture(UserResponse.newBuilder()
        .setUser(User.newBuilder()
          .setEmail(StringValue.of(dbUser.getEmail()))
          .setToken(DummyJwtUtil.toToken(dbUser.getId()))
          .setUsername(StringValue.of(dbUser.getUsername()))
          .setBio(StringValue.of(dbUser.getBio()))
          .setImage(StringValue.of(dbUser.getImage())))
        .build());
  }

  // TODO(godspeedchu): Move to util.
  private static String hashFunc(String raw) {
    return raw;
  }
}
