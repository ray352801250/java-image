package com.dodoca.create_image.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: tianguanghui
 * @create: 2019-07-03 14:01
 **/
public interface QRCodeCreateService {

    /**
     *  将指定内容生成二维码图片
     * @param QRCodeContent 要生成二维码的内容
     * @return 生成后的二维码图片全地址
     */
    String generateQRCodeImage(String QRCodeContent);


    /**
     * 将指定内容生成二维码加入响应结果
     * @param content 要生成二维码的内容
     * @param response 响应体
     */
    void generateQRCodeStream(String content, HttpServletResponse response);
}
