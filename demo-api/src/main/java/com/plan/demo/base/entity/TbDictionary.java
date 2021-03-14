package com.plan.demo.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table tb_dictionary - 
 * @time 2021-03-14 13:32:42
 */
public class TbDictionary {
    /**
     * 主键id，对应表字段为：tb_dictionary.id
     */
    @ApiModelProperty("主键id")
    private String id;

    /**
     * 编码值，对应表字段为：tb_dictionary.code
     */
    @ApiModelProperty("编码值")
    private String code;

    /**
     * 编码名称，对应表字段为：tb_dictionary.name
     */
    @ApiModelProperty("编码名称")
    private String name;

    /**
     * 创建时间，对应表字段为：tb_dictionary.create_time
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_dictionary.update_time
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}