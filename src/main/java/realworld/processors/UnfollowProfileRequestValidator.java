package realworld.processors;

import realworld.proto.UnfollowProfileRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class UnfollowProfileRequestValidator implements GrpcRequestValidator<UnfollowProfileRequest> {

  @Override
  public void validate(UnfollowProfileRequest request) {
    if (request.getUsername().isEmpty()) {
      throw ExceptionUtil.badRequest("username is empty.");
    }
  }
}
