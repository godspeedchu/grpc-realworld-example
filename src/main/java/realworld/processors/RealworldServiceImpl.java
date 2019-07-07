package realworld.processors;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.protobuf.Empty;
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
import realworld.proto.RealworldServiceGrpc;
import realworld.proto.RegisterRequest;
import realworld.proto.TagsResponse;
import realworld.proto.UnfavoriteArticleRequest;
import realworld.proto.UnfollowProfileRequest;
import realworld.proto.UpdateArticleRequest;
import realworld.proto.UpdateUserRequest;
import realworld.proto.UserResponse;
import realworld.infrastructure.grpc.GrpcMethodHandler;
import io.grpc.stub.StreamObserver;

final class RealworldServiceImpl extends RealworldServiceGrpc.RealworldServiceImplBase {

  private final Provider<GrpcMethodHandler<LoginRequest, UserResponse>>
    loginUserHandlerProvider;
  private final Provider<GrpcMethodHandler<RegisterRequest, UserResponse>>
    registerUserHandlerProvider;
  private final Provider<GrpcMethodHandler<Empty, UserResponse>>
    getUserHandlerProvider;
  private final Provider<GrpcMethodHandler<UpdateUserRequest, UserResponse>>
    updateUserHandlerProvider;
  private final Provider<GrpcMethodHandler<GetProfileRequest, ProfileResponse>>
    getProfileHandlerProvider;
  private final Provider<GrpcMethodHandler<FollowProfileRequest, ProfileResponse>>
    followProfileHandlerProvider;
  private final Provider<GrpcMethodHandler<UnfollowProfileRequest, ProfileResponse>>
    unfollowProfileHandlerProvider;
  private final Provider<GrpcMethodHandler<CreateArticleRequest, ArticleResponse>>
    createArticleHandlerProvider;
  private final Provider<GrpcMethodHandler<GetArticleRequest, ArticleResponse>>
    getArticleHandlerProvider;
  private final Provider<GrpcMethodHandler<ListArticlesRequest, ArticlesResponse>>
    listArticlesHandlerProvider;
  private final Provider<GrpcMethodHandler<UpdateArticleRequest, ArticleResponse>>
    updateArticleHandlerProvider;
  private final Provider<GrpcMethodHandler<FavoriteArticleRequest, ArticleResponse>>
    favoriteArticleHandlerProvider;
  private final Provider<GrpcMethodHandler<UnfavoriteArticleRequest, ArticleResponse>>
    unfavoriteArticleHandlerProvider;
  private final Provider<GrpcMethodHandler<FeedArticlesRequest, ArticlesResponse>>
    feedArticlesHandlerProvider;
  private final Provider<GrpcMethodHandler<AddCommentRequest, CommentResponse>>
    addCommentHandlerProvider;
  private final Provider<GrpcMethodHandler<ListCommentsRequest, CommentsResponse>>
    listCommentsHandlerProvider;
  private final Provider<GrpcMethodHandler<DeleteCommentRequest, Empty>>
    deleteCommentHandlerProvider;
  private final Provider<GrpcMethodHandler<ListTagsRequest, TagsResponse>>
    listTagsHandlerProvider;

