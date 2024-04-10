package com.seven.excel;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.io.Resources;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
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
import java.util.Arrays;
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

    @Test
    public void test() {
        List<List<String>> head = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        head.add(Arrays.asList("test", "test"));
        head.add(Arrays.asList("test", "hhh"));
        data.add(Arrays.asList("qwe", "asd"));
        data.add(Arrays.asList("qwe", "hoa"));
        EasyExcelFactory.write("src/main/java/com/seven/excel/test.xlsx")
                .sheet(0)
                .registerWriteHandler(new LoopMergeStrategy(2,0))
                .registerWriteHandler(horizontalCellStyleStrategy())
                .head(head)
                .doWrite(data);
    }

    private HorizontalCellStyleStrategy horizontalCellStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置对齐（左对齐）
        //headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 设置对齐（居中对齐）
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 背景色, 设置为白色，也是默认颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }
}
