/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : corn

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 17/11/2022 18:17:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2118 DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (1, '首页', 0, 0, '/home', '/Home', 'C', '0', '', 'dashboard', 1, '2022-11-06 00:00:00', 1, '2022-11-07 21:18:36', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2, '系统管理', 0, 1, '', '', 'M', '0', '', 'system', 1, '2022-11-06 00:00:00', 1, '2022-11-07 21:17:41', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (3, '菜单管理', 2, 0, '/menu', '/Menu', 'C', '0', NULL, 'tree-table', 1, '2022-11-06 00:00:00', 1, '2022-11-09 09:31:02', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2095, '角色管理', 2, 1, '/role', '/Role', 'C', '0', NULL, 'peoples', 1, '2022-11-08 00:00:00', 1, '2022-11-08 12:06:06', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2096, '测试1', 0, 0, '', NULL, 'M', '0', NULL, '404', 1, '2022-11-10 00:00:00', 1, '2022-11-10 18:28:56', '1');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2097, '用户管理', 2, 2, '/user', '/User', 'C', '0', NULL, 'user', 1, '2022-11-10 00:00:00', 1, '2022-11-14 20:49:07', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2098, '新增菜单', 3, 0, '', NULL, 'F', '0', 'menu::add', '#', 1, '2022-11-14 00:00:00', 1, '2022-11-14 16:51:31', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2099, '删除菜单', 3, 1, '', NULL, 'F', '0', 'menu::delete', '#', 1, '2022-11-14 00:00:00', 1, '2022-11-14 16:51:38', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2100, '修改菜单', 3, 2, '', NULL, 'F', '0', 'menu::put', '#', 1, '2022-11-14 00:00:00', 1, '2022-11-14 16:51:43', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2101, '查看菜单', 3, 3, '', NULL, 'F', '0', 'menu::query', '#', 1, '2022-11-14 00:00:00', 1, '2022-11-14 16:51:48', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2102, '新增角色', 2095, 0, '', NULL, 'F', '0', 'role::add', '#', 1, '2022-11-14 20:04:03', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2103, '删除角色', 2095, 1, '', NULL, 'F', '0', 'role::delete', '#', 1, '2022-11-14 20:04:40', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2104, '修改角色', 2095, 2, '', NULL, 'F', '0', 'role::put', '#', 1, '2022-11-14 20:05:00', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2105, '查看角色', 2095, 3, '', NULL, 'F', '0', 'role::query', '#', 1, '2022-11-14 20:05:18', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2106, '新增用户', 2097, 0, '', NULL, 'F', '0', 'user::add', '#', 1, '2022-11-14 20:12:42', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2107, '删除用户', 2097, 1, '', NULL, 'F', '0', 'user::delete', '#', 1, '2022-11-14 20:13:23', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2108, '修改用户', 2097, 2, '', NULL, 'F', '0', 'user::put', '#', 1, '2022-11-14 20:13:41', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2109, '查看用户', 2097, 3, '', NULL, 'F', '0', 'user::query', '#', 1, '2022-11-14 20:13:58', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2110, '小程序管理', 0, 2, '', NULL, 'M', '0', NULL, 'wechat', 1, '2022-11-14 21:08:15', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2111, '微信用户管理', 2110, 0, '/wx/user', '/WxUser', 'C', '0', NULL, 'user', 1, '2022-11-14 00:00:00', 1, '2022-11-14 21:17:05', '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2112, '查看用户', 2111, 0, '', NULL, 'F', '0', 'wx::user::query', '#', 1, '2022-11-15 09:32:44', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2113, '修改用户', 2111, 0, '', NULL, 'F', '0', 'wx::user::put', '#', 1, '2022-11-15 11:13:05', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2114, '取样记录管理', 2110, 1, '/record', '/Record', 'C', '0', NULL, 'documentation', 1, '2022-11-15 22:10:33', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2115, '查看取样记录', 2114, 0, '', NULL, 'F', '0', 'record::query', '#', 1, '2022-11-15 22:54:21', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2116, '修改取样记录', 2114, 1, '', NULL, 'F', '0', 'record::put', '#', 1, '2022-11-16 01:20:14', NULL, NULL, '0');
INSERT INTO `menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2117, '删除取样记录', 2114, 2, '', NULL, 'F', '0', 'record::delete', '#', 1, '2022-11-16 14:30:13', NULL, NULL, '0');
COMMIT;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '检测名称',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `longitude` varchar(255) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) DEFAULT NULL COMMENT '纬度',
  `result_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '识别结果',
  `result_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '相似度',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '2' COMMENT '记录状态 0通过 1未通过 2审核中',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检测记录表';

-- ----------------------------
-- Records of record
-- ----------------------------
BEGIN;
INSERT INTO `record` (`id`, `name`, `img_url`, `remark`, `longitude`, `latitude`, `result_name`, `result_value`, `status`, `create_by`, `create_time`, `del_flag`) VALUES (28, 'css', 'https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/images/f5bbef57-5b42-40fa-b76c-31affdf0ad98.jpg', '褐斑病又称立枯丝核疫病 brown spot，主要是由立枯丝核菌引起的一种真菌病害。广泛分布于世界各地，可以侵染所有草坪草，其中尤以冷季型草坪禾草受害最重，造成草坪植株死亡，使草坪形成大面积秃斑，极大地破坏草坪景观。可以侵染所有草坪草，其中尤以冷季型草坪禾草受害最重，造成草坪植株死亡。', '109.44726', '24.28154', '褐斑病', '0.7', '0', 'oG8dg5HAeGxGA8G6Nc4Snmgzne04', '2022-11-13 00:00:00', 1);
INSERT INTO `record` (`id`, `name`, `img_url`, `remark`, `longitude`, `latitude`, `result_name`, `result_value`, `status`, `create_by`, `create_time`, `del_flag`) VALUES (29, '测试001', 'https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/images/384c7f5f-35e6-472c-a8cb-4bf9f9438701.jpeg', '无', '109.44726', '24.28154', '水稻东格鲁病毒病', '0.95', '0', 'oG8dg5HAeGxGA8G6Nc4Snmgzne04', '2022-11-16 00:00:00', 0);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3 COMMENT='角色信息表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, '超级管理员', 'root', 0, '0', '0', 1, '2022-11-06 22:47:30', 1, '2022-11-16 14:30:24');
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (25, '测试', 'test', 1, '0', '1', 1, '2022-11-08 19:48:07', 1, '2022-11-08 23:48:41');
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (26, '测试2', 'test', 2, '0', '1', 1, '2022-11-08 19:49:24', 1, '2022-11-09 09:09:44');
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (27, '测试3', 'test', 3, '0', '1', 1, '2022-11-08 19:50:16', 1, '2022-11-09 09:10:02');
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (28, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:27', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (29, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:29', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (30, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:32', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (31, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:35', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (32, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:38', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (33, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:43', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (34, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:46', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (35, '1', '1', 0, '0', '1', 1, '2022-11-09 09:21:55', NULL, NULL);
INSERT INTO `role` (`id`, `role_name`, `role_key`, `role_sort`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (36, '普通用户', 'user', 1, '0', '0', 1, '2022-11-13 17:07:11', 1, '2022-11-15 09:10:06');
COMMIT;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
BEGIN;
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (25, 1);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (25, 2);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (25, 3);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (25, 2095);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 3);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2095);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2097);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 1);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2101);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2105);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2109);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2110);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (36, 2111);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 1);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 3);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2098);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2099);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2100);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2101);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2095);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2102);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2103);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2104);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2105);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2097);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2106);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2107);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2108);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2109);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2110);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2111);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2112);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2113);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2114);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2115);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2116);
INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES (1, 2117);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `create_by` bigint DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14787164048681 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `user_name`, `password`, `nick_name`, `status`, `avatar`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (1, 'admin@qq.com', '123456', '大只', '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` (`id`, `user_name`, `password`, `nick_name`, `status`, `avatar`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (2, 'test@qq.com', '123456', '测试', '0', NULL, 1, '2022-11-13 16:05:49', 1, '2022-11-15 09:10:19', 0);
INSERT INTO `user` (`id`, `user_name`, `password`, `nick_name`, `status`, `avatar`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (14787164048678, 'test@qq', '123456', '测试02', '0', NULL, 1, '2022-11-13 22:32:02', NULL, NULL, 0);
INSERT INTO `user` (`id`, `user_name`, `password`, `nick_name`, `status`, `avatar`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (14787164048679, '13123123', '312312312', '测试03', '0', NULL, 1, '2022-11-13 22:33:13', 1, '2022-11-14 20:51:30', 1);
INSERT INTO `user` (`id`, `user_name`, `password`, `nick_name`, `status`, `avatar`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES (14787164048680, '23123213123', 'ba34114299719f15b1bdd6498322f0fa', '测试', '0', NULL, 1, '2022-11-16 15:51:05', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (14787164048678, 1);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (14787164048678, 36);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (14787164048679, 36);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (2, 36);
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (14787164048680, 36);
COMMIT;

-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `open_id` varchar(255) DEFAULT NULL COMMENT '微信开放id',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of wx_user
-- ----------------------------
BEGIN;
INSERT INTO `wx_user` (`id`, `nick_name`, `avatar_url`, `open_id`, `status`, `create_time`) VALUES (1, '大只', 'https://thirdwx.qlogo.cn/mmopen/vi_32/AtZRPN7R7KC19k4gSBicCE3splRHbFqWzzsyYetbRaDoAOkiccRnpA6wicrxPeCqQP3UGUL6IbsAFzAtkNoeo6KPw/132', 'oG8dg5HAeGxGA8G6Nc4Snmgzne04', '0', '2022-11-15 00:00:00');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
