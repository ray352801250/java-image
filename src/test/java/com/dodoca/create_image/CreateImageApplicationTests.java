package com.dodoca.create_image;

import com.dodoca.create_image.service.QRCodeCreateService;
import com.dodoca.create_image.utils.BufferedImageUtil;
import com.dodoca.create_image.utils.QRCodeGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateImageApplicationTests {

    @Autowired
    QRCodeCreateService qrCodeCreateServiceImpl;

    @Test
    public void contextLoads() {
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
        BufferedImageUtil.overlapImage("D:\\image\\backGround.jpg", "D:\\image\\123.jpg", "D:\\image\\goods.jpg",
                "D:\\image\\111.jpg", "我是小月亮", "我为一哥悠购代言",
                "Mheiihoo魅护山茶弹力美肌沐浴露  300ml", "￥ 49.00", "D:\\image\\seven.jpg");
    }

    @Test
    public void testcreatePhotoAtQRCodeCenter() throws Exception {
        long start = System.currentTimeMillis();
        QRCodeGenerator.createPhotoAtQRCodeCenter("https://shop13290509.wxrrd.com/goods/121509756", 135 ,135 , "D:\\image\\logo.jpg", "jpg", "D:\\image\\ten.jpg");
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start));

    }

}
