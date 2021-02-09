package com.plan.frame.pdf.controller;

import java.util.List;

import com.plan.frame.entity.ValueObject;
import com.plan.frame.pdf.service.DocumentService;
import com.plan.frame.util.FileUtil;
import com.plan.frame.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description 文书打印及下载类
 * @ClassName DocumentController
 * @Date 2019/7/9 14:46
 */
@ApiIgnore
@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    /**
     * 文书预览和下载
     * @param drivenId 必填 启动数据查询Id
     * @param keyword  必填 关键字
     * @param download 下载标志位（0-打印，1-下载）
     * @param save 文书保存标志位（0-不保存，1-保存）
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/pdfShowDownload",method = RequestMethod.GET)
    public void pdfShowDownload(@RequestParam(value="drivenId",required = true) String drivenId,
                                @RequestParam(value="keyword",required = true) String keyword,
                                @RequestParam(value="download",required = false,defaultValue = "0") String download,
                                @RequestParam(value="save",required = false,defaultValue = "1") String save,
            HttpServletResponse response) throws Exception{

        Map<String,Object> map = new HashedMap();
        if(StringUtil.equalsString(save,"1")){
            map.put("download","1");
        }else{
            map.put("download",download);
        }
        map.put("save",save);
        Map<String,Object> documentMap = documentService.getDocumentFile(drivenId,keyword,map);
        byte[] data = FileUtil.toByteArray2((File)documentMap.get("file"));
        //判断是下载还是预览
        if(StringUtil.equalsString(download,"0")){
            //设置content-type
            response.setContentType("application/pdf");
        }else{//下载
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode((String)documentMap.get("fileName"),"UTF-8"));
        }
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.close();
    }

    /**
     * 文书预览和下载 post 用于传递echarts图表
     *  drivenId 必填 启动数据查询Id
     *  keyword  必填 关键字
     *  download 下载标志位（0-打印，1-下载）
     *  save 文书保存标志位（0-不保存，1-保存）
     *  response
     * @throws Exception
     */
    @RequestMapping(value = "/pdfShowDownload2",method = RequestMethod.POST)
    public void pdfShowDownload2(@RequestBody ValueObject req, HttpServletResponse response) throws Exception{
        String drivenId = req.get("drivenId").toString();
        String keyword = req.get("keyword").toString();
        String download = req.get("download").toString();
        String save = req.get("save").toString();
        Map<String,Object> map = new HashedMap();
        map.put("img",req);
        if(StringUtil.equalsString(save,"1")){
            map.put("download","1");
        }else{
            map.put("download",download);
        }
        map.put("save",save);
        Map<String,Object> documentMap = documentService.getDocumentFile(drivenId,keyword,map);
        byte[] data = FileUtil.toByteArray2((File)documentMap.get("file"));
        //判断是下载还是预览
        if(StringUtil.equalsString(download,"0")){
            //设置content-type
            response.setContentType("application/pdf");
        }else{//下载
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode((String)documentMap.get("fileName"),"UTF-8"));
        }
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.close();
    }
}
