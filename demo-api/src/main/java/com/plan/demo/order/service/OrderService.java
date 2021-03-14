package com.plan.demo.order.service;

import com.plan.demo.base.dao.TbOrderDao;
import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.order.dto.ReqCancelOrderDto;
import com.plan.frame.exception.SystemException;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator 2021/3/14 0014
 * @version V1.0.0
 * @description 订单管理service
 */
@Service
public class OrderService {
    @Autowired
    private TbOrderDao tbOrderDao;
    public void cancelOrder(ReqCancelOrderDto reqCancelOrderDto)throws Exception{
        if(CommonUtil.isEmpty(reqCancelOrderDto.getId())){
            throw new SystemException("取消订单失败","订单id不能为空","请联系管理员处理");
        }
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqCancelOrderDto.getId());
        if(CommonUtil.isEmpty(tbOrder)){
            throw new SystemException("取消订单失败","订单不存在","请联系管理员处理");
        }
        //判断订单是否可以取消
        //1.如果是预约单则出发前1个小时内不能取消订单
        //2.如果是实时单则司机接单后5分钟内可取消订单
        if(StringUtil.equalsString(tbOrder.getOrderRealType(),"1")){
            //预约单 当前时间是否小于预约时间减一个小时
        }
        tbOrder.setCancelReason(reqCancelOrderDto.getCancelReason());
        tbOrder.setOrderStatus("0");

    }
}
