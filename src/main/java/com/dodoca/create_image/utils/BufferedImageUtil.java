package com.dodoca.create_image.utils;

import com.dodoca.create_image.constants.ActivityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import static com.dodoca.create_image.constants.ActivityType.PINTUAN;
import static com.dodoca.create_image.constants.ActivityType.SECKILL;

/**
 * @description: 处理图片和成
 * @author: tianguanghui
 * @create: 2019-07-03 13:30
 **/
public class BufferedImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(BufferedImageUtil.class);

    public static String overlapImage(String weChatAvatarPath, String goodsImagePath,
                                      String qrCodePath, String message01, String message02, String goodsTile,
                                      String goodsPrice, String outPutPath, String activity) throws IOException {
        //创建一个不带透明色的对象作为背景图
        BufferedImage background = new BufferedImage(600, 950, BufferedImage.TYPE_INT_RGB);
        //读取微信头像图片到内存
        BufferedImage weChatAvatarBufferedImage = getBufferedImageByPath(weChatAvatarPath);
        BufferedImage weChatAvatar1 = resizeImage(100, 100, weChatAvatarBufferedImage);
        BufferedImage weChatAvatar = transferImgForRoundImage(false, weChatAvatar1);
        //读取商品图片
        BufferedImage goodsImageBufferedImage = getBufferedImageByPath(goodsImagePath);
        BufferedImage goodsImage = resizeImage(580, 580, goodsImageBufferedImage);
        //读取二维码图片到内存
        BufferedImage qrCodeBufferedImage = getBufferedImageByPath(qrCodePath);
        BufferedImage qrCode = resizeImage(132, 132, qrCodeBufferedImage);
        Graphics2D g = background.createGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, background.getWidth(), background.getHeight());
        int directAxis = 11;
        g.drawImage(goodsImage, 10, directAxis, goodsImage.getWidth(), goodsImage.getHeight(), null);
        directAxis += goodsImage.getHeight();
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.PLAIN, 28));
        directAxis += 38;
        if (goodsTile.length() >= 22) {
            String subTile1 = goodsTile.substring(0, 22);
            String subTile2 = goodsTile.substring(22);
            g.drawString(subTile1, 22, directAxis);
            directAxis += 38;
            g.drawString(subTile2, 22, directAxis);
        }else {
            g.drawString(goodsTile, 22, directAxis);
        }
        directAxis += 45;
        g.setFont(new Font("微软雅黑", Font.PLAIN, 32));
        g.setColor(Color.RED);
        g.drawString(goodsPrice, 20, directAxis);
        directAxis += 10;
        if (activity != null) {
            BufferedImage activityImage;
            String basePath = ResourceUtils.getURL("classpath:").getPath();
            logger.info("basePath : " + basePath);
            switch (activity){
                case SECKILL :
                    activityImage = getBufferedImageByPath(basePath + "static/images/miaosha.png");
                    break;
                case PINTUAN :
                    activityImage = getBufferedImageByPath(basePath + "static/images/pintuan.png");
                    break;
                default:
                    activityImage = null;
            }
            if (activityImage != null) {
                g.drawImage(activityImage, 20 , directAxis, activityImage.getWidth(), activityImage.getHeight(), null);
            }
        }
        //二维码具体顶端的距离
        directAxis = 746;
        g.drawImage(weChatAvatar, 24, directAxis + 30, weChatAvatar.getWidth(), weChatAvatar.getHeight(), null);
        g.setFont(new Font("宋体", Font.PLAIN, 28));
        g.setColor(Color.black);
        g.drawString(message01, 44 + weChatAvatar.getWidth(), directAxis + 24 +  weChatAvatar.getHeight()/2);
        g.setFont(new Font("宋体", Font.PLAIN, 24));
        g.setColor(Color.gray);
        g.drawString(message02, 44 + weChatAvatar.getWidth(), directAxis + 59 +  weChatAvatar.getHeight()/2);
        g.drawImage(qrCode, 426, directAxis, qrCode.getWidth(), qrCode.getHeight(), null);
        directAxis += qrCode.getHeight();
        g.setFont(new Font("宋体", Font.PLAIN, 22));
        g.drawString("长按识别二维码", 415, directAxis + 30);
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

    public static void main(String[] args) throws FileNotFoundException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String path2 = ResourceUtils.getURL("classpath:").getPath();
        System.out.println("path: " + path);
        System.out.println("path2: " + path2);

    }

}
