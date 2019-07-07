package realworld.core.auth;

import com.google.common.base.Strings;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

/**
 * A simple ClientInterceptor which injects the key into Metadata.
 */
public final class ClientAuthInterceptor implements ClientInterceptor {

  private final String key;

  public ClientAuthInterceptor(String key) {
    this.key = key;
  }

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method,
      CallOptions callOptions,
      Channel next) {
    return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        if (!Strings.isNullOrEmpty(key)) {
          System.out.println("putting key " + key);
          headers.put(Constants.METADATA_KEY, key);
        }
        super.start(new SimpleForwardingClientCallListener<RespT>(responseListener) {}, headers);
      }
    };
  }
}
