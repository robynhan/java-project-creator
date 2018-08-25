package org.robynhan.com.runner;

import static java.util.Optional.ofNullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class GuiceJuintRunner extends BlockJUnit4ClassRunner {

  private Class<? extends Module> module;

  @Target({ ElementType.TYPE, ElementType.METHOD })
  @Retention(RetentionPolicy.RUNTIME)
  @Inherited
  public @interface GuiceModule {

    Class<? extends Module> value();
  }

  public GuiceJuintRunner(final Class<?> klass) throws InitializationError {
    super(klass);
  }

  private Injector createInjectorFor(final Class<? extends Module> klass)
      throws IllegalAccessException, InstantiationException {
    return Guice.createInjector(klass.newInstance());
  }

  private Class<? extends Module> getModuleFor(final Class<?> module) {
    GuiceModule annotation = module.getAnnotation(GuiceModule.class);
    return ofNullable(annotation).map(GuiceModule::value).orElse(null);
  }

  private Class<? extends Module> getModuleFor(final FrameworkMethod method) {
    final GuiceModule annotation = method.getAnnotation(GuiceModule.class);
    return ofNullable(annotation).map(GuiceModule::value).orElse(null);
  }

  private Class<? extends Module> getModuleFor(final FrameworkMethod method, final Class<?> module) {
    if (getModuleFor(method) != null) {
      return getModuleFor(method);
    }
    return getModuleFor(module);
  }

  @Override
  public Object createTest() throws InstantiationException, IllegalAccessException {
    return createInjectorFor(module).getInstance(getTestClass().getJavaClass());
  }

  @Override
  protected void runChild(final FrameworkMethod method, final RunNotifier notifier) {
    module = getModuleFor(method, method.getDeclaringClass());
    super.runChild(method, notifier);
  }
}
