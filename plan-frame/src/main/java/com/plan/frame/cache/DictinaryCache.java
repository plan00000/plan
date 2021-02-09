package com.plan.frame.cache;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.plan.frame.system.dto.login.dictionary.DictionaryDto;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.HttpUtil;
import com.plan.frame.util.SpringContextHolder;
import com.plan.frame.util.StringUtil;
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
    @Value("${service.permission.url}")
    private String permissionUrl;

    private Map<String,List<DictionaryDto>> dictCodeCache = new HashMap<>();

    /**
     * 根据字典类型获取字典信息
     * @param dictType
     * @return
     */
    public List<DictionaryDto> getDicCodeByDictType(String dictType){
        if(dictCodeCache.size()==0|| CommonUtil.isEmpty(dictCodeCache.get(dictType))){
            synchronized(dictCodeCache){
                loadDict(dictType,null);
            }
        }
        List<DictionaryDto> dictList =dictCodeCache.get(dictType);
        return  dictList;
    }

    /**
     * 根据字典类型获取字典树信息
     * @param dictType
     * @param hasChildren
     */
    public List<DictionaryDto> getDicCodeTreeByDictType(String dictType,Boolean hasChildren){
        if(dictCodeCache.size()==0 ||CommonUtil.isEmpty(dictCodeCache.get(dictType+"_child"))){
            synchronized(dictCodeCache){
                loadDict(dictType,hasChildren);
            }
        }
        List<DictionaryDto> dictList = dictCodeCache.get(dictType+"_child");
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
                loadDict(dictType,null);
            }
        }
        List<DictionaryDto> dictionaryDtoList = dictCodeCache.get(dictType);
        if(CommonUtil.isNotEmpty(dictionaryDtoList)){
            for (DictionaryDto dictionaryDto : dictionaryDtoList) {
                if (StringUtil.equalsString(dictionaryDto.getBianMa(), dictCode)) {
                    return dictionaryDto.getName();
                }
            }
        }
        return null;
    }

    public synchronized void loadDict(String dictType,Boolean hasChildren){

        Map<String,String> params = new HashMap<>();
        params.put("bianma",dictType);
        if(CommonUtil.isNotEmpty(hasChildren)){
            params.put("hasChildren",hasChildren.toString());
        }
        String s = HttpUtil.post(permissionUrl + "/getDictionaryByBM", params);
        if(CommonUtil.isNotEmpty(s)){
            JSONObject result = JSONObject.parseObject(s);
            if ("1".equals(result.getString("code"))) {
                JSONArray jsonArray = result.getJSONArray("data");
                if(CommonUtil.isNotEmpty(jsonArray)) {
                    List<DictionaryDto> dictionaryList = parseDictionaryList(jsonArray);
                    if(CommonUtil.isNotEmpty(dictionaryList)) {
                        if (CommonUtil.isNotEmpty(hasChildren)) {
                            this.dictCodeCache.put(dictType + "_child", dictionaryList);
                        } else {
                            this.dictCodeCache.put(dictType, dictionaryList);
                        }
                    }
                }
            }
        }
    }

    /**
     * 解析字典数组
     * @param jsonArray
     * @return
     */
    private List<DictionaryDto> parseDictionaryList(JSONArray jsonArray){
        List<DictionaryDto> dictionaryDtoList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(jsonArray)) {
            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                DictionaryDto dictionaryDto = new DictionaryDto();
                dictionaryDto.setZdId(obj.getString("ZD_ID"));
                dictionaryDto.setJb(obj.getString("JB"));
                dictionaryDto.setBianMa(obj.getString("BIANMA"));
                dictionaryDto.setName(obj.getString("NAME"));
                dictionaryDto.setOrderBy(obj.getString("ORDER_BY"));
                dictionaryDto.setParentId(obj.getString("PARENT_ID"));
                dictionaryDto.setpBm(obj.getString("P_BM"));
                dictionaryDto.setSysCreateTime(obj.getString("SYS_CREATE_TIME"));
                dictionaryDto.setSysUpdateTime(obj.getString("SYS_UPDATE_TIME"));

                JSONArray subAarray = obj.getJSONArray("SUBDIC");
                List<DictionaryDto> subDicList = parseDictionaryList(subAarray);
                dictionaryDto.setSubDic(subDicList);
                dictionaryDtoList.add(dictionaryDto);
            }
        }
        return dictionaryDtoList;
    }


    public static DictinaryCache getCache() {
        return (DictinaryCache) SpringContextHolder.getBean("dictinaryCache");
    }


}
