package com.seven.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Barcode4j {
    /**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }


        //修改格式
        Code128Bean bean = new Code128Bean();


        // 精细度
        final int dpi = 50;
        // module宽度
        final double moduleWidth = UnitConv.mm2pt(0.25d);
        final double barHeight = UnitConv.pt2mm(32d);
        // System.out.println(UnitConv.p);
        // 配置对象
        bean.setModuleWidth(moduleWidth);
        // bean.setWideFactor(3);
        // msg高度
        bean.setBarHeight(barHeight);
        // 白边显示
        bean.doQuietZone(true);
        bean.setQuietZone(2.0d);
        // 字体大小
        bean.setFontSize(8d);
        // 支持的字体 "OCR-B,Helvetica,Arial";
        bean.setFontName("Arial");
        System.out.println(bean.getBarWidth(1));
        System.out.println(bean.getBarWidth(2));
        System.out.println(moduleWidth);
        String format = "image/png";
        try {

            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY,
                    false, 0);

            // 生成条形码
            bean.generateBarcode(canvas, msg);

            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String msg = "E391482S0066";
        String path = "C:/home/barcode1.png";
        generateFile(msg, path);
    }
    @Test
    public void contextLoads() {
        //生成条形码数字
        String msg = "88886666";
        //生成位置信息
        String path = "C:/home/666.png";
        //生成高度
        int height = 160;
        //生成宽度
        double width = UnitConv.in2mm(1.0f / height);
        //输出流
        File file = new File(path);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Code39Bean c39 = new Code39Bean();
        // 设置属性
        c39.setModuleWidth(width);
        c39.setWideFactor(2);
        // 输出类型
        String format = "image/png";
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, format, height, BufferedImage.TYPE_BYTE_BINARY,false, 0);


        // 生成图片
        c39.generateBarcode(canvas, msg);
        try {
            // 关闭流
            canvas.finish();
            System.out.println("ok...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Test
    public void testZxing() {
        String str = "E391482S0066";
        File file = new File("C:/home/zxing.png");
        int width = 45;
        int height = 10;
        BitMatrix matrix = null;
        MultiFormatWriter writer = new MultiFormatWriter();
        matrix = writer.encode(str, BarcodeFormat.CODE_128, width, height, null);
        try (FileOutputStream outputStream = new FileOutputStream(file)){
            ImageIO.write(MatrixToImageWriter.toBufferedImage(matrix), "png", outputStream);
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    @Test
    public void testUtil() {
        BufferedImage image = GoogleBarCodeUtils.insertWords(GoogleBarCodeUtils.getBarCode("34101020100000112340"), "34101020100000112340");
        ImageIO.write(image, "png", new File("C:/home/zxing2.png"));
    }
}
