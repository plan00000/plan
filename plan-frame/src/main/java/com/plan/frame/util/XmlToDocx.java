package com.plan.frame.util;

import com.plan.frame.entity.ValueObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author fanhongzhi
 * @version V1.0
 * @Description: xml转存doc
 * @date 2019/4/13
 */
public class XmlToDocx {
    @Value("${file.tempPath}")
    private static String tempPath;

    /**
     *
     * @param documentFile 动态生成数据的docunment.xml文件
     * @param docxTemplate docx的模板
     * @param toFilePath  需要导出的文件路径
     * @throws ZipException
     * @throws IOException
     */

    public void outDocx(File documentFile, String docxTemplate, String toFilePath) throws ZipException, IOException {

        try {
            File docxFile = FileUtil.createFile(docxTemplate);
            ZipFile zipFile = new ZipFile(docxFile);
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
            ZipOutputStream zipout = new ZipOutputStream(FileUtil.getFileOutPutStream(toFilePath));
            int len = -1;
            byte[] buffer = new byte[1024];
            while (zipEntrys.hasMoreElements()) {
                ZipEntry next = zipEntrys.nextElement();
                InputStream is = zipFile.getInputStream(next);
                // 把输入流的文件传到输出流中 如果是word/document.xml由我们输入
                zipout.putNextEntry(new ZipEntry(next.toString()));
                if ("word/document.xml".equals(next.toString())) {
                    InputStream in = new FileInputStream(documentFile);
                    while ((len = in.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    in.close();
                } else {
                    while ((len = is.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    is.close();
                }
            }
            zipout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param path 文件路径
     * @param fileName 文件名
     * @param path2 http地址
     * @param dataMap 数据map
     */
    public static void createDoc(String path, String fileName, String path2, Map<String, Object> dataMap){
        try {

            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(FileUtil.createFile(path2));
            Template t = configuration.getTemplate("ajclyjs.ftl"); // 文件名
            File outFile = FileUtil.createFile(path + fileName);
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "UTF-8"));
            t.process(dataMap, out);
            // outFile.deleteOnExit();

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void createDocWithName(String path, String fileName, String path2, Map<String, Object> dataMap){
        try {

            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(FileUtil.createFile(path2));
            Template t = configuration.getTemplate(fileName +".ftl"); // 文件名
            File dir=FileUtil.createFile(path);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File outFile = FileUtil.createFile(path + fileName+".doc");
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "UTF-8"));
            t.process(dataMap, out);
            // outFile.deleteOnExit();

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * freemarker模板转出pdf文件
     * @param path doc路径
     * @param docName doc文件名
     * @param fileName 模板文件名
     * @param ftlTemplatePath ftl模板路径
     * @param dataMap 数据
     * @return
     */
    public static ValueObject ftlToDoc(String path, String docName, String fileName, String ftlTemplatePath, Map<String, Object> dataMap)throws Exception{
        ValueObject valueObject = new ValueObject();

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(FileUtil.createFile(ftlTemplatePath));
        Template t = configuration.getTemplate(fileName + ".ftl"); // 文件名

        File outFile = FileUtil.createFile(path + docName + ".doc");
        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outFile), "UTF-8"));
        t.process(dataMap, out);
        out.flush();
        out.close();
        valueObject.put("path",outFile.getPath());
        //返回路径doc的完整路径
        return valueObject;

    }
}
