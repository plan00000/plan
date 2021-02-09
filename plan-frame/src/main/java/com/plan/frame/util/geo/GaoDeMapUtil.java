package com.plan.frame.util.geo;

/**
 * @Author: wushiyao
 * @Description（类描述）:
 * @Date: Created in 2020/11/4 8:51
 */


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmgps.tocc.frame.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高德地图测试
 *
 * @author cp
 *
 */
public class GaoDeMapUtil {

    // 高德应用的地址
    private static String gaodeAppID = "7c666da64e913ec19e9bc4af75f3b621";
    // 地理编码地址
    private static String map_codeurl = "http://restapi.amap.com/v3/geocode/geo";

	/*
	 * static { Properties properties = new Properties(); try { gaodeAppID =
	 * properties.getProperty("appkey"); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

    /**
     * 输入地址返回识别后的信息
     *
     * @param address
     * @return 返回的类gaodelocation，详见类
     */
    public static GaodeLocation  getLocatoin(String address) {
        GaodeLocation location = null;
        if (address != null) {
            try {
                location = new GaodeLocation();
                String url = map_codeurl.replace("parameters", "");
                Map<String,String> params = new HashMap<String,String>();
                params.put("key",gaodeAppID);
                params.put("address",address);
                String result = HttpUtil.get(url, params);
                JSONObject jsonObject = JSONObject.parseObject(result);

                // 解析json
                location.setStatus(jsonObject.getString("status"));
                location.setInfo(jsonObject.getString("info"));
                location.setCount(jsonObject.getString("count"));
                List<Geocodes> geocodes = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
                // 遍历解析出来的结果
                if ((jsonArray != null) && (jsonArray.size() > 0)) {
                    JSONObject jo = (JSONObject) jsonArray.get(0);
                    Geocodes go = new Geocodes();
                    go.setFormatted_address(jo.getString("formatted_address"));
                    go.setProvince(jo.getString("province"));
                    go.setCitycode(jo.getString("citycode"));
                    go.setCity(jo.getString("city"));
                    go.setDistrict(jo.getString("district"));
                    // 地址所在的乡镇
                    JSONArray ts = jo.getJSONArray("township");
                    if (ts != null && ts.size() > 0) {
                        go.setTownship(ts.getString(0));
                    }
                    // 地址编号
                    go.setAdcode(jo.getString("adcode"));
                    // 街道
                    JSONArray jd = jo.getJSONArray("street");
                    if (jd != null && jd.size() > 0) {
                        go.setStreet(jd.getString(0));
                    }
                    // 号码
                    JSONArray hm = jo.getJSONArray("number");
                    if (hm != null && hm.size() > 0) {
                        go.setStreet(hm.getString(0));
                    }
                    if(!"".equals(jo.getString("location"))){
                        String[] addLocation= jo.getString("location").split(",");
                        go.setLon(addLocation[0]);
                        go.setLat(addLocation[1]);
                    }
                    go.setLevel(jo.getString("level"));
                    geocodes.add(go);
                }
                location.setGeocodes(geocodes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    public static void main(String[] args) {
        GaoDeMapUtil gdm = new GaoDeMapUtil();
        GaodeLocation result = gdm.getLocatoin("国贸蓝海");
        System.out.println(JSON.toJSONString(result));
    }
}
