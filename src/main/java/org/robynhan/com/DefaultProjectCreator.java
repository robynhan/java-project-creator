package org.robynhan.com;

import static java.util.Optional.ofNullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DefaultProjectCreator implements ProjectCreator {

  private Gson gson;
  private Configuration configuration;
  private ProjectCreatorPluginExtension extension;

  @Inject
  public DefaultProjectCreator(final Gson gson, final Configuration configuration,
      final ProjectCreatorPluginExtension extension) {
    this.gson = gson;
    this.configuration = configuration;
    this.extension = extension;
  }

  @Override
  public void create() {
    String path = extension.getDirectory() + "/" + extension.getProjectName();
    ofNullable(getStructure(extension.getType())).
        ifPresent(structure -> generateResource(extension.getType(), structure, path, getTemplateValues()));
  }

  private Map<String, Object> getTemplateValues() {
    return ImmutableMap.of("projectName", extension.getProjectName(),
        "groupName", extension.getGroupName(),
        "version", extension.getVersion());
  }

  private void generateResource(final String type, final JsonObject entry1, final String path,
      final Map<String, Object> templateValues) {
    new File(path).mkdir();
    entry1.entrySet().forEach(entry -> {
      JsonElement value = entry.getValue();
      if (value.isJsonPrimitive()) {
        File srcFile = getResourceFile(type, value.getAsString());
        File destFile = new File(path, entry.getKey());
        copyFile(value.getAsString(), srcFile, destFile, templateValues);
      } else if (value.isJsonObject()) {
        new File(path, entry.getKey()).mkdir();
        generateResource(type, entry.getValue().getAsJsonObject(), path + "/" + entry.getKey(),
            templateValues);
      }
    });
  }

  private void copyFile(final String srcFileName, final File srcFile, final File destFile,
      final Map<String, Object> templateValues) {
    try {
      if (FilenameUtils.getExtension(srcFile.getName()).equals("ftl")) {
        Template template = configuration.getTemplate(srcFileName);
        template.process(templateValues, new FileWriter(destFile));
      } else {
        FileUtils.copyFile(srcFile, destFile);
      }
    } catch (IOException | TemplateException ignored) {
    }
  }

  private JsonObject getStructure(final String type) {
    File file = getResourceFile(type, "structure.json");
    try {
      return gson.fromJson(new FileReader(file), JsonObject.class);
    } catch (FileNotFoundException ignored) {
    }
    return null;
  }

  private File getResourceFile(final String type, final String fileName) {
    return new File(getClass().getClassLoader().getResource("types/" + type + "/" + fileName).getFile());
  }

}