  @Inject
  RealworldServiceImpl(
      Provider<GrpcMethodHandler<LoginRequest, UserResponse>> loginUserHandlerProvider,
      Provider<GrpcMethodHandler<RegisterRequest, UserResponse>> registerUserHandlerProvider,
      Provider<GrpcMethodHandler<Empty, UserResponse>> getUserHandlerProvider,
      Provider<GrpcMethodHandler<UpdateUserRequest, UserResponse>> updateUserHandlerProvider,
      Provider<GrpcMethodHandler<GetProfileRequest, ProfileResponse>> getProfileHandlerProvider,
      Provider<GrpcMethodHandler<FollowProfileRequest, ProfileResponse>> followProfileHandlerProvider,
      Provider<GrpcMethodHandler<UnfollowProfileRequest, ProfileResponse>> unfollowProfileHandlerProvider,
      Provider<GrpcMethodHandler<CreateArticleRequest, ArticleResponse>> createArticleHandlerProvider,
      Provider<GrpcMethodHandler<GetArticleRequest, ArticleResponse>> getArticleHandlerProvider,
      Provider<GrpcMethodHandler<ListArticlesRequest, ArticlesResponse>> listArticlesHandlerProvider,
      Provider<GrpcMethodHandler<UpdateArticleRequest, ArticleResponse>> updateArticleHandlerProvider,
      Provider<GrpcMethodHandler<FavoriteArticleRequest, ArticleResponse>> favoriteArticleHandlerProvider,
      Provider<GrpcMethodHandler<UnfavoriteArticleRequest, ArticleResponse>> unfavoriteArticleHandlerProvider,
      Provider<GrpcMethodHandler<FeedArticlesRequest, ArticlesResponse>> feedArticlesHandlerProvider,
      Provider<GrpcMethodHandler<AddCommentRequest, CommentResponse>> addCommentHandlerProvider,
      Provider<GrpcMethodHandler<ListCommentsRequest, CommentsResponse>> listCommentsHandlerProvider,
      Provider<GrpcMethodHandler<DeleteCommentRequest, Empty>> deleteCommentHandlerProvider,
      Provider<GrpcMethodHandler<ListTagsRequest, TagsResponse>> listTagsHandlerProvider) {
    this.loginUserHandlerProvider = loginUserHandlerProvider;
    this.registerUserHandlerProvider = registerUserHandlerProvider;
    this.getUserHandlerProvider = getUserHandlerProvider;
    this.updateUserHandlerProvider = updateUserHandlerProvider;
    this.getProfileHandlerProvider = getProfileHandlerProvider;
    this.followProfileHandlerProvider = followProfileHandlerProvider;
    this.unfollowProfileHandlerProvider = unfollowProfileHandlerProvider;
    this.createArticleHandlerProvider = createArticleHandlerProvider;
    this.getArticleHandlerProvider = getArticleHandlerProvider;
    this.listArticlesHandlerProvider = listArticlesHandlerProvider;
    this.updateArticleHandlerProvider = updateArticleHandlerProvider;
    this.favoriteArticleHandlerProvider = favoriteArticleHandlerProvider;
    this.unfavoriteArticleHandlerProvider = unfavoriteArticleHandlerProvider;
    this.feedArticlesHandlerProvider = feedArticlesHandlerProvider;
    this.addCommentHandlerProvider = addCommentHandlerProvider;
    this.listCommentsHandlerProvider = listCommentsHandlerProvider;
    this.deleteCommentHandlerProvider = deleteCommentHandlerProvider;
    this.listTagsHandlerProvider = listTagsHandlerProvider;
  }

  @Override
  public void loginUser(LoginRequest request, StreamObserver<UserResponse> response) {
    loginUserHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void registerUser(RegisterRequest request, StreamObserver<UserResponse> response) {
    registerUserHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void getUser(Empty request, StreamObserver<UserResponse> response) {
    getUserHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void updateUser(UpdateUserRequest request, StreamObserver<UserResponse> response) {
    updateUserHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void getProfile(GetProfileRequest request, StreamObserver<ProfileResponse> response) {
    getProfileHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void followProfile(FollowProfileRequest request, StreamObserver<ProfileResponse> response) {
    followProfileHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void unfollowProfile(UnfollowProfileRequest request, StreamObserver<ProfileResponse> response) {
    unfollowProfileHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void createArticle(CreateArticleRequest request, StreamObserver<ArticleResponse> response) {
    createArticleHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void getArticle(GetArticleRequest request, StreamObserver<ArticleResponse> response) {
    getArticleHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void listArticles(ListArticlesRequest request, StreamObserver<ArticlesResponse> response) {
    listArticlesHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void updateArticle(UpdateArticleRequest request, StreamObserver<ArticleResponse> response) {
    updateArticleHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void favoriteArticle(FavoriteArticleRequest request, StreamObserver<ArticleResponse> response) {
    favoriteArticleHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void unfavoriteArticle(UnfavoriteArticleRequest request, StreamObserver<ArticleResponse> response) {
    unfavoriteArticleHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void feedArticles(FeedArticlesRequest request, StreamObserver<ArticlesResponse> response) {
    feedArticlesHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void addComment(AddCommentRequest request, StreamObserver<CommentResponse> response) {
    addCommentHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void listComments(ListCommentsRequest request, StreamObserver<CommentsResponse> response) {
    listCommentsHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void deleteComment(DeleteCommentRequest request, StreamObserver<Empty> response) {
    deleteCommentHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void listTags(ListTagsRequest request, StreamObserver<TagsResponse> response) {
    listTagsHandlerProvider.get().handleGrpcMethod(request, response);
  }
}
