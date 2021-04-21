package com.plan.demo.order.service;

import com.plan.demo.base.dao.TbDriverDao;
import com.plan.demo.base.dao.TbEvaluateDao;
import com.plan.demo.base.dao.TbOrderDao;
import com.plan.demo.base.dao.TbPassengerDao;
import com.plan.demo.base.entity.TbDriver;
import com.plan.demo.base.entity.TbEvaluate;
import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.base.entity.TbPassenger;
import com.plan.demo.order.dao.OrderMapper;
import com.plan.demo.order.dto.*;
import com.plan.demo.util.GpsAreaUtil;
import com.plan.frame.entity.ValueObject;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.DateUtil;
import com.plan.frame.util.StringUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ljw 2021/3/14 0014
 * @version V1.0.0
 * @description 订单管理service
 */
@Service
public class OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private TbOrderDao tbOrderDao;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TbDriverDao tbDriverDao;
    @Autowired
    private TbPassengerDao tbPassengerDao;
    @Autowired
    private TbEvaluateDao tbEvaluateDao;

    /**
     * 新增订单
     * @param reqAddOrderDto
     * @throws Exception
     */
    public ResAddOrderDto addOrder(ReqAddOrderDto reqAddOrderDto)throws Exception{
        //1.如果是预约单则预约时间要大于当前时间一个小时以上；
        if(StringUtil.equalsString(reqAddOrderDto.getOrderRealType(),"1")){
            Date orderBespeakTime = DateUtil.str2Date(reqAddOrderDto.getOrderBespeakTime(),"yyyy-MM-dd HH:mm:ss");
            Date bespeakTimeMinOneHouse =DateUtils.addHours(orderBespeakTime,-1);
            Date nowDate =new Date();
            if(nowDate.before(bespeakTimeMinOneHouse)){
                throw new SystemException("新增订单失败","预约时间不能小于一个小时","请重新选择预约时间");
            }
        }
        //2.如果有存在进行中的订单，则不能从复下单

        ReqOrderRealTypeDto reqOrderRealTypeDto = new ReqOrderRealTypeDto();
        reqOrderRealTypeDto.setUserId(ThreadLocalHelper.getUser().getId());
        reqOrderRealTypeDto.setOrderRealType("0");
        List<ValueObject> nowOrderVoList = orderMapper.findNowOrderList(reqOrderRealTypeDto);
        if(CommonUtil.isNotEmpty(nowOrderVoList)){
            throw new SystemException("新增订单失败","已存在一个订单，不能从复下单","请耐心等待司机");
        }

        String orderStartLon = reqAddOrderDto.getOrderStartLon().replace("Optional(","");
        orderStartLon = orderStartLon.replace(")","");
        orderStartLon = orderStartLon.replace("-","");

        String orderStartLat = reqAddOrderDto.getOrderStartLat();
        orderStartLat = orderStartLat.replace("Optional(","");
        orderStartLat = orderStartLat.replace(")","");
        orderStartLat = orderStartLat.replace("-","");

        String orderEndLon = reqAddOrderDto.getOrderEndLon();
        orderEndLon = orderEndLon.replace("Optional(","");
        orderEndLon = orderEndLon.replace(")","");
        orderEndLon = orderEndLon.replace("-","");

        String orderEndLat = reqAddOrderDto.getOrderEndLat();
        orderEndLat = orderEndLat.replace("Optional(","");
        orderEndLat = orderEndLat.replace(")","");
        orderEndLat = orderEndLat.replace("-","");

        reqAddOrderDto.setOrderStartLat(orderStartLat);
        reqAddOrderDto.setOrderStartLon(orderStartLon);
        reqAddOrderDto.setOrderEndLon(orderEndLon);
        reqAddOrderDto.setOrderEndLat(orderEndLat);

        TbOrder tbOrder = new TbOrder();
        BeanHelper.copyBeanValue(reqAddOrderDto,tbOrder);
        tbOrder.setUserId(ThreadLocalHelper.getUser().getId());
        tbOrder.setOrderTime(new Date());
        tbOrder.setCreateTime(new Date());
        tbOrder.setOrderStatus("1");
        Long id = orderMapper.findMaxId();
        tbOrder.setId(id+1);
        tbOrderDao.insert(tbOrder);
        ResAddOrderDto resAddOrderDto = new ResAddOrderDto();
        resAddOrderDto.setId(tbOrder.getId());
        return resAddOrderDto;
    }

    /**
     * 获取用户订单信息
     */
    public ResPassengerOrderResultDto getPassengerOrderList(){
        UserInfoDto userInfoDto = ThreadLocalHelper.getUser();
        ReqOrderRealTypeDto reqOrderRealTypeDto = new ReqOrderRealTypeDto();
        reqOrderRealTypeDto.setUserId(userInfoDto.getId());
        List<ValueObject> valueObjectList = orderMapper.findNowOrderList(reqOrderRealTypeDto);
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
            if(CommonUtil.isNotEmpty(tbOrder.getOrderReceiveTime())) {
                Date orderReceiveTimeFiveMinutes = DateUtils.addMinutes(tbOrder.getOrderReceiveTime(), 5);
                Date nowDate = new Date();
                if (nowDate.before(orderReceiveTimeFiveMinutes)) {
                    throw new SystemException("取消订单失败", "已过取消订单时间", "请您耐心等待司机");
                }
            }
        }
        tbOrder.setCancelReason(reqCancelOrderDto.getCancelReason());
        tbOrder.setOrderStatus("0");
        tbOrderDao.update(tbOrder);
    }

    /**
     * 获取线路列表信息
     * @return
     * @throws Exception
     */
    public ResOrderLineResultDto getLineList()throws Exception{
        ResOrderLineResultDto resOrderLineResultDto = new ResOrderLineResultDto();
        List<ValueObject> lineVoList = orderMapper.findLineList();
        List<ResOrderLineDto> resLineDtoList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(lineVoList)){
            resLineDtoList=BeanHelper.voListToBeanList(lineVoList,ResOrderLineDto.class);
        }
        resOrderLineResultDto.setOrderLineList(resLineDtoList);
        return resOrderLineResultDto;
    }

    /**
     * 获取订单信息
     * @param reqOrderInfoDto
     * @return
     * @throws Exception
     */
    public ResPassengerOrderInfoDto getPassengerOrderInfo(ReqOrderInfoDto reqOrderInfoDto) throws Exception{
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderInfoDto.getId());
        if(CommonUtil.isEmpty(tbOrder)){
            throw new SystemException("获取订单信息失败","不存在该订单","请联系管理员处理");
        }
        ResPassengerOrderInfoDto resPassengerOrderInfoDto = new ResPassengerOrderInfoDto();
        BeanHelper.copyBeanValue(tbOrder,resPassengerOrderInfoDto);
        //如果订单处于司机已接单、订单进行中，订单完成则返回对应的司机信息
        if(CommonUtil.isNotEmpty(tbOrder.getDriveId())){
            TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(tbOrder.getDriveId());
            ResPassengerOrderInfoDriverDto resPassengerOrderInfoDriverDto = new ResPassengerOrderInfoDriverDto();
            if(CommonUtil.isNotEmpty(tbDriver)){
                BeanHelper.copyBeanValue(tbDriver,resPassengerOrderInfoDriverDto);
            }
            resPassengerOrderInfoDto.setResPassengerOrderInfoDriverDto(resPassengerOrderInfoDriverDto);
        }
        //获取订单的订单评价
        TbEvaluate tbEvaluateQuery = new TbEvaluate();
        tbEvaluateQuery.setOrderId(tbOrder.getId());
        List<TbEvaluate> tbEvaluateList = tbEvaluateDao.selectByEntitySelective(tbEvaluateQuery);
        if(CommonUtil.isNotEmpty(tbEvaluateList)){
            resPassengerOrderInfoDto.setOrderHasEvaluateFlag("1");
            TbEvaluate tbEvaluate = tbEvaluateList.get(0);
            resPassengerOrderInfoDto.setOrderStar(tbEvaluate.getOrderStar());
            resPassengerOrderInfoDto.setOrderEvaluate(tbEvaluate.getOrderEvaluate());
            resPassengerOrderInfoDto.setOrderService(tbEvaluate.getOrderService());
        }else{
            resPassengerOrderInfoDto.setOrderHasEvaluateFlag("0");
        }
        return resPassengerOrderInfoDto;
    }

    /**
     * 6-获取乘客与司机的距离
     * @param reqOrderDto
     * @return
     * @throws Exception
     */
    public String getPassengerDriverDistance(ReqOrderDto reqOrderDto)throws Exception{
        String distanceStr = null;
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderDto.getId());
        if(CommonUtil.isEmpty(tbOrder)){
            throw new SystemException("获取乘客与司机的距离","不存在该订单","请联系管理员处理");
        }
        if(CommonUtil.isNotEmpty(tbOrder.getDriveId())&&CommonUtil.isNotEmpty(tbOrder.getUserId())){
            TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(tbOrder.getDriveId());
            TbPassenger tbPassenger = tbPassengerDao.selectByPrimaryKey(tbOrder.getUserId());
            Double distance = GpsAreaUtil.getDistance(tbPassenger.getLon(),tbPassenger.getLat(),tbDriver.getLon(),tbDriver.getLat());
            distanceStr = String.valueOf(distance);
        }
        return distanceStr;
    }

    /**
     * 去获取司机订单详细信息
     * @param reqOrderDto
     * @return
     * @throws Exception
     */
    public ResDriverOrderInfoDto getDriverOrderInfo(ReqOrderDto reqOrderDto)throws Exception{
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderDto.getId());
        if(CommonUtil.isEmpty(tbOrder)){
            throw new SystemException("获取订单详情信息失败","不存在该订单","请刷新页面");
        }
        ResDriverOrderInfoDto resDriverOrderInfoDto = new ResDriverOrderInfoDto();
        BeanHelper.copyBeanValue(tbOrder,resDriverOrderInfoDto);
        if(StringUtil.equalsString(tbOrder.getOrderType(),"0")){
            resDriverOrderInfoDto.setOrderTypeInfo(tbOrder.getOrderUserNum()+"人包车");
        }else{
            resDriverOrderInfoDto.setOrderTypeInfo(tbOrder.getOrderUserNum()+"人拼车");
        }
        TbPassenger tbPassenger = tbPassengerDao.selectByPrimaryKey(tbOrder.getUserId());
        resDriverOrderInfoDto.setMobileNo(tbPassenger.getMobileno());
        resDriverOrderInfoDto.setLat(tbPassenger.getLat());
        resDriverOrderInfoDto.setLon(tbPassenger.getLon());

        return resDriverOrderInfoDto;
    }

    /**
     *获取乘客完成订单信息
     * @param
     * @return
     * @throws Exception
     */
    public ResCompleteOrderResultDto getPassengerCompleteOrderList()throws Exception{
        ResCompleteOrderResultDto resCompleteOrderResultDto = new ResCompleteOrderResultDto();
        logger.error("用户id："+ThreadLocalHelper.getUser().getId()+",用户名："+ThreadLocalHelper.getUser().getUserName());
        long passengerId = ThreadLocalHelper.getUser().getId();

        TbOrder tbOrderQuery = new TbOrder();
        tbOrderQuery.setOrderStatus("4");
        tbOrderQuery.setUserId(passengerId);
        List<TbOrder> tbOrderList = tbOrderDao.selectByEntitySelective(tbOrderQuery);
        List<ResCompleteOrderDto> completeOrderDtoList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(tbOrderList)){
            for(TbOrder tbOrder:tbOrderList){
                ResCompleteOrderDto resCompleteOrderDto = new ResCompleteOrderDto();
                resCompleteOrderDto.setId(tbOrder.getId());
                resCompleteOrderDto.setOrderTime(DateUtil.date2Str(tbOrder.getOrderTime(),"yyyy-MM-dd HH:mm:ss"));
                if(StringUtil.equalsString(tbOrder.getOrderType(),"0")) {
                    resCompleteOrderDto.setOrderTypeInfo(tbOrder.getOrderUserNum() + "人包车");
                }else{
                    resCompleteOrderDto.setOrderTypeInfo(tbOrder.getOrderUserNum() + "人拼车");
                }
                resCompleteOrderDto.setOrderStartAddress(tbOrder.getOrderStartAddress());
                resCompleteOrderDto.setOrderEndAddress(tbOrder.getOrderEndAddress());
                resCompleteOrderDto.setOrderStatus(tbOrder.getOrderStatus());
                completeOrderDtoList.add(resCompleteOrderDto);
            }
            resCompleteOrderResultDto.setCompleteOrderDtoList(completeOrderDtoList);
        }
        return resCompleteOrderResultDto;
    }

    /**
     *获取司机完成订单信息
     * @param
     * @return
     * @throws Exception
     */
    public ResCompleteOrderResultDto getDriverCompleteOrderList()throws Exception{
        ResCompleteOrderResultDto resCompleteOrderResultDto = new ResCompleteOrderResultDto();
        logger.error("用户id："+ThreadLocalHelper.getUser().getId()+",用户名："+ThreadLocalHelper.getUser().getUserName());
        long driverId = ThreadLocalHelper.getUser().getId();

        TbOrder tbOrderQuery = new TbOrder();
        tbOrderQuery.setOrderStatus("4");
        tbOrderQuery.setDriveId(driverId);
        List<TbOrder> tbOrderList = tbOrderDao.selectByEntitySelective(tbOrderQuery);
        List<ResCompleteOrderDto> completeOrderDtoList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(tbOrderList)){
            for(TbOrder tbOrder:tbOrderList){
                ResCompleteOrderDto resCompleteOrderDto = new ResCompleteOrderDto();
                resCompleteOrderDto.setId(tbOrder.getId());
                resCompleteOrderDto.setOrderTime(DateUtil.date2Str(tbOrder.getOrderTime(),"yyyy-MM-dd HH:mm:ss"));
                if(StringUtil.equalsString(tbOrder.getOrderType(),"0")) {
                    resCompleteOrderDto.setOrderTypeInfo(tbOrder.getOrderUserNum() + "人包车");
                }else{
                    resCompleteOrderDto.setOrderTypeInfo(tbOrder.getOrderUserNum() + "人拼车");
                }
                resCompleteOrderDto.setOrderStartAddress(tbOrder.getOrderStartAddress());
                resCompleteOrderDto.setOrderEndAddress(tbOrder.getOrderEndAddress());
                completeOrderDtoList.add(resCompleteOrderDto);
            }
            resCompleteOrderResultDto.setCompleteOrderDtoList(completeOrderDtoList);
        }

        return resCompleteOrderResultDto;
    }

    /**
     * 司机完成订单
     * @param reqOrderDto
     * @throws Exception
     */
    public void completeOrder(ReqOrderDto reqOrderDto)throws Exception{
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderDto.getId());
        if(CommonUtil.isEmpty(tbOrder)){
            throw new SystemException("司机完成订单失败","不存在该订单","请联系管理员处理");
        }
        tbOrder.setOrderStatus("4");
        tbOrderDao.update(tbOrder);
    }

    /**
     * 司机接乘客（行程开始）
     * @param reqOrderDto
     * @throws Exception
     */
    public void driverPickUpPassenger(ReqOrderDto reqOrderDto) throws Exception{
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderDto.getId());
        if(CommonUtil.isEmpty(tbOrder)){
            throw new SystemException("司机完成订单失败","不存在该订单","请联系管理员处理");
        }
        if(!StringUtil.equalsString(tbOrder.getOrderStatus(),"2")){
            throw new SystemException("司机完成订单失败","该订单状态已变化","请重新刷新订单");
        }
        tbOrder.setOrderStatus("3");
        tbOrderDao.update(tbOrder);
    }

    /**
     * 订单评价
     * @param reqOrderEvaluationDto
     * @throws Exception
     */
    public void orderEvaluation(ReqOrderEvaluationDto reqOrderEvaluationDto)throws Exception{
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderEvaluationDto.getId());
        TbEvaluate tbEvaluate = new TbEvaluate();
        long id = orderMapper.findTbEvaluateMaxId();
        BeanHelper.copyBeanValue(reqOrderEvaluationDto,tbEvaluate);
        tbEvaluate.setId(id+1);
        tbEvaluate.setDriverId(tbOrder.getDriveId());
        tbEvaluate.setOrderId(reqOrderEvaluationDto.getId());
        tbEvaluate.setCreateTime(new Date());
        tbEvaluateDao.insert(tbEvaluate);
    }

    /**
     * 修改订单状态
     * @param reqOrderNextStatusDto
     * @throws Exception
     */
    public void nexOrderStatus(ReqOrderNextStatusDto reqOrderNextStatusDto) throws Exception{
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderNextStatusDto.getId());
        if(StringUtil.equalsString(reqOrderNextStatusDto.getStatus(),"0"));{
            tbOrder.setOrderStatus("0");
            tbOrder.setCancelReason("司机太慢了");
            tbOrder.setUpdateTime(new Date());
        }
        if(StringUtil.equalsString(reqOrderNextStatusDto.getStatus(),"2")){
            //司机已接单
            tbOrder.setOrderStatus("2");
            tbOrder.setDriveId(35L);
            tbOrder.setUpdateTime(new Date());
        }
        if(StringUtil.equalsString(reqOrderNextStatusDto.getStatus(),"3")){
            //行程开始
            tbOrder.setOrderStatus("3");
            tbOrder.setOrderReceiveTime(new Date());
            tbOrder.setUpdateTime(new Date());
        }
        if(StringUtil.equalsString(reqOrderNextStatusDto.getStatus(),"4")){
            //行程结束
            tbOrder.setOrderStatus("4");
            tbOrder.setUpdateTime(new Date());
        }
        if(StringUtil.equalsString(reqOrderNextStatusDto.getStatus(),"5")){
            //行程超时
            tbOrder.setOrderStatus("5");
            tbOrder.setUpdateTime(new Date());
        }
        tbOrderDao.updateSelective(tbOrder);
    }

    /**
     *
     * @param reqOrderDto
     * @return
     * @throws Exception
     */
    public ResCompleteOrderInfoDto completeOrderInfo(ReqOrderDto reqOrderDto)throws Exception{
        TbOrder tbOrder = tbOrderDao.selectByPrimaryKey(reqOrderDto.getId());
        if(CommonUtil.isEmpty(tbOrder)){
            throw new SystemException("获取完成订单详细信息失败","订单不存在","请重新刷新");
        }
        ResCompleteOrderInfoDto resCompleteOrderInfoDto = new ResCompleteOrderInfoDto();
        BeanHelper.copyBeanValue(reqOrderDto,resCompleteOrderInfoDto);
        TbEvaluate tbEvaluateQuery = new TbEvaluate();
        tbEvaluateQuery.setOrderId(tbOrder.getId());
        List<TbEvaluate> tbEvaluateList = tbEvaluateDao.selectByEntitySelective(tbEvaluateQuery);
        if(CommonUtil.isNotEmpty(tbEvaluateList)){
            TbEvaluate tbEvaluate = tbEvaluateList.get(0);
            resCompleteOrderInfoDto.setOrderStar(tbEvaluate.getOrderStar());
            resCompleteOrderInfoDto.setOrderEvaluate(tbEvaluate.getOrderEvaluate());
            resCompleteOrderInfoDto.setOrderService(tbEvaluate.getOrderService());
        }
        return resCompleteOrderInfoDto;
    }
}
