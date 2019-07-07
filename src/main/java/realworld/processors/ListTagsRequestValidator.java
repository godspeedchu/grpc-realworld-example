package realworld.processors;

import realworld.proto.ListTagsRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class ListTagsRequestValidator implements GrpcRequestValidator<ListTagsRequest> {

  @Override
  public void validate(ListTagsRequest request) {}
}
