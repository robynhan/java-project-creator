package org.robynhan.com;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ProjectCreatorPlugin implements Plugin<Project> {

  static final String TASK_NAME = "projectCreator";

  @Override
  public void apply(final Project target) {
    prepareExtension(target);
    target.getTasks().create(TASK_NAME, ProjectCreatorTask.class);
  }

  private void prepareExtension(final Project target) {
    ProjectCreatorPluginExtension pluginExtension = target.getExtensions()
        .create(TASK_NAME, ProjectCreatorPluginExtension.class);
    pluginExtension.fillNullWithDefaultValue();
    ProjectCreatorModule.setPluginExtension(pluginExtension);
  }
}
