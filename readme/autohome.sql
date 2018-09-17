
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `authority_name` varchar(100) DEFAULT NULL COMMENT '权限名',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '使用状态，0启用，1禁用，2删除',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `parent_id` int(11) DEFAULT NULL COMMENT '父ID',
  `authority_order` int(11) DEFAULT NULL COMMENT '排序',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', '系统管理', '0', '2018-08-24 14:51:08', '2018-09-03 16:50:39', '0', '1', '', 'xitongguanli');
INSERT INTO `authority` VALUES ('2', '仓库管理', '0', '2018-08-24 14:51:23', '2018-09-03 16:59:38', '0', '2', '', 'daishouhuo');
INSERT INTO `authority` VALUES ('3', '销售管理', '0', '2018-08-24 14:52:45', '2018-09-03 16:59:36', '0', '3', '', 'dingdanguanli');
INSERT INTO `authority` VALUES ('4', '客户管理', '0', '2018-08-24 14:53:03', '2018-09-03 16:59:35', '0', '4', '', 'jishuhezuoqiatanhezuohuitan');
INSERT INTO `authority` VALUES ('5', '统计管理', '0', '2018-08-24 14:53:24', '2018-09-03 16:49:22', '0', '5', null, 'tongji');
INSERT INTO `authority` VALUES ('6', '用户管理', '0', '2018-08-31 09:10:21', '2018-09-03 16:56:50', '1', '1', '/user', 'yonghu');
INSERT INTO `authority` VALUES ('7', '角色管理', '0', '2018-09-03 15:18:46', '2018-09-03 17:02:15', '1', '2', '/role', 'qunzu');
INSERT INTO `authority` VALUES ('8', '权限管理', '0', '2018-09-03 16:49:35', '2018-09-03 16:57:32', '1', '3', '/authority', 'quanxianguanli');
INSERT INTO `authority` VALUES ('9', '流水号管理', '0', '2018-09-03 16:54:23', '2018-09-03 16:56:09', '1', '4', '/serial_number', 'icon9');
INSERT INTO `authority` VALUES ('10', '仓库信息', '0', '2018-09-03 17:09:33', '2018-09-03 17:18:58', '2', '1', '/store', 'gongyingshangguanli3');
INSERT INTO `authority` VALUES ('11', '汽车信息', '0', '2018-09-03 17:10:25', '2018-09-03 17:19:09', '2', '2', '/cars', 'wuliugongying01');
INSERT INTO `authority` VALUES ('13', '库存信息', '0', '2018-09-03 17:10:42', '2018-09-03 17:18:44', '2', '3', '/stock', 'gongyinglian1');
INSERT INTO `authority` VALUES ('14', '订单信息', '0', '2018-09-03 17:11:38', '2018-09-03 17:19:39', '3', '1', '/order', 'dingdan');
INSERT INTO `authority` VALUES ('15', '销售信息', '0', '2018-09-03 17:12:01', '2018-09-03 17:20:00', '3', '2', '/sales', 'jingxiaoshangfuwu');
INSERT INTO `authority` VALUES ('16', '厂商信息', '0', '2018-09-03 17:12:35', '2018-09-03 17:20:10', '4', '1', '/manufacturer', 'hezuohuobanguanli');
INSERT INTO `authority` VALUES ('17', '客户信息', '0', '2018-09-03 17:12:52', '2018-09-03 17:18:18', '4', '2', '/customer', 'yaoqinghaoyoupengyou3');
INSERT INTO `authority` VALUES ('18', '回访信息', '0', '2018-09-03 17:13:09', '2018-09-03 17:21:20', '4', '3', '/visit', 'quanbudingdan');
INSERT INTO `authority` VALUES ('19', '库存统计', '0', '2018-09-03 17:15:14', '2018-09-03 17:20:47', '5', '1', '/statistics/store', 'caiwutongji');
INSERT INTO `authority` VALUES ('20', '趋势分析', '0', '2018-09-03 17:15:28', '2018-09-03 17:20:34', '5', '2', '/statistics/order', 'jixiao');
INSERT INTO `authority` VALUES ('21', '客户统计', '0', '2018-09-03 17:16:11', '2018-09-03 17:20:57', '5', '3', '/statistics/customer', 'shejituzhi');
INSERT INTO `authority` VALUES ('22', '汽车详情', '0', '2018-09-12 15:46:30', '2018-09-12 15:47:49', '3', '3', '/cars_detail', 'wuliugongying01');
INSERT INTO `authority` VALUES ('23', '汽车图片', '0', '2018-09-14 09:48:35', '2018-09-14 10:19:42', '2', '4', '/car_img', 'tupian');

