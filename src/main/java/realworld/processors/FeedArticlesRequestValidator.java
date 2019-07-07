package realworld.processors;

import realworld.proto.FeedArticlesRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class FeedArticlesRequestValidator implements GrpcRequestValidator<FeedArticlesRequest> {

  @Override
  public void validate(FeedArticlesRequest request) {
    if (request.getOffset().getValue() < 0) {
      throw ExceptionUtil.badRequest("offline should not be negative.");
    }
  }
}
