package com.dodoca.create_image.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

import static de.ailis.pherialize.Pherialize.unserialize;

/**
 * @description:
 * @author: tianguanghui
 * @create: 2019-07-08 09:33
 **/
public class AESUtil {

    // 共通鍵
    private static final String ENCRYPTION_KEY = "QqRb4d2TlBcE0SY8xLycs6mMPUPpImeb";
    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String str = "eyJpdiI6InFoS2JjTlwvNzRUTzJmS0FjcDVxWThRPT0iLCJ2YWx1ZSI6IjlDVlwvVWpOQXVEaDdleUh4and0NUVLNmc2R1FcL09cL0FnbVdiXC9yc2VrNEZPd1BXaXgwWUlXb25LWDdVWDhURXcraHVyc1lFeXh0RkVQZjdZbG5SMWNqdz09IiwibWFjIjoiNWE5ZDdhZmIwMTFiOWNiYmM2MDkwMDhmODdjM2E2OTUwMGFhMjkxYTQ0MzM5OTcyMzY2NmFlNTZlNTg4MjJmMyJ9";

        System.out.println(getCacheInfo(str));
    }


    public static String decrypt(String src, String iv) {
        String decrypted = "";
        try {
            cipher.init(Cipher.DECRYPT_MODE, makeKey(), makeIv(iv));
            decrypted = new String(cipher.doFinal(Base64.decode(src)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decrypted;
    }

    static AlgorithmParameterSpec makeIv(String iv) {
        try {
            return new IvParameterSpec(Base64.decode(iv));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static Key makeKey() {
        try {
            // MessageDigest md = MessageDigest.getInstance("SHA-256");
            // byte[] key = md.digest("QqRb4d2TlBcE0SY8xLycs6mMPUPpImeb".getBytes("UTF-8"));
            return new SecretKeySpec(ENCRYPTION_KEY.getBytes("UTF-8"), "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String,String> getCacheInfo(String wxrrdWapSession){
        Map<String,String> result = new HashMap<>();
        if (wxrrdWapSession == null || wxrrdWapSession.isEmpty()) {
            return result;
        }
        JSONObject jsonObject = JSON.parseObject(new String(Base64.decode(wxrrdWapSession)));
        String encrypted = jsonObject.getString("value");
        String cacheId = unserialize(AESUtil.decrypt(encrypted, jsonObject.getString("iv"))).toString();
        //取chache的数据
        //String cacheInfo = Cache.get("laravel:"+cacheId);
        String cacheInfo = "a:11:{s:6:\"_token\";s:40:\"dbPaaNSCAEmXrnQOpaJK2yxnxFq81pG8IgEniz3N\";s:9:\"_previous\";a:1:{s:3:\"url\";s:22:\"https://hnym.wxrrd.com\";}s:5:\"flash\";a:2:{s:3:\"old\";a:0:{}s:3:\"new\";a:0:{}}s:6:\"member\";a:11:{s:2:\"id\";i:34202909;s:14:\"member_account\";i:10034204925;s:13:\"mobile_prefix\";s:3:\"+86\";s:6:\"mobile\";s:11:\"13501009425\";s:4:\"name\";s:6:\"老幺\";s:6:\"avatar\";s:64:\"https://ms.wrcdn.com/2019/06/20/FhSEhx5f2zf_Dx-34uPmbZy3cbpI.jpg\";s:16:\"is_verify_mobile\";i:1;s:6:\"gender\";i:1;s:7:\"country\";s:9:\"阿根廷\";s:8:\"province\";s:21:\"布宜诺斯艾利斯\";s:4:\"city\";s:0:\"\";}s:6:\"guider\";a:2:{s:11:\"gid13325765\";i:13964834;s:11:\"gid13336092\";i:22901114;}s:7:\"partner\";a:1:{s:11:\"pid13325765\";i:95404;}s:15:\"fromuid13325765\";i:34202909;s:15:\"fromuid13336092\";i:13753230;s:37:\"guider_invitation_13336092_34202909_0\";s:19:\"2019-07-02 10:11:30\";s:37:\"guider_invitation_13336092_34202909_1\";s:19:\"2019-07-02 10:11:30\";s:9:\"_sf2_meta\";a:3:{s:1:\"u\";i:1562222899;s:1:\"c\";i:1561974221;s:1:\"l\";s:1:\"0\";}}";
        result = (HashMap)unserialize(cacheInfo).getValue();
        return result;
    }
}
