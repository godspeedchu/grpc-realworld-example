package realworld.api;

import realworld.proto.RegisterRequest;
import realworld.proto.User;
import realworld.infrastructure.exceptions.BadRequestException;
import realworld.infrastructure.grpc.GrpcRequestValidator;
import io.grpc.StatusException;

class RegisterUserRequestValidator implements GrpcRequestValidator<RegisterRequest> {

  @Override
  public void validate(RegisterRequest request) throws StatusException {
    if (!request.hasUser()) {
      throw new BadRequestException("user is empty.");
    }

    User user = request.getUser();
    if (user.getEmail().isEmpty()) {
      throw new BadRequestException("email is empty.");
    }
    if (user.getUsername().isEmpty()) {
      throw new BadRequestException("username is empty.");
    }
    if (user.getPassword().isEmpty()) {
      throw new BadRequestException("password is empty.");
    }

    // TODO(godspeedchu): Check for other fields.
  }
}
