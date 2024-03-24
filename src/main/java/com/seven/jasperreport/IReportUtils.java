// package com.seven.jasperreport;
//
// package com.ntocc.util.ireport;
//
// import com.ntocc.config.RDSConnection;
// import net.sf.jasperreports.engine.*;
// import net.sf.jasperreports.engine.export.*;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.util.Map;
//
// /**
//  * @author: luocw
//  * @date: 2019/03/04 09:28
//  * @Desc: :描述该类的主要功能
//  */
// @Component
// public class IReportUtils {
//
//     private static Connection connection;
//
//     private static Logger logger = LoggerFactory.getLogger(IReportUtils.class);
//
//     /**
//      * 获取数据库连接
//      * @return
//      */
//     public static Connection getConnection(){
//         String driver = RDSConnection.driver;
//         String url = RDSConnection.url;
//         String user = RDSConnection.user;
//         String pass = RDSConnection.pass;
//         try {
//             Class.forName(driver);
//             Connection con = DriverManager.getConnection(url, user, pass);
//             return con;
//         }catch(Exception e){
//             e. printStackTrace();
//             logger.error(e.getMessage());
//         }
//         return null;
//     }
//
//     /**
//      * 单例模式
//      * @return
//      */
//     public static Connection getConn(){
//         if (connection == null ){
//             connection = getConnection();
//         }
//         return connection;
//     }
//
//
//     /**使用模板和数据库直连模式不安全,不建议使用
//      * 导出pdf
//      * @param jasperFile
//      * @param paramMap
//      * @param defaultFileName
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToPdf(String jasperFile, Map<String,Object> paramMap, String defaultFileName, HttpServletRequest request,
//                                    HttpServletResponse response) throws IOException, JRException {
//         if (StringUtils.isEmpty(defaultFileName)){
//             defaultFileName =  "export.pdf";
//         }else {
//             defaultFileName = defaultFileName + ".pdf";
//         }
//         String fileName = new String(defaultFileName.getBytes("GBK"), "ISO8859_1");
//         response.setHeader("Content-disposition", "attachment; filename="
//                 + fileName);
//         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,paramMap, getConn());
//         ServletOutputStream ouputStream = response.getOutputStream();
//         JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
//         ouputStream.flush();
//         ouputStream.close();
//     }
//
//     /**
//      * 导出doc文件
//      * @param jasperFile
//      * @param paramMap
//      * @param defaultFileName
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToDoc(String jasperFile, Map<String,Object> paramMap, String defaultFileName, HttpServletRequest request,
//                                    HttpServletResponse response) throws IOException, JRException {
//         if (StringUtils.isEmpty(defaultFileName)){
//             defaultFileName =  "export.doc";
//         }else {
//             defaultFileName = defaultFileName + ".doc";
//         }
//         String fileName = new String(defaultFileName.getBytes("GBK"), "ISO8859_1");
//         response.setHeader("Content-disposition", "attachment; filename="
//                 + fileName);
//         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,paramMap, getConn());
//         //设置导出输出流
//         JRExporter exporter = new JRRtfExporter();
//         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
//                 response.getOutputStream());
//         exporter.exportReport();
//     }
//
//
//     /**
//      * 导出html文件
//      * @param jasperFile
//      * @param paramMap
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToHtml(String jasperFile, Map<String,Object> paramMap, HttpServletRequest request,
//                                     HttpServletResponse response) throws IOException, JRException {
//         response.setContentType("text/html");
//         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,paramMap, getConn());
//         ServletOutputStream ouputStream = response.getOutputStream();
//         JRHtmlExporter exporter = new JRHtmlExporter();
//         exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
//                 Boolean.FALSE);
//         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//         exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
//         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
//         //设置图片文件存放路径，此路径为服务器上的绝对路径
//         String imageDIR =request.getSession().getServletContext().getRealPath("/");
//         exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, imageDIR);
//
//         //设置图片请求URI
//         String imageURI = request.getContextPath() + "/";
//         exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imageURI);
//
//         //设置导出图片到图片存放路径
//         exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.TRUE);
//         exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
//         exporter.exportReport();
//
//         ouputStream.flush();
//         ouputStream.close();
//     }
//
//
//     /**
//      * 导出xls文件
//      * @param jasperFile
//      * @param paramMap
//      * @param defaultFileName
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToExcel(String jasperFile, Map<String,Object> paramMap, String defaultFileName, HttpServletRequest request,
//                                      HttpServletResponse response) throws IOException, JRException {
//         if (StringUtils.isEmpty(defaultFileName)){
//             defaultFileName =  "export.xls";
//         }else {
//             defaultFileName = defaultFileName + ".xls";
//         }
//         String fileName = new String(defaultFileName.getBytes("GBK"), "ISO8859_1");
//         response.setHeader("Content-disposition", "attachment; filename="
//                 + fileName);
//         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,paramMap, getConn());
//         //设置导出输出流
//         ServletOutputStream ouputStream = response.getOutputStream();
//         JRXlsExporter exporter = new JRXlsExporter();
//         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
//         // 删除记录最下面的空行
//         exporter.setParameter(
//                 JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
//                 Boolean.TRUE);
//         // 删除多余的ColumnHeader
//         exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
//                 Boolean.FALSE);
//         // 显示边框
//         exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
//                 Boolean.FALSE);
//         exporter.exportReport();
//         ouputStream.flush();
//         ouputStream.close();
//     }
//
//
//
//     /**
//      * 导出pdf
//      * @param jasperFile
//      * @param paramMap
//      * @param defaultFileName
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToPdf(String jasperFile, Map<String,Object> paramMap, String defaultFileName, HttpServletRequest request,
//                                    HttpServletResponse response, JRDataSource dataSource) throws IOException, JRException {
//         if (StringUtils.isEmpty(defaultFileName)){
//             defaultFileName =  "export.pdf";
//         }else {
//             defaultFileName = defaultFileName + ".pdf";
//         }
//         String fileName = new String(defaultFileName.getBytes("GBK"), "ISO8859_1");
//         response.setHeader("Content-disposition", "attachment; filename="
//                 + fileName);
//         JasperPrint jasperPrint = new JasperPrintWithDataSource(paramMap, jasperFile, dataSource).getJasperPrint();
//         ServletOutputStream ouputStream = response.getOutputStream();
//         JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
//         ouputStream.flush();
//         ouputStream.close();
//     }
//
//     /**
//      * 导出doc文件
//      * @param jasperFile
//      * @param paramMap
//      * @param defaultFileName
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToDoc(String jasperFile, Map<String,Object> paramMap, String defaultFileName, HttpServletRequest request,
//                                    HttpServletResponse response, JRDataSource dataSource) throws IOException, JRException {
//         if (StringUtils.isEmpty(defaultFileName)){
//             defaultFileName =  "export.doc";
//         }else {
//             defaultFileName = defaultFileName + ".doc";
//         }
//         String fileName = new String(defaultFileName.getBytes("GBK"), "ISO8859_1");
//         response.setHeader("Content-disposition", "attachment; filename="
//                 + fileName);
//         JasperPrint jasperPrint = new JasperPrintWithDataSource(paramMap, jasperFile, dataSource).getJasperPrint();
//
// //        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,paramMap, getConn());
//         //设置导出输出流
//         JRExporter exporter = new JRRtfExporter();
//         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
//                 response.getOutputStream());
//         exporter.exportReport();
//     }
//
//
//     /**
//      * 导出html文件
//      * @param jasperFile
//      * @param paramMap
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToHtml(String jasperFile, Map<String,Object> paramMap, HttpServletRequest request,
//                                     HttpServletResponse response, JRDataSource dataSource) throws IOException, JRException {
//         response.setContentType("text/html");
//         JasperPrint jasperPrint = new JasperPrintWithDataSource(paramMap, jasperFile, dataSource).getJasperPrint();
// //        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,paramMap, getConn());
//         ServletOutputStream ouputStream = response.getOutputStream();
//         JRHtmlExporter exporter = new JRHtmlExporter();
//         exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
//                 Boolean.FALSE);
//         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//         exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
//         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
//         //设置图片文件存放路径，此路径为服务器上的绝对路径
//         String imageDIR =request.getSession().getServletContext().getRealPath("/");
//         exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, imageDIR);
//
//         //设置图片请求URI
//         String imageURI = request.getContextPath() + "/";
//         exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imageURI);
//
//         //设置导出图片到图片存放路径
//         exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.TRUE);
//         exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
//         exporter.exportReport();
//
//         ouputStream.flush();
//         ouputStream.close();
//     }
//
//
//     /**
//      * 导出xls文件
//      * @param jasperFile
//      * @param paramMap
//      * @param defaultFileName
//      * @param request
//      * @param response
//      * @throws IOException
//      * @throws JRException
//      */
//     public static void exportToExcel(String jasperFile, Map<String,Object> paramMap, String defaultFileName, HttpServletRequest request,
//                                      HttpServletResponse response, JRDataSource dataSource) throws IOException, JRException {
//         if (StringUtils.isEmpty(defaultFileName)){
//             defaultFileName =  "export.xls";
//         }else {
//             defaultFileName = defaultFileName + ".xls";
//         }
//         String fileName = new String(defaultFileName.getBytes("GBK"), "ISO8859_1");
//         response.setHeader("Content-disposition", "attachment; filename="
//                 + fileName);
//         JasperPrint jasperPrint = new JasperPrintWithDataSource(paramMap, jasperFile, dataSource).getJasperPrint();
//         //设置导出输出流
//         ServletOutputStream ouputStream = response.getOutputStream();
//         JRXlsExporter exporter = new JRXlsExporter();
//         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
//         // 删除记录最下面的空行
//         exporter.setParameter(
//                 JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
//                 Boolean.TRUE);
//         // 删除多余的ColumnHeader
//         exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
//                 Boolean.FALSE);
//         // 显示边框
//         exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
//                 Boolean.FALSE);
//         exporter.exportReport();
//         ouputStream.flush();
//         ouputStream.close();
//     }
//
// }
