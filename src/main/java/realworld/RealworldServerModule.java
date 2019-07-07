package realworld;

import com.google.inject.AbstractModule;
import realworld.api.RealworldServiceModule;
import realworld.core.dao.impl.DaoModule;
import realworld.infrastructure.concurrent.ListeningExecutorServiceModule;
import realworld.infrastructure.grpc.GrpcServerConfigModule;
import realworld.infrastructure.grpc.GrpcServerModule;

/** Configure RealworldServer. */
public final class RealworldServerModule extends AbstractModule {

  private static final int GRPC_PORT = 50051;

  @Override
  protected void configure() {
    install(new ListeningExecutorServiceModule());
    install(new GrpcServerModule());
    install(GrpcServerConfigModule.forNettyServer(GRPC_PORT));
    install(new RealworldServiceModule());
    install(new DaoModule());
  }
}
