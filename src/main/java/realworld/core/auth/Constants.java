package realworld.core.auth;

import io.grpc.Metadata;

final class Constants {
  private Constants() {}

  static final Metadata.Key<String> METADATA_KEY =
    Metadata.Key.of("Key", Metadata.ASCII_STRING_MARSHALLER);
}
