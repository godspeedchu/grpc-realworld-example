package realworld.infrastructure.concurrent;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Annotations {
  private Annotations() {}

  @BindingAnnotation
  @Retention(RetentionPolicy.RUNTIME)
  public @interface NonBlockingThreadPool {}
}
