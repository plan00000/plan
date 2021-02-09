package com.plan.frame.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.plan.frame.cache.RoleCache;
import com.plan.frame.exception.ConditionException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.dto.login.ButtonDto;
import com.plan.frame.system.dto.login.ResLoginDto;
import com.plan.frame.system.dto.login.menu.MenuDto;
import com.plan.frame.system.dto.login.menu.ReqFindMenuTreeDto;
import com.plan.frame.system.dto.login.menu.ResFindMenuTreeDto;
import com.plan.frame.system.dto.login.userInfo.ResPermissionDto;
import com.plan.frame.system.dto.login.userInfo.RoleDto;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.util.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author: linzhihua
 * @Description（类描述）: 用户登录service
 * @Date: Created in 2020/4/20 15:12
 * @Modified By:
 */
@Service
public class LoginService {

    protected Logger logger = Logger.getLogger(this.getClass());

    private static int PASSWORD_EXPIRED_DAYS = 90;
    //token过期时长，目前设置成一个小时
    @Value("${token_exp}")
    private String tokenExp ;
    /**
     * 是否启用cas单点登录
     * 启用不进行token验证
     * 不启用就进行token验证
     */

    @Value("${spring.menhuurl}")
    private String menHuUrl;

    @Value("${cas.casServerUrlPrefix}")
    private String casServerUrl;

    @Value("${rsa.privateModulus}")
    private String privateModulus;
    @Value("${rsa.privateExponent}")
    private String privateExponent;

    @Autowired
    private RedisUtils redisUtil;


    @Value("${service.permission.url}")
    private String permissionUrl;

    /**
     * 菜单
     */
    @Value("${system-top-menu-code}")
    private String sysMenuCode;

    /**
     * 单点退出登录
     */
    @Value("${cas.casServerUrlPrefix2}")
    private String casServerUrlPrefix2;

    /**
     * 登录校验
     * @param request
     * @return
     */
    public void checkAccount(String toredirect,HttpServletRequest request, HttpServletResponse response)throws Exception{

        String userName = "";

        Assertion assertion = AssertionHolder.getAssertion();
        Cookie[] cookies = request.getCookies();

        //处理当没有登录成功的情况   fixed 暂时跳转到登录页
        if (assertion != null) {
            AttributePrincipal principal = assertion.getPrincipal();
            if (principal != null) {
                userName = principal.getName();
            } else {
                if (null != toredirect && !"".equals(toredirect)){
                    response.sendRedirect(toredirect);
                }else {
                    response.sendRedirect(menHuUrl);
                }
            }
        } else {
            if (null != toredirect && !"".equals(toredirect)){
                response.sendRedirect(toredirect);
            }else {
                response.sendRedirect(menHuUrl);
            }
        }

        if (CommonUtil.isEmpty(userName)) {
            response.sendRedirect(casServerUrl);
            throw new ConditionException("用户登录失败","用户未登录","请重新登录");
        } else {//插入登录日志
            if (null != toredirect && !"".equals(toredirect)){
                response.sendRedirect(toredirect);
            }else {
                response.sendRedirect(menHuUrl);
            }
        }
    }

    /**
     * 用户登录-生成token
     * @param request
     * @return
     * @throws Exception
     */
    public ResLoginDto loginAccount(HttpServletRequest request)throws Exception{
        ResLoginDto resLoginDto = new ResLoginDto();
        String userName = "";
        Assertion assertion = AssertionHolder.getAssertion();
        //处理当没有登录成功的情况   fixed 暂时跳转到登录页
        if (assertion != null) {
            AttributePrincipal principal = assertion.getPrincipal();
            if (principal != null) {
                userName = principal.getName();
                resLoginDto.setUserName(userName);
            } else {
                resLoginDto.setRedirectUrl(menHuUrl);
            }
        } else {
            resLoginDto.setRedirectUrl(menHuUrl);
        }
        UserInfoDto userInfoDto = getUserInfo(userName);
        //生成token
        //token过期时间
        if(CommonUtil.isEmpty(tokenExp)){
            tokenExp = "60";
        }
        //由于修改配置过于麻烦所有临时修改此处1.8*60 约等于 2*50
        Long tokenExpTime = 2*50*1000*Long.parseLong(tokenExp);
        Long tokenDate = System.currentTimeMillis()+tokenExpTime;
        String token = JwtUtil.getToken(userName,tokenDate);
        resLoginDto.setToken(token);
        resLoginDto.setTokenDate(DateUtil.date2Str(new Date(tokenDate),"yyyy-MM-dd HH:mm:ss"));
        //对token进行加密
        String key = DigestUtils.md5DigestAsHex(token.getBytes());
        //放入缓存
        redisUtil.set(key,userInfoDto,3L, TimeUnit.HOURS);
        resLoginDto.setRedirectUrl(menHuUrl);

        return resLoginDto;
    }


