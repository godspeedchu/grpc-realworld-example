package realworld.infrastructure.grpc;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.protobuf.Message;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public final class GrpcMethodHandler<Request extends Message, Response extends Message> {

  private final Logger logger = Logger.getLogger(GrpcMethodHandler.class.getName());

  private final GrpcRequestValidator<Request> validator;
  private final GrpcRequestProcessor<Request, Response> processor;
  private final Executor executor;

  public GrpcMethodHandler(
    GrpcRequestValidator<Request> validator,
    GrpcRequestProcessor<Request, Response> processor,
    Executor executor) {
    this.validator = validator;
    this.processor = processor;
    this.executor = executor;
  }

  public void handleGrpcMethod(Request request, StreamObserver<Response> streamObserver) {
    try {
      validator.validate(request);
      Futures.addCallback(
          processor.execute(request),
          new FutureCallback<Response>() {
            public void onSuccess(Response response) {
              Context.current().run(() -> {
                streamObserver.onNext(response);
                streamObserver.onCompleted();
              });
            }
            public void onFailure(Throwable t) {
              logger.severe("exception " + t);
              Context.current().run(() -> streamObserver.onError(t));
            }
          },
          executor);
    }
    catch (Exception e) {
      logger.severe("exception " + e);
      streamObserver.onError(e);
    }
  }
}
