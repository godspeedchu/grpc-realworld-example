package realworld.infrastructure.grpc;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Annotations {

  /**
   * Binding annotations for a ServerType that decides the transport type of the gRPC server created
   * by GrpcServerModule
   */
  @BindingAnnotation
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GrpcServerType {}

  /**
   * Binding annotations for configs required by a Netty-based gRPC server created by
   * GrpcServerModule.
   */
  @BindingAnnotation
  @Retention(RetentionPolicy.RUNTIME)
  public @interface NettyGrpcConf {}

  /**
   * Binding annotations for configs required by a in-process gRPC server created by
   * GrpcServerModule.
   */
  @BindingAnnotation
  @Retention(RetentionPolicy.RUNTIME)
  public @interface InProcessGrpcConf {}
}
