package org.robynhan.com;

import static java.util.Optional.ofNullable;

public class ProjectCreatorPluginExtension {

  private static final String DEFAULT_TYPE = "java";
  private static final String DEFAULT_DIRECTORY = ".";
  private static final String DEFAULT_PROJECT_NAME = "NewProject";
  private static final String DEFAULT_GROUP_NAME = "default.group.com";
  private static final String DEFAULT_VERSION = "1.0.0-SNAPSHOT";

  private String type;
  private String directory;
  private String projectName;
  private String groupName;
  private String version;

  public ProjectCreatorPluginExtension() {
  }

  public static ProjectCreatorPluginExtension defaultExtension() {
    return new ProjectCreatorPluginExtension().fillNullWithDefaultValue();
  }

  public ProjectCreatorPluginExtension fillNullWithDefaultValue() {
    type = ofNullable(type).orElse(DEFAULT_TYPE);
    directory = ofNullable(directory).orElse(DEFAULT_DIRECTORY);
    projectName = ofNullable(projectName).orElse(DEFAULT_PROJECT_NAME);
    groupName = ofNullable(groupName).orElse(DEFAULT_GROUP_NAME);
    version = ofNullable(version).orElse(DEFAULT_VERSION);
    return this;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getDirectory() {
    return directory;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getGroupName() {
    return groupName;
  }

  public String getVersion() {
    return version;
  }

  public void setDirectory(final String directory) {
    this.directory = directory;
  }

  public void setProjectName(final String projectName) {
    this.projectName = projectName;
  }

  public void setGroupName(final String groupName) {
    this.groupName = groupName;
  }

  public void setVersion(final String version) {
    this.version = version;
  }
}
