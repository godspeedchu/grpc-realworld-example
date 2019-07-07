package realworld.processors;

import realworld.proto.FollowProfileRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class FollowProfileRequestValidator implements GrpcRequestValidator<FollowProfileRequest> {

  @Override
  public void validate(FollowProfileRequest request) {
    if (request.getUsername().isEmpty()) {
      throw ExceptionUtil.badRequest("username is empty.");
    }
  }
}
