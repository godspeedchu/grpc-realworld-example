package realworldclient;

import com.google.inject.Inject;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import realworld.proto.RealworldServiceGrpc.RealworldServiceFutureStub;
import realworld.proto.LoginRequest;
import realworld.proto.RegisterRequest;
import realworld.proto.UserResponse;

public final class RealworldClientImpl implements RealworldClient {

  private final RealworldServiceFutureStub stub;

  @Inject
  RealworldClientImpl(RealworldServiceFutureStub stub) {
    this.stub = stub;
  }

  public ListenableFuture<UserResponse> loginUser(LoginRequest request) {
    return stub.loginUser(request);
  }

  public ListenableFuture<UserResponse> registerUser(RegisterRequest request) {
    return stub.registerUser(request);
  }
}
