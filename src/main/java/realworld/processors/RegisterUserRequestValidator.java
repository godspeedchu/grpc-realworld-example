package realworld.processors;

import realworld.proto.RegisterRequest;
import realworld.proto.User;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class RegisterUserRequestValidator implements GrpcRequestValidator<RegisterRequest> {

  @Override
  public void validate(RegisterRequest request) {
    if (!request.hasUser()) {
      throw ExceptionUtil.badRequest("user is empty.");
    }

    User user = request.getUser();
    if (!user.hasEmail() || user.getEmail().getValue().isEmpty()) {
      throw ExceptionUtil.badRequest("email is empty.");
    }
    if (!user.hasUsername() || user.getUsername().getValue().isEmpty()) {
      throw ExceptionUtil.badRequest("username is empty.");
    }
    if (!user.hasPassword() || user.getPassword().getValue().isEmpty()) {
      throw ExceptionUtil.badRequest("password is empty.");
    }

    // TODO(godspeedchu): Check for other fields.
  }
}
