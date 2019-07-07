package realworld.processors;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import java.util.logging.Logger;
import realworld.core.dao.ArticleDao;
import realworld.infrastructure.exceptions.ExceptionUtil;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.proto.ListTagsRequest;
import realworld.proto.TagsResponse;

class ListTagsRequestProcessor implements GrpcRequestProcessor<ListTagsRequest, TagsResponse> {

  private static final Logger logger = Logger.getLogger(ListTagsRequestProcessor.class.getName());

  private final ArticleDao articleDao;

  @Inject
  ListTagsRequestProcessor(ArticleDao articleDao) {
    this.articleDao = articleDao;
  }

  @Override
  public ListenableFuture<TagsResponse> execute(ListTagsRequest request) {
    return Futures.immediateFuture(TagsResponse.newBuilder()
        .addAllTags(articleDao.listTags())
        .build());
  }
}
