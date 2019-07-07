package realworld.processors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.protobuf.Empty;
import realworld.core.auth.ServerAuthInterceptor;
import realworld.infrastructure.concurrent.Annotations.NonBlockingThreadPool;
import realworld.infrastructure.grpc.GrpcMethodHandler;
import realworld.infrastructure.grpc.GrpcRequestProcessor;
import realworld.infrastructure.grpc.GrpcRequestValidator;
import realworld.infrastructure.grpc.GrpcServerInterceptorModule;
import realworld.infrastructure.grpc.GrpcServerModule;
import realworld.proto.AddCommentRequest;
import realworld.proto.ArticleResponse;
import realworld.proto.ArticlesResponse;
import realworld.proto.CommentResponse;
import realworld.proto.CommentsResponse;
import realworld.proto.CreateArticleRequest;
import realworld.proto.DeleteArticleRequest;
import realworld.proto.DeleteCommentRequest;
import realworld.proto.FavoriteArticleRequest;
import realworld.proto.FeedArticlesRequest;
import realworld.proto.FollowProfileRequest;
import realworld.proto.GetArticleRequest;
import realworld.proto.GetProfileRequest;
import realworld.proto.ListArticlesRequest;
import realworld.proto.ListCommentsRequest;
import realworld.proto.ListTagsRequest;
import realworld.proto.LoginRequest;
import realworld.proto.ProfileResponse;
import realworld.proto.RegisterRequest;
import realworld.proto.TagsResponse;
import realworld.proto.UnfavoriteArticleRequest;
import realworld.proto.UnfollowProfileRequest;
import realworld.proto.UpdateArticleRequest;
import realworld.proto.UpdateUserRequest;
import realworld.proto.UserResponse;

public final class RealworldServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    GrpcServerModule.addServiceBinding(binder()).to(RealworldServiceImpl.class);
    GrpcServerInterceptorModule.addServerInterceptorBinding(binder())
      .to(ServerAuthInterceptor.class);
  }

  @Provides
  GrpcMethodHandler<LoginRequest, UserResponse> provideLoginUserMethodHandler(
    LoginUserRequestValidator validator,
    LoginUserRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<LoginRequest, UserResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<RegisterRequest, UserResponse> provideRegisterUserMethodHandler(
    RegisterUserRequestValidator validator,
    RegisterUserRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<RegisterRequest, UserResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<Empty, UserResponse> provideGetUserMethodHandler(
    GetUserRequestValidator validator,
    GetUserRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<Empty, UserResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<UpdateUserRequest, UserResponse> provideUpdateUserMethodHandler(
    UpdateUserRequestValidator validator,
    UpdateUserRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<UpdateUserRequest, UserResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<GetProfileRequest, ProfileResponse> provideGetProfileMethodHandler(
    GetProfileRequestValidator validator,
    GetProfileRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<GetProfileRequest, ProfileResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<FollowProfileRequest, ProfileResponse> provideFollowProfileMethodHandler(
    FollowProfileRequestValidator validator,
    FollowProfileRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<FollowProfileRequest, ProfileResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<UnfollowProfileRequest, ProfileResponse> provideUnfollowProfileMethodHandler(
    UnfollowProfileRequestValidator validator,
    UnfollowProfileRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<UnfollowProfileRequest, ProfileResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<CreateArticleRequest, ArticleResponse> provideCreateArticleMethodHandler(
    CreateArticleRequestValidator validator,
    CreateArticleRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<CreateArticleRequest, ArticleResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<GetArticleRequest, ArticleResponse> provideGetArticleMethodHandler(
    GetArticleRequestValidator validator,
    GetArticleRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<GetArticleRequest, ArticleResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<ListArticlesRequest, ArticlesResponse> provideListArticlesMethodHandler(
    ListArticlesRequestValidator validator,
    ListArticlesRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<ListArticlesRequest, ArticlesResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<UpdateArticleRequest, ArticleResponse> provideUpdateArticleMethodHandler(
    UpdateArticleRequestValidator validator,
    UpdateArticleRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<UpdateArticleRequest, ArticleResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<FavoriteArticleRequest, ArticleResponse> provideFavoriteArticleMethodHandler(
    FavoriteArticleRequestValidator validator,
    FavoriteArticleRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<FavoriteArticleRequest, ArticleResponse>(validator, processor, executor);
  }
  
  @Provides
  GrpcMethodHandler<UnfavoriteArticleRequest, ArticleResponse> provideUnfavoriteArticleMethodHandler(
    UnfavoriteArticleRequestValidator validator,
    UnfavoriteArticleRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<UnfavoriteArticleRequest, ArticleResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<FeedArticlesRequest, ArticlesResponse> provideFeedArticlesMethodHandler(
    FeedArticlesRequestValidator validator,
    FeedArticlesRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<FeedArticlesRequest, ArticlesResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<AddCommentRequest, CommentResponse> provideAddCommentMethodHandler(
    AddCommentRequestValidator validator,
    AddCommentRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<AddCommentRequest, CommentResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<ListCommentsRequest, CommentsResponse> provideListCommentsMethodHandler(
    ListCommentsRequestValidator validator,
    ListCommentsRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<ListCommentsRequest, CommentsResponse>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<DeleteCommentRequest, Empty> provideDeleteCommentMethodHandler(
    DeleteCommentRequestValidator validator,
    DeleteCommentRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<DeleteCommentRequest, Empty>(validator, processor, executor);
  }

  @Provides
  GrpcMethodHandler<ListTagsRequest, TagsResponse> provideListTagsMethodHandler(
    ListTagsRequestValidator validator,
    ListTagsRequestProcessor processor,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<ListTagsRequest, TagsResponse>(validator, processor, executor);
  }
}
