package com.seven.mybatis.config;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Properties;
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
// @Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class MybatisInterceptor implements Interceptor {
    private Properties prop;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        // Object[] args = invocation.getArgs();
        // Object target = invocation.getTarget();
        // MappedStatement ms = (MappedStatement) args[0];
        // Object parameters = args[1];
        // BoundSql boundSql = ms.getBoundSql(parameters);
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(handler);
        
        // MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // final String databaseId = ms.getConfiguration().getDatabaseId(); // 向mybatis获取当前的数据库类型
        // SqlCommandType sqlCmdType = ms.getSqlCommandType();

        // // 获取原始sql
        // final String originalSql = (String) metaObject.getValue("delegate.boundSql.sql");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        this.prop = properties;
        properties.forEach((k, v) -> System.out.println(k + " : " + v));
        // Interceptor.super.setProperties(properties);
    }
    private Object getOriginalTargetObject(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject mo = SystemMetaObject.forObject(target);
            return getOriginalTargetObject(mo.getValue("h.target"));
        } else {
            return target;
        }
    }
}
