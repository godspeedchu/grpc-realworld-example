package realworld.processors;

import realworld.proto.DeleteArticleRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class DeleteArticleRequestValidator implements GrpcRequestValidator<DeleteArticleRequest> {

  @Override
  public void validate(DeleteArticleRequest request) {
    if (request.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }
  }
}
