package realworld.processors;

import realworld.proto.LoginRequest;
import realworld.proto.User;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class LoginUserRequestValidator implements GrpcRequestValidator<LoginRequest> {

  @Override
  public void validate(LoginRequest request) {
    if (!request.hasUser()) {
      throw ExceptionUtil.badRequest("user is empty.");
    }

    User user = request.getUser();
    if (!user.hasEmail() || user.getEmail().getValue().isEmpty()) {
      throw ExceptionUtil.badRequest("email is empty.");
    }
    if (!user.hasPassword() || user.getPassword().getValue().isEmpty()) {
      throw ExceptionUtil.badRequest("password is empty.");
    }

    // TODO(godspeedchu): Check for other fields.
  }
}
