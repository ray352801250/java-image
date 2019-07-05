package com.dodoca.create_image.service.impl;

import com.dodoca.create_image.exception.MyServiceException;
import com.dodoca.create_image.service.QRCodeCreateService;
import com.dodoca.create_image.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: tianguanghui
 * @create: 2019-07-03 14:01
 **/
@Service
public class QRCodeCreateServiceImpl implements QRCodeCreateService {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeCreateServiceImpl.class);

    @Value("${QRCode_image_dir}")
    String QRCodeImageDir;

    @Override
    public String generateQRCodeImage(String QRCodeContent) {
        logger.info("generateQRCodeImage >>> QRCodeContent:{}", QRCodeContent);
        try {
            String filePath = QRCodeImageDir + "111.png";
            //根据具体业务设二维码名称和大小
            QRCodeGenerator.generateQRCodeImage(QRCodeContent, 350, 350, "jpg", filePath);
            return filePath;
        } catch (WriterException | IOException e) {
            logger.error(e.getMessage(), e);
            throw new MyServiceException("生成二维码异常", 1011);
        }
    }

    @Override
    public void generateQRCodeStream(String content, HttpServletResponse response) {
        try {
            QRCodeGenerator.generateQRCodeImageStream(content, 350, 350, response);
        } catch (WriterException e) {
            logger.error(e.getMessage(), e);
            throw new MyServiceException("生成二维码异常", 1011);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new MyServiceException("将二维码写入响应体异常", 1012);
        }
    }

}
