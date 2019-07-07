package realworld.core.auth;

import com.google.protobuf.Timestamp;
import com.google.protobuf.InvalidProtocolBufferException;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import realworld.proto.internal.DbUser;
import realworld.proto.internal.JsonWebToken;

public final class DummyJwtUtil {

  // Token lasts for 5 minutes.
  private static final int EXPIRE_DURATION_SECOND = 300;

  private DummyJwtUtil() {}

  public static String toToken(String userId) {
    Instant now = Instant.now();
    Timestamp expireAt = Timestamp.newBuilder()
      .setSeconds(now.getEpochSecond() + EXPIRE_DURATION_SECOND)
      .build();
    JsonWebToken jwt = JsonWebToken.newBuilder()
      .setSubject(userId)
      .setExpireAt(expireAt)
      .build();
    return Base64.getUrlEncoder().withoutPadding().encodeToString(jwt.toByteArray());
  }

  public static Optional<String> fromToken(String token) {
    try {
      JsonWebToken jwt = JsonWebToken.parseFrom(
          Base64.getUrlDecoder().decode(token));
      if (jwt.getExpireAt().getSeconds() < Instant.now().getEpochSecond()) {
        return Optional.empty();
      }
      return Optional.of(jwt.getSubject());
    } catch (InvalidProtocolBufferException e) {
      return Optional.empty();
    }
  }
}
