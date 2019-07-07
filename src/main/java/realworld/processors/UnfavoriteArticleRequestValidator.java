package realworld.processors;

import realworld.proto.UnfavoriteArticleRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class UnfavoriteArticleRequestValidator implements GrpcRequestValidator<UnfavoriteArticleRequest> {

  @Override
  public void validate(UnfavoriteArticleRequest request) {
    if (request.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }
  }
}
