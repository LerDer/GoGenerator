package com.wd.util;

import com.wd.vo.TableVO;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.commons.lang3.StringUtils;

/**
 * 生成对象
 *
 * @author lww
 * @date
 */
public class FreeMarkerUtil {

    public static Boolean genStruct(TableVO vo, String outPath) {
        return genCode(vo, outPath, "struct.ftl", ".go", "/template", null, true);
    }

    public static Boolean genApi(TableVO vo, String outPath) {
        return genCode(vo, outPath, "api.ftl", "_api.go", "/template", null, true);
    }

    public static Boolean genDB(TableVO vo, String outPath) {
        return genCode(vo, outPath, "service.ftl", "_service.go", "/template", null, true);
    }

    public static Boolean genRouter(TableVO vo, String outPath) {
        return genCode(vo, outPath, "router.ftl", "_router.go", "/template", null, true);
    }

    public static Boolean genInit(TableVO vo, String outPath) {
        Boolean res;
        res = genCode(vo, outPath + "/response", "common.ftl", ".go", "/template/response", "common", false);
        res &= genCode(vo, outPath + "/response", "response.ftl", ".go", "/template/response", "response", false);
        res &= genCode(vo, outPath + "/middleware", "cors.ftl", ".go", "/template/middleware", "cors", false);
        res &= genCode(vo, outPath + "/middleware", "loadtls.ftl", ".go", "/template/middleware", "loadtls", false);
        res &= genCode(vo, outPath + "/middleware", "logger.ftl", ".go", "/template/middleware", "logger", false);

        res &= genCode(vo, outPath + "/configs/resources", "config.ftl", ".yaml", "/template/configs/resources", "config", false);
        res &= genCode(vo, outPath + "/configs", "config.ftl", ".go", "/template/configs", "config", false);
        res &= genCode(vo, outPath + "/configs", "email.ftl", ".go", "/template/configs", "email", false);
        res &= genCode(vo, outPath + "/configs", "global.ftl", ".go", "/template/configs", "global", false);
        res &= genCode(vo, outPath + "/configs", "log.ftl", ".go", "/template/configs", "log", false);
        res &= genCode(vo, outPath + "/configs", "mysql.ftl", ".go", "/template/configs", "mysql", false);
        res &= genCode(vo, outPath + "/configs", "redis.ftl", ".go", "/template/configs", "redis", false);

        res &= genCode(vo, outPath + "/utils", "date.ftl", ".go", "/template/utils", "date", false);
        res &= genCode(vo, outPath + "/utils", "time.ftl", ".go", "/template/utils", "time", false);

        res &= genCode(vo, outPath + "/corn", "CornDemo.ftl", ".go", "/template/corn", "CornDemo", false);

        res &= genCode(vo, outPath, "main.ftl", ".go", "/template", "main", false);
        res &= genCode(vo, outPath, "go.ftl", ".mod", "/template", "go", false);
        return res;
    }

    private static Boolean genCode(TableVO vo, String outPath, String templateName, String postfix, String tPath, String fileName, boolean addTableName) {
        Writer out = null;
        try {
            // step1 创建freeMarker配置实例
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
            //获取模板文件
            configuration.setClassForTemplateLoading(FreeMarkerUtil.class, tPath);
            configuration.setTemplateLoader(new ClassTemplateLoader(FreeMarkerUtil.class, tPath));
            // step2 获取模版路径
            //String path = "/template/";
            //configuration.setDirectoryForTemplateLoading(file.getParentFile());
            // step3 创建数据模型
            // step4 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // step5 生成数据
            String parentPath = outPath + "/" + (addTableName ? vo.getTableNameHump() : "") + "/";
            String finalName = StringUtils.isBlank(fileName) ? vo.getTableName().toLowerCase() + postfix : fileName + postfix;
            File docFile = new File(parentPath + finalName);
            if (!docFile.getParentFile().exists()) {
                docFile.getParentFile().mkdirs();
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(vo, out);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

/*
    public static void main11(String[] args) throws IOException, TemplateException {
        Writer out;
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        // step2 获取模版路径
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "com/ler/template";
        //configuration.setDirectoryForTemplateLoading(new File("").getCanonicalFile()); ///Users/lww/Desktop/workspace/gogen/
        configuration.setDirectoryForTemplateLoading(new File(path)); ///Users/lww/Desktop/workspace/gogen/
        // step3 创建数据模型
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("well", "WORLD");
        // step4 加载模版文件
        Template template = configuration.getTemplate("struct.ftl");
        // step5 生成数据
        File docFile = new File(CLASS_PATH + "/" + "struct.go");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
        // step6 输出文件
        template.process(dataMap, out);
    }*/

}
