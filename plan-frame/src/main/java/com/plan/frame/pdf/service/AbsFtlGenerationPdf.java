package com.plan.frame.pdf.service;

import com.plan.frame.entity.ValueObject;
import com.plan.frame.system.base.entity.SysDocumentTemplate;
import com.plan.frame.util.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description pdf生成成接口-实现类   此类实现的是根据ftl方式实现的pdf打印
 * @ClassName FtlGenerationPdf
 * @Date 2019/7/9 10:05
 */
public abstract class AbsFtlGenerationPdf extends AbsFileSave implements IPdfGeneration{
    @Value("${file.tempPath}")
    private String tempPath;

    @Value("${documentTemplatePath}")
    private String documentTemplatePath;

    @Override
    public File generate(String drivenId, SysDocumentTemplate sysDocumentTemplate, Map<String, Object> map) throws Exception{
        // 获取要写入模板的数据
        Map<String, Object> pdfData = constructPdfBean(drivenId, map);
        String templatePath =Thread.currentThread().getContextClassLoader().getResource("").getPath()+documentTemplatePath;
        // 拿到数据后组装
//        String templatePath = FileUtil.getServletContextImpPath() + documentTemplatePath;

        // 如果有父模板就用父模板的ftl
        String ftlTemplateName = sysDocumentTemplate.getTemplateCode();
        if(CommonUtil.isNotEmpty(sysDocumentTemplate.getParentCode())){
            ftlTemplateName = sysDocumentTemplate.getParentCode();
        }

        // 根据模板生成doc文件  默认是d:doucument/yyyyMMdd/目录下
        String docPath = DateUtil.date2Str(new Date(), "yyyyMMdd") + System.getProperties().getProperty("file.separator");
        String docName = CommonUtil.getUUID();
        ValueObject valueObject=  XmlToDocx.ftlToDoc(tempPath+docPath,docName,ftlTemplateName,templatePath,pdfData);
        File file = null;
        String docFilePath = (String)valueObject.get("path");
        //判断是不是下载，如果是下载则返回doc file对象，如果不是则返回pdf File对象
        String download = (String)map.get("download");
        if(CommonUtil.isNotEmpty(download)&& StringUtil.equalsString(download,"1")){
            //文书下载返回doc File
            file = FileUtil.createFile(docFilePath);
        }else {
            //doc再转换成pdf文件
            Word2pdf.docToPdf(docFilePath, tempPath+docPath + docName + ".pdf");
            file = FileUtil.createFile(tempPath + docPath + docName + ".pdf");
        }
        return file;
    }
    public abstract Map<String, Object> constructPdfBean(String drivenId,Map<String,Object> map) throws Exception;

}
