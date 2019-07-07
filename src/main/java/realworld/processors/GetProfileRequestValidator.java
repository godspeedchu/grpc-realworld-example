package realworld.processors;

import realworld.proto.GetProfileRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class GetProfileRequestValidator implements GrpcRequestValidator<GetProfileRequest> {

  @Override
  public void validate(GetProfileRequest request) {
    if (request.getUsername().isEmpty()) {
      throw ExceptionUtil.badRequest("username is empty.");
    }
  }
}
