package com.dodoca.create_image.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import java.awt.*;
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

    public static String overlapImage(String backgroundPath, String weChatAvatarPath, String goodsImagePath, String qrCodePath, String message01, String message02, String goodsTile, String goodsPrice, String outPutPath) throws IOException {
        //创建一个不带透明色的对象作为背景图
        BufferedImage backgroundPathBufferedImage = ImageIO.read(new File(backgroundPath));
        BufferedImage background = resizeImage(415, 775, backgroundPathBufferedImage);
//        BufferedImage background = new BufferedImage(415, 775, BufferedImage.TYPE_INT_RGB);
        //读取微信头像图片到内存
        BufferedImage weChatAvatarBufferedImage = ImageIO.read(new File(weChatAvatarPath));
        BufferedImage weChatAvatar = resizeImage(50, 50, weChatAvatarBufferedImage);
        //读取商品图片
        BufferedImage goodsImageBufferedImage = ImageIO.read(new File(goodsImagePath));
        BufferedImage goodsImage = resizeImage(415, 400, goodsImageBufferedImage);
        //读取二维码图片到内存
        BufferedImage qrCodeBufferedImage = ImageIO.read(new File(qrCodePath));
        BufferedImage qrCode = resizeImage(135, 135, qrCodeBufferedImage);

        //在背景图片中添加入需要写入的信息，例如：扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！
        //String message = "扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！";
        Graphics2D g = background.createGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
//        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑", Font.BOLD, 10));
        g.drawImage(weChatAvatar, 20, 20, weChatAvatar.getWidth(), weChatAvatar.getHeight(), null);
        g.drawString(message01, 90, 20);
        g.drawString(message02, 90, 40);
        g.drawImage(goodsImage, 0, 100, goodsImage.getWidth(), goodsImage.getHeight(), null);
        g.drawString(goodsTile, 20, 500);
        g.drawString(goodsPrice, 20, 520);
        g.drawImage(qrCode, 30, 540, qrCode.getWidth(), qrCode.getHeight(), null);
        g.dispose();
//            ImageIO.write(background, "jpg", new File("这里是一个输出图片的路径"));
        ImageIO.write(background, "jpg", new File(outPutPath));
        return outPutPath;
    }

    private static BufferedImage resizeImage(int x, int y, BufferedImage bfi) {
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
    public BufferedImage loadImageLocal(String imgPath) throws IOException {
        return ImageIO.read(new File(imgPath));
    }


    /**
     * 导入网络图片到缓冲区
     *
     * @param imgName
     * @return
     */
    public BufferedImage loadImageUrl(String imgName) throws IOException {
        URL url = new URL(imgName);
        return ImageIO.read(url);
    }
}
