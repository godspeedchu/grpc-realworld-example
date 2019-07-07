package realworld.infrastructure.grpc;

import static realworld.infrastructure.grpc.Annotations.GrpcServerType;
import static realworld.infrastructure.grpc.Annotations.InProcessGrpcConf;
import static realworld.infrastructure.grpc.Annotations.NettyGrpcConf;

import com.google.inject.AbstractModule;
import realworld.infrastructure.grpc.GrpcServerModule.ServerType;

/**
 * Module installs necessary configs to run a gRPC server in Netty or InProcess mode.
 */
public final class GrpcServerConfigModule extends AbstractModule {

  private final ServerType serverType;
  private final String name;
  private final int port;

  private GrpcServerConfigModule(ServerType serverType, String name, int port) {
    this.serverType = serverType;
    this.name = name;
    this.port = port;
  }

  @Override
  protected void configure() {
    bind(ServerType.class)
      .annotatedWith(GrpcServerType.class)
      .toInstance(serverType);

    bind(String.class)
      .annotatedWith(InProcessGrpcConf.class)
      .toInstance(name);

    bind(Integer.class)
      .annotatedWith(NettyGrpcConf.class)
      .toInstance(port);
  }

  public static GrpcServerConfigModule forNettyServer(int port) {
    return new GrpcServerConfigModule(ServerType.NETTY, "", port);
  }

  public static GrpcServerConfigModule forInProcessServer(String name) {
    return new GrpcServerConfigModule(ServerType.IN_PROCESS, name, 0);
  }
}
