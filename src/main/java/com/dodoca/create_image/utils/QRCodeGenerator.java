package com.dodoca.create_image.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @description:
 * @author: tianguanghui
 * @create: 2019-07-01 15:37
 **/
public class QRCodeGenerator {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static BitMatrix generateQRCode(String content, int width, int height) throws WriterException {
        //设置图片的文字编码以及内边框
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //边框距
        hints.put(EncodeHintType.MARGIN, 0);
        // 设置二维码纠错能力级别为H（最高）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        BitMatrix encode = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//        BufferedImage image = MatrixToImageWriter.toBufferedImage(encode);
        return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
    }

    /**
     * 将指定内容生成二维码图片并保存到指定位置
     * @param text 二维码内容
     * @param width 宽
     * @param height 高
     * @param filePath 生成的二维码存放路径
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImage(String text, int width, int height, String imageType, String filePath) throws WriterException, IOException {
        BitMatrix bitMatrix = generateQRCode(text, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, imageType, path);
    }

    /**
     * 将指定内容生成二维码图片并写入响应体
     * @param text 二维码内容
     * @param width 宽
     * @param height 高
     * @param response 响应体
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImageStream(String text, int width, int height, HttpServletResponse response) throws WriterException, IOException {
        BitMatrix bitMatrix = generateQRCode(text, width, height);
        //给相应添加头部信息，主要告诉浏览器返回的是图片流
        response.setHeader("Cache-Control", "no-store");
        // 不设置缓存
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream());
    }

    /**
     * 在二维码中间加入图片
     * @param content 二维码内容
     * @param width 宽
     * @param height 高
     * @param logoFile 中心logo图片
     * @param imgType 生成的图片类型
     * @param filePath 生成的图片路径
     * @throws Exception
     */
    public static void createPhotoAtQRCodeCenter(String content, int width, int height, String logoFile, String imgType, String filePath) throws Exception {
        BitMatrix bitMatrix = generateQRCode(content, width, height);
        BufferedImage bufferedImage = toBufferedImage(bitMatrix);
        BufferedImage logoFileBufferedImageByPath = BufferedImageUtil.getBufferedImageByPath(logoFile);
        BufferedImage logo1 = BufferedImageUtil.resizeImage(bufferedImage.getWidth()/3, bufferedImage.getHeight()/3, logoFileBufferedImageByPath);
        BufferedImage logo = BufferedImageUtil.transferImgForRoundImage(true, logo1);
        int imageWidth = logo.getWidth();
        int imageHeight = logo.getHeight();
        Graphics2D g = bufferedImage.createGraphics();
        //获取bufImg的中间位置
        int centerX = bufferedImage.getMinX() + bufferedImage.getWidth()/2 - imageWidth/2;
        int centerY = bufferedImage.getMinY() + bufferedImage.getHeight()/2 - imageHeight/2;
        g.drawImage(logo, centerX, centerY, imageWidth, imageHeight,null);
        g.dispose();
        bufferedImage.flush();
        ImageIO.write(bufferedImage, imgType, new File(filePath));
    }


    /**
     *
     * 将二维矩阵对象转换到 BufferedImage
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 解码（读取二维码图片中的文本信息）
     * @param p_w_picpathPath 二维码图片路径
     * @return 文本信息
     */
    public static String decode(String p_w_picpathPath) throws NotFoundException, IOException {
        // 返回的文本信息
        String content = "";
        // 创建图片文件
        File file = new File(p_w_picpathPath);
        if (!file.exists()) {
            return content;
        }
        BufferedImage p_w_picpath = null;
        p_w_picpath = ImageIO.read(file);
        if (null == p_w_picpath) {
            return content;
        }
        // 解码
        LuminanceSource source = new BufferedImageLuminanceSource(p_w_picpath);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result rs = new MultiFormatReader().decode(bitmap, hints);
        content = rs.getText();
        return content;
    }


}
