package com.dodoca.create_image.controller;

import com.dodoca.create_image.exception.BaseResult;
import com.dodoca.create_image.exception.ResultUtil;
import com.dodoca.create_image.service.QRCodeCreateService;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: tianguanghui
 * @create: 2019-07-03 14:57
 **/
@RestController
public class GenerateQRCodeController {

    @Autowired
    QRCodeCreateService qrCodeCreateServiceImpl;


    @RequestMapping(value = "/generateQRCodeStream", method = RequestMethod.GET)
    public void generateQRCodeStream(String content, HttpServletResponse response) {
        qrCodeCreateServiceImpl.generateQRCodeStream(content, response);
    }



}
