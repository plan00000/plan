package com.plan.demo.order.controller;
import com.plan.demo.order.dto.ReqAddOrderDto;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Administrator 2021/3/14 0014
 * @version V1.0.0
 * @description 订单管理类
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    /**
     * @Description:订单添加
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "订单添加")
    @RequestMapping(value = "/addOrder",method = RequestMethod.POST)
    public Result<String> addOrder(ReqAddOrderDto reqAddOrderDto)throws RuntimeException{
        try {

            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("订单添加失败", e, "请联系管理员！");
            }
        }
    }
}
