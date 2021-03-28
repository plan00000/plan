package com.plan.demo.order.service;

import com.plan.demo.base.dao.TbOrderDao;
import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.order.dao.OrderMapper;
import com.plan.demo.order.dto.dispath.DriverDto;
import com.plan.demo.util.GpsAreaUtil;
import com.plan.frame.entity.ValueObject;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        if(CommonUtil.isNotEmpty(dfpTbOrderList)){
            //2.开始分配每个订单
            for(TbOrder tbOrder:dfpTbOrderList){
                oneOrderToDriver(tbOrder);
            }

        }

    }

    /**
     * 单个订单指派
     * @param tbOrder
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void oneOrderToDriver(TbOrder tbOrder){
        //2.查询所有空闲的司机,放到对应司机空闲池里
        List<ValueObject> freeDriverVoList = orderMapper.findFreeDriverList();
        List<DriverDto> driverDtoList = null;
        if(CommonUtil.isNotEmpty(freeDriverVoList)){
            driverDtoList = BeanHelper.voListToBeanList(freeDriverVoList,DriverDto.class);
            //司机不为空
            if(CommonUtil.isNotEmpty(driverDtoList)){
                if(driverDtoList.size()>1) {
                    //计算司机的距离
                    Map<Long, Double> driverDistance = new HashMap<>();
                    //
                    Map<Long,Double> driverEvaluate = new HashMap<>();
                    for (DriverDto driverDto : driverDtoList) {
                        //获取司机的经纬度和订单经纬度进行计算
                        if(CommonUtil.isNotEmpty(driverDto.getLon())&&CommonUtil.isNotEmpty(driverDto.getLat())) {
                            Double distance = GpsAreaUtil.getDistance(tbOrder.getOrderStartLon(), tbOrder.getOrderStartLat(), driverDto.getLon(), driverDto.getLat());
                            driverDistance.put(driverDto.getId(), distance);
                        }else{
                            //如果司机未有经纬度则设置最大
                            driverDistance.put(driverDto.getId(),new Double(999999));
                        }
                        driverEvaluate.put(driverDto.getId(),Double.valueOf(driverDto.getDriverStar()));
                    }

                    //获取最近距离
                    Double min = new Double(driverDistance.get(driverDtoList.get(0)));
                    long minDisDriverId = driverDtoList.get(0).getId();
                    for (Long o : driverDistance.keySet()) {
                        if(driverDistance.get(o)<min){
                            min = driverDistance.get(0);
                            minDisDriverId = o;
                        }
                    }
                    for(Long driverId:driverEvaluate.keySet()){
                        if(driverId ==minDisDriverId) {
                            driverEvaluate.put(driverId,driverEvaluate.get(minDisDriverId)+ new Double(5));
                        }else{
                            driverEvaluate.put(driverId,driverEvaluate.get(minDisDriverId)+ new Double(3));
                        }
                    }
                    //找出最大值
                    long maxEvaluateDriverId = minDisDriverId;
                    Double maxEvaluateDriver = driverEvaluate.get(minDisDriverId);
                    for (Long o : driverDistance.keySet()) {
                        if(driverDistance.get(o)>maxEvaluateDriver){
                           maxEvaluateDriverId = o;
                        }
                    }
                    tbOrder.setDriveId(maxEvaluateDriverId);
                }else{
                    tbOrder.setDriveId(driverDtoList.get(0).getId());
                }

            }
        }
    }
}
