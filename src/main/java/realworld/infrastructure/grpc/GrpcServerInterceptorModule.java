package realworld.infrastructure.grpc;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import io.grpc.ServerInterceptor;
import java.util.Set;

public final class GrpcServerInterceptorModule extends AbstractModule {

  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), ServerInterceptor.class);
  }

  public static LinkedBindingBuilder<ServerInterceptor> addServerInterceptorBinding(
      Binder binder) {
    return Multibinder.newSetBinder(binder, ServerInterceptor.class).addBinding();
  }

  public static void addServerInterceptorBinding(
      Binder binder, Class<ServerInterceptor> serverInterceptorClass) {
    addServerInterceptorBinding(binder).to(serverInterceptorClass);
  }
}
