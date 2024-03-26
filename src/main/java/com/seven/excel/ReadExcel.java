package com.seven.excel;

import com.google.gson.Gson;
import com.seven.utils.AsposeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ReadExcel {
    @SneakyThrows
    @Test
    public void readExcel() {
        File file = new File("src/main/resources/office/国际模板.xlsx");
        String outPath = "src/main/resources/office/国际唛头格式说明out";
        Map<String, Object> map = new HashMap<>();
        map.put("{powerGear}", "580M");
        map.put("{workOrderNo}", "O12434");
        map.put("{moduleType}", "MD-HHAFG");
        map.put("{currentGear}", "H");
        map.put("{trayNo}", "20240323Y1004");
        map.put("{pic0}", "C:/home/zxing4.png");
        map.put("{pic1}", "C:/home/zxing4.png");
        map.put("{code1}", "20240323Y1004");
        map.put("{pic2}", null);
        map.put("{packingSize}", "123*143*343");
        map.put("{netWeight}", "1234.7");
        map.put("{grossWeight}", "124");
        map.put("{trayQty}", null);
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook;
        Sheet sheet;
        if (file.getName().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
            outPath = outPath + ".xlsx";
        } else {
            workbook = new HSSFWorkbook(inputStream);
            outPath = outPath + ".xls";
        }
        sheet = workbook.getSheetAt(0);
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        // System.out.println(sheet.getLastRowNum() + "---" + sheet.getPhysicalNumberOfRows());
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            // System.out.println(row.getLastCellNum() + "---" + row.getPhysicalNumberOfCells());
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                if (Objects.nonNull(cell) && cell.getCellType().name().equals("STRING")) {
                    String stringCellValue = cell.getStringCellValue();
                    if (stringCellValue.contains("{packingSize}") || stringCellValue.contains("{netWeight}")) {
                        String replaceStr = replaceStr(stringCellValue, map);
                        cell.setCellValue(replaceStr);
                    } else if (map.containsKey(stringCellValue)) {
                        if (Objects.isNull(map.get(stringCellValue))) {
                            cell.setCellValue("");
                        } else {
                            if (stringCellValue.contains("pic")) {
                                log.info("行：" + i + " 列：" + j);
                                String path = (String) map.get(stringCellValue);
                                Path path1 = Paths.get(path);
                                InputStream input = Files.newInputStream(path1);
                                BufferedImage image = ImageIO.read(input);
                                int width = Units.pixelToEMU(image.getWidth());
                                int height = Units.pixelToEMU(image.getHeight());
                                log.info("图片宽：" + width);
                                log.info("图片高：" + height);
                                log.info("列宽" + sheet.getColumnWidth(cell.getColumnIndex()));
                                log.info("行高" + row.getHeightInPoints());
                                log.info("列像素: " + sheet.getColumnWidthInPixels(cell.getColumnIndex()));
                                int pixelToEMU = Units.pixelToEMU((int) sheet.getColumnWidthInPixels(cell.getColumnIndex()));
                                int widthToEMU = Units.columnWidthToEMU(sheet.getColumnWidth(cell.getColumnIndex()));
                                log.info("列宽EMU: " + widthToEMU);
                                log.info("列像素EMU: " + pixelToEMU);
                                int heightEMU = Units.toEMU(row.getHeightInPoints());
                                log.info("行Point EMU" + heightEMU);
                                // float cellWidth = sheet.getColumnWidthInPixels(cell.getColumnIndex());
                                // float cellHeight = (row.getHeightInPoints() / 72) * 96;
                                // int dx1 = (int) ((cellWidth - width) / 2 / 96 * 72);
                                // int dy1 = (int) ((cellHeight - height) / 2 / 96 * 72);
                                // int dx2 = (int) ((cellWidth + width) / 2 / 96 * 72);
                                // int dy2 = (int) (cellHeight + height) / 2 / 96 * 72;
                                // 拿到合并单元格的列宽
                                if (i == 5 && j == 6) {
                                    for (int k = 7; k < row.getLastCellNum(); k++) {
                                        pixelToEMU += Units.pixelToEMU((int) sheet.getColumnWidthInPixels(k));
                                    }
                                    log.info("合并单元格列宽：" + pixelToEMU);
                                }
                                int dx1 = (pixelToEMU - width) / 2;
                                int dy1 = (heightEMU - height) / 2;
                                // ClientAnchor anchor = drawing.createAnchor(dx1, dy1, dx2, dy2, cell.getColumnIndex(), cell.getRowIndex(), cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
                                ClientAnchor anchor = drawing.createAnchor(dx1, dy1, 0, 0, cell.getColumnIndex(), cell.getRowIndex(), cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
                                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                                InputStream input1 = Files.newInputStream(path1);
                                int picIndex = workbook.addPicture(IOUtils.toByteArray(input1), Workbook.PICTURE_TYPE_PNG);
                                log.info("picIndex" + picIndex);
                                Picture picture = drawing.createPicture(anchor, picIndex);
                                picture.resize();
                                cell.setCellValue("");
                                input1.close();
                            } else {
                                cell.setCellValue((String) map.get(stringCellValue));
                            }
                        }
                    }
                    // System.out.println("行：" + i + " 列：" + j  +  " " + cell.getStringCellValue());
                }
                // Gson gson = new Gson();
                // String json = gson.toJson(cell);
                //
                // System.out.println(json);
            }
        }
        OutputStream outputStream = Files.newOutputStream(Paths.get(outPath));
        workbook.write(outputStream);
        // InputStream fileInputStream1 = new FileInputStream(outPath);
        // AsposeUtil.excel2pdf(fileInputStream1, "test.pdf");
        inputStream.close();
        outputStream.close();
    }

    private String replaceStr(String stringCellValue, Map<?, ?> map) {
        String re = "\\{([^\\}]+)\\}";
        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(stringCellValue);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        for (String s : list) {
            String res = "{" + s + "}";
            if (map.containsKey(res) && Objects.nonNull(map.get(res))) {
                stringCellValue = stringCellValue.replace(res, (CharSequence) map.get(res));
            } else {
                stringCellValue = stringCellValue.replace(res, StringUtils.leftPad("", 4, ""));
            }
        }
        return stringCellValue;
    }

    private Map<String, Integer> insertImageToCell(String imageFile) {
        Map<String, Integer> map = new HashMap<>();
        BufferedImage bufferImg;
        int maxWidth = 1023, maxHeight = 255;
        // x1=12左侧预留12宽度，y1=15上方预留15宽度
        int x1 = 12, y1 = 15, x2 = maxWidth, y2 = maxHeight;
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            File file = new File(imageFile);
            if (file.exists()) {
                long lenth = file.length();
                if (lenth > 10) {
                    bufferImg = ImageIO.read(file);
                    /* 原始图像的宽度和高度 */
                    int width = bufferImg.getWidth();
                    int height = bufferImg.getHeight();
                    if (width > height) {// 宽大于高
                        // 假设左右各留出12宽度，图片宽设置为最大，占据约1000（=maxWidth-12*2）
                        double rate = (double) (maxWidth - x1 * 2) / width;// 比例
                        int eHeight = (int) (height * rate);// 等比例算出需要的高度
                        // 如果图片高度+y1大于255，则设置图片的高度为255-y1=240，重新计算宽度
                        if (eHeight + y1 > maxHeight) {
                            rate = (double) (maxHeight - y1) / height;// 重新计算比例
                            int ewidth = (int) (width * rate);// 等比例算出需要的宽度
                            y2 = (maxHeight - y1);// y2坐标
                            x2 = ewidth + x1;
                            map.put("y2", y2);
                            map.put("x2", x2);
                            if (x2 < 1012) {// 如果图片不居中，重新计算x1位置，使图片居中
                                x1 = (maxWidth - x2) / 2;// 图片居中
                                x2 = ewidth + x1;
                            }
                            map.put("x1", x1);
                            map.put("x2", x2);
                        } else {
                            y2 = y1 + eHeight;// 这里就不设置图片上下居中了（保留图片距离上方15）
                            x2 = (maxWidth - x1 * 2);
                            map.put("y2", y2);
                            map.put("x2", x2);
                            map.put("x1", x1);
                            map.put("x2", x2);
                        }
                    } else {// 高大于宽
                        // 假设上留出15宽度，图片高设置为最大，占据240（=255-15）
                        double rate = (double) (maxHeight - y1) / height;// 比例
                        int ewidth = (int) (width * rate);// 等比例算出需要的宽度
                        // 如果图片高度>宽，高最大为240，宽最大为1012，所以宽不会超出
                        y2 = (maxHeight - y1);
                        x2 = ewidth + x1;
                        if (x2 < 1012) {// 如果图片不居中，重新计算x1位置，使图片居中
                            x1 = (maxWidth - x2) / 2;// 图片居中
                            x2 = ewidth + x1;
                        }
                        map.put("y2", y2);
                        map.put("x2", x2);
                        map.put("x1", x1);
                        map.put("x2", x2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
