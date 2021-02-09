package com.plan.frame.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xieyanling
 * @date 2020/11/25
 */
@ApiModel(description = "通用应答对象")
@Getter
@Setter
public class CommonResultDto {
    public static Integer CODE_SUCCESS = 1;
    public static Integer CODE_FAILD = 0;
    @ApiModelProperty("业务处理结果编码值，1-成功,其他-失败")
    private Integer commonResultCode;

    @ApiModelProperty("失败说明")
    private String commonResultMsg;

    public CommonResultDto(){}

    public static CommonResultDto success(){
        CommonResultDto result = new CommonResultDto();
        result.setCommonResultCode(CODE_SUCCESS);
        return result;
    }
    public static CommonResultDto fail(String msg){
        CommonResultDto result = new CommonResultDto();
        result.setCommonResultCode(CODE_FAILD);
        result.setCommonResultMsg(msg);
        return result;
    }

    public static CommonResultDto faild(Integer resultCode, String msg){
        CommonResultDto result = new CommonResultDto();
        result.setCommonResultCode(resultCode);
        result.setCommonResultMsg(msg);
        return result;
    }
}

