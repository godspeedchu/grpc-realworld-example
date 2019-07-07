package realworld.api;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import java.util.Optional;
import realworld.core.auth.DummyJwtUtil;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.NotFoundException;
import realworld.infrastructure.grpc.GrpcApi;
import realworld.proto.LoginRequest;
import realworld.proto.User;
import realworld.proto.UserResponse;
import realworld.proto.internal.DbUser;

class LoginUserGrpcApi implements GrpcApi<LoginRequest, UserResponse> {

  private final UserDao userDao;

  @Inject
  LoginUserGrpcApi(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<UserResponse> execute(LoginRequest request) {
    Optional<DbUser> userOptional = userDao.getUserByEmail(request.getUser().getEmail());
    if (!userOptional.isPresent()) {
      throw new NotFoundException("user not found.");
    }
    DbUser user = userOptional.get();
    if (user.getPasswordHash().equals(hashFunc(request.getUser().getPassword()))) {
      return Futures.immediateFuture(UserResponse.newBuilder()
          .setUser(User.newBuilder()
            .setEmail(user.getEmail())
            .setToken(DummyJwtUtil.toToken(user.getId()))
            .setUsername(user.getUsername())
            .setBio(user.getBio())
            .setImage(user.getImage()))
          .build());
    } else {
      throw new NotFoundException("incorrect email or password.");
    }
  }

  // TODO(godspeedchu): Move to util.
  private static String hashFunc(String raw) {
    return raw;
  }
}
