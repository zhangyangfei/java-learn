package com.zyf.javabase.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * io流
 */
public class IoTest {

    private static String CHARSET_UTF_8 = "UTF-8";

    private static File fileRead = new File("java-base/io.txt");

    private static File fileWrite = new File("java-base/io2.txt");

    public static void main(String[] args) throws IOException {
//        readFile1();
//        readFile2();
//        readFile3();
//        readFile4();
//        readFile5();

//        writeFile1();
//        writeFile2(); // 常用
//        writeFile3();
//        writeFile4();// 常用
//        copyFile1();
//        copyFile2();
        copyFile3();
    }

    /**
     * 写文件方式1：字节流写
     */
    private static void writeFile1() {
        try (InputStream fis = new FileInputStream(fileRead);
             OutputStream fos = new FileOutputStream(fileWrite)) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
//                fos.flush();//不需要，继承父类获得方法，且是个空方法
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件方式2：字节缓冲流写
     */
    private static void writeFile2() {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileRead), 1024);
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileWrite), 1024)) {
            int len;
            byte[] bytes = new byte[1024];
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件方式3：字符流写
     */
    private static void writeFile3() {
        try (InputStream fis = new FileInputStream(fileRead);
             InputStreamReader isr = new InputStreamReader(fis, CHARSET_UTF_8);
             OutputStream os = new FileOutputStream(fileWrite);
             OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_UTF_8);) {
            int len;
            char[] chars = new char[1024];
            while ((len = isr.read(chars)) != -1) {
                osw.write(chars, 0, len);
                osw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件方式4：字符缓冲流写
     * <p>
     * 文件 --> 文件字节流 --> 字符流 --> 字符缓冲流
     * 字符 --> 字符缓冲流 --> 字符流 --> 文件字节流 --> 文件
     */
    private static void writeFile4() {
        try (// 读
             InputStream fis = new FileInputStream(fileRead);
             Reader isr = new InputStreamReader(fis, CHARSET_UTF_8);
             BufferedReader br = new BufferedReader(isr);
             // 写
             OutputStream os = new FileOutputStream(fileWrite);
             Writer osw = new OutputStreamWriter(os, CHARSET_UTF_8);
             BufferedWriter bw = new BufferedWriter(osw)) {
            /*
            // 方式A：缓存区读写
            int len;
            char[] chars = new char[1024];
            while ((len = br.read(chars)) != -1) {
                bw.write(chars, 0, len);
                bw.flush();
            }
            */
            // 方式B：按照行读写
            // 按照行读（一行数据可以解析成一个数据对象时适用）
            String line;
            while ((line = br.readLine()) != null) {
                // 按照行写（一个数据对象需要解析成一行数据时适用）
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =======================================上面是写=====================================================
    // =======================================下面是读=====================================================


    /**
     * 读文件方式1：字节流读取文件
     */
    private static void readFile1() throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(fileRead);
        int len;
        /*
            缓存数组，固定长度。
            read向缓存数组存入数据可能填不满，那么剩余的元素是之前操作的残留，本次不能取，故需要指定偏移量（为0）和长度。
         */
        byte[] bytes = new byte[1024];
        while ((len = fis.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, CHARSET_UTF_8));
        }
        fis.close();
        System.out.println(sb);
    }

    /**
     * 读文件方式2：字节缓冲流读取文件
     * <p>
     * File对象 ---> 文件字节流 ---> 字节缓冲流
     */
    private static void readFile2() throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(fileRead);
        BufferedInputStream bis = new BufferedInputStream(fis, 1024);
        int len;
        byte[] bytes = new byte[1024];
        while ((len = bis.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, CHARSET_UTF_8));
        }
        bis.close();
        fis.close();
        System.out.println(sb);
    }

    /**
     * 读文件方式3：字符流读取文件
     * <p>
     * 文件全路径名 ---> 文件字节流 FileInputStream ---> 字节流转字符流 InputStreamReader
     */
    private static StringBuilder readFile3() throws IOException {
        StringBuilder sb = new StringBuilder();
        // 文件字节流
        FileInputStream fis = new FileInputStream(fileRead);
        // 字节流转换字符流
        InputStreamReader isr = new InputStreamReader(fis, CHARSET_UTF_8);
        int len;
        char[] chuf = new char[1024];
        // 如果InputStreamReader流read(char[])结束则返回- 1
        while ((len = isr.read(chuf)) != -1) {
            sb.append(chuf, 0, len);
        }
        isr.close();
        fis.close();
        System.out.println(sb);
        return sb;
    }

    /**
     * 读文件方式4：字符缓冲流读取文件
     * <p>
     * File对象 ---> 文件输入字节流 FileInputStream ---> 字节流转字符流 InputStreamReader ---> 字符缓冲流 BufferedReader
     */
    private static List<String> readFile4() throws IOException {
        List<String> lines = new ArrayList<>();
        // 文件字节流
        FileInputStream fis = new FileInputStream(fileRead);
        // 字节流转换字符流
        InputStreamReader isr = new InputStreamReader(fis, CHARSET_UTF_8);
        // 字符缓冲流
        BufferedReader br = new BufferedReader(isr, 1024);
        String lineStr;
        while ((lineStr = br.readLine()) != null) {
            lines.add(lineStr);
            System.out.println(lineStr);
        }
        br.close();
        isr.close();
        fis.close();
        return lines;
    }

    /**
     * 读文件方式5：字符缓冲流读取文件，利用读取字符文件的方便类FileReader
     * <p>
     * File对象 --> 读取字符文件的方便类FileReader，缺点：无法转换编码 ---> 字符缓冲流 BufferedReader
     */
    private static List<String> readFile5() throws IOException {
        List<String> lines = new ArrayList<>();
        // 读取字符文件的方便类
        FileReader fr = new FileReader(fileRead);
        // 字符缓冲流
        BufferedReader br = new BufferedReader(fr, 1024);
        String lineStr;
        while ((lineStr = br.readLine()) != null) {
            lines.add(lineStr);
            System.out.println(lineStr);
        }
        br.close();
        fr.close();
        return lines;
    }

    // =======================================下面是复制文件比较=====================================================
    /**
     * 复制件方式1：字节流、缓存字节数组
     */
    private static void copyFile1() {
        // 缓存字节数组大小
        int cacheSize = 1024 * 1024 * 20 ;
        Long start = System.currentTimeMillis();
        try (InputStream fis = new FileInputStream(new File("E:\\09.自定义线程池-线程池类和测试类编写.avi"));
             OutputStream fos = new FileOutputStream(new File("E:\\copyFile1.avi"))) {
            byte[] bytes = new byte[cacheSize];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("copyFile1耗时：" + (end - start));
        /*
           size -> 耗时
               1KB = 1024 * 1  -> 1561
               5KB =  1024 * 5 -> 639
               10KB =  1024 * 10 -> 428  【最优】
               1MB = 1024 * 1024  -> 441
               10MB = 1024 * 1024 * 10  -> 458
               20MB = 1024 * 1024 * 20  -> 490
               现象：缓存字节数组大小设置为 10KB时即可达到较高性能，再增大性能不提升
         */
    }

    /**
     * 复制件方式2：字节缓冲流、缓存字节数组
     */
    private static void copyFile2() {
        // 缓冲区大小
        int cacheSize = 1024 * 1024 * 20;
        Long start = System.currentTimeMillis();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("E:\\09.自定义线程池-线程池类和测试类编写.avi")),cacheSize);
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E:\\copyFile2.avi")),cacheSize)) {
            int len;
            // 缓存字节数组
            byte[] bytes = new byte[1024 * 10];
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("copyFile2耗时：" + (end - start)); // 1054
        /*
            缓冲区大小和缓存字节数据大小相同地设置
            size -> 耗时
               1KB = 1024 * 1  -> 1679
               5KB =  1024 * 5 -> 774
               10KB =  1024 * 10 -> 408
               1MB = 1024 * 1024  -> 435
               10MB = 1024 * 1024 * 10  -> 459
               20MB = 1024 * 1024 * 20  -> 527
         */
         /*
            new byte[1024 * 10]固定，以下为缓冲区大小：
            size -> 耗时
               1KB = 1024 * 1  -> 409
               5KB =  1024 * 5 -> 466
               10KB =  1024 * 10 -> 435
               1MB = 1024 * 1024  -> 575
               10MB = 1024 * 1024 * 10  -> 515
               20MB = 1024 * 1024 * 20  -> 563
               现象：缓冲区大小对读写耗时影响不大
         */
    }

    /**
     * 复制件方式3：字节流，每个字节单独读写，不要使用该方式
     */
    private static void copyFile3() {
        Long start = System.currentTimeMillis();
        try (InputStream fis = new FileInputStream(new File("E:\\09.自定义线程池-线程池类和测试类编写.avi"));
             OutputStream fos = new FileOutputStream(new File("E:\\copyFile3.avi"))) {
            int len;
            while ((len = fis.read()) != -1) {
                fos.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("copyFile1耗时：" + (end - start)); // 反正很久，等不及了
    }
}