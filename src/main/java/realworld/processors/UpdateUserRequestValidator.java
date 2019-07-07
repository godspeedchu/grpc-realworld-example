package realworld.processors;

import realworld.proto.UpdateUserRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class UpdateUserRequestValidator implements GrpcRequestValidator<UpdateUserRequest> {

  @Override
  public void validate(UpdateUserRequest request) {
    if (!request.hasUser()) {
      throw ExceptionUtil.badRequest("user is empty.");
    }

    // TODO(godspeedchu): Check for other fields.
  }
}
