package realworldclient;

import com.google.inject.Guice;
import io.grpc.StatusRuntimeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import realworld.proto.LoginRequest;
import realworld.proto.RegisterRequest;
import realworld.proto.User;
import realworld.proto.UserResponse;

/**
 * A simple client that requests a login from the {@link RealworldServer}.
 */
public class RealworldClientCli {
  private static final Logger logger = Logger.getLogger(RealworldClient.class.getName());

  /** Login */
  public static void loginUser(RealworldClient client, String email, String password) {
    logger.info("Will try to login " + email + " with password " + password + " ...");
    User.Builder builder = User.newBuilder().setEmail(email).setPassword(password);
    LoginRequest request = LoginRequest.newBuilder().setUser(builder).build();
    UserResponse response;
    try {
      response = client.loginUser(request).get();
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    } catch (Exception e) {
      logger.log(Level.WARNING, "Exception ", e);
      return;
    }
    logger.info("Response: " + response);
  }

  /** Register */
  public static void registerUser(RealworldClient client, String email, String username, String password) {
    logger.info("Will try to register " + username + "(" + email + ") with password " + password + " ...");
    User.Builder builder = User.newBuilder().setEmail(email).setUsername(username).setPassword(password);
    RegisterRequest request = RegisterRequest.newBuilder().setUser(builder).build();
    UserResponse response;
    try {
      response = client.registerUser(request).get();
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    } catch (Exception e) {
      logger.log(Level.WARNING, "Exception ", e);
      return;
    }
    logger.info("Response: " + response);
  }

  /**
   * Greet server. If provided, the first element of {@code args} is the name to use in the
   * greeting.
   */
  public static void main(String[] args) throws Exception {
    /* Access a service running on the local machine on port 50051 */
    String command = "";
    String email = "N/A";
    String username = "N/A";
    String password = "N/A";
    if (args.length >= 1) {
      command = args[0]; /* Use the arg to dispatch command */
    }

    RealworldClient client = Guice.createInjector(new RealworldClientModule())
      .getInstance(RealworldClient.class);
    switch (command) {
      case "LOGIN": {
        email = args[1]; /* Use the arg as the email to login */
        password = args[2]; /* Use the arg as the password to login */
        loginUser(client, email, password);
        break;
      }
      case "REGISTER": {
        email = args[1]; /* Use the arg as the email to login */
        username = args[2]; /* Use the arg as the username to login */
        password = args[3]; /* Use the arg as the password to login */
        registerUser(client, email, username, password);
        break;
      }
    }
  }
}
