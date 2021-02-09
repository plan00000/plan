package com.plan.frame.entity;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.plan.frame.util.CommonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description 分页器实体
 * @ClassName Pagination
 * @Date 2019/8/2 14:15
 */
@ApiModel(value = "分页器实体")
public class Pageination {
    @ApiModelProperty(value = "第几页")
    private Integer page;
    @ApiModelProperty(value = "页限制条数")
    private Integer pageSize;
    @ApiModelProperty(value = "总条数")
    private Long total;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
    /**
     * 调用此方法后的查询会分页
     */
    public Pageination startPage() {
        if (CommonUtil.isEmpty(this.page)) {
            this.setPage(1);
        }
        if (CommonUtil.isEmpty(this.pageSize)) {
            this.setPageSize(10);
        }
        PageHelper.startPage(this.getPage(), this.getPageSize());
        return this;
    }

    /**
     * 将查询后的list传入,得到Pagination对象返回前端
     *
     * @param list 查询后的list
     * @return
     */
    public Pageination endPage(List list) {
        if (CommonUtil.isEmpty(list)) {
            this.total = 0l;
            this.page = 1;
        }
        PageInfo page = new PageInfo(list);
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
        this.page = page.getPageNum();
        return this;
    }
}
