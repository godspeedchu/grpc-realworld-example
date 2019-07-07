package realworld.api;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import realworld.core.auth.ServerAuthInterceptor;
import realworld.infrastructure.concurrent.Annotations.NonBlockingThreadPool;
import realworld.infrastructure.grpc.GrpcApi;
import realworld.infrastructure.grpc.GrpcMethodHandler;
import realworld.infrastructure.grpc.GrpcRequestValidator;
import realworld.infrastructure.grpc.GrpcServerInterceptorModule;
import realworld.infrastructure.grpc.GrpcServerModule;
import realworld.proto.LoginRequest;
import realworld.proto.RegisterRequest;
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
    LoginUserGrpcApi api,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<LoginRequest, UserResponse>(validator, api, executor);
  }

  @Provides
  GrpcMethodHandler<RegisterRequest, UserResponse> provideRegisterUserMethodHandler(
    RegisterUserRequestValidator validator,
    RegisterUserGrpcApi api,
    @NonBlockingThreadPool ListeningExecutorService executor) {
    return new GrpcMethodHandler<RegisterRequest, UserResponse>(validator, api, executor);
  }
}
