package com.magustek.com.htxtpc.util.common.util;

import java.util.Base64;
import java.util.UUID;

public class CommonUtil {

    private static final Base64.Decoder decoder = Base64.getDecoder();  //解码器
    private static final Base64.Encoder encoder = Base64.getEncoder();  //编码器

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    /**
     * base64编码
     * @param bytes
     * @return
     */
    public static String encodeByBase64(byte[] bytes) {
        return encoder.encodeToString(bytes);
    }

}
