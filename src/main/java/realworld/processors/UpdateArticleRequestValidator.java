package realworld.processors;

import realworld.proto.UpdateArticleRequest;
import realworld.proto.Article;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class UpdateArticleRequestValidator implements GrpcRequestValidator<UpdateArticleRequest> {

  @Override
  public void validate(UpdateArticleRequest request) {
    if (!request.hasArticle()) {
      throw ExceptionUtil.badRequest("article is empty.");
    }

    Article article = request.getArticle();
    if (article.getSlug().isEmpty()) {
      throw ExceptionUtil.badRequest("slug is empty.");
    }

    // TODO(godspeedchu): Check for other fields.
  }
}