    /**
     * 获取当前用户信息
     * @return
     */
    public UserInfoDto getCurrentUser(){
        UserInfoDto userInfo=getUserInfo(ThreadLocalHelper.getUser().getUserName());
        return userInfo;
    }

    /**
     * 从权限系统获取用户信息
     * @param userName
     * @return
     */
    public UserInfoDto getUserInfo(String userName){
        Map<String, String> params=new HashMap<>();
        params.put("username", userName);
        UserInfoDto userInfoDto = new UserInfoDto();
        String s = HttpUtil.post(permissionUrl + "/getUserInfo", params);
        if(CommonUtil.isEmpty(s)){
            return userInfoDto;
        }
        JSONObject result = JSONObject.parseObject(s);
        if(CommonUtil.isEmpty(result)){
            throw new SystemException("获取用户信息失败","从权限系统获取用户失败","请联系管理员进行处理");
        }
        String code = result.getString("code");
        if(!StringUtil.equalsString("1",code)){
            throw new SystemException("获取用户信息失败","从权限系统获取用户失败","请联系管理员进行处理");
        }
        JSONObject data = result.getJSONObject("data");
        userInfoDto.setDeptId(data.getString("DEPT_ID"));
        userInfoDto.setDeptName(data.getString("DEPT_NAME"));
//        userInfoDto.setPassword(data.getString("PASSWORD"));
        userInfoDto.setPhone(data.getString("PHONE"));
        userInfoDto.setIp(data.getString("IP"));
        userInfoDto.setUserId(data.getString("USER_ID"));
        userInfoDto.setUserName(data.getString("USERNAME"));
        userInfoDto.setRoleId(data.getString("ROLE_ID"));
        userInfoDto.setRoleName(data.getString("ROLE_NAME"));
        userInfoDto.setLastLogin(data.getString("LAST_LOGIN"));
        userInfoDto.setEmail(data.getString("EMAIL"));
        userInfoDto.setName(data.getString("NAME"));

        userInfoDto.setLastUpdatePass(data.getString("LAST_UPDATE_PASS"));
        try{
            int days =  DateUtil.getDaySub(userInfoDto.getLastUpdatePass().substring(0, 10), DateUtil.date2Str(new Date(), "yyyy-MM-dd"));
            if(days > PASSWORD_EXPIRED_DAYS){
                userInfoDto.setPasswordExpired(true);
            }else{
                userInfoDto.setPasswordExpired(false);
            }
        }catch (Exception e){
            userInfoDto.setPasswordExpired(true);
        }

        return userInfoDto;
    }

    /**
     * 用户退出登录操作
     * @param response
     * @throws Exception
     */
    public void logout(HttpServletResponse response)throws Exception{
        //redis移除token和用户信息
//        String token = ThreadLocalHelper.getToken();
//        redisUtil.remove(DigestUtils.md5DigestAsHex(token.getBytes()));
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        }catch (Exception e){
        }

