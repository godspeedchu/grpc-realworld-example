package realworld.processors;

import realworld.proto.CreateArticleRequest;
import realworld.proto.Article;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestValidator;

class CreateArticleRequestValidator implements GrpcRequestValidator<CreateArticleRequest> {

  @Override
  public void validate(CreateArticleRequest request) {
    if (!request.hasArticle()) {
      throw ExceptionUtil.badRequest("article is empty.");
    }

    Article article = request.getArticle();
    if (article.getTitle().isEmpty()) {
      throw ExceptionUtil.badRequest("title is empty.");
    }
    if (article.getDescription().isEmpty()) {
      throw ExceptionUtil.badRequest("description is empty.");
    }
    if (article.getBody().isEmpty()) {
      throw ExceptionUtil.badRequest("body is empty.");
    }

    // TODO(godspeedchu): Check for other fields.
  }
}
