package realworld.processors;

import realworld.proto.DeleteCommentRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class DeleteCommentRequestValidator implements GrpcRequestValidator<DeleteCommentRequest> {

  @Override
  public void validate(DeleteCommentRequest request) {
    if (request.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }

    if (request.getId().isEmpty()) {
      throw ExceptionUtil.badRequest("id is empty.");
    }
  }
}
