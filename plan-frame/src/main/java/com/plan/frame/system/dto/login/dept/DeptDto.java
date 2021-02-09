package com.plan.frame.system.dto.login.dept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description: 部门具体信息dto
 * @ClassName DeptDto
 * @Date 2020/6/19 16:08
 */
public class DeptDto {
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "字段编号")
    private String seqNo;
    @ApiModelProperty(value = "部门id")
    private String id;
    @ApiModelProperty(value = "部门状态")
    private String deptStatus;
    @ApiModelProperty(value = "对该部门的描述")
    private String description;
    @ApiModelProperty(value = "部门编号")
    private String deptNo;
    @ApiModelProperty(value = "父类部门id")
    private String parentId;
    @ApiModelProperty(value = "子级列表")
    private List<DeptDto> subDept;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<DeptDto> getSubDept() {
        return subDept;
    }

    public void setSubDept(List<DeptDto> subDept) {
        this.subDept = subDept;
    }
}
