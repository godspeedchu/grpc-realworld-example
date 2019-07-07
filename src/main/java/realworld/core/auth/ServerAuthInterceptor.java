package realworld.core.auth;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

/**
 * A simple ServerInterceptor which takes the key in request header and put
 * the same key into the response header.
 */
public final class ServerAuthInterceptor implements ServerInterceptor {

  @Inject
  ServerAuthInterceptor() {}

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call,
      final Metadata requestHeaders,
      ServerCallHandler<ReqT, RespT> next) {
    String key = requestHeaders.get(Constants.METADATA_KEY);

    if (Strings.isNullOrEmpty(key)) {
      call.close(Status.UNAUTHENTICATED, new Metadata());
      return new ServerCall.Listener<ReqT>() {};
    }

    return Contexts.interceptCall(
      Context.current(),
      new SimpleForwardingServerCall<ReqT, RespT>(call) {
        @Override
        public void sendHeaders(Metadata responseHeaders) {
          responseHeaders.put(Constants.METADATA_KEY, key);
          super.sendHeaders(responseHeaders);
        }
      },
      requestHeaders,
      next);
  }
}
