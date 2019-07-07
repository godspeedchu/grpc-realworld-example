package realworld.processors;

import static com.google.common.truth.Truth.assertThat;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.protobuf.StringValue;
import realworld.core.dao.UserDao;
import realworld.core.dao.impl.DaoModule;
import realworld.proto.UserResponse;
import realworld.proto.LoginRequest;
import realworld.proto.User;
import realworld.proto.UserResponse;
import realworld.proto.internal.DbUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class LoginUserRequestProcessorTest {

  @Inject
  private LoginUserRequestProcessor processor;

  @Inject
  private UserDao userDao;

  @Before
  public void setUp() throws Exception {
    Guice.createInjector(
        new DaoModule()
        )
    .injectMembers(this);
  }

  @Test
  public void execute_success() throws Exception {
    userDao.createUser(DbUser.newBuilder()
        .setId("1")
        .setEmail("some@email.com")
        .setUsername("some")
        .setPasswordHash("pswd")
        .build());
    LoginRequest request = LoginRequest.newBuilder()
      .setUser(User.newBuilder()
        .setEmail(StringValue.of("some@email.com"))
        .setPassword(StringValue.of("pswd")))
      .build();

    UserResponse response = processor.execute(request).get();

    assertThat(response.getUser().getEmail().getValue()).isEqualTo("some@email.com");
    assertThat(response.getUser().getUsername().getValue()).isEqualTo("some");
  }
}