-- ----------------------------
-- Table structure for car_img
-- ----------------------------
DROP TABLE IF EXISTS `car_img`;
CREATE TABLE `car_img` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `car_name` varchar(255) DEFAULT NULL COMMENT '汽车名称',
  `car_model` varchar(255) DEFAULT NULL COMMENT '汽车型号',
  `car_color` varchar(255) DEFAULT NULL COMMENT '汽车颜色',
  `car_img` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `car_note` varchar(255) DEFAULT NULL COMMENT '描述',
  `use_status` tinyint(4) DEFAULT NULL COMMENT '数据状态，0启用，1禁用，2删除，3已选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_img
-- ----------------------------
INSERT INTO `car_img` VALUES ('2', 'B', 'H', 'D', 'F:\\ideas-workspace\\carmanage\\plantImg\\B_H_D.jpeg', '2018-09-14 11:00:05', '2018-09-14 11:00:05', '测试', '0');

-- ----------------------------
-- Table structure for car_info
-- ----------------------------
DROP TABLE IF EXISTS `car_info`;
CREATE TABLE `car_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `car_code` varchar(100) DEFAULT NULL COMMENT '汽车编号',
  `car_name` varchar(100) DEFAULT NULL COMMENT '汽车名称',
  `car_model` varchar(100) DEFAULT NULL COMMENT '汽车型号',
  `car_color` varchar(100) DEFAULT NULL COMMENT '汽车颜色',
  `engine_number` varchar(100) DEFAULT NULL COMMENT '发动机编号，不重复',
  `yieldly` varchar(100) DEFAULT NULL COMMENT '生产地',
  `flow` varchar(100) DEFAULT NULL COMMENT '车辆流向',
  `store_id` int(11) DEFAULT NULL COMMENT '所属仓库',
  `manufacturer_id` int(11) DEFAULT NULL COMMENT '所属厂商',
  `production_date` date DEFAULT NULL COMMENT '生产日期',
  `storage_date` date DEFAULT NULL COMMENT '入库日期',
  `car_note` varchar(500) DEFAULT NULL COMMENT '汽车描述',
  `img_url` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '数据状态，0启用，1禁用，2删除，3已选',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`engine_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='汽车信息';

-- ----------------------------
-- Records of car_info
-- ----------------------------
INSERT INTO `car_info` VALUES ('1', 'A', 'B', 'C', 'D', 'E', 'A', '11111111', '1', '1', '2018-08-15', '2018-08-30', 'CES', null, '0', '2018-08-30 10:21:03', '2018-09-13 10:11:14');
INSERT INTO `car_info` VALUES ('2', 'F', 'G', 'H', 'I', 'J', 'B', null, '2', '1', '2018-08-08', '2018-08-15', null, null, '0', '2018-08-30 10:21:33', '2018-09-12 16:05:38');
INSERT INTO `car_info` VALUES ('3', 'S', 'B', 'H', 'I', 'T', 'C', null, '4', '1', '2018-08-23', '2018-08-30', null, null, '0', '2018-08-30 10:22:02', '2018-09-12 16:05:40');
INSERT INTO `car_info` VALUES ('4', 'A', 'B', 'H', 'D', 'R', 'D', '11111111', '3', '2', '2018-08-22', '2018-08-22', '77777777777777', null, '3', '2018-08-30 10:22:38', '2018-09-14 13:53:02');
INSERT INTO `car_info` VALUES ('5', 'QC201809130000001', 'B', 'H', 'D', 'dsfadfas', 'sdfas', '11111111', '1', '1', '2018-09-13', '2018-09-13', '444444', null, '0', '2018-09-13 17:45:56', '2018-09-13 17:45:56');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `customer_code` varchar(100) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(100) DEFAULT NULL COMMENT '客户名',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别，0男，1女，2其他',
  `telphone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `identity_card` varchar(20) DEFAULT NULL COMMENT '身份证',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '数据状态,0启用，1禁用，2删除',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `provincial_id` int(11) DEFAULT NULL COMMENT '省',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`customer_code`),
  UNIQUE KEY `AK_Key_3` (`identity_card`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='客户信息表';

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '11', '22', '0', '13615281658', '612401199201280528', '南昌黄马', '0', '2018-08-30 11:13:24', '2018-09-11 16:33:39', '1');
INSERT INTO `customer` VALUES ('2', '12', '234', '0', '13571641215', '612301199101280528', '北京大栅栏', '0', '2018-08-31 14:21:12', '2018-09-11 16:30:44', '1');
INSERT INTO `customer` VALUES ('3', 'CK201809110000002', '测试', '0', '15812354578', '610201199101280528', '江苏扬中通威', '0', '2018-09-11 16:01:52', '2018-09-11 16:34:24', '12');

-- ----------------------------
-- Table structure for manufacturer
-- ----------------------------
DROP TABLE IF EXISTS `manufacturer`;
CREATE TABLE `manufacturer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `manufacturer_code` varchar(50) DEFAULT NULL COMMENT '厂商编号',
  `manufacturer_name` varchar(100) DEFAULT NULL COMMENT '厂商名',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `telphone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `postcode` varchar(20) DEFAULT NULL COMMENT '邮编',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '数据状态，0启用，1禁用，2删除',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`manufacturer_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='厂商信息';

