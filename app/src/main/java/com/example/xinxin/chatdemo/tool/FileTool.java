package com.example.xinxin.chatdemo.tool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileTool {
    private final static String TAG = "FileTool";

    /**
     * 删除一个文件夹及其里边的文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        Log.e("deleteFile", "file " + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
        Log.e("deleteFile", file.exists() + "  file  " + file.getAbsolutePath());
    }

    public static File createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return file;
    }

    /**
     * 将字符串写入到文件
     *
     * @param file
     * @param text
     */
    public static void writeTextToFile(File file, String text, boolean flag) {
        if (file.getName().indexOf(".dat") > 0) {//是压缩文件
            writeTextToGzipFile(file, text, flag);
        } else {
            writeTextToNormalFile(file, text, flag);
        }
    }

    /**
     * 将字符串写入到文件
     *
     * @param file
     * @param text
     */
    public static void writeTextToNormalFile(File file, String text, boolean flag) {


        try {
            FileOutputStream outputStream = new FileOutputStream(file, flag);
            byte[] bytes = text.getBytes("utf-8");
            outputStream.write(bytes);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("FileTool:writeToFile", e.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("FileTool:writeToFile", e.toString());
        } catch (IOException e) {
            Log.e("FileTool:write:ToFile", e.toString());
        }
    }

    /**
     * 压缩字符串数据
     *
     * @param file
     * @param text
     * @param flag
     */
    public static void writeTextToGzipFile(File file, String text, boolean flag) {
        Tool.showLog(TAG, "writeTextToGzipFile " + text);

        try {
            FileOutputStream outputStream = new FileOutputStream(file, flag);
            GZIPOutputStream gos = new GZIPOutputStream(outputStream);
            byte[] bytes = text.getBytes("utf-8");
            gos.write(bytes);
            gos.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            Tool.showLog(TAG, "writeTextToGzipFile catch1 " + e.toString());
        } catch (UnsupportedEncodingException e) {
            Tool.showLog(TAG, "writeTextToGzipFile catch2 " + e.toString());
        } catch (IOException e) {
            Tool.showLog(TAG, "writeTextToGzipFile catch3 " + e.toString());
        }

    }

    /**
     * 读取一个文件中字符串
     *
     * @param file
     * @return
     */
    public static String readTextForFile(File file) {
        if (file == null) return "";
        if (!file.exists()) return "";
        if (file.getName().indexOf(".dat") > 0) {//是压缩文件
            return readTextForGzipFile(file);
        } else {
            return readTextForNormalFile(file);
        }
    }

    public static String readTextForNormalFile(File file) {
        if (file == null) return "";
        if (!file.exists()) return "";
        String str = "";
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            str = new String(buffer, "utf-8");
        } catch (FileNotFoundException e) {
            Log.e("FileTool-readFile", e.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("FileTool-readFile", e.toString());
        } catch (IOException e) {
            Log.e("FileTool-readFile", e.toString());
        }
        return str;
    }

    public static String readTextForGzipFile(File file) {
        if (file == null) return "";
        if (!file.exists()) return "";

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPInputStream gis;
            gis = new GZIPInputStream(new FileInputStream(file));
            int len = -1;
            byte[] data = new byte[1024];
            while ((len = gis.read(data)) != -1) {
                bos.write(data, 0, len);
            }
            bos.close();
            gis.close();
            return bos.toString();

        } catch (Exception e) {
            Log.e("FileTool-readFile", e.toString());
            return "";
        }
    }


    public static void copyFiles(Context context, String assetsDir, String path) {
        try {
            String[] fileList = context.getResources().getAssets().list(assetsDir);
            for (String fileName : fileList) {
                String outFileName = path + fileName;
                File dbf = new File(outFileName);
                if (!dbf.getParentFile().exists()) {
                    dbf.getParentFile().mkdirs();
                }
                dbf.createNewFile();
                InputStream myInput = context.getAssets().open(assetsDir + "/" + fileName);
                OutputStream myOutput = new FileOutputStream(outFileName, false);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("FileTool:copy:Files", e.toString());
        } catch (IOException e) {
            Log.e("FileTool:copy:Files", e.toString());
        }
    }

    public static void copyBigDataToSD(Context context, String assetsFilePath,
                                       String strOutFileName,
                                       OnSuccessInterface onSuccessInterface) {
        try {
            File file = new File(strOutFileName);
            if (!file.exists()) {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                file.createNewFile();
            }
            OutputStream myOutput = new FileOutputStream(strOutFileName);
            InputStream myInput = context.getAssets().open(assetsFilePath);
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
            if (onSuccessInterface != null) {
                onSuccessInterface.onSuccess(strOutFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyBigDataToZIPSD(Context context, String assetsFilePath,
                                          String strOutFileName) {
        try {
            createFile(strOutFileName);
            File file = new File(strOutFileName);
            InputStream myInput = context.getAssets().open(assetsFilePath);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int len = -1;
            byte[] data = new byte[1024];
            while ((len = myInput.read(data)) != -1) {
                bos.write(data, 0, len);
            }
            if (strOutFileName.endsWith(".dat")) {
                writeTextToGzipFile(file, bos.toString(), false);
            }
            myInput.close();
            bos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 按照文件名称排序
    public static ArrayList<File> orderByName(String fliePath) {

        List<File> files = Arrays.asList(new File(fliePath).listFiles());
        ArrayList<File> childfiles = new ArrayList<File>();

        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File f : files) {
            childfiles.add(f);
        }
        return childfiles;
    }

    // 按照文件名称排序
    public static List<File> orderByName(File dir) {
        if (dir.isFile()) return null;
        List<File> files = Arrays.asList(dir.listFiles());
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return files;
    }

    // 按照文件名称排序: 参数 文件、文件夹均可
    public static List<File> orderFilesByName(File dirFile) {
        List<File> files = new ArrayList<File>();
        if (dirFile.isFile()) {
            files.add(dirFile);
        } else {
            files = Arrays.asList(dirFile.listFiles());
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }

        return files;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyLocationFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");

        }
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */
    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道

        } catch (IOException e) {

        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * 将一个文件拷贝到另一个文件夹
     *
     * @param oldPath
     * @param newPath
     */

    public static void copy(String oldPath, String newPath) {
        File resFile = new File(oldPath);
        File desFile = new File(newPath);
        if (!desFile.exists()) {
            createFile(newPath);
        }
        try {
            FileInputStream inputStream = new FileInputStream(resFile);
            FileOutputStream outputStream = new FileOutputStream(desFile);
            FileChannel in = inputStream.getChannel();
            FileChannel out = outputStream.getChannel();
            in.transferTo(0, in.size(), out);
            out.close();
            in.close();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将一个文件移动到另一个文件夹
     *
     * @param oldPath
     * @param newPath
     */
    public static void move(String oldPath, String newPath) {

        File oldFile = new File(oldPath);//用旧的路径实例化一个file对象
        if (!oldFile.exists()) return;
        File fnewpath = new File(newPath);
        if (!fnewpath.exists())
            fnewpath.mkdirs();

        File fnew = new File(newPath + "/" + oldFile.getName());
        if (fnew.exists()) return;
        if (!oldFile.getName().endsWith(".bap")) {
            oldFile.renameTo(fnew);
        }
    }

    /**
     * 读取文件大小
     */

    public static String getFileSize(String path) {
        return getFileSize(new File(path));
    }

    /**
     * 读取文件大小
     */

    public static String getFileSize(File file) {
        String fileSize_txt = "0B";
        if (file.exists()) {
            long fileSize = getFolderSize(file);
            fileSize_txt = getFormatSizeFormB(fileSize);
            Log.e("fileTool", "fileSize_txt=" + fileSize_txt);
        }
        return fileSize_txt;
    }

    private static long getFolderSize(File file) {
        long size = 0;
        if (!file.exists()) return size;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * @param fileSizeStr 文件的大小 单位是 B
     * @return
     */
    public static String getFileSizeFormB(String fileSizeStr) {
        if (TextUtils.isEmpty(fileSizeStr)) return "";
        try {
            long fileSize = Long.parseLong(fileSizeStr);
            return getFormatSizeFormB(fileSize);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param fileSize 文件的大小 单位是 B
     * @return
     */
    public static String getFormatSizeFormB(long fileSize) {

        DecimalFormat format = new DecimalFormat("0.00");
        String fileSize_txt = "";
        if ((fileSize / 1024.0) > 1024.0) {
            fileSize_txt = format.format(new BigDecimal(fileSize / 1024.0 / 1024.0)) + "M";
        } else if ((fileSize / 1024.0) > 1.0) {
            fileSize_txt = format.format(new BigDecimal(fileSize / 1024.0)) + "Kb";
        } else {
            fileSize_txt = format.format(new BigDecimal(fileSize)) + "B";
        }
        Log.e("fileTool", "fileSize_txt=" + fileSize_txt);
        return fileSize_txt;
    }

}
