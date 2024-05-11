package com.seven.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.seven.utils.JsonUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class ListStringTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    @SneakyThrows
    public void setNonNullParameter(
            PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        if (Objects.isNull(parameter)) {
            ps.setString(i, null);
        } else {
            ps.setString(i, JsonUtil.toString(parameter));
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (StringUtils.isBlank(result)) {
            return Collections.emptyList();
        }
        return JsonUtil.toList(result, String.class);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (StringUtils.isBlank(result)) {
            return Collections.emptyList();
        }
        return JsonUtil.toList(result, String.class);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (StringUtils.isBlank(result)) {
            return Collections.emptyList();
        }
        return JsonUtil.toList(result, String.class);
    }
}
