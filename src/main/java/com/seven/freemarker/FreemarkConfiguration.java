package com.seven.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkConfiguration {

    public Configuration configuration = null;

    private static class SingletonClassInstance{
        private static final FreemarkConfiguration INSTANCE = new FreemarkConfiguration();
    }

    private FreemarkConfiguration(){
        // 创建Configuration实例，指定版本
        configuration = new Configuration(Configuration.getVersion());
        // 指定configuration对象模板文件存放的路径
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        // 设置config的默认字符集，一般是UTF-8
        configuration.setDefaultEncoding("UTF-8");
        // 设置错误控制器
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static FreemarkConfiguration getInstance(){
        return SingletonClassInstance.INSTANCE;
    }
}
