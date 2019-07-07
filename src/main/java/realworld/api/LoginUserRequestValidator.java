package realworld.api;

import realworld.proto.LoginRequest;
import realworld.proto.User;
import realworld.infrastructure.exceptions.BadRequestException;
import realworld.infrastructure.grpc.GrpcRequestValidator;
import io.grpc.StatusException;

class LoginUserRequestValidator implements GrpcRequestValidator<LoginRequest> {

  @Override
  public void validate(LoginRequest request) throws StatusException {
    if (!request.hasUser()) {
      throw new BadRequestException("user is empty.");
    }

    User user = request.getUser();
    if (user.getEmail().isEmpty()
        || user.getPassword().isEmpty()) {
      throw new BadRequestException("email or password is empty.");
    }

    // TODO(godspeedchu): Check for other fields.
  }
}
