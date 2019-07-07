package realworld.infrastructure.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import java.util.logging.Logger;

/**
 * Base class for module-based servers.
 */
public abstract class AbstractServer {
  protected Logger logger = Logger.getLogger(getClass().getName());

  private Injector injector;
  private ServiceManager serviceManager;

  protected abstract Iterable<Module> getModules();

  public synchronized void configure() {
    final ImmutableList<Module> baseModules =
      ImmutableList.<Module>of(new ServiceManagerModule());
    injector = Guice.createInjector(Stage.PRODUCTION, Iterables.concat(baseModules, getModules()));
    serviceManager = injector.getInstance(ServiceManager.class);
  }

  public synchronized void start() {
    System.out.println("Starting service...");
    serviceManager.startAsync();

    System.out.println("Waiting to become healthy...");
    serviceManager.awaitHealthy();

    System.out.println("Service started");
  }

  public synchronized void stop() {
    System.out.println("Stopping service...");
    serviceManager.stopAsync();

    System.out.println("Waiting to stop...");
    serviceManager.awaitStopped();

    System.out.println("Service stopped");
  }

  public void run(String... args) {
    configure();
    start();
    serviceManager.awaitStopped();
  }
}
