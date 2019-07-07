package realworld.infrastructure.grpc;

import com.google.protobuf.Message;

public interface GrpcRequestValidator<Request extends Message> {

  public void validate(Request request) throws Exception;
}
