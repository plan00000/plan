package com.plan.demo.user;

import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @Author: linzhihua
 * @Description:
 * @Date: Created in 2021/2/23 9:02
 * @Modified By:
 */
@Controller
@RequestMapping("/user")
public class UserManagerController {

    /**
     * @return
     */
    @RequestMapping(value = "/index")
    public String UserManager() throws RuntimeException {
        try {

            return "index";
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("", e, "请联系管理员！");
            }
        }
    }
}