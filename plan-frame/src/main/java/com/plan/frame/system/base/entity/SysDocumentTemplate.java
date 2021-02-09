package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @table SYS_DOCUMENT_TEMPLATE - 打印文书模板表
 * @time 2020-07-24 15:52:13
 */
public class SysDocumentTemplate {
    /**
     * 主键，对应表字段为：SYS_DOCUMENT_TEMPLATE.ID
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 模板类型，对应表字段为：SYS_DOCUMENT_TEMPLATE.TEMPLATE_TYPE
     */
    @ApiModelProperty("模板类型")
    private String templateType;

    /**
     * 模板名称，对应表字段为：SYS_DOCUMENT_TEMPLATE.TEMPLATE_NAME
     */
    @ApiModelProperty("模板名称")
    private String templateName;

    /**
     * 顺序值，对应表字段为：SYS_DOCUMENT_TEMPLATE.ORDER_VALUE
     */
    @ApiModelProperty("顺序值")
    private String orderValue;

    /**
     * 模板代码值，对应表字段为：SYS_DOCUMENT_TEMPLATE.TEMPLATE_CODE
     */
    @ApiModelProperty("模板代码值")
    private String templateCode;

    /**
     * 模板存放路径，对应表字段为：SYS_DOCUMENT_TEMPLATE.TEMPLATE_PATH
     */
    @ApiModelProperty("模板存放路径")
    private String templatePath;

    /**
     * 模板流程，对应表字段为：SYS_DOCUMENT_TEMPLATE.TEMPLATE_PROCESS
     */
    @ApiModelProperty("模板流程")
    private String templateProcess;

    /**
     * 文件类型0-文书，1-附件,2-其他，对应表字段为：SYS_DOCUMENT_TEMPLATE.FILE_TYPE
     */
    @ApiModelProperty("文件类型0-文书，1-附件,2-其他")
    private String fileType;

    /**
     * 父模版代码值，对应表字段为：SYS_DOCUMENT_TEMPLATE.PARENT_CODE
     */
    @ApiModelProperty("父模版代码值")
    private String parentCode;

    /**
     * 文号前缀，对应表字段为：SYS_DOCUMENT_TEMPLATE.SYMBOL_PREFIX
     */
    @ApiModelProperty("文号前缀")
    private String symbolPrefix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplateProcess() {
        return templateProcess;
    }

    public void setTemplateProcess(String templateProcess) {
        this.templateProcess = templateProcess;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSymbolPrefix() {
        return symbolPrefix;
    }

    public void setSymbolPrefix(String symbolPrefix) {
        this.symbolPrefix = symbolPrefix;
    }
}