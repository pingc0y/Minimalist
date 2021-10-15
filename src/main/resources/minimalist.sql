/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50640
Source Host           : 127.0.0.1:3306
Source Database       : mtx_student_system

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2021-10-09 14:55:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `assets`
-- ----------------------------
DROP TABLE IF EXISTS `assets`;
CREATE TABLE `assets` (
  `id` varchar(30) NOT NULL COMMENT 'id',
  `hostname` varchar(20) NOT NULL COMMENT '主机名',
  `address` varchar(60) NOT NULL COMMENT '地址',
  `protocol` varchar(10) NOT NULL DEFAULT '0' COMMENT '协议',
  `port` int(5) NOT NULL COMMENT '端口',
  `activate` int(1) NOT NULL DEFAULT '0' COMMENT '激活',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产';


-- ----------------------------
-- Table structure for `assets_authorization`
-- ----------------------------
DROP TABLE IF EXISTS `assets_authorization`;
CREATE TABLE `assets_authorization` (
  `id` varchar(30) NOT NULL COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `user_id` varchar(255) NOT NULL COMMENT '用户',
  `user_group_id` varchar(255) NOT NULL COMMENT '用户组',
  `assets_id` varchar(255) NOT NULL COMMENT '资产',
  `assets_user_id` varchar(255) NOT NULL COMMENT '资产用户',
  `activate` int(1) NOT NULL COMMENT '激活',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL COMMENT '失效时间',
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产授权';

-- ----------------------------
-- Table structure for `assets_user`
-- ----------------------------
DROP TABLE IF EXISTS `assets_user`;
CREATE TABLE `assets_user` (
  `id` varchar(30) NOT NULL COMMENT 'id',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `protocol` varchar(10) NOT NULL COMMENT '协议',
  `password` text NOT NULL COMMENT '密码',
  `secret_key` text COMMENT '密钥',
  `active_directory` varchar(255) DEFAULT NULL COMMENT 'AD域',
  `remark` text COMMENT '备注',
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产用户';

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `browser_name` varchar(255) DEFAULT '' COMMENT '浏览器',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip地址',
  `os_name` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `path` text COMMENT '请求地址',
  `method` varchar(255) DEFAULT NULL COMMENT '请求类型',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `msg` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3000 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志';

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `hiher_id` int(3) DEFAULT NULL COMMENT '上级id',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单名字',
  `type` int(2) DEFAULT NULL COMMENT '类型',
  `opentype` varchar(255) DEFAULT NULL COMMENT '打开类型',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `href` varchar(255) DEFAULT NULL COMMENT '地址,默认为空',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=504 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('100', '0', '我的资产', '1', '_iframe', 'layui-icon layui-icon-component', '/view/assetsMy/assetsMy');
INSERT INTO `menu` VALUES ('200', '0', '用户管理', '0', null, 'layui-icon layui-icon-username', null);
INSERT INTO `menu` VALUES ('201', '200', '用户列表', '1', '_iframe', '', '/view/user/user');
INSERT INTO `menu` VALUES ('202', '200', '用户组', '1', '_iframe', '', '/view/userGroup/userGroup');
INSERT INTO `menu` VALUES ('300', '0', '资产管理', '0', null, 'layui-icon layui-icon-template-1', null);
INSERT INTO `menu` VALUES ('301', '300', '资产列表', '1', '_iframe', '', '/view/assets/assets');
INSERT INTO `menu` VALUES ('302', '300', '资产用户', '1', '_iframe', '', '/view/assetsUser/assetsUser');
INSERT INTO `menu` VALUES ('400', '0', '权限管理', '0', null, 'layui-icon layui-icon-auz', null);
INSERT INTO `menu` VALUES ('401', '400', '资产授权', '1', '_iframe', '', '/view/assetsAuthorization/assetsAuthorization');
INSERT INTO `menu` VALUES ('500', '0', '系统管理', '0', null, 'layui-icon layui-icon-util', null);
INSERT INTO `menu` VALUES ('501', '500', '会话管理', '1', '_iframe', '', '/view/video/video');
INSERT INTO `menu` VALUES ('502', '500', '行为日志', '1', '_iframe', '', '/view/system/logging');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL COMMENT '用户主键id',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `roles` int(2) DEFAULT NULL COMMENT '角色',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `status` int(2) DEFAULT '0' COMMENT '是否可用',
  `creation_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1421302348978902529', 'admin', 'admin', '1', 'niupi', '0', '2021-10-09 12:03:16');

-- ----------------------------
-- Table structure for `user_group`
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id` varchar(30) NOT NULL COMMENT 'id',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户',
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';

