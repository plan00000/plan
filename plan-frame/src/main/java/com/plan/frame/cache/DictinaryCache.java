package com.plan.frame.cache;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.plan.frame.system.dto.login.dictionary.DictionaryDto;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.HttpUtil;
import com.plan.frame.util.SpringContextHolder;
import com.plan.frame.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description：字典信息缓存
 * @ClassName DictinaryCache
 * @Date 2020/7/1 11:33
 */
@Component("dictinaryCache")
public class DictinaryCache {



    private Map<String,List<DictionaryDto>> dictCodeCache = new HashMap<>();

    /**
     * 根据字典类型获取字典信息
     * @param dictType
     * @return
     */
    public List<DictionaryDto> getDicCodeByDictType(String dictType){
        if(dictCodeCache.size()==0|| CommonUtil.isEmpty(dictCodeCache.get(dictType))){
            synchronized(dictCodeCache){
                loadDict(dictType);
            }
        }
        List<DictionaryDto> dictList =dictCodeCache.get(dictType);
        return  dictList;
    }


    /**
     * 根据字典类型和字典码获取中文名
     * @param dictType
     * @param dictCode
     * @return
     */
    public String getDictCnName(String dictType, String dictCode) {
        if(dictCodeCache.size()==0||CommonUtil.isEmpty(dictCodeCache.get(dictType))){
            synchronized(dictCodeCache){
                loadDict(dictType);
            }
        }
        List<DictionaryDto> dictionaryDtoList = dictCodeCache.get(dictType);
        if(CommonUtil.isNotEmpty(dictionaryDtoList)){
            for (DictionaryDto dictionaryDto : dictionaryDtoList) {
                if (StringUtil.equalsString(dictionaryDto.getCode(), dictCode)) {
                    return dictionaryDto.getName();
                }
            }
        }
        return null;
    }

    public synchronized void loadDict(String dictType){


    }



    public static DictinaryCache getCache() {
        return (DictinaryCache) SpringContextHolder.getBean("dictinaryCache");
    }



}
