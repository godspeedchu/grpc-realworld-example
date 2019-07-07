package realworld.api;

import com.google.inject.Inject;
import com.google.inject.Provider;
import realworld.proto.LoginRequest;
import realworld.proto.RealworldServiceGrpc;
import realworld.proto.RegisterRequest;
import realworld.proto.UserResponse;
import realworld.infrastructure.grpc.GrpcMethodHandler;
import io.grpc.stub.StreamObserver;

final class RealworldServiceImpl extends RealworldServiceGrpc.RealworldServiceImplBase {

  private final Provider<GrpcMethodHandler<LoginRequest, UserResponse>>
    loginUserHandlerProvider;
  private final Provider<GrpcMethodHandler<RegisterRequest, UserResponse>>
    registerUserHandlerProvider;

  @Inject
  RealworldServiceImpl(
      Provider<GrpcMethodHandler<LoginRequest, UserResponse>> loginUserHandlerProvider,
      Provider<GrpcMethodHandler<RegisterRequest, UserResponse>> registerUserHandlerProvider) {
    this.loginUserHandlerProvider = loginUserHandlerProvider;
    this.registerUserHandlerProvider = registerUserHandlerProvider;
  }

  @Override
  public void loginUser(LoginRequest request, StreamObserver<UserResponse> response) {
    loginUserHandlerProvider.get().handleGrpcMethod(request, response);
  }

  @Override
  public void registerUser(RegisterRequest request, StreamObserver<UserResponse> response) {
    registerUserHandlerProvider.get().handleGrpcMethod(request, response);
  }
}
