package org.robynhan.com;

import static org.mockito.Mockito.when;
import static org.robynhan.com.ProjectCreatorModule.getFreemarkerConfiguration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProjectCreatorTest {

  @Mock
  private ProjectCreatorPluginExtension pluginExtension;

  private DefaultProjectCreator projectCreator;

  @Before
  public void before() {
    projectCreator = new DefaultProjectCreator(new Gson(), getFreemarkerConfiguration(), pluginExtension);
  }

  @Test
  public void test_should_create_project_structure() throws IOException {
    String projectName = "sampleProject";
    String destFolder = "./src/test/output";

    when(pluginExtension.getDirectory()).thenReturn(destFolder);
    when(pluginExtension.getType()).thenReturn("java");
    when(pluginExtension.getGroupName()).thenReturn("org.robynhan.com");
    when(pluginExtension.getVersion()).thenReturn("1.0.0");
    when(pluginExtension.getProjectName()).thenReturn(projectName);
    projectCreator.create();

    File projectFolder = new File(destFolder + "/" + projectName);
    Assert.assertTrue(projectFolder.exists());
    FileUtils.deleteDirectory(projectFolder);
  }

}