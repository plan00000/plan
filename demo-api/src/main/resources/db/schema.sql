-- ==============================================================
--  城际网约车数据库 cjc
-- ==============================================================
drop database if exists `cjc`;
create database `cjc`;
use `cjc`;
-- =================================
-- 乘客表
-- =================================
DROP TABLE IF EXISTS `tb_passenger`;
CREATE TABLE `tb_passenger` (
  `id` varchar(32) NOT NULL ,
  -- 用户信息表-性别：1-男生，2-女生
  `sex` varchar(20) DEFAULT '0' COMMENT '性别sex：1-男生，2-女生',
  -- 用户信息表-用户名称
  `user_name` varchar(40) DEFAULT '' COMMENT '用户名称',
  -- 手机号码
  `mobileno` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号码',
  -- 状态 0-禁用1-启用2-删除
  `state` varchar(6) DEFAULT '0' COMMENT '用户状态state： 0-禁用,1-启用,2-删除',
  -- 用户目前位置
  `location` varchar(30) default null COMMENT '用户目前位置',
  -- 用户所在位置经纬度--纬度
  `lat` varchar(20) default null COMMENT '用户所在位置经纬度--纬度',
  -- 用户所在位置经纬度--经度
  `lon` varchar(20) default null COMMENT '用户所在位置经纬度--经度',
  -- 身份证号
  `id_card` varchar(30) DEFAULT  null COMMENT '身份证号',
  -- 创建时间
  `create_time` datetime DEFAULT null COMMENT '创建时间',
  -- 更新时间
  `update_time`datetime DEFAULT null COMMENT '更新时间',
  -- 备注
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- =================================
-- 司机表
-- =================================
DROP TABLE IF EXISTS `tb_driver`;
CREATE TABLE `tb_driver` (
`id` varchar(32) NOT NULL ,
  -- 用户信息表-性别：1-男生，2-女生
  `sex` varchar(20) DEFAULT '0' COMMENT '性别：1-男生，2-女生',
  -- 年龄
  `age` VARCHAR(10) DEFAULT null COMMENT '年龄',
  -- 用户信息表-用户名称
  `user_name` varchar(40) DEFAULT '' COMMENT '用户名称',
  -- 手机号码
  `mobileno` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号码',
  -- 状态 0-禁用1-启用2-删除
  `state` varchar(6) DEFAULT '0' COMMENT '状态 0-禁用,1-启用,2-删除',
  -- 用户目前位置
  `location` varchar(30) default null COMMENT '用户目前位置',
  -- 用户所在位置经纬度--纬度
  `lat` varchar(20) default null COMMENT '用户所在位置经纬度--纬度',
  -- 用户所在位置经纬度--经度
  `lon` varchar(20) default null COMMENT '用户所在位置经纬度--经度',
  -- 身份证号
  `id_card` varchar(30) DEFAULT  null COMMENT '身份证号',
  -- 车牌号
  `car_no` varchar(50) DEFAULT  null COMMENT '车牌号',
  -- 车辆颜色
  `car_color` VARCHAR (20) DEFAULT null comment '车身颜色',
  -- 车辆品牌
  `car_mark` VARCHAR (50) DEFAULT null comment '车辆品牌',
  -- 驾驶证号
  `drive_no` VARCHAR(50) DEFAULT null COMMENT '驾驶证号',
  -- 司机评分级：几颗星最高5个星1代表一个星
  `drive_star` VARCHAR (10) DEFAULT null comment '司机评分级：几颗星最高5个星1代表一个星',
   -- 创建时间
  `create_time` datetime DEFAULT null COMMENT '创建时间',
  -- 更新时间
  `update_time`datetime DEFAULT null COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 线路表
-- =================================
DROP TABLE IF EXISTS `tb_line`;
CREATE TABLE `tb_line` (
  -- ID
  `id` VARCHAR (32) NOT NULL COMMENT '主键id',
  -- 起点
  `start_address` varchar(30)  DEFAULT NULL COMMENT '起点',
  -- 终点
  `end_address` varchar(30) DEFAULT NULL COMMENT '终点',
  -- 起点经度
  `start_lon` varchar(20) DEFAULT NULL COMMENT '起点经度',
  -- 起点纬度
  `start_lat` varchar(20) DEFAULT NULL COMMENT '起点纬度',
  -- 终点经度
  `end_lon` varchar(20) DEFAULT NULL COMMENT '终点经度',
  -- 终点纬度
  `end_lat` varchar(20) DEFAULT NULL COMMENT '终点纬度',
  -- 创建时间
  `create_time` datetime DEFAULT null COMMENT '创建时间',
  -- 更新时间
  `update_time`datetime DEFAULT null COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 订单表
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  -- 订单表id
  `id` VARCHAR (32) NOT NULL COMMENT '订单表id',
  -- 下单时间
  `order_time` datetime DEFAULT null COMMENT '下单时间',
  -- 订单号
  `order_no` varchar(40) DEFAULT NULL COMMENT '订单号',
  -- 订单起点
  `order_start_address` varchar(40) DEFAULT NULL COMMENT '订单起点',
  -- 订单起点经度
  `order_start_lon` VARCHAR (20) DEFAULT null COMMENT '订单起点经度',
  -- 订单起点纬度
  `order_start_lat` VARCHAR(20) DEFAULT null COMMENT '订单起点纬度',
  -- 订单结束点
  `order_end_address` VARCHAR (50) DEFAULT null COMMENT '订单结束点',
  -- 订单结束点经度
  `order_end_lon` VARCHAR (20) DEFAULT null COMMENT '订单结束点经度',
  -- 订单结束纬度
  `order_end_lat` VARCHAR(20) DEFAULT null COMMENT '订单起点纬度',
  -- 司机id
  `drive_id` VARCHAR(32) DEFAULT null COMMENT '司机id',
  -- 订单类型:0-包车，1-拼车
  `order_type` varchar(10) DEFAULT NULL COMMENT '订单类型:0-包车，1-拼车',
  -- 订单种类：0-实时单，1-预约单
  `order_real_type` varchar(10) DEFAULT NULL COMMENT '订单种类：0-实时单，1-预约单',
  -- 预约时间
  `order_bespeak_time` datetime DEFAULT null COMMENT '预约时间',
  -- 订单状态:0-已取消，1-派单中，2-司机已接单，3-行程开始，4-行程结束'
  `order_status` varchar(10) DEFAULT null COMMENT '订单状态:0-已取消，1-派单中，2-司机已接单，3-行程开始，4-行程已完成',
  -- 订单派单时间
  `order_receive_time` datetime DEFAULT null comment '订单派单时间',
  -- 关联用户id
  `user_id` VARCHAR (32) DEFAULT null COMMENT '关联用户id',
  -- 乘客人数
  `order_user_num` VARCHAR (10) DEFAULT null COMMENT '乘客人数',
  -- 所属线路
  `line_id` VARCHAR (10) DEFAULT null,
  -- 订单备注
  `remark` VARCHAR (200) DEFAULT null,
    -- 取消原因
  `cancel_reason` VARCHAR (100) DEFAULT null COMMENT '取消原因',
   -- 创建时间
  `create_time` datetime DEFAULT null COMMENT '创建时间',
  -- 更新时间
  `update_time`datetime DEFAULT null COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 订单评价表
-- =================================
DROP TABLE IF EXISTS `tb_evaluate`;
CREATE TABLE `tb_evaluate` (
  -- ID
  `id` VARCHAR (32) NOT NULL COMMENT '主键id',
  -- 关联用户id
  `user_id` VARCHAR (32) DEFAULT null COMMENT '用户id',
  -- 司机id
  `driver_id` VARCHAR (32) DEFAULT null COMMENT '司机id',
  -- 订单id
  `order_id` VARCHAR (32) DEFAULT null COMMENT '订单id',
  -- 订单号
  `order_no` VARCHAR (32) NOT NULL COMMENT '订单表id',
  -- 订单评价星级：几颗星最高5个星
  `order_star` VARCHAR (10) DEFAULT null COMMENT '订单评价星级：几颗星最高5个星',
  -- 订单服务评价(111111-车内整洁，认路准，驾驶平稳，态度好，有礼貌，服务周到)
  `order_service` varchar(5) DEFAULT null COMMENT '订单服务评价(111111-车内整洁，认路准，驾驶平稳，态度好，有礼貌，服务周到)',
  -- 订单评价
  `order_evaluate` VARCHAR(300) DEFAULT null COMMENT '订单评价',
  -- 创建时间
  `create_time` datetime DEFAULT null COMMENT '创建时间',
  -- 更新时间
  `update_time`datetime DEFAULT null  COMMENT '更新时间',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 预约用车表
-- =================================
DROP TABLE IF EXISTS `tb_bespeak`;
CREATE TABLE `tb_bespeak` (
  -- ID
  `id` VARCHAR (32) NOT NULL COMMENT '主键id',
  -- 预约编号
  `bespeak_no` VARCHAR(32) DEFAULT NULL COMMENT '预约编号',
  -- 预约起点
  `bespeak_start_location` VARCHAR (50) DEFAULT null COMMENT '预约起点',
  -- 预约结束点
  `bespeak_end_location` VARCHAR (50) DEFAULT null COMMENT '预约起点',
  -- 预约起点经度
  `bespeak_start_lon` VARCHAR (20) DEFAULT null COMMENT '预约起点经度',
  -- 预约起点纬度
  `bespeak_start_lat` VARCHAR(20) DEFAULT null COMMENT '预约起点纬度',
  -- 预约结束点经度
  `bespeak_end_lon` VARCHAR (20) DEFAULT null COMMENT '预约结束点经度',
  -- 预约结束纬度
  `bespeak_end_lat` VARCHAR(20) DEFAULT null COMMENT '预约结束纬度',
  -- 下单时间
  `order_time` datetime DEFAULT null COMMENT '下单时间',
  -- 关联用户id
  `user_id` VARCHAR (32) DEFAULT null COMMENT '关联用户id',
  -- 乘客人数
  `order_user_num` VARCHAR (10) DEFAULT null COMMENT '乘客人数',
  -- 所属线路
  `line_id` VARCHAR (10) DEFAULT null,
   -- 订单备注
  `remark` VARCHAR (200) DEFAULT null,
  -- 创建时间
  `create_time` datetime DEFAULT null COMMENT '创建时间',
  -- 更新时间
  `update_time`datetime DEFAULT null COMMENT '更新时间',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- =================================
-- 字典表
-- =================================
DROP TABLE IF EXISTS `tb_dictionary`;
CREATE TABLE `tb_dictionary` (
  -- ID
  `id` VARCHAR (32) NOT NULL COMMENT '主键id',
  -- 字典类型：
  `dictionary_Type` VARCHAR (20) not null comment '字典类型',
  -- 编码值
  `code` VARCHAR (5) NOT NULL COMMENT '编码值',
  -- 编码名称
  `name` VARCHAR (5) NOT NULL COMMENT '编码名称',
  -- 创建时间
  `create_time` datetime DEFAULT null COMMENT '创建时间',
  -- 更新时间
  `update_time`datetime DEFAULT null COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;