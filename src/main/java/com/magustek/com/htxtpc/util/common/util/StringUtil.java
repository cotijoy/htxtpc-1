package com.magustek.com.htxtpc.util.common.util;

/**
 *
 * 功能描述： 字符串处理的工具类 <br>
 */
public class StringUtil {

    /**
     * 检查字符串是否为null或空字符串""。
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * @param str 要检查的字符串
     * @return 如果为空, 则返回true
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }
}
