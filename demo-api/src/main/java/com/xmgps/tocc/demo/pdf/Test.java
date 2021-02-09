package com.xmgps.tocc.demo.pdf;


import com.xmgps.tocc.frame.pdf.service.AbsFtlGenerationPdf;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description  文书数据组装类
 * @ClassName Test
 * @Date 2019/7/19 14:30
 */
@Component("test")
public class Test extends AbsFtlGenerationPdf {

    @Override
    public Map<String, Object> constructPdfBean(String drivenId, Map<String, Object> map) throws Exception {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("hello","恭喜你文书生成成功了");
        return resultMap;
    }
}