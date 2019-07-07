package realworldclient;

import com.google.common.util.concurrent.ListenableFuture;
import realworld.proto.LoginRequest;
import realworld.proto.RegisterRequest;
import realworld.proto.UserResponse;

public interface RealworldClient {

  ListenableFuture<UserResponse> loginUser(LoginRequest request);
  
  ListenableFuture<UserResponse> registerUser(RegisterRequest request);
}
