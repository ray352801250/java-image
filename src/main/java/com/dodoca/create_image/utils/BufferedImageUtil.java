package com.dodoca.create_image.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @description: 处理图片和成
 * @author: tianguanghui
 * @create: 2019-07-03 13:30
 **/
public class BufferedImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(BufferedImageUtil.class);

    public static String overlapImage(String weChatAvatarPath, String goodsImagePath,
                                      String qrCodePath, String message01, String message02, String goodsTile,
                                      String goodsPrice, String outPutPath) throws IOException {
        //创建一个不带透明色的对象作为背景图
        BufferedImage background = new BufferedImage(415, 750, BufferedImage.TYPE_INT_RGB);
        //读取微信头像图片到内存
//        BufferedImage weChatAvatarBufferedImage = ImageIO.read(new File(weChatAvatarPath));
        BufferedImage weChatAvatarBufferedImage = getBufferedImageByPath(weChatAvatarPath);
        BufferedImage weChatAvatar1 = resizeImage(100, 100, weChatAvatarBufferedImage);
        BufferedImage weChatAvatar = transferImgForRoundImage(false, weChatAvatar1);
        //读取商品图片
//        BufferedImage goodsImageBufferedImage = ImageIO.read(new File(goodsImagePath));
        BufferedImage goodsImageBufferedImage = getBufferedImageByPath(goodsImagePath);
        BufferedImage goodsImage = resizeImage(415, 400, goodsImageBufferedImage);
        //读取二维码图片到内存
//        BufferedImage qrCodeBufferedImage = ImageIO.read(new File(qrCodePath));
        BufferedImage qrCodeBufferedImage = getBufferedImageByPath(goodsImagePath);
        BufferedImage qrCode = resizeImage(135, 135, qrCodeBufferedImage);
        Graphics2D g = background.createGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, background.getWidth(), background.getHeight());
        int directAxis = 20;
        g.drawImage(goodsImage, 0, directAxis, goodsImage.getWidth(), goodsImage.getHeight(), null);
        directAxis += goodsImage.getHeight();
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        directAxis += 10;
        g.drawString(goodsTile, 20, directAxis);
        directAxis += 30;
        g.setColor(Color.RED);
        g.drawString(goodsPrice, 20, directAxis);
        directAxis += 80;
        g.drawImage(weChatAvatar, 20, directAxis, weChatAvatar.getWidth(), weChatAvatar.getHeight(), null);
        g.setColor(Color.black);
        g.drawString(message01, 25 + weChatAvatar.getWidth(), directAxis + weChatAvatar.getHeight()/2 - 20);
        g.drawString(message02, 25 + weChatAvatar.getWidth(), directAxis + weChatAvatar.getHeight()/2 + 5);
        g.drawImage(qrCode, background.getWidth() - 155, directAxis - 10, qrCode.getWidth(), qrCode.getHeight(), null);
        g.dispose();
        ImageIO.write(background, "jpg", new File(outPutPath));
        return outPutPath;
    }

    public static BufferedImage resizeImage(int x, int y, BufferedImage bfi) {
        //创建一个不带透明色的对象
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        //获得在图像上绘图的Graphics对象
        bufferedImage.getGraphics().drawImage(
                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

    /**
     * 导入本地图片到缓冲区
     *
     * @param imgPath
     * @return
     */
    public static BufferedImage loadImageLocal(String imgPath) throws IOException {
        return ImageIO.read(new File(imgPath));
    }


    /**
     * 导入网络图片到缓冲区
     *
     * @param imgName
     * @return
     */
    public static BufferedImage loadImageUrl(String imgName) throws IOException {
        URL url = new URL(imgName);
        return ImageIO.read(url);
    }

    /**
     * 将方形图片转换为圆形
     * @param bi 图片地址
     * @return
     * @throws IOException
     */
    public static BufferedImage transferImgForRoundImage(boolean isBlank, BufferedImage bi) throws IOException {
        // 创建一个带透明色的BufferedImage
        BufferedImage image = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        // 创建一个椭圆形的2D图像
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi.getWidth(), bi.getHeight());
        Graphics2D g2 = image.createGraphics();
        //控制内切圆外的区域颜色为黑色
        if (isBlank) {
            g2.setComposite(AlphaComposite.Clear);
        }
        //设置背景为白色
        g2.setBackground(Color.WHITE);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
        g2.setClip(shape);
        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(bi, 0, 0, null);
        g2.dispose();
        return image;
    }

    /**
     * 根据图片全路径 加载图片
     * @param imagePath 图片全路径
     * @return
     * @throws IOException
     */
    public static BufferedImage getBufferedImageByPath(String imagePath) throws IOException {
        if (imagePath.startsWith("http")) {
            return loadImageUrl(imagePath);
        }
         return loadImageLocal(imagePath);
    }

}
