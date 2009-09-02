package ie.ucd.bon.printer.template;

import ie.ucd.bon.util.FileUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class FreeMarkerTemplate {

  private static Configuration config;
  private final Map<String,Object> dataModel;
  
  public FreeMarkerTemplate() {
    dataModel = new HashMap<String,Object>();
  }
  
  public FreeMarkerTemplate addToDataModel(String name, Object o) {
    dataModel.put(name, o);
    return this;
  }
  
  public void writeTemplate(Writer out, String templateName) {
    writeTemplate(out, templateName, dataModel);
  }
  
  private static Configuration getConfig() {
    if (config == null) {
      config = new Configuration();
      config.setClassForTemplateLoading(FileUtil.class, "/templates/");
    }
    return config;
  }
  
  public static void writeTemplate(Writer out, String templateName, Map<String,Object> dataModel) {
    try {
      dataModel.put("prepareManifest", new PrepareManifestForXHTMLMethod());
      getConfig().getTemplate(templateName).process(dataModel, out);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    }
  }
  
}