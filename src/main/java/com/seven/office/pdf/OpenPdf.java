package com.seven.office.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class OpenPdf {
    @Test
    public void test() throws IOException {
        Document document = new Document();
        BaseFont SIMSUN = BaseFont.createFont("C:\\Windows\\Fonts\\simsun.ttc,0", BaseFont.IDENTITY_H, true);
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont, 14f);
        PdfWriter.getInstance(document, new FileOutputStream("C:/home/openpdf.pdf"));
        document.open();
        document.add(new Paragraph("hello world, 哈后噶和我父亲", font));
        Table table = new Table(3, 3);
        table.addCell(new Cell(new Chunk("好好韩国", font)));
        document.add(table);
        document.close();
    }
}
