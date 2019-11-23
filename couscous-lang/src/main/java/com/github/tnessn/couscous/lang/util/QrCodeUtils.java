package com.github.tnessn.couscous.lang.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QrCodeUtils {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static final int ON_COLOR = BLACK;   //前景色
    private static final int OFF_COLOR = WHITE;  //背景色


    public static BitMatrix encode(String contents, int width, int height) throws WriterException {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定边框
        hints.put(EncodeHintType.MARGIN, 2);

        // 设置生成二维码的类型
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

        return bitMatrix;
    }


    public static void encode(String contents, int width, int height, String imgPath, String format) throws Exception {
        encode(contents, width, height, imgPath, format, null);
    }

    public static void encode(String contents, int width, int height, String imgPath, String format, String logoPath) throws Exception {
        try {
            //MatrixToImageWriter.writeToPath(bitMatrix, FORMAT, imgPath, new MatrixToImageConfig(ON_COLOR , OFF_COLOR));

            BitMatrix bitMatrix = encode(contents, width, height);

            writeToFile(bitMatrix, imgPath, new MatrixToImageConfig(ON_COLOR , OFF_COLOR), format, logoPath);

        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception();
        }
    }


    public static void writeToFile(BitMatrix matrix, String imgPath, MatrixToImageConfig config, String format, String logoPath) throws IOException {


        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix, config);

        //添加logo
        if(StringUtils.isNotEmpty(logoPath)){
            image = overlapImage(image, imgPath, logoPath);
        }
        ImageIO.write(image, format, new File(imgPath));
    }

    /**
     * 二维码添加自定义logo
     */
    public static BufferedImage overlapImage(BufferedImage image, String imgPath, String logoPath) throws IOException {
        BufferedImage logo = ImageIO.read(new File(logoPath));
        Graphics2D g = image.createGraphics();
        int width=image.getWidth()/5;
        int height=image.getHeight()/5;
        int x=(image.getWidth()-width)/2;
        int y=(image.getHeight()-height)/2;
        g.drawImage(logo, x, y, width, height, null);
        g.dispose();
        return image;
    }

    /**
     * 去白边
     * @param qrCode
     * @param width
     * @param height
     * @return
     */
    private static BitMatrix render(QRCode qrCode, int width, int height) {
        ByteMatrix input = qrCode.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth ;
        int qrHeight = inputHeight;
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);
        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;

        if(leftPadding >= 0 ) {
            outputWidth = outputWidth - 2 * leftPadding ;
            leftPadding = 0;
        }
        if(topPadding >= 0) {
            outputHeight = outputHeight - 2 * topPadding;
            topPadding = 0;
        }

        BitMatrix output = new BitMatrix(outputWidth, outputHeight);

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    output.setRegion(outputX, outputY, multiple, multiple);
                }
            }
        }

        return output;
    }
}
