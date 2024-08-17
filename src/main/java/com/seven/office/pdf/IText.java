package com.seven.office.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.seven.utils.AsposeUtil;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class IText {
    @SneakyThrows
    @Test
    public void test() {
        String sourceFile = "src/main/java/com/seven/pdf/test.pdf";
        String targetFile = "src/main/java/com/seven/pdf/out.pdf";
        // 读取源文件
        PdfReader reader = new PdfReader(sourceFile);

        Rectangle pageSize = reader.getPageSize(1);
        Rectangle rectangle = new Rectangle(PageSize.A4);

        // 创建新的文档
        Document document = new Document(rectangle);
        // 创建目标PDF文件
        FileOutputStream outputStream = new FileOutputStream(targetFile);
        PdfCopy pdfCopy = new PdfSmartCopy(document, outputStream);

        // 获取源文件的页数
        int pages = reader.getNumberOfPages();
        float height = pageSize.getHeight();
        float width = pageSize.getWidth();

        document.open();
        // 判断横向、纵向
        PdfDictionary page;
        PdfNumber rotate;
        for (int p = 1; p <= pages; p++) {
            page = reader.getPageN(p);
            rotate = page.getAsNumber(PdfName.ROTATE);
            if (rotate == null) {
                page.put(PdfName.ROTATE, new PdfNumber(90));
            } else {
                page.put(PdfName.ROTATE, new PdfNumber((rotate.intValue() + 90) % 360));
            }
            // pdfCopy.addPage(pdfCopy.getImportedPage(reader, page));
        }

        reader.close();
        document.close();
        pdfCopy.close();
        outputStream.close();
    }

    @SneakyThrows
    @Test
    public void test1() {
        String sourceFile = "src/main/java/com/seven/pdf/唛头模板.xlsx";
        String targetFile = "src/main/java/com/seven/pdf/纵向.pdf";
        InputStream inputStream = new FileInputStream(sourceFile);
        AsposeUtil.excel2pdf(inputStream, targetFile);
    }
}
