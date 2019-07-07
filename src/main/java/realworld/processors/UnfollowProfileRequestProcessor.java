package realworld.processors;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import java.util.Optional;
import realworld.core.auth.Constants;
import realworld.core.auth.DummyJwtUtil;
import realworld.core.dao.UserDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.Profile;
import realworld.proto.ProfileResponse;
import realworld.proto.UnfollowProfileRequest;
import realworld.proto.internal.DbUser;

class UnfollowProfileRequestProcessor implements GrpcRequestProcessor<UnfollowProfileRequest, ProfileResponse> {

  private final UserDao userDao;

  @Inject
  UnfollowProfileRequestProcessor(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<ProfileResponse> execute(UnfollowProfileRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    if (userId.isEmpty()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
    Optional<DbUser> callerOptional = userDao.getUser(userId);
    if (!callerOptional.isPresent()) {
      throw ExceptionUtil.unauthorized("unauthorized request.");
    }
   
    String username = request.getUsername();
    Optional<DbUser> userOptional = userDao.getUserByUsername(username);
    if (!userOptional.isPresent()) {
      throw ExceptionUtil.notFound("profile not found.");
    }
    DbUser user = userOptional.get();
    userDao.unfollowUser(userId, user.getId());
    return Futures.immediateFuture(ProfileResponse.newBuilder()
        .setProfile(Profile.newBuilder()
          .setUsername(user.getUsername())
          .setBio(StringValue.of(user.getBio()))
          .setImage(StringValue.of(user.getImage()))
          .setFollowing(BoolValue.of(userDao.isFollowingUser(userId, user.getId()))))
        .build());
  }
}
