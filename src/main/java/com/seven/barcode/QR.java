package com.seven.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * zxing 生成二维码 条形码
 */
public class QR {
    // 生成QR码的方法
    public void generateQRCode(String data, int width, int height, String filePath) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符编码
            hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H); // 错误纠正级别
            hints.put(EncodeHintType.MARGIN, 1); // 二维码边距

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            // 创建BufferedImage对象来表示QR码
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            // 将QR码保存到文件
            File qrCodeFile = new File(filePath);
            ImageIO.write(image, "png", qrCodeFile);

            System.out.println("QR码已生成并保存到: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 生成条形码的方法
    public void generateBarcode(String data, int width, int height, String filePath) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符编码

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.CODE_128, width, height, hints);

            // 创建BufferedImage对象来表示条形码
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0 : 0xFFFFFF); // 生成黑色条和白色背景的条形码
                }
            }

            // 将条形码保存到文件
            File barcodeFile = new File(filePath);
            ImageIO.write(image, "png", barcodeFile);

            System.out.println("条形码已生成并保存到: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQr() {
       // generateQRCode("https://www.baidu.com", 30, 30, );
       //  像素/DPI = 英寸 * 25.4 = 毫米
       //  dpi = 96 1毫米约等于3.78像素
       //  generateBarcode("20240324Y1004", 170, 38, "C:/home/zxing3.png");
        generateBarcode("20240324Y1004", 170, 25, "C:/home/zxing4.png");
    }

}
