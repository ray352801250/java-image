package com.dodoca.create_image.controller;

import com.dodoca.create_image.utils.BufferedImageUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.dodoca.create_image.constants.ActivityType.SECKILL;

/**
 * @description:
 * @author: TianGuangHui
 * @create: 2019-07-10 11:22
 **/
@RestController
public class GoodsShareImageController {


    @RequestMapping(value = "/testOverlapImage", method = RequestMethod.GET)
    public void test(HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/");
        System.out.println("path: " + path);
        path = path.replaceAll("\\\\", "/");
        System.out.println("path: " + path);
        BufferedImageUtil.overlapImage("http://thirdwx.qlogo.cn/mmopen/vi_32/ZqYOBqC5fPibylCwdzwZtQx0ibibS9IQFWb5nV2jS7qo0wic6vtzUJAzzlJ8Tia1LtbKx7Tnf9VJxK2sLIZ4P2Ir1ZQ/132", "https://ms.wrcdn.com/2019/06/15/FkmDAQlZdaeFn6FgRLaaXkIx4BZY.jpg",
                "D:\\image\\ten.jpg", "我是小月亮", "我为一哥悠购代言",
                "MY GUEST男士冰丝隐形袜 5双装", "￥49.00", "D:\\image\\overlap9.jpg", SECKILL);

    }
}