        response.sendRedirect(casServerUrlPrefix2);
    }

    /**
     *
     * @return
     */
    public ResPermissionDto getPermissionDto(){
        ResPermissionDto resPermissionDto = new ResPermissionDto();
        //从权限系统获取子系统全部菜单以及按钮
        String userName =  ThreadLocalHelper.getUser().getUserName();
        if(CommonUtil.isEmpty(userName)) {
            throw new ConditionException("获取用户权限失败","没有该用户","请输入正确的账号");
        }
        //用户信息
        UserInfoDto userInfoDto = getUserInfo(userName);
        if(CommonUtil.isEmpty(userInfoDto)){
            throw new ConditionException("系统获取权限失败","获取用户信息失败","请联系管理员处理");
        }

        //用户角色信息
        RoleDto roleDto = RoleCache.getCache().getRoleByRoleId(userInfoDto.getRoleId());

        resPermissionDto.setUserName(userName);
        resPermissionDto.setUserInfo(userInfoDto);
        resPermissionDto.setRoleId(userInfoDto.getRoleId());
        resPermissionDto.setRole(roleDto);
        return resPermissionDto;
    }

    /**
     * 获取菜单树
     * @param userName
     * @param req
     * @return
     */
    public ResFindMenuTreeDto findMenuTree(String userName, ReqFindMenuTreeDto req){
        if(CommonUtil.isEmpty(req.getMenuCode())){
            req.setMenuCode(sysMenuCode); // 获取整个系统的菜单
        }
        Map<String, String> params=new HashMap<>();
        params.put("username", userName);
        params.put("systemName", req.getMenuCode());

        ResFindMenuTreeDto res = new ResFindMenuTreeDto();
        String s = HttpUtil.post(permissionUrl + "/getSubMenu", params);
        if(CommonUtil.isEmpty(s)){
            return res;
        }
        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            JSONArray jsonArray = result.getJSONArray("data");

            List<ButtonDto> buttonList = new ArrayList<>();
            List<MenuDto> menuList = parseMenuList(jsonArray, buttonList, null);
            res.setMenuDtoList(menuList);
            res.setButtonDtoList(buttonList);
        }
        return res;
    }

    /**
     * 解析菜单数组
     * @param jsonArray
     * @return
     */
    private List<MenuDto> parseMenuList(JSONArray jsonArray, List<ButtonDto> allBtnList, MenuDto parent){
        List<MenuDto> menuList = new ArrayList<>();
        if(jsonArray == null || jsonArray.size() == 0){
            return menuList;
        }

        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            MenuDto menu = new MenuDto();
            menu.setMenuId(obj.getString("MENU_ID"));
            menu.setMenuCode(obj.getString("MENU_TYPE"));
            menu.setMenuUrl(obj.getString("MENU_URL"));
            menu.setMenuName(obj.getString("MENU_NAME"));
            menu.setMenuIcon(obj.getString("MENU_ICON"));
            menu.setMenuOrder(obj.getString("MENU_ORDER"));
            menu.setParentId(obj.getString("PARENT_ID"));
            menu.setModuleName(obj.getString("MODULE_NAME"));
            menu.setModuleUrl(obj.getString("MODULE_URL"));
            if(parent != null && CommonUtil.isNotEmpty(menu.getModuleUrl())){
                menu.setFullUrl(parent.getFullUrl() + menu.getMenuUrl());
            }else{
                menu.setFullUrl(menu.getMenuUrl());
            }

            List<ButtonDto> buttonList = parseMenuButton(menu.getMenuCode(), obj.getJSONArray("BUTTON"));
            menu.setButtonList(buttonList);
            if(buttonList != null && buttonList.size() > 0){
                allBtnList.addAll(buttonList);
            }

            JSONArray subAarray = obj.getJSONArray("SUBMENU");
            List<MenuDto> subMenuList = parseMenuList(subAarray, allBtnList, menu);
            menu.setSubMenuList(subMenuList);

            menuList.add(menu);
        }
        return menuList;
    }

    /**
     * 解析按钮信息
     * @param btnAarray
     * @return
     */
    private List<ButtonDto> parseMenuButton(String menuCode, JSONArray btnAarray){
        List<ButtonDto> buttonList = new ArrayList<>();
        if(btnAarray == null || btnAarray.size() == 0){
            return buttonList;
        }
        for (Object o : btnAarray) {
            JSONObject obj = (JSONObject) o;
            ButtonDto btn = new ButtonDto();
            btn.setButtonId(obj.getString("BUTTON_ID"));
            btn.setButtonRemark(obj.getString("BUTTON_REMARK"));
            btn.setButtonNo(menuCode + ":" + obj.getString("BUTTON_NO"));
            btn.setButtonOrder(obj.getString("BUTTON_ORDER"));
            btn.setMenuId(obj.getString("MENU_ID"));
            btn.setButtonName(obj.getString("BUTTON_NAME"));

            buttonList.add(btn);
        }
        return buttonList;
    }

    /**
     * 获取菜单树
     * @param req
     * @return
     */
    public ResRouteMenuDto getRouteMenuTree( ReqFindMenuTreeDto req){
        if(CommonUtil.isEmpty(req.getMenuCode())){
            req.setMenuCode(sysMenuCode); // 获取整个系统的菜单
        }
        Map<String, String> params=new HashMap<>();
        params.put("username", ThreadLocalHelper.getUser().getUserName());
        params.put("systemName", req.getMenuCode());

        ResRouteMenuDto res = new ResRouteMenuDto();
        String s = HttpUtil.post(permissionUrl + "/getSubMenu", params);
        if(CommonUtil.isEmpty(s)){
            return res;
        }
        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            JSONArray jsonArray = result.getJSONArray("data");
            List<RouteMenu> menuList = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                RouteMenu menu = new RouteMenu();
                menu.setMenuId(obj.getString("MENU_ID"));
                menu.setName(obj.getString("MENU_TYPE"));
                String path =obj.getString("MENU_URL");
                String pathFirst =path.substring(0, 1);
                if(StringUtil.equalsString("/",pathFirst)){
                    menu.setPath(path);
                }else{
                    menu.setPath("/"+path);
                }
                Meta meta = new Meta();
                meta.setIcon(obj.getString("MENU_ICON"));
                meta.setTitle(obj.getString("MENU_NAME"));
                menu.setMeta(meta);
                menu.setMenuOrder(obj.getString("MENU_ORDER"));
                menu.setParentId(obj.getString("PARENT_ID"));
                menu.setModuleName(obj.getString("MODULE_NAME"));
                String component = obj.getString("MODULE_URL");


                JSONArray subAarray = obj.getJSONArray("SUBMENU");
                if(CommonUtil.isEmpty(subAarray)){
                    List<RouteMenu> children = new ArrayList<>();
                    RouteMenu routeMenu = new RouteMenu();
                    if(StringUtil.equalsString("/",pathFirst)){
                        routeMenu.setPath(path.substring(1));
                    }else{
                        routeMenu.setPath(path);
                    }
                    Meta metaChildren = new Meta();
                    metaChildren.setIcon(obj.getString("MENU_ICON"));
                    metaChildren.setTitle(obj.getString("MENU_NAME"));
                    routeMenu.setMeta(meta);
                    routeMenu.setComponent(component);
                    routeMenu.setName(obj.getString("MENU_TYPE"));
                    children.add(routeMenu);

                    menu.setComponent("Layout");
                    menu.setChildren(children);
                }else {
                    List<RouteMenu> children = parseRouteMenuList(subAarray);
                    menu.setChildren(children);
                    if(CommonUtil.isEmpty(component)){
                        component = "Layout";
                    }
                    menu.setComponent(component);
                }
                menuList.add(menu);
            }
            res.setRouteMenuList(menuList);
        }
        return res;
    }

    /**
     * 解析菜单数组
     * @param jsonArray
     * @return
     */
    private List<RouteMenu> parseRouteMenuList(JSONArray jsonArray){
        List<RouteMenu> menuList = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            RouteMenu menu = new RouteMenu();
            menu.setMenuId(obj.getString("MENU_ID"));
            menu.setName(obj.getString("MENU_TYPE"));
            String path =obj.getString("MENU_URL");
            String pathFirst =path.substring(0, 1);
            if(StringUtil.equalsString("/",pathFirst)){
                menu.setPath(path.substring(1));
            }else{
                menu.setPath(path);
            }
            Meta meta = new Meta();
            meta.setIcon(obj.getString("MENU_ICON"));
            meta.setTitle(obj.getString("MENU_NAME"));
            menu.setMeta(meta);
            menu.setMenuOrder(obj.getString("MENU_ORDER"));
            menu.setParentId(obj.getString("PARENT_ID"));
            menu.setModuleName(obj.getString("MODULE_NAME"));
            String component = obj.getString("MODULE_URL");
            if(CommonUtil.isEmpty(component)){
                component = "Layout";
            }
            menu.setComponent(component);

            JSONArray subAarray = obj.getJSONArray("SUBMENU");
            List<RouteMenu> children = parseRouteMenuList(subAarray);
            menu.setChildren(children);

            menuList.add(menu);
        }
        return menuList;
    }


    /**
     * 从权限系统获取字典信息
     * @param reqDictionaryDto
     * @return
     * @throws Exception
     */
    public ResDictinaryDto findDicByBianMa(ReqDictionaryDto reqDictionaryDto) throws Exception{
        ResDictinaryDto resDictinaryDto = new ResDictinaryDto();
        List<DictionaryDto> dictionaryDtoList;
        if(CommonUtil.isNotEmpty(reqDictionaryDto.getHasChildren())&&StringUtil.equalsString("1",reqDictionaryDto.getHasChildren())) {
            dictionaryDtoList= DictinaryCache.getCache().getDicCodeTreeByDictType(reqDictionaryDto.getBianMa(),"1".equals(reqDictionaryDto.getHasChildren()) ? true : false);
        }else{
            dictionaryDtoList = DictinaryCache.getCache().getDicCodeByDictType(reqDictionaryDto.getBianMa());
        }
        resDictinaryDto.setDictionaryDtoList(dictionaryDtoList);
        return resDictinaryDto;
    }

    /**
     *
     * @param reqDeptDto
     * @return
     * @throws Exception
     */
    public ResDeptDto getDeptForIdOrNo(ReqDeptDto reqDeptDto) throws Exception{
        ResDeptDto resDeptDto = new ResDeptDto();
        Map<String, String> params=new HashMap<>();
        params.put("type", reqDeptDto.getType());
        if(CommonUtil.isNotEmpty(reqDeptDto.getHasChildren())&&StringUtil.equalsString("1",reqDeptDto.getHasChildren())) {
            params.put("hasChildren", reqDeptDto.getHasChildren());
        }
        params.put("content",reqDeptDto.getContent());

        String s = HttpUtil.post(permissionUrl + "/getDeptForIdOrNo", params);
        if(CommonUtil.isEmpty(s)){
            return resDeptDto;
        }
        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            JSONArray jsonArray = result.getJSONArray("data");

            List<DeptDto> deptDtoList = parseDeptList(jsonArray);
            resDeptDto.setDeptList(deptDtoList);
        }

        return  resDeptDto;
    }

    /**
     * 解析部门数组
     * @param jsonArray
     * @return
     */
    private List<DeptDto> parseDeptList(JSONArray jsonArray){
        List<DeptDto> deptDtoList = new ArrayList<>();
        if(jsonArray == null || jsonArray.size() == 0){
            return deptDtoList;
        }

        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            DeptDto deptDto = new DeptDto();
            deptDto.setId(obj.getString("ID"));
            deptDto.setDeptName(obj.getString("DEPT_NAME"));
            deptDto.setDeptNo(obj.getString("DEPT_NO"));
            deptDto.setDeptStatus(obj.getString("DESCRIPTION"));
            deptDto.setParentId(obj.getString("PARENT_ID"));
            deptDto.setSeqNo(obj.getString("SEQ_NO"));
            JSONArray subAarray = obj.getJSONArray("SUBDEPT");
            List<DeptDto> subDeptList = parseDeptList(subAarray);
            deptDto.setSubDept(subDeptList);
            deptDtoList.add(deptDto);
        }
        return deptDtoList;
    }


    /**
     * 修改密码
     * @param req
     * @return
     */
    public Result<String> updatePsw(ReqUpdatePswDto req){
        UserInfoDto user = ThreadLocalHelper.getUser();
        String userName = user.getUserName();

        PrivateKey privateKey = RsaUtil.getRSAPrivateKey(privateModulus, privateExponent);
        String oldPassword = RsaUtil.decryptString(privateKey, req.getOldPwd().replace(' ', '+'));
        String password = RsaUtil.decryptString(privateKey, req.getNewPwd().replace(' ', '+'));

        Map<String, String> params=new HashMap<>();
        params.put("username", userName);
        params.put("oldPwd", oldPassword); // 密码为明文
        params.put("newPwd", password);

        Result<String> res = new Result<String>();
        String s = HttpUtil.post(permissionUrl + "/updatePwd", params);
        if(CommonUtil.isEmpty(s)){
            return ResultHelper.faile("修改密码失败");
        }
        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            return ResultHelper.success("修改密码成功");
        }else{
            return ResultHelper.faile("修改密码失败:" + result.getString("msg"));
        }
    }

    /**
     * 判断是否有按钮权限
     */
    public Boolean isHasButtonAuth(String userName, String buttonNo){
        Map<String, String> params=new HashMap<>();
        params.put("username", userName);
        params.put("button_no", buttonNo);

        String s = HttpUtil.post(permissionUrl + "/getRoleForButton", params);
        if(CommonUtil.isEmpty(s)){
            return false;
        }
        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            return true;
        }else{
            return false;
        }
    }


    /**
     * 登录验证
     * @param userName
     * @param decrypt 密码是否加密
     * @return
     */
    public Boolean checkPassword(String userName, String pass, Boolean decrypt){
        String password = pass;
        if(decrypt){
            PrivateKey privateKey = RsaUtil.getRSAPrivateKey(privateModulus, privateExponent);
            password = RsaUtil.decryptString(privateKey, pass.replace(' ', '+'));
        }

        Map<String, String> params=new HashMap<>();
        params.put("username", userName);
        params.put("password", password);// 密码为明文

        String s = HttpUtil.post(permissionUrl + "/loginCheck", params);
        if(CommonUtil.isEmpty(s)){
            return false;
        }
        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            return true;
        }else{
            return false;
        }
    }
}