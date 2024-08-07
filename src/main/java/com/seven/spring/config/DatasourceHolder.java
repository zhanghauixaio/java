package com.seven.spring.config;

import cn.hutool.core.collection.CollUtil;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;
@UtilityClass
public class DatasourceHolder {
    private ThreadLocal<LinkedList<String>> DATA_SOURCE_NAME = new ThreadLocal<>();
    public final static String MASTER_DATASOURCE = "masterDatasource";
    public final static String SLAVE_DATASOURCE = "slaveDatasource";
    public static String getDataSourceName() {
        if (CollUtil.isEmpty(DATA_SOURCE_NAME.get())) {
            return null;
        }
        return DATA_SOURCE_NAME.get().getLast();
    }

    public void setDataSource(String datasourceName) {
        if (StringUtils.isBlank(datasourceName)) {
            return;
        }
        if (Objects.isNull(DATA_SOURCE_NAME.get())) {
            DATA_SOURCE_NAME.set(new LinkedList<>());
        }
        DATA_SOURCE_NAME.get().addLast(datasourceName);
    }
}
