package org.robynhan.com;

import static org.robynhan.com.ProjectCreatorPluginExtension.defaultExtension;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import freemarker.template.Configuration;

public class ProjectCreatorModule extends AbstractModule {

  private static ProjectCreatorPluginExtension pluginExtension = defaultExtension();

  @Override
  protected void configure() {
    bind(ProjectCreator.class).to(DefaultProjectCreator.class).in(Singleton.class);
    bind(Gson.class).toInstance(new Gson());
    bind(Configuration.class).toInstance(getFreemarkerConfiguration());
    bind(ProjectCreatorPluginExtension.class).toInstance(pluginExtension);
  }

  public static void setPluginExtension(final ProjectCreatorPluginExtension pluginExtension) {
    ProjectCreatorModule.pluginExtension = pluginExtension;
  }

  @VisibleForTesting
  static Configuration getFreemarkerConfiguration() {
    Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
    configuration.setDefaultEncoding("UTF-8");
    configuration.setClassForTemplateLoading(ProjectCreatorModule.class, "/");
    return configuration;
  }

}
