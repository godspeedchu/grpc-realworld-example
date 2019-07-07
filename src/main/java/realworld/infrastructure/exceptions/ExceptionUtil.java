package realworld.infrastructure.exceptions;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

/** Util class for working with gRPC exceptions. */
public final class ExceptionUtil {

  private ExceptionUtil() {}

  public static StatusRuntimeException unauthorized(String message) {
    return Status.UNAUTHENTICATED.withDescription(message).asRuntimeException();
  }

  public static StatusRuntimeException permissionDenied(String message) {
    return Status.PERMISSION_DENIED.withDescription(message).asRuntimeException();
  }

  public static StatusRuntimeException notFound(String message) {
    return Status.NOT_FOUND.withDescription(message).asRuntimeException();
  }

  public static StatusRuntimeException badRequest(String message) {
    return Status.INVALID_ARGUMENT.withDescription(message).asRuntimeException();
  }
}
