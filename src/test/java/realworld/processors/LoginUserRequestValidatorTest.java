package realworld.processors;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.protobuf.StringValue;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import realworld.proto.LoginRequest;
import realworld.proto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class LoginUserRequestValidatorTest {

  private final LoginUserRequestValidator validator = new LoginUserRequestValidator();

  @Test
  public void validateRequest_success() throws Exception {
    LoginRequest request = LoginRequest.newBuilder()
      .setUser(User
        .newBuilder()
        .setEmail(StringValue.of("some@email.com"))
        .setPassword(StringValue.of("pswd")))
      .build();

    validator.validate(request);
  }

  @Test
  public void validate_throwsExceptionForEmptyUser() {
    StatusRuntimeException e = assertThrows(
        StatusRuntimeException.class,
        () -> validator.validate(LoginRequest.getDefaultInstance()));

    Status status = e.getStatus();
    assertThat(status.getCode()).isEqualTo(Code.INVALID_ARGUMENT);
    assertThat(status.getDescription()).isEqualTo("user is empty.");
  }
}
