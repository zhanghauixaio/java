package com.seven.office.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Word {
    @SneakyThrows
    @Test
    public void test() {
        // File file = new File("src/main/java/template.docx");
        // FileInputStream inputStream = new FileInputStream(file);
        InputStream stream = Word.class.getClassLoader().getResourceAsStream("template.docx");
        XWPFDocument document = new XWPFDocument(stream);
        Iterator<XWPFTable> iterator = document.getTablesIterator();
        while (iterator.hasNext()) {
            XWPFTable table = iterator.next();
            List<XWPFTableRow> rows = table.getRows();
            int i = 1;
            for (XWPFTableRow row : rows) {
                System.out.println("-----row " + i + " -----");
                i++;
                List<XWPFTableCell> cells = row.getTableCells();
                int j = 1;
                for (XWPFTableCell cell : cells) {
                    System.out.println("-----column " + j + "----------");
                    j++;
                    System.out.println(cell.getText());
                }
            }
            // table.getColBandSize()
        }
    }
    @SneakyThrows
    @Test
    public void testTemplate() {
        File file = new File("src/main/java/template.docx");
        XWPFTemplate template = XWPFTemplate.compile(file);
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "John");
        map.put("xxx", "haohfa");
        map.put("1", Pictures.ofLocal("C:/home/barcode1.png").sizeInCm(3.9d, 0.8d).create());
        template.render(map);
        template.writeAndClose(new FileOutputStream("out.docx"));
    }
}