-- ----------------------------
-- Records of manufacturer
-- ----------------------------
INSERT INTO `manufacturer` VALUES ('1', 'CES', 'CE', '测试', '13571641215', '101100', '江苏扬中通威', '0', '2018-08-30 10:20:48', '2018-09-11 17:04:19');
INSERT INTO `manufacturer` VALUES ('2', 'SS', 'SS', 'ad', '15812354578', '101100', '阿打算发', '0', '2018-08-30 10:20:56', '2018-09-11 17:04:30');
INSERT INTO `manufacturer` VALUES ('3', 'CS201809110000001', 'Sxxx', '测试', '15812354578', '101101', 'asda', '0', '2018-09-11 17:05:11', '2018-09-11 17:05:51');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_code` varchar(50) DEFAULT NULL COMMENT '订单号',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `car_id` int(11) DEFAULT NULL COMMENT '汽车ID',
  `closing_cost` decimal(20,3) DEFAULT NULL COMMENT '成交价',
  `sales_date` date DEFAULT NULL COMMENT '销售日期',
  `order_status` tinyint(4) DEFAULT '0' COMMENT '订单状态,0未出库，1已出库，2在运，3已送达，4退货',
  `order_note` varchar(500) DEFAULT NULL COMMENT '订单说明',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '数据状态，0启用，1禁用，2删除',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`order_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='订单信息表';

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('1', 'sss', '1', '1', '1.000', '2018-08-30', '0', null, '0', '2018-08-30 11:13:32', '2018-08-30 11:13:32');
INSERT INTO `order_info` VALUES ('2', 'ee', '1', '2', '333.000', '2018-08-30', '0', null, '0', '2018-08-30 11:14:03', '2018-08-30 11:14:03');
INSERT INTO `order_info` VALUES ('3', 'fsd', '1', '1', '34234.000', '2018-08-24', '0', null, '0', '2018-08-30 13:58:56', '2018-08-30 14:40:09');
INSERT INTO `order_info` VALUES ('4', 'dgdgf', '1', '1', '34235.000', '2018-08-24', '0', null, '0', '2018-08-30 13:59:16', '2018-08-30 13:59:16');
INSERT INTO `order_info` VALUES ('5', 'DD201809150000001', '2', '4', '111111.000', '2018-09-15', '0', '测试', '0', '2018-09-15 13:54:19', '2018-09-15 13:54:19');

-- ----------------------------
-- Table structure for provincial
-- ----------------------------
DROP TABLE IF EXISTS `provincial`;
CREATE TABLE `provincial` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '省市自治区的名字',
  `order` int(11) DEFAULT NULL COMMENT '序号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of provincial
