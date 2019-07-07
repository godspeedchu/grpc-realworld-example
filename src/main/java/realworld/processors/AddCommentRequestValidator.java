package realworld.processors;

import realworld.proto.AddCommentRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class AddCommentRequestValidator implements GrpcRequestValidator<AddCommentRequest> {

  @Override
  public void validate(AddCommentRequest request) {
    if (request.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }
    if (!request.hasComment()) {
      throw ExceptionUtil.badRequest("comment is empty.");
    }
    if (request.getComment().getBody().isEmpty()) {
      throw ExceptionUtil.badRequest("comment.body is empty.");
    }
  }
}
