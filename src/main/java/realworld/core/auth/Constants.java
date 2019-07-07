package realworld.core.auth;

import io.grpc.Context;
import io.grpc.Metadata;

public final class Constants {
  private Constants() {}

  public static final Metadata.Key<String> METADATA_AUTHORIZATION_KEY =
    Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

  public static final Context.Key<String> CONTEXT_USER_ID_KEY = Context.key("userId");
}
