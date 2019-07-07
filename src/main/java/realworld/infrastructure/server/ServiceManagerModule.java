package realworld.infrastructure.server;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import java.util.Set;

public final class ServiceManagerModule extends AbstractModule {

  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), Service.class);
  }

  @Provides
  @Singleton
  ServiceManager provideServiceManager(Set<Service> services) {
    return new ServiceManager(services);
  }
}
