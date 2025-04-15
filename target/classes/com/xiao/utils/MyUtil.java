package com.xiao.utils;

import java.util.Random;

public class MyUtil {
    private static final Random r = new Random();

    // len长度的随机小写字母字符串
    public static String randomLetterStr(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= len; i++)
            sb.append((char) ((int)'a' + r.nextInt(26)));
        return sb.toString();
    }

    public static String randomNumStr(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= len; i++)
            sb.append(r.nextInt(10));
        return sb.toString();
    }
}
