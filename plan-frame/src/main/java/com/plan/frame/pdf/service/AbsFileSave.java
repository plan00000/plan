package com.plan.frame.pdf.service;

import com.plan.frame.annex.service.FileService;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.system.base.dao.SysFileInfoDao;
import com.plan.frame.system.base.entity.SysDocumentTemplate;
import com.plan.frame.system.base.entity.SysFileInfo;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.FileUpload;
import com.plan.frame.util.FileUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description  该类是抽象类-实现生成文件的保存等操作-目前由于项目无需对打印的材料进行留存，所以该类操作为空
 * @ClassName AbsFileSave
 * @Date 2019/7/9 10:07
 */
public abstract class AbsFileSave {
    @Value("${file.uploadPath}")
    private String uploadPath;
    @Autowired
    private SysFileInfoDao sysFileInfoDao;
    @Autowired
    private FileService fileService;
    /**
     *
     * @param driverId 驱动数据组装主键Id
     * @param sysDocumentTemplate 关键字实体类
     * @return
     */
    public final Map<String,Object> save(String driverId, SysDocumentTemplate sysDocumentTemplate, Map<String,Object> reqMap) throws Exception{
        //生成文件
        File file = ((IPdfGeneration) this).generate(driverId,sysDocumentTemplate,reqMap);
        if(CommonUtil.isNotEmpty(reqMap.get("save"))){
            //存放路径
            String relativePath = uploadPath +driverId + System.getProperties().getProperty("file.separator")
                    +sysDocumentTemplate.getTemplateCode() +System.getProperties().getProperty("file.separator");
            //查询附件表是否已生成过该文书
            List<SysFileInfo> sysFileInfoList = fileService.findFileList(sysDocumentTemplate.getTemplateCode(),driverId);
            if(CommonUtil.isNotEmpty(sysFileInfoList)){
                for(SysFileInfo sysFileInfo:sysFileInfoList){
                    //删除文书附件
                    File deleteFile = FileUtil.createFile(sysFileInfo.getFileDir());
                    if(deleteFile.exists()){
                        deleteFile.delete();
                    }
                    //删除文书记录信息
                    sysFileInfoDao.deleteByPrimaryKey(sysFileInfo.getFileId());
                }
            }
            String savePath = relativePath+sysDocumentTemplate.getTemplateName()+".doc";
            String fileDir = driverId + System.getProperties().getProperty("file.separator")
                    +sysDocumentTemplate.getTemplateCode() +System.getProperties().getProperty("file.separator")
                    +sysDocumentTemplate.getTemplateName()+".doc";
            //如果文件存在就删除
            File checkFile = FileUtil.createFile(savePath);
            if(checkFile.exists()){
                checkFile.delete();
            }
            InputStream inputStream = new FileInputStream(file);
            //附件保存到正式环境
            FileUpload.copyFile(inputStream, relativePath, sysDocumentTemplate.getTemplateName()+".doc");
            SysFileInfo sysFileInfo = new SysFileInfo();
            sysFileInfo.setFileId(CommonUtil.getUUID());
            //附件关联id
            sysFileInfo.setRelId(driverId);
            //附件名称
            sysFileInfo.setFileName(sysDocumentTemplate.getTemplateName()+".doc");
            //附件主题
            sysFileInfo.setFileTitle(sysDocumentTemplate.getTemplateCode());
            //附件类型
            sysFileInfo.setFileType(".doc");
            //附件路径
            sysFileInfo.setFileDir(fileDir);
            //附件排序
//            tbrsFileInfo.setOrderNo();
            //设置用户操作信息
            BeanHelper.setUserInfo(sysFileInfo, true);
            //保存
            sysFileInfoDao.insert(sysFileInfo);

        }
        Map<String,Object> documentDto = new HashedMap();
        documentDto.put("fileName",file.getName());
        documentDto.put("url",file.getPath());
        documentDto.put("file",file);
        return  documentDto;
    }
}
