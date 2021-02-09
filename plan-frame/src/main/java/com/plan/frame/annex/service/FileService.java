package com.plan.frame.annex.service;

import com.plan.frame.annex.dao.SysFileInfoExtDao;
import com.plan.frame.annex.dto.FileReqDto;
import com.plan.frame.annex.dto.FileSaveDto;
import com.plan.frame.exception.ConditionException;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.base.dao.SysFileInfoDao;
import com.plan.frame.system.base.entity.SysFileInfo;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.DateUtil;
import com.plan.frame.util.FileUpload;
import com.plan.frame.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @Created by linzhihua
 * @Description 附件相关操作service
 * @ClassName FileService
 * @Date 2019/7/5 8:39
 */
@Service
public class FileService {
    @Value("${file.tempPath}")
    private String tempPath;
    @Value("${file.uploadPath}")
    private String uploadPath;

    @Autowired
    private SysFileInfoDao sysFileInfoDao;
    @Autowired
    private SysFileInfoExtDao sysFileInfoExtDao;

    /**
     * 保存附件并把附件移到正式环境里
     *
     * @param fileSaveDto 附件保存参数
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveFilePath(FileSaveDto fileSaveDto) throws Exception {

        if (CommonUtil.isEmpty(fileSaveDto)) {
            //throw new ConditionException("错误信息","错误原因","建议");
            throw new ConditionException("附件保存出错","未传附件相关信息","请联系管理员");
        }
        if (CommonUtil.isEmpty(fileSaveDto.getRelId())) {
            throw new ConditionException("附件保存出错","附件关联id为空","请联系管理员");

        }
        if (CommonUtil.isEmpty(fileSaveDto.getAnnexName())) {
            throw new ConditionException("附件保存出错","附件名称为空","请联系管理员");

        }
        if (CommonUtil.isEmpty(fileSaveDto.getFileTitle())) {
            throw new ConditionException("附件保存出错","附件主题为空","请联系管理员");

        }
        if (CommonUtil.isEmpty(fileSaveDto.getAnnexDir())) {
            throw new ConditionException("附件保存出错","附件路径为空","请联系管理员");

        }

        //根据路径获取临时文件
        File tempFile = FileUtil.createFile(tempPath + fileSaveDto.getAnnexDir());
        if (!tempFile.exists()) {
            throw new ConditionException("附件保存出错","没有找到上传的附件","请联系管理员");

        }
        //正式路径
        String relativePath = DateUtil.date2Str(new Date(), "yyyyMMdd") +System.getProperties().getProperty("file.separator")
                +fileSaveDto.getRelId() + System.getProperties().getProperty("file.separator")
                +fileSaveDto.getFileTitle() +System.getProperties().getProperty("file.separator") + fileSaveDto.getAnnexName();
        File checkFile = FileUtil.createFile(uploadPath+relativePath);
        if (checkFile.exists()) {
            //正式环境如果有重复的文件，则要对附件名称进行处理
            String fileName = fileSaveDto.getAnnexName();
            String[] fileNameArray = fileName.split("\\.");
            if(CommonUtil.isNotEmpty(fileNameArray)){
                StringBuffer fileNameBuf = new StringBuffer();
                fileNameBuf.append(fileNameArray[0]);
                fileNameBuf.append("1");
                fileNameBuf.append(fileNameArray[1]);
                fileSaveDto.setAnnexName(fileName);
            }
            throw new ConditionException("附件保存出错","已存在该附件","请联系管理员");

        }
        String path = uploadPath
                +DateUtil.date2Str(new Date(), "yyyyMMdd") +System.getProperties().getProperty("file.separator")
                +fileSaveDto.getRelId() + System.getProperties().getProperty("file.separator")
                +fileSaveDto.getFileTitle() +System.getProperties().getProperty("file.separator");
        InputStream inputStream = FileUtil.getFileInPutStream(tempFile.getPath());
        //附件保存到正式环境
        FileUpload.copyFile(inputStream, path, fileSaveDto.getAnnexName());

        SysFileInfo sysFileInfo = new SysFileInfo();
        //主键id
        sysFileInfo.setFileId(CommonUtil.getUUID());
        //附件关联id
        sysFileInfo.setRelId(fileSaveDto.getRelId());
        //附件名称
        sysFileInfo.setFileName(fileSaveDto.getAnnexName());
        //附件主题
        sysFileInfo.setFileTitle(fileSaveDto.getFileTitle());
        String tempName = tempFile.getName();
        String fileType = tempName.substring(tempName.lastIndexOf("."), tempName.length());
        //附件类型
        sysFileInfo.setFileType(fileType);
        //附件路径
        sysFileInfo.setFileDir(relativePath);
        //附件排序
        sysFileInfo.setOrderNo(fileSaveDto.getOrderNo());
        //设置用户操作信息
        if(CommonUtil.isNotEmpty(ThreadLocalHelper.getUser())){
            sysFileInfo.setCreateUser(ThreadLocalHelper.getUser().getUserName());
            sysFileInfo.setUpdateUser(ThreadLocalHelper.getUser().getUserName());
        }
        sysFileInfo.setCreateTime(new Date());
        sysFileInfo.setUpdateTime(new Date());
        //保存
        sysFileInfoDao.insert(sysFileInfo);
        //
        if(CommonUtil.isNotEmpty(fileSaveDto.getBackAnnexIdFlag())&&CommonUtil.equalsNumberOrChar(fileSaveDto.getBackAnnexIdFlag(),"1")){
            return sysFileInfo.getFileId();
        }
        return "";
    }

    /**
     * 查询附件列表
     *
     * @param fileTitle
     * @param relId
     * @return
     */
    public List<SysFileInfo> findFileList(String fileTitle, String relId) {
        SysFileInfo queryEnty = new SysFileInfo();
        queryEnty.setRelId(relId);
        queryEnty.setFileTitle(fileTitle);
        List<SysFileInfo> sysFileInfoList = sysFileInfoDao.selectByEntitySelective(queryEnty);
        return sysFileInfoList;
    }

    /**
     * 查询附件列表
     * @param fileTitle
     * @param relIdList 关联idLists
     * @return
     */
    public List<SysFileInfo> findFileList(String fileTitle,List<String> relIdList){
        FileReqDto fileReqDto = new FileReqDto();
        fileReqDto.setRelIdList(relIdList);
        fileReqDto.setFileTitle(fileTitle);
        List<SysFileInfo> tbrsAnnexInfoList = sysFileInfoExtDao.selectByEntitySelective(fileReqDto);
        return tbrsAnnexInfoList;
    }

    /**
     * 删除附件
     *
     * @param annexId
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deltetFile(String annexId) {

        SysFileInfo sysFileInfo = sysFileInfoDao.selectByPrimaryKey(annexId);
        if (CommonUtil.isEmpty(sysFileInfo)) {
            throw new ConditionException("附件删除错误","不存在"+annexId+"的附件","请联系管理员");

        }
        String path = uploadPath + sysFileInfo.getFileDir();
        File file = FileUtil.createFile(path);
        if (file.exists()) {
            file.delete();
        }
        //删除文件记录
        sysFileInfoDao.deleteByPrimaryKey(annexId);
        return true;
    }

    /**
     * 根据ID获取实体
     * @param fileId
     * @return
     */
    public SysFileInfo getById(String fileId){
        return sysFileInfoDao.selectByPrimaryKey(fileId);
    }

}
