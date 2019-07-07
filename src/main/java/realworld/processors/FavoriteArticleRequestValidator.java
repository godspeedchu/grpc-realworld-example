package realworld.processors;

import realworld.proto.FavoriteArticleRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class FavoriteArticleRequestValidator implements GrpcRequestValidator<FavoriteArticleRequest> {

  @Override
  public void validate(FavoriteArticleRequest request) {
    if (request.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }
  }
}
