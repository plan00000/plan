package com.plan.demo.order.service;

import com.plan.demo.base.dao.TbOrderDao;
import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.order.dao.OrderMapper;
import com.plan.demo.order.dto.ReqAddOrderDto;
import com.plan.demo.order.dto.ReqCancelOrderDto;
import com.plan.demo.order.dto.ResPassengerOrderDto;
import com.plan.demo.order.dto.ResPassengerOrderResultDto;
import com.plan.frame.entity.ValueObject;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.StringUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator 2021/3/14 0014
 * @version V1.0.0
 * @description 订单管理service
 */
@Service
public class OrderService {
    @Autowired
    private TbOrderDao tbOrderDao;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 新增订单
     * @param reqAddOrderDto
     * @throws Exception
     */
    public void addOrder(ReqAddOrderDto reqAddOrderDto)throws Exception{
        //1.如果是预约单则预约时间要大于当前时间一个小时以上；
        if(StringUtil.equalsString(reqAddOrderDto.getOrderRealType(),"1")){
            Date bespeakTimeMinOneHouse =DateUtils.addHours(reqAddOrderDto.getOrderBespeakTime(),-1);
            Date nowDate =new Date();
            if(nowDate.before(bespeakTimeMinOneHouse)){
                throw new SystemException("新增订单失败","预约时间不能小于一个小时","请重新选择预约时间");
            }
        }
        //2.如果订单是实时订单，判断是否存在另一个实时订单
        if(StringUtil.equalsString(reqAddOrderDto.getOrderRealType(),"0")){
            ValueObject valueObject = new ValueObject();
            valueObject.put("userId",ThreadLocalHelper.getUser().getId());
            valueObject.put("orderRealType","0");
            List<ValueObject> nowOrderVoList = orderMapper.findNowOrderList(valueObject);
            if(CommonUtil.isNotEmpty(nowOrderVoList)){
                throw new SystemException("新增订单失败","已存在一个实时订单，不能从复下单","请耐心等待司机");
            }
        }
        TbOrder tbOrder = new TbOrder();
        BeanHelper.copyBeanValue(reqAddOrderDto,tbOrder);
        tbOrder.setId(CommonUtil.getUUID());
        tbOrder.setCreateTime(new Date());
        tbOrder.setOrderStatus("1");
        tbOrderDao.insert(tbOrder);
    }

    /**
     * 获取用户订单信息
     */
    public ResPassengerOrderResultDto getPassengerOrderList(){
        UserInfoDto userInfoDto = ThreadLocalHelper.getUser();
        ValueObject valueObject = new ValueObject();
        valueObject.put("userId",userInfoDto.getId());
        List<ValueObject> valueObjectList = orderMapper.findNowOrderList(valueObject);
        ResPassengerOrderResultDto resPassengerOrderResultDto = new ResPassengerOrderResultDto();
        if(CommonUtil.isNotEmpty(valueObjectList)){
            List<ResPassengerOrderDto> resPassengerOrderDtoList = BeanHelper.voListToBeanList(valueObjectList,ResPassengerOrderDto.class);
            if(CommonUtil.isNotEmpty(resPassengerOrderDtoList)){
                resPassengerOrderResultDto.setResPassengerOrderDtoList(resPassengerOrderDtoList);
            }
        }
        return resPassengerOrderResultDto;
    }

    /**
     * 取消订单
     * @param reqCancelOrderDto
     * @throws Exception
     */
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
            Date bespeakTimeMinOneHouse =DateUtils.addHours(tbOrder.getOrderBespeakTime(),-1);
            Date nowDate = new Date();
            if(nowDate.before(bespeakTimeMinOneHouse)){
                throw new SystemException("取消订单失败","已过取消订单时间","请您耐心等待司机");
            }
        }else{
            Date orderReceiveTimeFiveMinutes = DateUtils.addMinutes(tbOrder.getOrderReceiveTime(),5);
            Date nowDate = new Date();
            if(nowDate.before(orderReceiveTimeFiveMinutes)){
                throw new SystemException("取消订单失败","已过取消订单时间","请您耐心等待司机");
            }
        }
        tbOrder.setCancelReason(reqCancelOrderDto.getCancelReason());
        tbOrder.setOrderStatus("0");
        tbOrderDao.update(tbOrder);
    }
}
