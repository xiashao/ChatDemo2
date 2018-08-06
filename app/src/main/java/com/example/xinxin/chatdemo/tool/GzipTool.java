package com.example.xinxin.chatdemo.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Administrator on 2017/7/6.
 */
public class GzipTool {
    /**
     * 功能描述：解压压缩数据
     *
     * @param zipData
     * @return
     * @throws Exception
     */
    public static String decompressForGzip(String zipData) throws Exception {
        return decompressStrForGzip(new ByteArrayInputStream(zipData.getBytes()));
    }

    /**
     * 功能描述：解压gzip数据，返回字符串
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String decompressStrForGzip(InputStream is) throws Exception {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPInputStream gis;
            gis = new GZIPInputStream(is);
            int len = -1;
            byte[] data = new byte[1024];
            while ((len = gis.read(data)) != -1) {
                bos.write(data, 0, len);
            }
            bos.close();
            gis.close();
            return bos.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 功能描述：压缩文件
     * @param filePath
     * @return
     * @throws Exception
     */
    /**
     * @param filePath      要压缩的文件路径
     * @param filePathAfter 压缩后的文件路径
     * @throws Exception
     */
    public static void compressFileForGzip(String filePath, String filePathAfter) throws Exception {
        File file = new File(filePath);
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[1024];

            FileOutputStream fos = new FileOutputStream(filePathAfter);

            GZIPOutputStream gos = new GZIPOutputStream(fos);
            while (fis.read(data) > 0) {
                gos.write(data);
            }
            fis.close();
            gos.close();
            fos.close();
        } catch (IOException e) {
            //TODO log
            throw e;
        }
    }

    /**
     * 功能描述：压缩字符串数据
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static void compressForGzip(String data, String filePath) throws Exception {
        if (data==null) return;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            GZIPOutputStream gos = new GZIPOutputStream(fos);
            gos.write(data.getBytes());
            gos.close();
            fos.close();
        } catch (IOException e) {
            //TODO log
            throw e;
        }
    }
}
