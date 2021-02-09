package com.plan.frame.util;

/**
 * @Author Huangry
 * @Description: 文件处理
 * @Date 2019-02-18
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
public class FileUtil {

    private static String[]  whiteDir;

    /**获取文件大小 返回 KB 保留3位小数  没有文件时返回0
     * @param filepath 文件完整路径，包括文件名
     * @return
     */
    public static Double getFilesize(String filepath){
        File backupath = createFile(filepath);
        return Double.valueOf(backupath.length())/1000.000;
    }

    public static void setWhiteDir(String[] whiteDir) {
        FileUtil.whiteDir = whiteDir;
    }

    /**
     * 创建目录
     * @param destDirName
     * @return
     */
    public static Boolean createDir(String destDirName) {
        File dir = createFile(destDirName);
        //判断有没有父路径，就是判断文件整个路径是否存在
        if(!dir.getParentFile().exists()){
            //不存在就全部创建
            return dir.getParentFile().mkdirs();
        }
        return false;
    }

    /**
     * 删除文件
     * @param filePathAndName
     *            String 文件路径及名称 如c:/fqf.txt
     * @param filePathAndName
     *            String
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myDelFile = createFile(filePath);
            myDelFile.delete();
        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 读取到字节数组0
     * @param filePath //路径
     * @throws IOException
     */
    public static byte[] getContent(String filePath) throws IOException {
        File file = createFile(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        fi.close();
        return buffer;
    }

    /**
     * 读取到字节数组1
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filePath) throws IOException {

        File f = createFile(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(in!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    /**
     * 读取到字节数组2
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filePath) throws IOException {
        File f = createFile(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if(channel!=null){
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fs!=null){
                    fs.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据文件获取
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(File file)throws IOException{
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if(channel!=null){
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fs!=null){
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray3(String filePath) throws IOException {

        FileChannel fc = null;
        RandomAccessFile rf = null;
        try {
            rf = createRandomAccessFile(filePath);
            fc = rf.getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            //System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(rf!=null){
                    rf.close();
                }
                if(fc!=null){
                    fc.close();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param response
     * @param filePath		//文件完整路径(包括文件名和扩展名)
     * @param fileName		//下载后看到的文件名
     * @return  文件名
     */
    public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception{

        byte[] data = FileUtil.toByteArray(filePath);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();

    }

    /**
     * @param inputFileName 你要压缩的文件夹(整个完整路径)
     * @param zipFileName 压缩后的文件(整个完整路径)
     * @throws Exception
     */
    public static Boolean zip(String inputFileName, String zipFileName) throws Exception {
        zip(zipFileName, createFile(inputFileName));
        return true;
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        out.flush();
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            //System.out.println(base);
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    public static File createFile(String fileName) {
        if(checkDirPermission(fileName)){
            return  new File(fileName);
        }
        return null;
    }
    public static File createFile(String dir,String fileName) {
        if(checkDirPermission(dir)){
            return  new File(dir,fileName);
        }
        return null;
    }
    public  static FileInputStream  getFileInPutStream(String filePath) throws FileNotFoundException{
        FileInputStream fi=null;
        if(StringUtils.isNotEmpty(filePath) && checkDirPermission(filePath)){
            fi = new FileInputStream(filePath);
        }
        return fi;
    }
    public static RandomAccessFile createRandomAccessFile(String filePath) throws FileNotFoundException{
        RandomAccessFile fi=null;
        if(StringUtils.isNotEmpty(filePath) && checkDirPermission(filePath)){
            fi = new RandomAccessFile(filePath, "r");
        }
        return fi;

    }
    public  static FileOutputStream  getFileOutPutStream(String filePath) throws FileNotFoundException{
        FileOutputStream fi=null;
        if(StringUtils.isNotEmpty(filePath)){
            fi = new FileOutputStream(filePath);
        }
        return fi;
    }
    public  static FileOutputStream  getFileOutPutStream(File file) throws FileNotFoundException{
        FileOutputStream fi=null;
        if(file!=null){
            fi = new FileOutputStream(file);
        }
        return fi;
    }

    private static boolean checkDirPermission(String file){
        boolean has=true;
        if(StringUtils.isNotEmpty(file) && whiteDir!=null){
            has=false;
            for(int i=0;i<whiteDir.length;i++){
                if(file.startsWith(whiteDir[i])){
                    has = true;
                }
            }
            if(!has){
                throw new RuntimeException("该目录无文件创建权限！");
            }
        }

        return has;
    }


    public static void main(String [] temp){
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = servletRequestAttributes.getResponse();

            fileDownload(response,"D:\\file\\SubstanceAdd\\7eb915bb5a2942b9877dec0afe4f2840\\11.txt","11.txt");//你要压缩的文件夹      和  压缩后的文件
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}