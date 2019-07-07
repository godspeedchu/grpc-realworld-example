package realworld.core.auth;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

/**
 * A simple ServerInterceptor which takes the jwt in request header and put
 * the same jwt into the response header.
 */
public final class ServerAuthInterceptor implements ServerInterceptor {

  private final Logger logger = Logger.getLogger(ServerAuthInterceptor.class.getName());

  @Inject
  ServerAuthInterceptor() {}

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call,
      final Metadata requestHeaders,
      ServerCallHandler<ReqT, RespT> next) {
    String jwt = requestHeaders.get(Constants.METADATA_AUTHORIZATION_KEY);
    logger.info("received JWT " + jwt);

    Optional<String> userId = Optional.empty();
    if (Strings.isNullOrEmpty(jwt)) {
      logger.info("received unauthenticated call.");
    } else {
      userId = DummyJwtUtil.fromToken(jwt.substring(jwt.lastIndexOf(" ") + 1));
      if (!userId.isPresent()) {
        // JWT token is invalid.
        call.close(Status.UNAUTHENTICATED, new Metadata());
        return new ServerCall.Listener<ReqT>() {};
      }
    }

    return Contexts.interceptCall(
      userId.isPresent() ? Context.current().withValue(Constants.CONTEXT_USER_ID_KEY, userId.get()) : Context.current(),
      new SimpleForwardingServerCall<ReqT, RespT>(call) {
        @Override
        public void sendHeaders(Metadata responseHeaders) {
          if (jwt != null) {
            responseHeaders.put(Constants.METADATA_AUTHORIZATION_KEY, jwt);
          }
          super.sendHeaders(responseHeaders);
        }
      },
      requestHeaders,
      next);
  }
}
