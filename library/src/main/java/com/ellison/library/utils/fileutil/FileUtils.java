package com.ellison.library.utils.fileutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.ellison.library.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ellison.Sun
 * @date 2017/3/16
 * <p>
 * 文件目录选择
 */
public class FileUtils {

    private static FileUtils util;
    private static int REDCOUNT = 4 * 1024;

    private Context context = null;

    // 单例
    public static FileUtils getInstance() {
        if (util == null) {
            util = new FileUtils();
        }
        return util;
    }

    private FileUtils() {

    }

    public void setContext(Context c) {
        this.context = c;
    }

    public Context getContext() {
        return context;
    }

    /**
     * @param path 传入路径字符串
     * @return File
     */
    public File createFileIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                new File(path.substring(0, path.lastIndexOf(File.separator)))
                        .mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public String makeFilePath(Context context, String path, String fileName) {
        File file;
        if (Environment.getExternalStorageState().equals("mounted")) {
            file = new File(Environment.getExternalStorageDirectory(), path);
        } else {
            file = context.getApplicationContext().getCacheDir();
        }
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        path = file.getAbsolutePath();
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        if (fileName != null) {
            path = path + fileName;
        }
        return path;
    }

    /**
     * 创建路径的父文件夹
     *
     * @param filePath
     * @return
     */
    public static File createParent(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        } else {
            File file = new File(filePath);
            if (file.exists()) {
                return file;
            } else {
                File parentFile = file.getParentFile();
                if (parentFile != null && parentFile.exists()) {
                    return file;
                } else {
                    if (parentFile.mkdirs()) {
                        return file;
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * 判断路径的父文件夹是否存在
     *
     * @param filePath
     * @return
     */
    public boolean parentExists(String filePath) {
        return createParent(filePath) != null;
    }


    /**
     * @param path 传入路径字符串
     * @return File
     */
    public File creatDirIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return file;
    }

    /**
     * @param path
     * @return
     */
    public boolean isExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 创建新的文件，如果有旧文件，先删除再创建
     *
     * @param path
     * @return
     */
    public File creatNewFile(String path) {
        File file = new File(path);
        if (isExist(path)) {
            file.delete();
        }
        createFileIfNotExist(path);
        return file;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public boolean deleteFile(String path) {
        File file = new File(path);
        if (isExist(path)) {
            file.delete();
        }
        return true;
    }

    // 删除一个目录
    public boolean deleteFileDir(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!isExist(path)) {
            return flag;
        }
        if (!file.isDirectory()) {

            file.delete();
            return true;
        }
        String[] filelist = file.list();
        File temp = null;
        for (int i = 0; i < filelist.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + filelist[i]);
            } else {
                temp = new File(path + File.separator + filelist[i]);
            }
            if (temp.isFile()) {

                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteFileDir(path + "/" + filelist[i]);// 先删除文件夹里面的文件
            }
        }
        file.delete();

        flag = true;
        return flag;
    }

    // 删除文件夹
    // param folderPath 文件夹完整绝对路径

    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public String[] getFlieName(String rootpath) {
        File root = new File(rootpath);
        File[] filesOrDirs = root.listFiles();
        if (filesOrDirs != null) {
            String[] dir = new String[filesOrDirs.length];
            int num = 0;
            for (int i = 0; i < filesOrDirs.length; i++) {
                if (filesOrDirs[i].isDirectory()) {
                    dir[i] = filesOrDirs[i].getName();

                    num++;
                }
            }
            String[] dirR = new String[num];
            num = 0;
            for (int i = 0; i < dir.length; i++) {
                if (dir[i] != null && !"".equals(dir[i])) {
                    dirR[num] = dir[i];
                    num++;
                }
            }
            return dirR;
        } else {
            return null;
        }
    }

    /**
     * //获得流
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public BufferedWriter getWriter(String path) throws FileNotFoundException,
            UnsupportedEncodingException {
        FileOutputStream fileout = null;
        fileout = new FileOutputStream(new File(path));
        OutputStreamWriter writer = null;
        writer = new OutputStreamWriter(fileout, "UTF-8");
        BufferedWriter w = new BufferedWriter(writer); // 缓冲区
        return w;
    }

    /**
     * 获取输入流
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public InputStream getInputStream(String path) throws FileNotFoundException {
        // if(Comments.DEBUG) System.out.println("path:"+path);
        FileInputStream filein = null;
        // if(Comments.DEBUG) System.out.println("2");
        // File file = createFileIfNotExist(path);
        File file = new File(path);
        filein = new FileInputStream(file);
        BufferedInputStream in = null;
        if (filein != null) {
            in = new BufferedInputStream(filein);
        }
        return in;
    }

    public boolean stateXmlControl(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return false;
        }
        if (f.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 将InputStream转换成byte数组
     *
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] inputStreamTOByte(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[6 * 1024];
        int count = -1;
        while ((count = in.read(data, 0, REDCOUNT)) != -1) {
            outStream.write(data, 0, count);
        }
        data = null;
        return outStream.toByteArray();
    }

    /**
     * 将OutputStream转换成byte数组
     *
     * @param out OutputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] outputStreamTOByte(OutputStream out)
            throws IOException {

        byte[] data = new byte[6 * 1024];
        out.write(data);
        return data;
    }

    /**
     * 将byte数组转换成InputStream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream byteTOInputStream(byte[] in) {
        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }

    /**
     * 将byte数组转换成OutputStream
     *
     * @param in
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static OutputStream byteTOOutputStream(byte[] in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(in);
        return out;
    }

    /**
     * 把输入流中的数据输入到Path里的文件里
     *
     * @param path
     * @param name
     * @param inputStream
     * @return
     */
    public File writeFromInputToSD(String path, String name, InputStream inputStream) {
        File file = null;
        OutputStream output = null;
        try {
            createSDDir(path);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(path);
            stringBuilder.append(name);
            file = createSDFile(stringBuilder.toString());

            output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int temp;
            while ((temp = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 把数据输入到Path里的文件里
     *
     * @param path
     * @param b    inputStream
     * @return
     */
    public File writeFromInputToSD(String path, byte[] b) {
        File file = null;
        OutputStream output = null;
        try {
            file = createFileIfNotExist(path);
            output = new FileOutputStream(file);
            output.write(b);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 方法：把一段文本保存到给定的路径中.
     */
    public void saveTxtFile(String filePath, String text) {
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            createFileIfNotExist(filePath);
            String txt = readTextLine(filePath);
            text = text + txt;
            FileOutputStream out = new FileOutputStream(filePath);
            // 构建一个写入器,用于向流中写入字符数据
            OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
            writer.write(text);
            // 关闭Writer,关闭输出流
            writer.close();
            out.close();
        } catch (Exception e) {
            String ext = e.getLocalizedMessage();
            // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();
        }

    }

    public void clearTxtFile(String filePath) {
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            String text = "";
            FileOutputStream out = new FileOutputStream(filePath);
            // 构建一个写入器,用于向流中写入字符数据
            OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
            writer.write(text);
            // 关闭Writer,关闭输出流
            writer.close();
            out.close();
        } catch (Exception e) {
            String ext = e.getLocalizedMessage();
            // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 读取一个给定的文本文件内容,并把内容以一个字符串的形式返回
     *
     * @param textFile
     * @return
     */
    public String readTextLine(String textFile) {
        try {
            // 首先构建一个文件输入流,该流用于从文本文件中读取数据
            FileInputStream input = new FileInputStream(textFile);
            // 为了能够从流中读取文本数据,我们首先要构建一个特定的Reader的实例,
            // 因为我们是从一个输入流中读取数据,所以这里适合使用InputStreamReader.
            InputStreamReader streamReader = new InputStreamReader(input,
                    "gb2312");
            // 为了能够实现一次读取一行文本的功能,我们使用了 LineNumberReader类,
            // 要构建LineNumberReader的实例,必须要传一个Reader实例做参数,
            // 我们传入前面已经构建好的Reder.
            LineNumberReader reader = new LineNumberReader(streamReader);
            // 字符串line用来保存每次读取到的一行文本.
            String line = null;
            // 这里我们使用一个StringBuilder来存储读取到的每一行文本,
            // 之所以不用String,是因为它每次修改都会产生一个新的实例,
            // 所以浪费空间,效率低.
            StringBuilder allLine = new StringBuilder();
            // 每次读取到一行,直到读取完成
            while ((line = reader.readLine()) != null) {
                allLine.append(line);
                // 这里每一行后面,加上一个换行符,LINUX中换行是”\n”,
                // windows中换行是”\r\n”.
                allLine.append("\n");
            }
            // 把Reader和Stream关闭
            streamReader.close();
            reader.close();
            input.close();
            // 把读取的字符串返回
            return allLine.toString();
        } catch (Exception e) {
            // Toast.makeText(this, e.getLocalizedMessage(),
            return "";
        }
    }

    /**
     * 把字加长，使其可以滚动，在音乐界面
     */
    public String dealString(String st, int size) {
        int value = size;
        if (st.length() >= value) {
            return "  " + st + "  ";
        } else {
            int t = (value - st.length()) / 2;
            for (int i = 0; i < t; i++) {
                st = " " + st + "  ";
            }
            return st;
        }
    }

    public String getTimeByFormat(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        // 获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public String getDateTimeBylong(long timeData, String dateformatBatt) {
        Date date = new Date(timeData);
        SimpleDateFormat format = new SimpleDateFormat(dateformatBatt);
        return format.format(date);
    }

    /**
     * 取前面的名字　"."
     *
     * @param source
     * @param flag
     * @return
     */
    public String getNameByFlag(String source, String flag) {
        // String[] source_spli = source.split(flag);
        String s = source.toLowerCase().replace(flag, "");
        return s.trim();
    }

    /**
     * 取Asset文件夹下文件
     *
     * @param paramContext
     * @param paramString
     * @return
     * @throws IOException
     */
    public InputStream getAssetsInputStream(Context paramContext,
                                            String paramString) throws IOException {
        return paramContext.getResources().getAssets().open(paramString);
    }

    /**
     * 以省内存的方式读取图片
     */
    public Bitmap getBitmap(InputStream is) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = 4;
        //获取资源图片
        //InputStream is = mContext.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * imageLoader 设置图片缓存的路径
     */
    public File getImageLoaderCached(Context applicationContext) {
        File externalCacheDir = applicationContext.getExternalCacheDir();
        File imageLoaderFile = new File(externalCacheDir, "imageLoader");
        if (!imageLoaderFile.exists()) {
            imageLoaderFile.mkdir();
            imageLoaderFile = new File(externalCacheDir, "imageLoader");
        }
        return imageLoaderFile;
    }

    /**
     * 根据传入的uniqueName获取硬盘缓存的路径地址。
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 在sd卡中创建目录
     *
     * @param dir
     * @return
     */
    public File createSDDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    /**
     * 是否超过 50M
     *
     * @return
     */
    public boolean isSdAvailable() {
        File file = new File(Constants.DOWNLOAD_PATH);
        if (!file.exists()) {
            file.mkdirs();
            if (!file.exists()) {
                return false;
            }
        }
        return getAvailableSize(Constants.DOWNLOAD_PATH) > 50 * 1024 * 1024;
    }

    /**
     * 是否比传入的值大
     *
     * @param size
     * @return
     */
    public boolean isSdAvailable(long size) {
        return getAvailableSize(Environment.getExternalStorageDirectory().getPath()) > size;
    }

    /**
     * 获取到 可用内存
     *
     * @param path
     * @return
     */
    public long getAvailableSize(String path) {
        StatFs statFs = new StatFs(path);
        statFs.getBlockCount();
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    /**
     * 文件是否存在
     *
     * @param str
     * @return
     */
    public boolean isFileExist(String str) {
        return new File(str).exists();
    }

}
