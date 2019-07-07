package realworld.infrastructure.concurrent;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.Provides;
import realworld.infrastructure.concurrent.Annotations.NonBlockingThreadPool;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public final class ListeningExecutorServiceModule extends AbstractModule {

  private final int THREAD_POOL_SIZE = 20;

  @Override
  protected void configure() {}

  @Provides
  @Singleton
  @NonBlockingThreadPool
  ListeningExecutorService provideListeningExecutorService(
      @NonBlockingThreadPool ExecutorService executorService) {
    return MoreExecutors.listeningDecorator(executorService);
  }

  @Provides
  @Singleton
  @NonBlockingThreadPool
  ExecutorService provideExecutorService() {
    return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
  }
}
