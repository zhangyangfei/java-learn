package com.zyf.javabase.regexp;

import java.util.regex.Pattern;

/**
 * 正则表达式
 */
public class RegExpTest {
    
    public static void main(String[] args){
//        test1();
//        test2();
        test3();

        // ^ 为匹配输入字符串的开始位置
        // $ 为匹配输入字符串的结束位置
        // + 号代表前面的字符必须至少出现一次
        // * 号代表前面的字符可以不出现，也可以出现一次或者多次（0次、或1次、或多次）
        // ? 问号代表前面的字符最多只可以出现一次（0次、或1次）

        // [0-9]+匹配多个数字， [0-9] 匹配单个数字，+ 匹配一个或者多个
        // abc$匹配字母 abc 并以 abc 结尾，$ 为匹配输入字符串的结束位置
    }

    private static void test1() {
        String str = "汉字";
        // 是否全部是汉字
        boolean isMatch = Pattern.matches("^[\u2E80-\u9FFF]+$", str);
        System.out.println(isMatch);

        boolean isMatch2 = str.matches("^[\u2E80-\u9FFF]+$");
        System.out.println(isMatch2);
    }

    private static void test2() {
        String str = "abc";
        boolean isMatch = Pattern.matches("[abc]?", str);
        System.out.println(isMatch);
    }

    private static void test3() {
        String str = "汉字Abc123-_";
        // 汉字、大小写字母、数字、横线、下划线、3到20位的长度
        boolean isMatch = Pattern.matches("^[A-Za-z0-9\u2E80-\u9FFF\\-_]{3,20}+$", str);
        System.out.println(isMatch);
    }
}
