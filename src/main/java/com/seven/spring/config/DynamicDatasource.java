package com.seven.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
@Slf4j
public class DynamicDatasource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DatasourceHolder.getDataSourceName();
        log.debug("data source name is: {}", dataSourceName);
        return dataSourceName;
    }
}
