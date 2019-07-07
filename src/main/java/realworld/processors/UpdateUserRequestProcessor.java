package realworld.processors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.StringValue;
import java.util.Optional;
import realworld.core.auth.Constants;
import realworld.core.auth.DummyJwtUtil;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.UpdateUserRequest;
import realworld.proto.User;
import realworld.proto.UserResponse;
import realworld.proto.internal.DbUser;

class UpdateUserRequestProcessor implements GrpcRequestProcessor<UpdateUserRequest, UserResponse> {

  private final UserDao userDao;

  @Inject
  UpdateUserRequestProcessor(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<UserResponse> execute(UpdateUserRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    if (userId.isEmpty()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
    Optional<DbUser> userOptional = userDao.getUser(userId);
    if (!userOptional.isPresent()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }

    DbUser.Builder builder = userOptional.get().toBuilder();
    User user = request.getUser();
    if (user.hasEmail()) {
      builder.setEmail(user.getEmail().getValue());
    }
    if (user.hasUsername()) {
      builder.setUsername(user.getUsername().getValue());
    }
    if (user.hasPassword()) {
      builder.setPasswordHash(hashFunc(user.getPassword().getValue()));
    }
    if (user.hasBio()) {
      builder.setBio(user.getBio().getValue());
    }
    if (user.hasImage()) {
      builder.setImage(user.getImage().getValue());
    }
    DbUser updatedUser = userDao.updateUser(builder.build());
    return Futures.immediateFuture(UserResponse.newBuilder()
        .setUser(User.newBuilder()
          .setEmail(StringValue.of(updatedUser.getEmail()))
          .setToken(DummyJwtUtil.toToken(updatedUser.getId()))
          .setUsername(StringValue.of(updatedUser.getUsername()))
          .setBio(StringValue.of(updatedUser.getBio()))
          .setImage(StringValue.of(updatedUser.getImage())))
        .build());
  }

  // TODO(godspeedchu): Move to util.
  private static String hashFunc(String raw) {
    return raw;
  }
}
