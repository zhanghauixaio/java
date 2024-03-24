package com.seven.jasperreport;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class JasperReportTest {
    @SneakyThrows
    @Test
    public void Test() {
        InputStream stream = JasperReportTest.class.getClassLoader().getResourceAsStream("com/seven/jasperreport/report1.jrxml");
        // 模板文件
        JasperReport jasperReport2 = (JasperReport) JRLoader.loadObject(new File("src/main/java/com/seven/jasperreport/report1.jasper"));
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/java/com/seven/jasperreport/report1.jrxml");
        // 模拟JavaBean
        // Map<String, Object> data = new HashMap<>();
        // data.put("powerGear", "480");
        // data.put("workOrderNo", "O2341243");
        // data.put("currentGear", "12.5");
        // data.put("trayQty", "12");
        List<MarkPrintRespVo> list = new ArrayList<>();
        MarkPrintRespVo vo = new MarkPrintRespVo();
        vo.setPowerGear("580M");
        vo.setWorkOrderNo("O12041");
        vo.setTrayNo("q32r12351");
        vo.setBarcodes(Arrays.asList("123", "1242", "124"));
        list.add(vo);
        // 自定义数据源
        Map<String, Object> param = new HashMap<>();
        // 数据源类型为JBean
        JRDataSource source = new JRBeanCollectionDataSource(list);
        // 设置数据
        // param.put("beans", source);
        // 填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, source);
        // 导出pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint, "jasperOut1.pdf");
    }
}
