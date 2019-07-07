package realworldclient;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import realworld.core.auth.ClientAuthInterceptor;
import realworld.proto.RealworldServiceGrpc;
import realworld.proto.RealworldServiceGrpc.RealworldServiceFutureStub;

public final class RealworldClientModule extends AbstractModule {

  private static final String HOST = "localhost";
  private static final int PORT = 50051;
  private static final String TEST_KEY = "test auth key";

  @Override
  protected void configure() {
    bind(RealworldClient.class).to(RealworldClientImpl.class).in(Singleton.class);
  }

  @Provides
  @Singleton
  RealworldServiceFutureStub providesRealworldServiceFutureStub() {
    ManagedChannel channel = NettyChannelBuilder.forAddress(HOST, PORT)
        .usePlaintext()
        .build();
    ClientInterceptor interceptor = new ClientAuthInterceptor(TEST_KEY);
    return RealworldServiceGrpc.newFutureStub(ClientInterceptors.intercept(channel, interceptor));
  }
}