-- ----------------------------
INSERT INTO `provincial` VALUES ('1', '北京', '1', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('2', '天津', '2', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('3', '河北', '3', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('4', '山西', '4', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('5', '内蒙古', '5', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('6', '辽宁', '6', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('7', '吉林', '7', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('8', '黑龙江', '8', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('9', '上海', '9', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('10', '江苏', '10', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('11', '浙江省', '11', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('12', '安徽', '12', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('13', '福建', '13', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('14', '江西', '14', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('15', '山东', '15', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('16', '河南', '16', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('17', '湖北', '17', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('18', '湖南', '18', '2018-09-11 15:04:23', '2018-09-11 15:04:23');
INSERT INTO `provincial` VALUES ('19', '广东', '19', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('20', '广西', '20', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('21', '海南', '21', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('22', '重庆', '22', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('23', '四川', '23', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('24', '贵州', '24', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('25', '云南', '25', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('26', '西藏', '26', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('27', '陕西', '27', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('28', '甘肃省', '28', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('29', '青海', '29', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('30', '宁夏', '30', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('31', '新疆', '31', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('32', '台湾', '32', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('33', '香港特别行政区', '33', '2018-09-11 15:04:24', '2018-09-11 15:04:24');
INSERT INTO `provincial` VALUES ('34', '澳门', '34', '2018-09-11 15:04:24', '2018-09-11 15:04:24');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名',
  `role_note` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '使用状态,0启用，1禁用，2删除',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', '系统管理员', '0', '2018-08-27 13:53:54', '2018-09-04 08:15:04');
INSERT INTO `role` VALUES ('2', '仓库管理员', '仓库管理员', '0', '2018-09-04 08:15:26', '2018-09-04 08:15:26');
INSERT INTO `role` VALUES ('3', '销售管理员', '销售管理员', '0', '2018-09-04 08:15:39', '2018-09-04 08:15:39');
INSERT INTO `role` VALUES ('4', '测试', '测试数据', '2', '2018-09-11 11:29:01', '2018-09-11 11:29:01');

-- ----------------------------
-- Table structure for role_authority
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority` (
  `role_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `authority_id` int(11) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-权限中间表';

-- ----------------------------
-- Records of role_authority
-- ----------------------------
INSERT INTO `role_authority` VALUES ('1', '1');
INSERT INTO `role_authority` VALUES ('1', '3');
INSERT INTO `role_authority` VALUES ('1', '2');
INSERT INTO `role_authority` VALUES ('1', '4');
INSERT INTO `role_authority` VALUES ('1', '5');
INSERT INTO `role_authority` VALUES ('1', '6');
INSERT INTO `role_authority` VALUES ('1', '7');
INSERT INTO `role_authority` VALUES ('1', '8');
INSERT INTO `role_authority` VALUES ('1', '9');
INSERT INTO `role_authority` VALUES ('1', '10');
INSERT INTO `role_authority` VALUES ('1', '11');
INSERT INTO `role_authority` VALUES ('1', '13');
INSERT INTO `role_authority` VALUES ('1', '14');
INSERT INTO `role_authority` VALUES ('1', '15');
INSERT INTO `role_authority` VALUES ('1', '16');
INSERT INTO `role_authority` VALUES ('1', '17');
INSERT INTO `role_authority` VALUES ('1', '18');
INSERT INTO `role_authority` VALUES ('1', '19');
INSERT INTO `role_authority` VALUES ('1', '20');
INSERT INTO `role_authority` VALUES ('1', '21');
INSERT INTO `role_authority` VALUES ('2', '2');
INSERT INTO `role_authority` VALUES ('2', '10');
INSERT INTO `role_authority` VALUES ('2', '11');
INSERT INTO `role_authority` VALUES ('2', '13');
INSERT INTO `role_authority` VALUES ('3', '3');
INSERT INTO `role_authority` VALUES ('3', '15');
INSERT INTO `role_authority` VALUES ('3', '14');
INSERT INTO `role_authority` VALUES ('3', '4');
INSERT INTO `role_authority` VALUES ('3', '16');
INSERT INTO `role_authority` VALUES ('3', '17');
INSERT INTO `role_authority` VALUES ('3', '18');
INSERT INTO `role_authority` VALUES ('4', '6');
INSERT INTO `role_authority` VALUES ('4', '7');
INSERT INTO `role_authority` VALUES ('4', '8');
INSERT INTO `role_authority` VALUES ('1', '22');
INSERT INTO `role_authority` VALUES ('1', '23');

-- ----------------------------
-- Table structure for store_info
-- ----------------------------
DROP TABLE IF EXISTS `store_info`;
CREATE TABLE `store_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `store_name` varchar(100) DEFAULT NULL COMMENT '仓库名',
  `address` varchar(500) DEFAULT NULL COMMENT '仓库地址',
  `max_capacity` int(11) DEFAULT '0' COMMENT '仓库最大容量',
  `capacity` int(11) DEFAULT '0' COMMENT '仓库现有容量',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '0启用，1禁用，2删除',
  `margin_capacity` int(11) DEFAULT '0' COMMENT '余量库存',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`store_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='仓库信息';

-- ----------------------------
-- Records of store_info
-- ----------------------------
INSERT INTO `store_info` VALUES ('1', '上海仓库', '上海陆家嘴', '100', '20', '2018-08-30 10:18:57', '2018-08-30 10:18:57', '0', '80');
INSERT INTO `store_info` VALUES ('2', '武汉仓库', '武汉凤凰塔', '100', '11', '2018-08-30 10:19:20', '2018-09-12 11:20:20', '0', '89');
INSERT INTO `store_info` VALUES ('3', '北京仓库', '北京三里屯', '200', '50', '2018-08-30 10:19:54', '2018-08-30 10:19:54', '0', '150');
INSERT INTO `store_info` VALUES ('4', '成都仓库', '成都太古里', '100', '1', '2018-09-12 11:19:47', '2018-09-12 11:21:04', '0', '99');

-- ----------------------------
-- Table structure for sys_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `sys_serial_number`;
CREATE TABLE `sys_serial_number` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `authority_id` int(11) DEFAULT NULL COMMENT '权限ID',
  `config_templet` varchar(50) DEFAULT NULL COMMENT '序列号模板',
  `currut_serial` int(11) DEFAULT NULL COMMENT '当前序列号',
  `max_serial` int(11) DEFAULT NULL COMMENT '最大位数',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '数据状态，0启用，1禁用，2删除',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_key1` (`config_templet`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='流水号表';

-- ----------------------------
-- Records of sys_serial_number
-- ----------------------------
INSERT INTO `sys_serial_number` VALUES ('2', '14', 'DD', '1', '6', '0', '2018-09-05 10:24:25', '2018-09-15 15:08:09');
INSERT INTO `sys_serial_number` VALUES ('3', '11', 'QC', '1', '6', '0', '2018-09-05 10:40:34', '2018-09-12 17:37:22');
INSERT INTO `sys_serial_number` VALUES ('5', '10', 'CK', '1', '6', '0', '2018-09-05 10:54:35', '2018-09-11 16:32:49');
INSERT INTO `sys_serial_number` VALUES ('6', '16', 'CS', '1', '6', '0', '2018-09-11 16:51:30', '2018-09-11 16:51:30');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `account` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `role_id` tinyint(4) DEFAULT NULL COMMENT '角色',
  `use_status` int(11) DEFAULT '0' COMMENT '使用状态,0为启用，1为禁用,2为删除',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'admin', '123456', '1', '0', '系统管理员', '2018-08-27 13:53:42', '2018-08-27 14:00:21');
INSERT INTO `user_info` VALUES ('2', 'order_admin', '123456', '3', '0', '一枚普朗克粒子', '2018-09-05 08:43:53', '2018-09-05 08:43:53');
INSERT INTO `user_info` VALUES ('3', 'store_admin', '123456', '2', '0', '楼中故客', '2018-09-05 08:46:15', '2018-09-05 08:46:15');
INSERT INTO `user_info` VALUES ('12', '测试', '123456', '4', '0', '测试', '2018-09-11 11:47:44', '2018-09-11 11:47:44');

-- ----------------------------
-- Table structure for visit_info
-- ----------------------------
DROP TABLE IF EXISTS `visit_info`;
CREATE TABLE `visit_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `order_id` int(11) DEFAULT NULL COMMENT '订单ID',
  `visit_date` date DEFAULT NULL COMMENT '回访时间',
  `visit_events` varchar(500) DEFAULT NULL COMMENT '回访事件',
  `visit_record` varchar(1000) DEFAULT NULL COMMENT '回访记录',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '数据状态,0启用，1禁用，2删除',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='回访信息';

-- ----------------------------
-- Records of visit_info
-- ----------------------------
INSERT INTO `visit_info` VALUES ('1', '1', '1', '2018-08-31', '1111', '1111', '0', '2018-08-31 12:05:32', '2018-08-31 12:05:32');
INSERT INTO `visit_info` VALUES ('2', '1', '4', '2018-09-06', '1212313', '1111111111111111', '0', '2018-09-14 18:07:34', '2018-09-15 15:36:48');
INSERT INTO `visit_info` VALUES ('3', '2', '5', '2018-09-15', '大深V广发', '本期按时', '0', '2018-09-15 18:13:51', '2018-09-15 18:13:51');
INSERT INTO `visit_info` VALUES ('4', '1', '4', '2018-09-06', '1212313', '1111111111111111', '0', '2018-09-15 18:20:45', '2018-09-15 18:20:45');
INSERT INTO `visit_info` VALUES ('5', '1', '4', '2018-09-06', '1212313', '1111111111111111', '0', '2018-09-15 18:21:08', '2018-09-15 18:21:08');
INSERT INTO `visit_info` VALUES ('6', '1', '2', '2018-09-17', 'ceshi 111zcccccccc', 'dhadkagfdkgasdfkgjadjf', '2', '2018-09-17 13:54:33', '2018-09-17 13:54:59');
