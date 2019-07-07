package realworld.infrastructure.grpc;

import static realworld.infrastructure.grpc.Annotations.GrpcServerType;
import static realworld.infrastructure.grpc.Annotations.InProcessGrpcConf;
import static realworld.infrastructure.grpc.Annotations.NettyGrpcConf;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import io.grpc.BindableService;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.grpc.ServerServiceDefinition;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.netty.NettyServerBuilder;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class GrpcServerModule extends AbstractModule {

  /** Type of gRPC server builder to use. */
  public static enum ServerType {
    NETTY,
    IN_PROCESS
  }

  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), BindableService.class);
    Multibinder.newSetBinder(binder(), Service.class)
      .addBinding()
      .to(GrpcServerService.class);
  }

  public static LinkedBindingBuilder<BindableService> addServiceBinding(Binder binder) {
    return Multibinder.newSetBinder(binder, BindableService.class).addBinding();
  }

  public static void addServiceBinding(Binder binder, Class<BindableService> serviceClass) {
    addServiceBinding(binder).to(serviceClass);
  }

  @Provides
  ServerBuilder<?> provideServerBuilder(
      @GrpcServerType ServerType serverType,
      @NettyGrpcConf int port,
      @InProcessGrpcConf String name) {
    switch (serverType) {
      case NETTY:
        return NettyServerBuilder.forPort(port);
      case IN_PROCESS:
        return InProcessServerBuilder.forName(name);
      default:
        throw new IllegalArgumentException("unhandled server type: " + serverType);
    }
  }

  @Provides
  @InProcessGrpcConf
  ManagedChannel provideInProcessChannel(@InProcessGrpcConf String name) {
    return InProcessChannelBuilder.forName(name).build();
  }

  private static class GrpcServerService extends AbstractIdleService {

    private final Server server;

    @Inject
    GrpcServerService(
        ServerBuilder<?> builder,
        Set<BindableService> services,
        Set<ServerInterceptor> serverInterceptors) {
      services.forEach(service -> {
        ServerServiceDefinition withInterceptors = ServerInterceptors
          .intercept(service, ImmutableList.copyOf(serverInterceptors));
        builder.addService(withInterceptors);
      });
      this.server = builder.build();
    }

    @Override
    protected void startUp() throws Exception {
      server.start();
    }

    @Override
    protected void shutDown() throws Exception {
      server.shutdownNow();
      if (!server.awaitTermination(5, TimeUnit.SECONDS)) {
        System.err.println("Timed out waitinf for server shutdown.");
      }
    }
  }
}
