package com.seven.mybatis.typehandler;

import com.seven.mybatis.enums.TypeEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class EnumHandler implements TypeHandler<TypeEnum> {
    private static HashMap<String, TypeEnum> map = new HashMap<>(2);
    static {
        for (TypeEnum value : TypeEnum.values()) {
            map.put(value.name(), value);
        }
    }
    @Override
    public void setParameter(PreparedStatement ps, int i, TypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
        System.out.println("setParameter");
    }

    @Override
    public TypeEnum getResult(ResultSet rs, String columnName) throws SQLException {
        String res = rs.getString(columnName);
        return map.get(res);
    }

    @Override
    public TypeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        String res = rs.getString(columnIndex);
        return map.get(res);
    }

    @Override
    public TypeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String res = cs.getString(columnIndex);
        return map.get(res);
    }
}
