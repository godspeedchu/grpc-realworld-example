package realworld.processors;

import realworld.proto.Article;
import realworld.proto.GetArticleRequest;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class GetArticleRequestValidator implements GrpcRequestValidator<GetArticleRequest> {

  @Override
  public void validate(GetArticleRequest request) {
    if (request.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }
  }
}
