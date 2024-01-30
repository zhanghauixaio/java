package com.seven.excel;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASSWORD = "123456";
    public static final String EXCEL_TYPE_XLS = "xls";
    public static final String EXCEL_TYPE_XLSX = "xlsx";
    private Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    public static Connection getStatement(){
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException {
        File file = new File("./src/main/resources/巢湖存货编码8.29.xlsx");
        InputStream stream = ResourceUtil.getStream("巢湖存货编码8.29.xlsx");
        URL resources = Resources.getResource("巢湖存货编码8.29.xlsx");
        List<Category> list = new ArrayList<>();
        EasyExcel.write().excelType(ExcelTypeEnum.XLSX).sheet("物品分类").doWrite(list);
        EasyExcel.write(file, Category.class).sheet("物品分类").doWrite(list);
        EasyExcel.read(file, Category.class, new AnalysisEventListener() {
                    @Override
                    public void invoke(Object data, AnalysisContext context) {
                        Category category = (Category) data;
                        list.add(category);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }
                })
                .sheet("物品分类")
                .doReadSync();
        Connection conn = getStatement();
        StringBuilder sb = new StringBuilder("insert into category(id,sn,name) values");
        list.forEach(category -> {
            String sql = "('" + category.getId() + "','" + category.getSn() + "','" + category.getName() + "'),";
            sb.append(sql);
        });
        sb.deleteCharAt(sb.lastIndexOf(","));
        System.out.println(sb);
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private HorizontalCellStyleStrategy horizontalCellStyleStrategy() {
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setWrapped(true);
        return new HorizontalCellStyleStrategy(null, writeCellStyle);
    }
}
