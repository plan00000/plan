package com.plan.frame.annex.controller;

import com.plan.frame.annex.dto.FileSaveDto;
import com.plan.frame.annex.service.FileService;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.ConditionException;
import com.plan.frame.helper.ResultHelper;
import com.plan.frame.system.base.entity.SysFileInfo;
import com.plan.frame.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;
import java.util.Map;


/**
 * @Created by linzhihua
 * @Description  附件相关类
 * @ClassName UploadController
 * @Date 2019/7/4 18:15
 */
@RestController
@RequestMapping(value="/file")
@Api(tags="92-附件相关接口")
public class FileController {
    @Value("${file.tempPath}")
    private String tempPath;
    @Value("${file.uploadPath}")
    private String uploadPath;

    @Autowired
    private FileService fileService;
    /**
     * 附件上传
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value="文件上传接口", notes = "{\"fileTitle\":\"zcgl\", \"backAnnexIdFlag\":\"1\"}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="relId",required = false,dataType = "String",paramType = "query",value = "关联id"),
            @ApiImplicitParam(name="fileTitle",required = false,dataType = "String",paramType = "query",value = "附件主题"),
            @ApiImplicitParam(name = "backAnnexIdFlag",required = false,dataType = "String",paramType = "query",value = "是否返回附件主键AnnexId,0-不返回，1-返回")
    })
    @RequestMapping(value = "/singleUpload",method = RequestMethod.POST)
    public Result<Map<String,Object>> uploads(@RequestParam(value = "relId",required = false) String relId,
                                              @RequestParam(value = "fileTitle",required = false) String fileTitle,
                                              @RequestParam(value = "backAnnexIdFlag",required = false) String backAnnexIdFlag,
                                              MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartFile file = request.getFile("file");
        //获取文件名称
        String fileName = file.getOriginalFilename().trim();
        String fileType =fileName.substring(fileName.lastIndexOf("."),fileName.length());
        //文件路径
        String path = DateUtil.date2Str(new Date(), "yyyyMMdd")+System.getProperties().getProperty("file.separator");
        //临时文件名
        String tempFileName = CommonUtil.getUUID()+fileType;
        //文件存到临时文件夹下
        FileUpload.fileUpNotExtName(file, tempPath + path , tempFileName);
        Map<String,Object> map = new HashedMap();
        //如果reqUploadDto不为空时，则直接调用保存
        if(CommonUtil.isNotEmpty(relId)&&CommonUtil.isNotEmpty(fileTitle)){
            FileSaveDto fileSaveDto = new FileSaveDto();
            fileSaveDto.setRelId(relId);
            fileSaveDto.setFileTitle(fileTitle);
            fileSaveDto.setAnnexDir(path+tempFileName);
            fileSaveDto.setAnnexName(fileName);
            fileSaveDto.setBackAnnexIdFlag(backAnnexIdFlag);
            String annexId =fileService.saveFilePath(fileSaveDto);
            if(CommonUtil.isNotEmpty(backAnnexIdFlag)&&CommonUtil.equalsNumberOrChar(backAnnexIdFlag,"1")){
                map.put("annexId",annexId);
            }
        }

        map.put("path",path+tempFileName);
        map.put("fileName",fileName);
        return ResultHelper.success(map);
    }

    /**
     * 文件的预览和下载
     * @param urlPath 路径(备注：如果文件表主键annexId没有而知道路径时使用)
     * @param download 下载标志
     * @param annexId 文件表key值
     * @param searchTemp 是否在临时文件查找附件
     * @param downShowName 下载文件名
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getFile",method = RequestMethod.GET)
    @ApiOperation("文件的预览及下载接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="urlPath",required = false,dataType = "String",paramType = "query",value = "路径"),
            @ApiImplicitParam(name="download",required = false,dataType = "String",paramType = "query",value = "是否下载(0-预览，1-下载)"),
            @ApiImplicitParam(name="annexId",required = false,dataType = "String",paramType = "query",value = "附件表主键"),
            @ApiImplicitParam(name="searchTemp",required = false,dataType = "String",paramType = "query",value = "是否在临时文件查找（0-否，1-是）"),
            @ApiImplicitParam(name="downShowName",required = false,dataType = "String",paramType = "query",value="文件下载时，文件显示名称")
    })
    public Result<String> getFile(@RequestParam(value = "urlPath", required = false) String urlPath,
                        @RequestParam(value ="download" ,required = false, defaultValue = "0") String download,
                        @RequestParam(value="annexId",required= false) String annexId,
                        @RequestParam(value="searchTemp",required = false,defaultValue = "0") String searchTemp,
                        @RequestParam(value="downShowName",required = false) String downShowName,
                        HttpServletResponse response) throws Exception {
        String path = "";
        String result = "";
        //如果有传文件表key值则根据key值查找文件
        if(CommonUtil.isNotEmpty(annexId)){
            SysFileInfo sysFileInfo = fileService.getById(annexId);
            if(CommonUtil.isNotEmpty(sysFileInfo)&&CommonUtil.isNotEmpty(sysFileInfo.getFileDir())){
                path = uploadPath+sysFileInfo.getFileDir().replaceAll("\\\\","/");
            }
        }else {
            if(CommonUtil.equalsNumberOrChar(searchTemp,"1")){
                path = tempPath + URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }else{
                path = uploadPath + URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }
        }
        //获取文件
        File file = FileUtil.createFile(path);
        String fileName = file.getName();
        String fileLx = path.substring(path.lastIndexOf("."));
        byte[] data = null;
        //如果附件是word文档则转换为pdf进行预览
        if((CommonUtil.equalsNumberOrChar(fileLx,".doc")||CommonUtil.equalsNumberOrChar(fileLx,".docx"))&&CommonUtil.equalsNumberOrChar(download,"0")){
            fileLx = ".pdf";
            String pdfPath = tempPath+DateUtil.date2Str(new Date(), "yyyyMMdd")+System.getProperties().getProperty("file.separator")+CommonUtil.getUUID()+fileLx;
            //判断路径是否存在不存在则创建路径
            File pdfDir = FileUtil.createFile(tempPath+DateUtil.date2Str(new Date(),"yyyyMMdd"));
            if(! pdfDir.exists()){
                 pdfDir.mkdir();
            }
            Word2pdf.docToPdf(path,pdfPath);
            File pdfFile = FileUtil.createFile(pdfPath);
            if(!pdfFile.exists()){
                throw new ConditionException("doc转化成pdf出错","系统错误","请联系管理员");
            }
            data = FileUtil.toByteArray2(pdfPath);
        }else{
            data = FileUtil.toByteArray2(path);
        }
        fileLx = fileLx.toLowerCase();
        //判断文件是哪种类型
        Map<String, String> fileType = getContentType();
        if (fileType.containsKey(fileLx)) {
            //设置content-type
            response.setContentType(fileType.get(fileLx));
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM");
        }
        //文件下载
        if(download.equals("1")) {
            //如果是文件下载则要对文件名处理
            if(CommonUtil.isNotEmpty(downShowName)){
                //如果下载显示名称不会空，则展示其名称
                fileName = downShowName;
            }
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        }else {
            result = "data:image/jpg;base64,"
                    +new BASE64Encoder().encode(org.apache.commons.io.FileUtils.readFileToByteArray(file));
        }

        OutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.close();
        return ResultHelper.success(result);
    }


    /**
     * 文件的预览和下载(完整路径)
     * @param urlPath 路径(备注：如果文件表主键annexId没有而知道路径时使用)
     * @param download 下载标志
     * @param annexId 文件表key值
     * @param searchTemp 是否在临时文件查找附件
     * @param downShowName 下载文件名
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getFileByUrl",method = RequestMethod.GET)
    @ApiOperation("文件的预览及下载接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="urlPath",required = false,dataType = "String",paramType = "query",value = "路径"),
            @ApiImplicitParam(name="download",required = false,dataType = "String",paramType = "query",value = "是否下载(0-预览，1-下载)"),
            @ApiImplicitParam(name="annexId",required = false,dataType = "String",paramType = "query",value = "附件表主键"),
            @ApiImplicitParam(name="searchTemp",required = false,dataType = "String",paramType = "query",value = "是否在临时文件查找（0-否，1-是）"),
            @ApiImplicitParam(name="downShowName",required = false,dataType = "String",paramType = "query",value="文件下载时，文件显示名称")
    })
    public Result<String> getFileByUrl(@RequestParam(value = "urlPath", required = false) String urlPath,
                                  @RequestParam(value ="download" ,required = false, defaultValue = "0") String download,
                                  @RequestParam(value="annexId",required= false) String annexId,
                                  @RequestParam(value="searchTemp",required = false,defaultValue = "0") String searchTemp,
                                  @RequestParam(value="downShowName",required = false) String downShowName,
                                  HttpServletResponse response) throws Exception {
        String path = null;
        String result = "";
        //如果有传文件表key值则根据key值查找文件
        if(CommonUtil.isNotEmpty(annexId)){
            SysFileInfo sysFileInfo = fileService.getById(annexId);
            if(CommonUtil.isNotEmpty(sysFileInfo)&&CommonUtil.isNotEmpty(sysFileInfo.getFileDir())){
                path = uploadPath+sysFileInfo.getFileDir().replaceAll("\\\\","/");
            }
        }else {
            if(CommonUtil.equalsNumberOrChar(searchTemp,"1")){
                path = URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }else{
                path = URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }
        }
        //获取文件
        File file = new File(path);
        String fileName = file.getName();
        String fileLx = path.substring(path.lastIndexOf("."));
        byte[] data = null;
        //如果附件是word文档则转换为pdf进行预览
        if((CommonUtil.equalsNumberOrChar(fileLx,".doc")||CommonUtil.equalsNumberOrChar(fileLx,".docx"))&&CommonUtil.equalsNumberOrChar(download,"0")){
            fileLx = ".pdf";
            String pdfPath = tempPath+DateUtil.date2Str(new Date(), "yyyyMMdd")+System.getProperties().getProperty("file.separator")+CommonUtil.getUUID()+fileLx;
            //判断路径是否存在不存在则创建路径
            File pdfDir = new File(tempPath+DateUtil.date2Str(new Date(),"yyyyMMdd"));
            if(! pdfDir.exists()){
                pdfDir.mkdir();
            }
            Word2pdf.docToPdf(path,pdfPath);
            File pdfFile = new File(pdfPath);
            if(!pdfFile.exists()){
                throw new ConditionException("doc转化成pdf出错","系统错误","请联系管理员");
            }
            data = FileUtil.toByteArray2(pdfPath);
        }else{
            data = FileUtil.toByteArray2(path);
        }
        fileLx = fileLx.toLowerCase();
        //判断文件是哪种类型
        Map<String, String> fileType = getContentType();
        if (fileType.containsKey(fileLx)) {
            //设置content-type
            response.setContentType(fileType.get(fileLx));
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM");
        }
        //文件下载
        if(download.equals("1")) {
            //如果是文件下载则要对文件名处理
            if(CommonUtil.isNotEmpty(downShowName)){
                //如果下载显示名称不会空，则展示其名称
                fileName = downShowName;
            }
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        }else {
            Base64.Encoder encoder = Base64.getEncoder();
            result = "data:image/jpg;base64,"
                    +encoder.encodeToString(org.apache.commons.io.FileUtils.readFileToByteArray(file));
        }

        OutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.close();
        return ResultHelper.success(result);
    }


    /**
     * 文件的预览和下载(完整路径)
     * @param urlPath 路径(备注：如果文件表主键annexId没有而知道路径时使用)
     * @param download 下载标志
     * @param annexId 文件表key值
     * @param searchTemp 是否在临时文件查找附件
     * @param downShowName 下载文件名
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getFileByHttpUrl",method = RequestMethod.GET)
    @ApiOperation("文件的预览及下载接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="urlPath",required = false,dataType = "String",paramType = "query",value = "路径"),
            @ApiImplicitParam(name="download",required = false,dataType = "String",paramType = "query",value = "是否下载(0-预览，1-下载)"),
            @ApiImplicitParam(name="annexId",required = false,dataType = "String",paramType = "query",value = "附件表主键"),
            @ApiImplicitParam(name="searchTemp",required = false,dataType = "String",paramType = "query",value = "是否在临时文件查找（0-否，1-是）"),
            @ApiImplicitParam(name="downShowName",required = false,dataType = "String",paramType = "query",value="文件下载时，文件显示名称")
    })
    public Result<String> getFileByHttpUrl(@RequestParam(value = "urlPath", required = false) String urlPath,
                                       @RequestParam(value ="download" ,required = false, defaultValue = "0") String download,
                                       @RequestParam(value="annexId",required= false) String annexId,
                                       @RequestParam(value="searchTemp",required = false,defaultValue = "0") String searchTemp,
                                       @RequestParam(value="downShowName",required = false) String downShowName,
                                       HttpServletResponse response) throws Exception {
        String path = null;
        String result = "";
        //如果有传文件表key值则根据key值查找文件
        if(CommonUtil.isNotEmpty(annexId)){
            SysFileInfo sysFileInfo = fileService.getById(annexId);
            if(CommonUtil.isNotEmpty(sysFileInfo)&&CommonUtil.isNotEmpty(sysFileInfo.getFileDir())){
                path = uploadPath+sysFileInfo.getFileDir().replaceAll("\\\\","/");
            }
        }else {
            if(CommonUtil.equalsNumberOrChar(searchTemp,"1")){
                path = URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }else{
                path = URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }
        }
        //获取文件
        HttpURLConnection httpUrl = null;
        URL url = null;
        url = new URL(path);
        httpUrl = (HttpURLConnection) url.openConnection();
        InputStream input = httpUrl.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        byte[] data =  output.toByteArray();
        String fileLx = ".jpg";
        Map<String, String> fileType = this.getContentType();
        if (fileType.containsKey(fileLx)) {
            response.setContentType((String)fileType.get(fileLx));
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM");
        }
        //文件下载
        if(download.equals("1")) {

            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("","UTF-8"));
        }else {
            result = "data:image/jpg;base64,"
                    +new BASE64Encoder().encode(data);
        }
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.close();
        output.close();
        return ResultHelper.success(result);
    }

    @RequestMapping(value = "/getFileFlow",method = RequestMethod.GET)
    @ApiOperation("获取文件流")
    @ApiImplicitParams({
            @ApiImplicitParam(name="urlPath",required = false,dataType = "String",paramType = "query",value = "路径"),
            @ApiImplicitParam(name="download",required = false,dataType = "String",paramType = "query",value = "是否下载(0-预览，1-下载)"),
            @ApiImplicitParam(name="annexId",required = false,dataType = "String",paramType = "query",value = "附件表主键"),
            @ApiImplicitParam(name="searchTemp",required = false,dataType = "String",paramType = "query",value = "是否在临时文件查找（0-否，1-是）"),
            @ApiImplicitParam(name="downShowName",required = false,dataType = "String",paramType = "query",value="文件下载时，文件显示名称")
    })
    public Result<String> getFileFlow(@RequestParam(value = "urlPath", required = false) String urlPath,
                                  @RequestParam(value ="download" ,required = false, defaultValue = "0") String download,
                                  @RequestParam(value="annexId",required= false) String annexId,
                                  @RequestParam(value="searchTemp",required = false,defaultValue = "0") String searchTemp,
                                  @RequestParam(value="downShowName",required = false) String downShowName,
                                  HttpServletResponse response) throws Exception {
        String path = "";
        String result = "";
        //如果有传文件表key值则根据key值查找文件
        if(CommonUtil.isNotEmpty(annexId)){
            SysFileInfo sysFileInfo = fileService.getById(annexId);
            if(CommonUtil.isNotEmpty(sysFileInfo)&&CommonUtil.isNotEmpty(sysFileInfo.getFileDir())){
                path = uploadPath+sysFileInfo.getFileDir().replaceAll("\\\\","/");
            }
        }else {
            if(CommonUtil.equalsNumberOrChar(searchTemp,"1")){
                path = tempPath + URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }else{
                path = uploadPath + URLDecoder.decode(URLDecoder.decode(urlPath, "utf-8"));
            }
        }
        //获取文件
        File file = FileUtil.createFile(path);
        String fileName = file.getName();
        String fileLx = path.substring(path.lastIndexOf("."));
        byte[] data = null;
        //如果附件是word文档则转换为pdf进行预览
        if((CommonUtil.equalsNumberOrChar(fileLx,".doc")||CommonUtil.equalsNumberOrChar(fileLx,".docx"))&&CommonUtil.equalsNumberOrChar(download,"0")){
            fileLx = ".pdf";
            String pdfPath = tempPath+DateUtil.date2Str(new Date(), "yyyyMMdd")+System.getProperties().getProperty("file.separator")+CommonUtil.getUUID()+fileLx;
            //判断路径是否存在不存在则创建路径
            File pdfDir = FileUtil.createFile(tempPath+DateUtil.date2Str(new Date(),"yyyyMMdd"));
            if(! pdfDir.exists()){
                pdfDir.mkdir();
            }
            Word2pdf.docToPdf(path,pdfPath);
            File pdfFile = FileUtil.createFile(pdfPath);
            if(!pdfFile.exists()){
                throw new ConditionException("doc转化成pdf出错","系统错误","请联系管理员");
            }
            data = FileUtil.toByteArray2(pdfPath);
        }else{
            data = FileUtil.toByteArray2(path);
        }
        fileLx = fileLx.toLowerCase();
        //判断文件是哪种类型
        Map<String, String> fileType = getContentType();
        if (fileType.containsKey(fileLx)) {
            //设置content-type
            response.setContentType(fileType.get(fileLx));
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM");
        }
        //文件下载
        if(download.equals("1")) {
            //如果是文件下载则要对文件名处理
            if(CommonUtil.isNotEmpty(downShowName)){
                //如果下载显示名称不会空，则展示其名称
                fileName = downShowName;
            }
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        }else {
            Base64.Encoder encoder = Base64.getEncoder();
            result = "data:image/jpg;base64,"
                    +encoder.encodeToString(org.apache.commons.io.FileUtils.readFileToByteArray(file));
        }

        return ResultHelper.success(result);
    }

    /**
     * 附件删除
     * @param annexId
     */
    @ApiOperation("删除附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name="annexId",required = false,dataType = "String",paramType = "query",value = "附件表主键id")
    })
    @RequestMapping(value = "/deleteFile",method = RequestMethod.GET)
    public Result<Map<String,Object>> deleteFile(@RequestParam(value = "annexId") String annexId){
        return ResultHelper.success(fileService.deltetFile(annexId));
    }



    /**
     * 根据文件类型获取contentType参数对象，用于设置
     * @return
     */
    private Map<String,String> getContentType(){
        Map<String,String> contentType = new HashedMap();
        contentType.put(".pdf","application/pdf");
        contentType.put(".gif","image/gif");
        contentType.put(".png","image/png");
        contentType.put(".jpe","image/jpeg");
        contentType.put(".jpeg","image/jpeg");
        contentType.put(".jpg","image/jpeg");
        contentType.put(".html","text/html");
        contentType.put(".doc","application/msword");
        contentType.put(".docx","application/msword");
        contentType.put(".ppt","application/x-ppt");
        contentType.put(".txt","text/plain");
        contentType.put(".bmp","application/x-bmp");
        contentType.put(".img","application/x-img");
        contentType.put(".ppt","appication/powerpoint");
        contentType.put(".zip","application/zip");
        contentType.put(".xls","application/vnd.ms-excel");
        contentType.put(".xlsx","application/vnd.ms-excel");
        return contentType;
    }

}
