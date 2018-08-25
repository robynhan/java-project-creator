package org.robynhan.com;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robynhan.com.ProjectCreatorPlugin.TASK_NAME;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

@Ignore
public class PlugInIntegrationTest {

  @Rule
  public final TemporaryFolder testProjectDir = new TemporaryFolder();

  @Before
  public void setup() throws IOException {
    File buildFile = testProjectDir.newFile("build.gradle");
    File resourceFile = new File(getClass().getClassLoader().getResource("test.build.gradle").getFile());
    copyFile(resourceFile, buildFile);
  }

  @Test
  public void test_should_execute_plugin() throws IOException {
    BuildResult result = GradleRunner.create()
        .withPluginClasspath()
        .withProjectDir(testProjectDir.getRoot())
        .withArguments(TASK_NAME)
        .build();

    System.out.println(result.getOutput());
    assertTrue(result.getOutput().contains("1 actionable task: 1 executed"));
    assertEquals(TaskOutcome.SUCCESS, result.task(":" + TASK_NAME).getOutcome());

    File projectFolder = new File("./src/test/output/sampleProject");
    Assert.assertTrue(projectFolder.exists());
    FileUtils.deleteDirectory(projectFolder);
  }
}
