package com.seven.spring.aop;

import com.seven.spring.config.DatasourceHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Aspect
@Component
public class DataSourceAop {
    @Before("@within(com.seven.spring.aop.Datasource) || @annotation(com.seven.spring.aop.Datasource)")
    public void ss(JoinPoint joinPoint) {
        DatasourceHolder.setDataSource(getDatasourceName(joinPoint));
    }

    private String getDatasourceName(JoinPoint joinPoint) {
        MethodSignature method = getMethod(joinPoint);
        Datasource datasource = method.getMethod().getAnnotation(Datasource.class);
        if (Objects.nonNull(datasource) && StringUtils.isNotBlank(datasource.value())) {
            return datasource.value();
        }
        Datasource classAnno = joinPoint.getTarget().getClass().getAnnotation(Datasource.class);
        return Objects.isNull(classAnno) ? null : classAnno.value();
    }

    private MethodSignature getMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("method signature convert error");
        }
        return  (MethodSignature) signature;
    }
}
