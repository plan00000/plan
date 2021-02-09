package com.plan.frame.pdf.service;

import com.plan.frame.exception.SystemException;
import com.plan.frame.system.base.dao.SysDocumentTemplateDao;
import com.plan.frame.system.base.entity.SysDocumentTemplate;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.SpringContextHolder;
import com.plan.frame.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description  文书打印下载service
 * @ClassName DocumentService
 * @Date 2019/7/9 15:13
 */
@Service
public class DocumentService {

    @Autowired
    private SysDocumentTemplateDao sysDocumentTemplateDao;

    /**
     * 文书预览及下载
     * @param drivenId 驱动Id
     * @param keyword 关键字
     * @param map 参数map
     * @return
     * @throws Exception
     */
    public Map<String,Object> getDocumentFile(String drivenId, String keyword, Map<String,Object> map)throws Exception{
        SysDocumentTemplate selectSysDocumentTemplate = new SysDocumentTemplate();
        selectSysDocumentTemplate.setTemplateCode(keyword);
        List<SysDocumentTemplate> sysDocumentTemplateList = sysDocumentTemplateDao.selectByEntitySelective(selectSysDocumentTemplate);
        if(CommonUtil.isEmpty(sysDocumentTemplateList)){
            throw new SystemException("pdf生成出錯","未找到模板号为："+keyword+"的模板记录","");
        }
        SysDocumentTemplate sysDocumentTemplate = sysDocumentTemplateList.get(0);
        //获取要注入的bean
        AbsFileSave absFileSave = null;
        // 如有父模板则用父模板的bean
        if(CommonUtil.isEmpty(sysDocumentTemplate.getParentCode())){
            absFileSave = SpringContextHolder.getBean(sysDocumentTemplate.getTemplateCode());
        }else{
            absFileSave = SpringContextHolder.getBean(sysDocumentTemplate.getParentCode());
        }
        Map<String,Object> documentMap= absFileSave.save(drivenId,sysDocumentTemplate,map);
        String download = (String)map.get("download");
        if(StringUtil.equalsString(download,"1")){
            documentMap.put("fileName",sysDocumentTemplate.getTemplateName()+".doc");
        }
        return  documentMap;
    }
}
