// package com.seven.spring.config;
//
// import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
// import com.zaxxer.hikari.HikariDataSource;
// import lombok.SneakyThrows;
// import org.apache.ibatis.session.SqlSessionFactory;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
// import javax.sql.DataSource;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Objects;
//
// @Configuration
// public class DatasourceConfiguration {
//     @ConfigurationProperties(prefix = "spring.datasource.master")
//     @Bean("masterDatasource")
//     public DataSource masterDatasource() {
//         return DataSourceBuilder.create().type(HikariDataSource.class).build();
//     }
//
//     @ConfigurationProperties(prefix = "spring.datasource.slave")
//     @Bean("slaveDatasource")
//     // @Qualifier(DatasourceHolder.SlaveDatasource)
//     public DataSource slaveDatasource() {
//         return DataSourceBuilder.create().type(HikariDataSource.class).build();
//     }
//     @Bean("DynamicDatasource")
//     public DynamicDatasource dynamicDatasource(DataSource masterDatasource, @Qualifier("slaveDatasource") DataSource slaveDatasource) {
//         DynamicDatasource dynamicDatasource = new DynamicDatasource();
//         Map<Object, Object> map = new HashMap<>();
//         map.put(DatasourceHolder.MASTER_DATASOURCE, masterDatasource);
//         map.put(DatasourceHolder.SLAVE_DATASOURCE, slaveDatasource);
//         dynamicDatasource.setTargetDataSources(map);
//         dynamicDatasource.setDefaultTargetDataSource(masterDatasource);
//         return dynamicDatasource;
//     }
//     @SneakyThrows
//     @Bean
//     public SqlSessionFactory sqlSessionFactory(DynamicDatasource dynamicDatasource) {
//         MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
//         factoryBean.setDataSource(dynamicDatasource);
//         factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/**/*.xml"));
//         return factoryBean.getObject();
//     }
// }
