package com.plan.frame.util;


import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.plan.frame.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Word2pdf {

    @Value("${file.tempPath}")
    private static String tempPath;

    protected static Logger logger = LoggerFactory.getLogger(Word2pdf.class);

    /*public static void main(String[] args) {
        Word2pdf.word2pdf("D:/cyq/项目相关/亳州/辣鸡/showPage.docx", "D:/cyq/项目相关/亳州/辣鸡/showPage.pdf");
    }*/

    /**
     * word转pdf
     * @param filePath 将要被转化的word文件地址
     * @param savePath 转化后pdf文件存放地址
     */
    public static void word2pdf(String filePath,String savePath){
        try {
//            long old = System.currentTimeMillis();

            // 验证License 若不验证则转化出的pdf文档会有水印产生
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
            License license = new License();
            license.setLicense(is);

            //新建一个空白pdf文档
            File file = FileUtil.createFile(savePath);
            FileOutputStream os = new FileOutputStream(file);

            Document doc = new Document(filePath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PDF);

//            long now = System.currentTimeMillis();
//            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * word转pdf
     * @param docFilePath
     * @param pdfFilePath
     * @return
     */
    public static boolean docToPdf(String docFilePath , String pdfFilePath){
        ActiveXComponent app = null;
        boolean flag = false;
        try {
            // 打开word
            app = new ActiveXComponent("Word.Application");
            // 设置word不可见,很多博客下面这里都写了这一句话，其实是没有必要的，因为默认就是不可见的，如果设置可见就是会打开一个word文档，对于转化为pdf明显是没有必要的
            //app.setProperty("Visible", false);
            // 获得word中所有打开的文档
            Dispatch documents = app.getProperty("Documents").toDispatch();
            // 打开文档
            Dispatch document = Dispatch.call(documents, "Open", docFilePath, false, true).toDispatch();
            // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
            File target = FileUtil.createFile(pdfFilePath);
            if (target.exists()) {
                target.delete();
            }
            // 另存为，将文档报错为pdf，其中word保存为pdf的格式宏的值是17
            Dispatch.call(document, "SaveAs", pdfFilePath, 17);
            // 关闭文档
            Dispatch.call(document, "Close", false);
            flag = true;
            return flag;
        }catch(Exception e) {
            throw new SystemException("pdf预览或下载失败",e,"请联系管理员处理");
        }finally {
            // 关闭office
            if(app!=null){
                app.invoke("Quit", 0);
            }

        }

    }

}
