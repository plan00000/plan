package com.plan.frame.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.plan.frame.system.dto.login.userInfo.RoleDto;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.HttpUtil;
import com.plan.frame.util.SpringContextHolder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Created by linzhihua
 * @Description 角色信息cache
 * @ClassName RoleCache
 * @Date 2020/6/10 19:06
 */
//@Component("roleCache")
public class RoleCache implements InitializingBean {
    @Value("${service.permission.url}")
    private String permissionUrl;

    private Map<String, RoleDto> roleCache = new HashMap<String, RoleDto>();

    /**
     * 获取单条区段信息
     * @param roleId
     * @return
     */
    public RoleDto getRoleByRoleId(String roleId) {
        if (roleCache.size() == 0) {
            synchronized (roleCache) {
                roleReLoad();
            }
        }
        return roleCache.get(roleId);
    }

    /**
     * 加载角色数据
     */
    public synchronized void roleReLoad() {

        List<RoleDto> roleDtoList = new ArrayList<>();
        Map<String, RoleDto> roleCacheTemp = new HashMap<>();

        String s = HttpUtil.post(permissionUrl + "/getAllRole", null);
        System.out.println("result\n" + s);

        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            JSONArray jsonArray = result.getJSONArray("data");
            roleDtoList=bulidRoleList(jsonArray);
        }
        if(CommonUtil.isNotEmpty(roleDtoList)){
            for(RoleDto roleDto:roleDtoList){
                roleCacheTemp.put(roleDto.getRoleId(),roleDto);
            }
        }

        this.roleCache = roleCacheTemp;
    }



    public static RoleCache getCache() {
        return (RoleCache) SpringContextHolder.getBean("roleCache");
    }

    public List<RoleDto> bulidRoleList(JSONArray jsonArray){
        List<RoleDto> roleList = new ArrayList<>();
        if(jsonArray == null || jsonArray.size() == 0){
            return roleList;
        }

        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            RoleDto roleDto = new RoleDto();
            roleDto.setRights(obj.getString("RIGHTS"));  //权限串
            roleDto.setRoleId( obj.getString("ROLE_ID"));  //角色id
            roleDto.setRoleName( obj.getString("ROLE_NAME"));  //角色名称
            roleDto.setAddQx( obj.getString("ADD_QX"));  //增加角色
            roleDto.setDelQx( obj.getString("DEL_QX"));  //删除角色
            roleDto.setEditQx( obj.getString("EDIT_QX"));  //编辑角色
            roleDto.setChaQx(obj.getString("CHA_QX") );  //查询角色
            roleList.add(roleDto);
        }
        return roleList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        roleReLoad();
    }
}
