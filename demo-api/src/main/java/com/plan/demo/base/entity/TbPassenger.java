package com.plan.demo.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table tb_passenger - 
 * @time 2021-03-12 15:29:32
 */
public class TbPassenger {
    /**
     * ，对应表字段为：tb_passenger.id
     */
    @ApiModelProperty("")
    private String id;

    /**
     * 性别：1-男生，2-女生，对应表字段为：tb_passenger.sex
     */
    @ApiModelProperty("性别：1-男生，2-女生")
    private String sex;

    /**
     * 用户名称，对应表字段为：tb_passenger.user_name
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 用户登录账号，对应表字段为：tb_passenger.user_account
     */
    @ApiModelProperty("用户登录账号")
    private String userAccount;

    /**
     * 真实姓名，对应表字段为：tb_passenger.realname
     */
    @ApiModelProperty("真实姓名")
    private String realname;

    /**
     * 头像，对应表字段为：tb_passenger.headimgurl
     */
    @ApiModelProperty("头像")
    private String headimgurl;

    /**
     * 密码，对应表字段为：tb_passenger.password
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 密码的盐，对应表字段为：tb_passenger.salt
     */
    @ApiModelProperty("密码的盐")
    private String salt;

    /**
     * 手机号码，对应表字段为：tb_passenger.mobileno
     */
    @ApiModelProperty("手机号码")
    private String mobileno;

    /**
     * 状态 0-禁用,1-启用,2-删除，对应表字段为：tb_passenger.state
     */
    @ApiModelProperty("状态 0-禁用,1-启用,2-删除")
    private String state;

    /**
     * 用户目前位置，对应表字段为：tb_passenger.location
     */
    @ApiModelProperty("用户目前位置")
    private String location;

    /**
     * 用户所在位置经纬度--纬度，对应表字段为：tb_passenger.lat
     */
    @ApiModelProperty("用户所在位置经纬度--纬度")
    private String lat;

    /**
     * 用户所在位置经纬度--经度，对应表字段为：tb_passenger.lon
     */
    @ApiModelProperty("用户所在位置经纬度--经度")
    private String lon;

    /**
     * 身份证号，对应表字段为：tb_passenger.id_card
     */
    @ApiModelProperty("身份证号")
    private String idCard;

    /**
     * 创建时间，对应表字段为：tb_passenger.create_time
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_passenger.update_time
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 备注，对应表字段为：tb_passenger.remark
     */
    @ApiModelProperty("备注")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}