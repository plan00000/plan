package com.plan.frame.util.geo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 * 车牌号转归属地
 * @author xieyanling
 * @date 2020/11/4
 */
public class CarCityUtil {
    private static Map<String, CarCityVo> mapCarCity = null;

    public static void main(String[] args) {
        CarCityVo vo = getCityByCarCode("冀C");
        CarCityVo vo1 = getCityByCarCode("冀B");
        System.out.println(vo.getProvince() + vo.getCity());
        System.out.println(vo1.getProvince() + vo1.getCity());
    }

    /**
     * 根据车牌前缀获取归属地信息
     * @param code 车牌前缀，如“闽D”
     * @return
     */
    public static CarCityVo getCityByCarCode(String code){
        if(mapCarCity == null){
            loadMapCarCity();
        }
        return mapCarCity.get(code);
    }

    /**
     * 加载json数据
     */
    private static void loadMapCarCity(){
        System.out.println("加载car_city.json");
        mapCarCity = new HashMap<>();
        try{
            String fileName = "car_city.json";
//            String str = readFile(fileName); //
            String str = readJarFile(fileName);
            JSONObject result = JSONObject.parseObject(str);
            JSONArray jsonArray = result.getJSONArray("data");

            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                CarCityVo vo = new CarCityVo();
                vo.setCode(obj.getString("code"));
                vo.setProvince(obj.getString("province"));
                vo.setCity(obj.getString("city"));
                vo.setPcode(obj.getString("Pcode"));

                mapCarCity.put(vo.getCode(), vo);
            }
        }catch (Exception e){
            System.out.println("加载car_city.json异常" + e.toString());
        }
    }

    /**
     * 读取jar包下的资源
     * @param fileName
     * @return
     */
    private static String readJarFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader( new InputStreamReader(CarCityUtil.class.getClassLoader().getResourceAsStream(fileName),"utf-8"));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null)
        {
            buffer.append(line);
        }
        return buffer.toString();
    }

    /**
     * 读取文件
     * @return
     */
    private static String readFile(String fileName) {
        String path = String.valueOf(CarCityUtil.class.getClassLoader().getResource("").getPath()) + fileName;
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr;
    }


}
