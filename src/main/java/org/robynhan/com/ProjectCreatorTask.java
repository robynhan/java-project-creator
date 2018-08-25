package org.robynhan.com;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ProjectCreatorTask extends DefaultTask {

  @TaskAction
  public void create() {
    Injector injector = Guice.createInjector(new ProjectCreatorModule());
    injector.getInstance(ProjectCreator.class).create();
  }
}
