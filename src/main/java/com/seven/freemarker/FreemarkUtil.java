package com.seven.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class FreemarkUtil {

    private static Logger logger = LoggerFactory.getLogger(FreemarkUtil.class);

    private static Configuration configuration;

    static {
        FreemarkConfiguration freemarkConfiguration = FreemarkConfiguration.getInstance();
        configuration = freemarkConfiguration.getConfiguration();
    }

    /**
     * freemarker 文件生成
     *
     * @param templateName
     * @param templateData
     * @param filePath
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFile(String templateName, Map<String, Object> templateData, String filePath) {
        BufferedWriter bufferedWriter = null;
        OutputStreamWriter outputStreamWriter = null;
        FileOutputStream fileOutputStream = null;
        try {
            Template template = configuration.getTemplate(templateName + ".ftl","utf-8");
            fileOutputStream = new FileOutputStream(filePath);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            template.process(templateData, bufferedWriter);
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
    }

    /**
     * freemarker 文件生成到响应流
     *
     * @param templateName
     * @param templateData
     * @param outputStream
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileToOutputStream(String templateName, Map<String, Object> templateData,
                                                  OutputStream outputStream) {
        try {
            Template template = configuration.getTemplate(templateName + ".ftl","utf-8");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            template.process(templateData, bufferedWriter);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }


    /**
     * freemarker 文件生成到响应流
     *
     * @param templateName
     * @param templateData
     * @param writer
     * @throws IOException
     * @throws TemplateException
     */
    public static void generateFileToOutputStream(String templateName, Map<String, Object> templateData,
                                                  Writer writer) throws Exception{
        Template template = configuration.getTemplate(templateName + ".ftl","utf-8");
        template.process(templateData, writer);
    }

}
