package realworld.api;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import realworld.core.auth.DummyJwtUtil;
import realworld.core.dao.UserDao;
import realworld.infrastructure.grpc.GrpcApi;
import realworld.proto.RegisterRequest;
import realworld.proto.User;
import realworld.proto.UserResponse;
import realworld.proto.internal.DbUser;

class RegisterUserGrpcApi implements GrpcApi<RegisterRequest, UserResponse> {

  private final UserDao userDao;

  @Inject
  RegisterUserGrpcApi(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<UserResponse> execute(RegisterRequest request) {
    User user = request.getUser();
    DbUser dbUser = userDao.createUser(DbUser.newBuilder()
      .setEmail(user.getEmail())
      .setUsername(user.getUsername())
      .setPasswordHash(hashFunc(user.getPassword()))
      .setBio(user.getBio())
      .setImage(user.getImage())
      .build());
    return Futures.immediateFuture(UserResponse.newBuilder()
        .setUser(User.newBuilder()
          .setEmail(dbUser.getEmail())
          .setToken(DummyJwtUtil.toToken(dbUser.getId()))
          .setUsername(dbUser.getUsername())
          .setBio(dbUser.getBio())
          .setImage(dbUser.getImage()))
        .build());
  }

  // TODO(godspeedchu): Move to util.
  private static String hashFunc(String raw) {
    return raw;
  }
}
