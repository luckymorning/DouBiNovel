package com.cn.lucky.morning.model.web.tools;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class CodeUtils {
    public static String MD5Pwd(String username, String pwd) {
        // 加密算法MD5
        // salt盐 username + salt
        // 迭代次数
        String md5Pwd = new SimpleHash("md5", pwd, ByteSource.Util.bytes(username + "salt"), 2).toHex();
        return md5Pwd;
    }
}
