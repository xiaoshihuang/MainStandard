package com.xintai.xhao.utils;

import android.os.Environment;
import android.util.Log;

import com.xintai.xhao.F;
import com.xintai.xhao.MyApplication;

import java.io.File;
import java.math.BigDecimal;

/**
 * 文件工具类
 */
public class FileUtils {
    public static File sdDir = null;//所有缓存（包含下载的Apk）都在此文件下面
    /**
     * 获取文件路径；
     * @param strRoot
     * @return
     */
    public static String getFilePath(String strRoot) {
        // 判断是否挂载外存储设备（sd卡或者系统分配的外部存储空间）
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {//如果存在，就使用外部存储
//            sdDir = Environment.getExternalStorageDirectory();// (6.0后写入需要用户授权)/mnt/sdcard
            sdDir = MyApplication.getInstance().getExternalCacheDir();//路径为:/mnt/sdcard/Android/data/< package name >/cach/…
            MyLog.i("存在--外部储存空间");
        } else { //内部存储,手机root过，才能查看
            sdDir =  MyApplication.getInstance().getCacheDir();//路径是:/data/data/< package name >/cach/…
            MyLog.i("使用的是内部储存空间");
            // File f1 = Environment.getDataDirectory(); /data
            // File f2 = Environment.getDownloadCacheDirectory(); /cache
            // File f3 = Environment.getRootDirectory(); /system
        }
        String strDir = sdDir.toString() + "/" + strRoot;
        return strDir;
    }

    /**
     * 获取指定文件夹内所有文件大小的和；
     * @param file file
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(java.io.File file){

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (File afile : fileList) {
                if (afile.isDirectory()) {
                    size = size + getFolderSize(afile);//递归
                } else {
                    size = size + afile.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化文件大小；
     * Byte->KB->MB->GB->TB
     * @param size size 这里的单位是Byte
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 删除指定的目录/文件；
     * 注意：目录下面有文件时，是不能直接删除目录的
     * @param file
     */
    public static  void deleteFolder(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                if(files!=null&&files.length>0){
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        deleteFolder(files[i]); // 把每个文件 用这个方法进行迭代
                    }
                }
            }
            file.delete();
    } else {
            Log.e(F.loggingTag,file.getPath()+"文件不存在！");
        }
    }

}
