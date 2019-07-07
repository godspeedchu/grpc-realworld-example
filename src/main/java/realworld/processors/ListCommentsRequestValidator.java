package realworld.processors;

import realworld.proto.ListCommentsRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class ListCommentsRequestValidator implements GrpcRequestValidator<ListCommentsRequest> {

  @Override
  public void validate(ListCommentsRequest request) {
    if (request.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }
  }
}
