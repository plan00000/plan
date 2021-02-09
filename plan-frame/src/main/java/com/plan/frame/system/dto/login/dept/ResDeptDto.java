package com.plan.frame.system.dto.login.dept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description：机构代码
 * @ClassName ResDeptDto
 * @Date 2020/6/19 16:05
 */
public class ResDeptDto {
    @ApiModelProperty(value = "部门列表")
    private List<DeptDto> deptList;

    public List<DeptDto> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptDto> deptList) {
        this.deptList = deptList;
    }
}
