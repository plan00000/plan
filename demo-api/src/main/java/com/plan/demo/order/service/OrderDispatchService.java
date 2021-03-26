package com.plan.demo.order.service;

import com.plan.demo.base.dao.TbOrderDao;
import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.order.dao.OrderMapper;
import com.plan.demo.order.dto.dispath.DriverDto;
import com.plan.frame.entity.ValueObject;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljw on 2021/3/24 0024.
 */
@Service
public class OrderDispatchService {

    @Autowired
    private TbOrderDao tbOrderDao;
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 */1 * * ?")
    public void orderDispatch(){
        TbOrder tbOrderQuery = new TbOrder();
        tbOrderQuery.setOrderStatus("1");
        //1.查询所有待派单订单
        List<TbOrder> dfpTbOrderList = tbOrderDao.selectByEntitySelective(tbOrderQuery);
        //2.查询所有空闲的司机,放到对应司机空闲池里
        List<ValueObject> freeDriverVoList = orderMapper.findFreeDriverList();
        List<DriverDto> driverDtoList =null;
        if(CommonUtil.isNotEmpty(freeDriverVoList)){
            driverDtoList = BeanHelper.voListToBeanList(freeDriverVoList,DriverDto.class);
        }
        Map<String,DriverDto> freeDriverMap = new HashMap<>();
        if(CommonUtil.isNotEmpty(driverDtoList)){

        }
        if(CommonUtil.isNotEmpty(dfpTbOrderList)){
            //2.开始分配每个订单
            for(TbOrder tbOrder:dfpTbOrderList){


            }

        }

    }
}
