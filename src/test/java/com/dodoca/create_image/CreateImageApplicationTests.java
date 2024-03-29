package com.dodoca.create_image;

import com.dodoca.create_image.service.QRCodeCreateService;
import com.dodoca.create_image.utils.BufferedImageUtil;
import com.dodoca.create_image.utils.QRCodeGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static com.dodoca.create_image.constants.ActivityType.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateImageApplicationTests {

    @Autowired
    QRCodeCreateService qrCodeCreateServiceImpl;

    @Test
    public void contextLoads() {
    }

    @Before
    public void before() {
        System.out.println("开始时间: " + System.currentTimeMillis());
    }
    @After
    public void after() {
        System.out.println("结束时间: " + System.currentTimeMillis());
    }


    /**
     * 测试生成指定内容的二维码
     */
    @Test
    public void testQRCodeCreateService() {
        qrCodeCreateServiceImpl.generateQRCodeImage("https://shop13290509.wxrrd.com/goods/121509756");
    }

    /**
     * 测试图片合成
     */
    @Test
    public void testOverlapImage() throws IOException {
//        BufferedImageUtil.overlapImage("D:\\images\\123.jpg", "D:\\images\\goods.jpg",
//                "D:\\images\\ten.jpg", "我是小月亮", "我为一哥悠购代言",
//                "Mheiihoo魅护山茶弹力美肌沐浴露  300ml", "￥49.00", "D:\\images\\overlap7.jpg");
        BufferedImageUtil.overlapImage("http://thirdwx.qlogo.cn/mmopen/vi_32/ZqYOBqC5fPibylCwdzwZtQx0ibibS9IQFWb5nV2jS7qo0wic6vtzUJAzzlJ8Tia1LtbKx7Tnf9VJxK2sLIZ4P2Ir1ZQ/132", "https://ms.wrcdn.com/2019/06/15/FkmDAQlZdaeFn6FgRLaaXkIx4BZY.jpg",
                "D:\\image\\ten.jpg", "我是小月亮", "我为一哥悠购代言",
                "MY GUEST男士冰丝隐形袜 5双装", "￥49.00", "D:\\image\\overlap9.jpg", PINTUAN);
    }

    /**
     * 测试添加logo到二维码中心
     * @throws Exception
     */
    @Test
    public void testCreatePhotoAtQRCodeCenter() throws Exception {
        QRCodeGenerator.createPhotoAtQRCodeCenter("https://shop13290509.wxrrd.com/goods/120301566",
                300 ,300 , "D:\\images\\logo1.jpg", "jpg", "D:\\images\\ten.jpg");
    }

    /**
     * 测试转换图片为圆形
     */
    @Test
    public void testTransferImgForRoundImage() throws IOException {
        BufferedImage bufferedImageByPath = BufferedImageUtil.getBufferedImageByPath("D:\\images\\123.jpg");
        BufferedImage bufferedImage = BufferedImageUtil.transferImgForRoundImage(true, bufferedImageByPath);
        if (bufferedImage != null) {
            System.out.println("===");
            ImageIO.write(bufferedImage, "JPG", new File("D:\\images\\456.jpg"));
        }
    }

}
