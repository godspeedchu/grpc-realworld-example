package realworld.processors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.StringValue;
import java.util.Optional;
import realworld.core.auth.DummyJwtUtil;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.LoginRequest;
import realworld.proto.User;
import realworld.proto.UserResponse;
import realworld.proto.internal.DbUser;

class LoginUserRequestProcessor implements GrpcRequestProcessor<LoginRequest, UserResponse> {

  private final UserDao userDao;

  @Inject
  LoginUserRequestProcessor(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<UserResponse> execute(LoginRequest request) {
    Optional<DbUser> userOptional = userDao.getUserByEmail(request.getUser().getEmail().getValue());
    if (!userOptional.isPresent()) {
      throw ExceptionUtil.notFound("user not found.");
    }
    DbUser user = userOptional.get();
    if (user.getPasswordHash().equals(hashFunc(request.getUser().getPassword().getValue()))) {
      return Futures.immediateFuture(UserResponse.newBuilder()
          .setUser(User.newBuilder()
            .setEmail(StringValue.of(user.getEmail()))
            .setToken(DummyJwtUtil.toToken(user.getId()))
            .setUsername(StringValue.of(user.getUsername()))
            .setBio(StringValue.of(user.getBio()))
            .setImage(StringValue.of(user.getImage())))
          .build());
    } else {
      throw ExceptionUtil.notFound("incorrect email or password.");
    }
  }

  // TODO(godspeedchu): Move to util.
  private static String hashFunc(String raw) {
    return raw;
  }
}
