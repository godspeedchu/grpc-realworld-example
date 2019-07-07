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
import realworld.proto.GetProfileRequest;
import realworld.proto.Profile;
import realworld.proto.ProfileResponse;
import realworld.proto.internal.DbUser;

class GetProfileRequestProcessor implements GrpcRequestProcessor<GetProfileRequest, ProfileResponse> {

  private final UserDao userDao;

  @Inject
  GetProfileRequestProcessor(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListenableFuture<ProfileResponse> execute(GetProfileRequest request) {
    String userId = Constants.CONTEXT_USER_ID_KEY.get();
    String username = request.getUsername();
    Optional<DbUser> userOptional = userDao.getUserByUsername(username);
    if (!userOptional.isPresent()) {
      throw ExceptionUtil.notFound("profile not found.");
    }
    DbUser user = userOptional.get();
    Profile.Builder builder = Profile.newBuilder()
      .setUsername(user.getUsername())
      .setBio(StringValue.of(user.getBio()))
      .setImage(StringValue.of(user.getImage()));
    if (!Strings.isNullOrEmpty(userId)) {
      builder.setFollowing(BoolValue.of(userDao.isFollowingUser(userId, user.getId())));
    }
    return Futures.immediateFuture(ProfileResponse.newBuilder()
        .setProfile(builder.build())
        .build());
  }
}
