package com.seven.utils;

import cn.hutool.core.io.FileUtil;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.License;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author zcr
 * @version 1.0
 * @date 2023/3/15 10:28
 */
@Slf4j
public class AsposeUtil {
    static {
        com.aspose.words.FontSettings.setDefaultFontName("SimSun");
    }


    private static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean getLicenseExcel() {
        boolean result = false;
        try {
            System.out.println(AsposeUtil.class.getClassLoader().getResource("").getPath());
            InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static InputStream license;

    public static boolean getLicensePPT() {
        boolean result = false;
        try {
            license = AsposeUtil.class.getClassLoader().getResourceAsStream("\\license.xml");
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static File doc2pdf(InputStream excelInput, String pdfPath) {
        File pdfFile = null;
        try {
            pdfFile = new File(pdfPath);
            if (pdfFile.exists()) {
                return pdfFile;
            }
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            if (!getLicense()) {
                return pdfFile;
            }
            //新建一个pdf文档
            BufferedOutputStream os = FileUtil.getOutputStream(pdfPath);
            //Address是将要被转化的word文档
            Document doc = new Document(excelInput);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            long now = System.currentTimeMillis();
            os.close();
            //转化用时
            pdfFile = new File(pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfFile;
    }


    public static boolean ppt2pdf(String pptPath, String pdfPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicensePPT()) {
            return false;
        }
        try {
            //新建一个pdf文档
            File file = new File(pdfPath);

            FileOutputStream os = new FileOutputStream(file);
            Presentation ppt = new Presentation(pptPath);

            ppt.save(os, com.aspose.slides.SaveFormat.Pdf);
            os.close();
            //转化用时
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File excel2pdf(InputStream excelInput, String pdfPath) {
        File pdfFile = null;
        try {
            pdfFile = new File(pdfPath);
            if (pdfFile.exists()) {
                return pdfFile;
            }
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            if (!getLicenseExcel()) {
                return pdfFile;
            }
            // 原始excel路径
            Workbook wb = new Workbook(excelInput);
            PdfSaveOptions xlsSaveOption = new PdfSaveOptions();
            xlsSaveOption.setAllColumnsInOnePagePerSheet(true);
            //xlsSaveOption.setDefaultFont("SimSun");

            BufferedOutputStream fileOS = FileUtil.getOutputStream(pdfPath);
            wb.save(fileOS, xlsSaveOption);
            fileOS.close();

            pdfFile = new File(pdfPath);
            //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfFile;
    }
//
//    /**
//     * 文件转pdf
//     *
//     * @param srcPath 原文件路径
//     * @param pdfPath pdf保存路径
//     */
//    public static void convertToPdf(String srcPath, String pdfPath) {
//        File file = new File(srcPath);
//        ConvertFactory convertFactory = ConvertFactoryBuilder.build(FileUtil.getSuffix(file.getName()));
//        if (Objects.isNull(convertFactory)) {
//            log.error("文件：{} 不支持pdf转换", srcPath);
//            return;
//        }
//        CompletableFuture.runAsync(() -> {
//            convertFactory.execution(srcPath, pdfPath);
//        });
//    }

//    /**
//     * 文件转pdf
//     * pdf保存路径和原文件路径保存一致
//     * @param srcPath
//     */
//    public static void convertToPdf(String srcPath) {
//        File file = new File(srcPath);
//        if (!file.exists()) {
//            return;
//        }
//        String pdfPath = file.getParent() + "/" + FileUtil.getPrefix(file.getName()) + ".pdf";
//        convertToPdf(srcPath, pdfPath);
//
//    }
}

//class ConvertFactoryBuilder {
//    static Map<String, ConvertFactory> methodMap = new HashMap<String, ConvertFactory>() {
//        {
//            put("doc", AsposeUtil::doc2pdf);
//            put("docx", AsposeUtil::doc2pdf);
//            put("xlsx", AsposeUtil::excel2pdf);
//            put("xls", AsposeUtil::excel2pdf);
//            put("ppt", AsposeUtil::ppt2pdf);
//        }
//    };
//
//    public static ConvertFactory build(String suffix) {
//        return methodMap.get(suffix);
//    }
//}
