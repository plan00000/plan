package com.plan.frame.pdf.service;

import com.plan.frame.system.base.entity.SysDocumentTemplate;

import java.io.File;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description 文件生成接口  继承该接口的实现类实现不同方式的pdf生成
 * @ClassName IPdfGeneration
 * @Date 2019/7/8 20:32
 */
public interface IPdfGeneration {
    /**
     * 生成接口
     * @param driverId  驱动数据组装主键Id
     * @param sysDocumentTemplate  模板类
     * @param map  参数
     * @return
     */
    File generate(String driverId, SysDocumentTemplate sysDocumentTemplate, Map<String, Object> map) throws Exception;
}
