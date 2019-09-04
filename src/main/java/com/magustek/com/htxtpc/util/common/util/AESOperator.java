package com.magustek.com.htxtpc.util.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESOperator {

    private static String sKey = "aku5892335283216";
    private static String ivParameter = "2695139qxl920301";
    private static AESOperator aesOperator = null;

    private AESOperator () {}

    public static AESOperator getAESOperator () {
        if (aesOperator == null) {
            aesOperator = new AESOperator();
        }
        return aesOperator;
    }

    /**
     * 加密
     * @return String 加密后的字符串
     */
    public static String encrypt (String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes()); // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(str.getBytes("utf-8"));
        return java.util.Base64.getEncoder().encodeToString(encrypted);  //用Base64加密
    }

    /**
     * 解密
     * @return String 解密后的字符串
     */
    public static String  decrypt (String str) throws Exception {
        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw,"AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted1 = java.util.Base64.getDecoder().decode(str);  //先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;

    }
}
