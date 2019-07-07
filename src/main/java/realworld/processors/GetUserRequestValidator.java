package realworld.processors;

import com.google.protobuf.Empty;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class GetUserRequestValidator implements GrpcRequestValidator<Empty> {

  @Override
  public void validate(Empty request) {}
}
