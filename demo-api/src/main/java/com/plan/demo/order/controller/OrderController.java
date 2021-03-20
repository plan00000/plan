package com.plan.demo.order.controller;
import com.plan.demo.order.dto.*;
import com.plan.demo.order.service.OrderService;
import com.plan.demo.user.dto.ReqDriverLocationDto;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljwwpr 2021/3/01 0014
 * @version V1.0.0
 * @description 订单管理类
 */
@RestController
@Api(tags = "2-订单管理接口")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * @Description:订单添加
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "1-订单添加")
    @RequestMapping(value = "/addOrder",method = RequestMethod.POST)
    public Result<String> addOrder(@RequestBody ReqAddOrderDto reqAddOrderDto)throws RuntimeException{
        try {
            orderService.addOrder(reqAddOrderDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("订单添加失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:获取线路信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "2-获取线路列表")
    @RequestMapping(value = "/getLine",method = RequestMethod.POST)
    public Result<ResOrderLineResultDto> getLine()throws RuntimeException{
        try {
            ResOrderLineResultDto resOrderLineResultDto = orderService.getLineList();
            return ResultHelper.success(resOrderLineResultDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取线路列表失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:获取乘客订单列表
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "3-获取乘客订单列表")
    @RequestMapping(value = "/getPassengerOrderList",method = RequestMethod.POST)
    public Result<ResPassengerOrderResultDto> getPassengerOrderList()throws RuntimeException{
        try {
            ResPassengerOrderResultDto resPassengerOrderResultDto = orderService.getPassengerOrderList();
            return ResultHelper.success(resPassengerOrderResultDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("订单添加失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * 取消订单接口
     * @param reqCancelOrderDto
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "4-取消订单")
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
    public Result<String> cancelOrder(@RequestBody ReqCancelOrderDto reqCancelOrderDto)throws RuntimeException{
        try {
            orderService.cancelOrder(reqCancelOrderDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("取消订单失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * 查看订单信息
     * @param reqOrderInfoDto
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "5-查看乘客订单信息")
    @RequestMapping(value = "/getOrderInfo",method = RequestMethod.POST)
    public Result<ResPassengerOrderInfoDto> getPassengerOrderInfo(@RequestBody ReqOrderInfoDto reqOrderInfoDto)throws RuntimeException{
        try {
            ResPassengerOrderInfoDto resPassengerOrderInfoDto =orderService.getPassengerOrderInfo(reqOrderInfoDto);
            return ResultHelper.success(resPassengerOrderInfoDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("查看订单信息失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:更新司机位置信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "6-获取乘客与司机的距离")
    @RequestMapping(value = "/getPassengerDriverDistance",method = RequestMethod.GET)
    public Result<String> getPassengerDriverDistance(@RequestBody ReqOrderDto reqOrderDto)throws RuntimeException{
        try {
            String distance = orderService.getPassengerDriverDistance(reqOrderDto);
            return ResultHelper.success(distance);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取乘车人信息失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:更新司机位置信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "7-获取司机端订单详细信息")
    @RequestMapping(value = "/getDriverOrderInfo",method = RequestMethod.GET)
    public Result<ResDriverOrderInfoDto> getDriverOrderInfo(@RequestBody ReqOrderDto reqOrderDto)throws RuntimeException{
        try {
            ResDriverOrderInfoDto resDriverOrderInfoDto = orderService.getDriverOrderInfo(reqOrderDto);
            return ResultHelper.success(resDriverOrderInfoDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取司机端订单详细信息", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:获取乘客历史完成订单
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "8-获取乘客历史完成订单")
    @RequestMapping(value = "/getPassengerCompleteOrderList",method = RequestMethod.GET)
    public Result<ResCompleteOrderResultDto> getPassengerCompleteOrderList()throws RuntimeException{
        try {
            ResCompleteOrderResultDto resCompleteOrderResultDto = orderService.getPassengerCompleteOrderList();
            return ResultHelper.success(resCompleteOrderResultDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取乘客历史完成订单失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:获取乘客历史完成订单
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "9-获取司机历史完成订单")
    @RequestMapping(value = "/getDriverCompleteOrderList",method = RequestMethod.GET)
    public Result<ResCompleteOrderResultDto> getCompleteOrderList()throws RuntimeException{
        try {
            ResCompleteOrderResultDto resCompleteOrderResultDto = orderService.getDriverCompleteOrderList();
            return ResultHelper.success(resCompleteOrderResultDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取司机历史完成订单失败", e, "请联系管理员！");
            }
        }
    }



}
