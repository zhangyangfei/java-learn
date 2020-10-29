package com.zyf.javabase.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip工具类
 */
public class ZipUtil {

    /**
     * 压缩
     *
     * @param files       需要压缩的文件集合
     * @param zipFileName 压缩包文件名
     * @throws Exception
     */
    public static void compress(List<File> files, String zipFileName) throws Exception {
        File file = new File(zipFileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            byte[] buffer = new byte[1024];
            for (int i = 0; i < files.size(); i++) {
                try (FileInputStream fis = new FileInputStream(files.get(i))) {
                    // 创建压缩包内的文件
                    out.putNextEntry(new ZipEntry(files.get(i).getName()));
                    int len;
                    // 读入文件内容到zip文件
                    while ((len = fis.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    out.closeEntry();
                }
            }
        }
    }

    /**
     * 解压
     *
     * @param zipFileName 待解压文件夹/文件
     * @param destDirPath 解压路径
     */
    public static void uncompress(String zipFileName, String destDirPath) throws Exception {
        // 当前压缩文件
        File srcFile = new File(zipFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
        // 解压输入流
        try (ZipInputStream zIn = new ZipInputStream(new FileInputStream(srcFile));) {
            ZipEntry entry = null;
            File file = null;
            while ((entry = zIn.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    file = new File(destDirPath, entry.getName());
                    if (!file.exists()) {
                        new File(file.getParent()).mkdirs();//创建此文件的上级目录
                    }
                    try (OutputStream out = new FileOutputStream(file);
                         BufferedOutputStream bos = new BufferedOutputStream(out)) {
                        int len = -1;
                        byte[] buf = new byte[1024];
                        while ((len = zIn.read(buf)) != -1) {
                            bos.write(buf, 0, len);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        testCompress();
        uncompress("java-base/ziptest/zip/1122.zip", "java-base/ziptest/unzip/");
    }

    static void testCompress() throws Exception {
        List<File> files = new ArrayList<>();
        files.add(new File("java-base/ziptest/file/11.txt"));
        files.add(new File("java-base/ziptest/file/22.txt"));
        compress(files, "java-base/ziptest/zip/1122.zip");
    }
}
