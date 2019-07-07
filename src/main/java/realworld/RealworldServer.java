package realworld;

import com.google.common.collect.ImmutableList;
import com.google.inject.Module;
import realworld.infrastructure.server.AbstractServer;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a {@code Realworld} server.
 */
public class RealworldServer extends AbstractServer {

  @Override
  protected Iterable<Module> getModules() {
    return ImmutableList.of(new RealworldServerModule());
  }

  public static void main(String[] args) throws Exception {
    new RealworldServer().run(args);
  }
}
