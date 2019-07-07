package realworld.processors;

import realworld.proto.ListArticlesRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class ListArticlesRequestValidator implements GrpcRequestValidator<ListArticlesRequest> {

  @Override
  public void validate(ListArticlesRequest request) {
    if (request.getOffset().getValue() < 0) {
      throw ExceptionUtil.badRequest("offline should not be negative.");
    }
  }
}
