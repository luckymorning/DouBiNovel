/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : novel

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 13/12/2019 22:51:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_authority
-- ----------------------------
DROP TABLE IF EXISTS `m_authority`;
CREATE TABLE `m_authority`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `group_id` bigint(20) NULL DEFAULT NULL,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1031 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_authority
-- ----------------------------
INSERT INTO `m_authority` VALUES (1000, '用户管理', 'USER_VIEW', '查看', 1000, '2019-11-25 20:22:54', '2019-11-25 20:22:54', '查看用户权限');
INSERT INTO `m_authority` VALUES (1001, '用户管理', 'USER_ADD', '添加', 1000, '2019-11-30 12:02:26', '2019-11-30 12:08:42', '用户添加权限');
INSERT INTO `m_authority` VALUES (1002, '用户管理', 'USER_DELETE', '删除', 1000, '2019-12-05 17:49:55', '2019-12-05 17:49:55', '删除用户权限');
INSERT INTO `m_authority` VALUES (1003, '用户管理', 'USER_UPDATE', '修改', 1000, '2019-12-05 17:50:12', '2019-12-05 17:50:12', '修改用户权限');
INSERT INTO `m_authority` VALUES (1004, '权限管理', 'AUTHORITY_VIEW', '查看', 1001, '2019-12-05 17:51:29', '2019-12-05 17:51:29', '权限查看');
INSERT INTO `m_authority` VALUES (1005, '权限管理', 'AUTHORITY_ADD', '添加', 1001, '2019-12-05 17:51:42', '2019-12-05 17:51:42', '');
INSERT INTO `m_authority` VALUES (1006, '权限管理', 'AUTHORITY_DELETE', '删除', 1001, '2019-12-05 17:51:57', '2019-12-05 17:51:57', '');
INSERT INTO `m_authority` VALUES (1007, '权限管理', 'AUTHORITY_UPDATE', '修改', 1001, '2019-12-05 17:52:08', '2019-12-05 17:52:08', '');
INSERT INTO `m_authority` VALUES (1008, '权限组管理', 'AUTHORITY_GROUP_VIEW', '查看', 1002, '2019-12-05 17:52:38', '2019-12-05 17:52:38', '');
INSERT INTO `m_authority` VALUES (1009, '权限组管理', 'AUTHORITY_GROUP_ADD', '添加', 1002, '2019-12-05 17:52:49', '2019-12-05 17:52:49', '');
INSERT INTO `m_authority` VALUES (1010, '权限组管理', 'AUTHORITY_GROUP_DELETE', '删除', 1002, '2019-12-05 17:52:58', '2019-12-05 17:52:58', '');
INSERT INTO `m_authority` VALUES (1011, '权限组管理', 'AUTHORITY_GROUP_UPDATE', '修改', 1002, '2019-12-05 17:53:11', '2019-12-05 17:53:11', '');
INSERT INTO `m_authority` VALUES (1012, '角色管理', 'ROLE_VIEW', '查看', 1003, '2019-12-05 17:55:06', '2019-12-05 17:55:06', '角色查看权限');
INSERT INTO `m_authority` VALUES (1013, '角色管理', 'ROLE_ADD', '添加', 1003, '2019-12-05 17:55:20', '2019-12-05 17:55:20', '角色添加权限');
INSERT INTO `m_authority` VALUES (1014, '角色管理', 'ROLE_DELETE', '删除', 1003, '2019-12-05 17:55:33', '2019-12-05 17:55:33', '角色删除权限');
INSERT INTO `m_authority` VALUES (1015, '角色管理', 'ROLE_UPDATE', '修改', 1003, '2019-12-05 17:55:49', '2019-12-05 17:55:49', '角色修改权限');
INSERT INTO `m_authority` VALUES (1016, '平台参数管理', 'SYSTEM_SETTING_VIEW', '查看', 1004, '2019-12-05 17:56:45', '2019-12-05 17:56:45', '平台参数查看权限');
INSERT INTO `m_authority` VALUES (1017, '平台参数管理', 'SYSTEM_SETTING_UPDATE', '修改', 1004, '2019-12-05 17:56:58', '2019-12-05 17:56:58', '平台参数修改权限');
INSERT INTO `m_authority` VALUES (1018, '更新日志管理', 'UPDATE_LOG_VIEW', '查看', 1005, '2019-12-05 17:58:20', '2019-12-05 17:58:20', '更新日志查看权限');
INSERT INTO `m_authority` VALUES (1019, '更新日志管理', 'UPDATE_LOG_ADD', '添加', 1005, '2019-12-05 17:58:34', '2019-12-05 17:58:34', '更新日志添加权限');
INSERT INTO `m_authority` VALUES (1020, '更新日志管理', 'UPDATE_LOG_DELETE', '删除', 1005, '2019-12-05 17:58:51', '2019-12-05 17:58:51', '更新日志删除权限');
INSERT INTO `m_authority` VALUES (1021, '更新日志管理', 'UPDATE_LOG_UPDATE', '修改', 1005, '2019-12-05 17:59:11', '2019-12-05 17:59:11', '更新日志修改权限');
INSERT INTO `m_authority` VALUES (1022, '书源管理', 'BOOK_SOURCE_VIEW', '查看', 1006, '2019-12-05 18:00:01', '2019-12-05 18:00:01', '书源查看权限');
INSERT INTO `m_authority` VALUES (1023, '书源管理', 'BOOK_SOURCE_ADD', '添加', 1006, '2019-12-05 18:00:17', '2019-12-05 18:00:17', '书源添加权限');
INSERT INTO `m_authority` VALUES (1024, '书源管理', 'BOOK_SOURCE_DELETE', '删除', 1006, '2019-12-05 18:00:29', '2019-12-05 18:00:29', '书源删除权限');
INSERT INTO `m_authority` VALUES (1025, '书源管理', 'BOOK_SOURCE_UPDATE', '修改', 1006, '2019-12-05 18:00:41', '2019-12-05 18:00:41', '书源修改权限');
INSERT INTO `m_authority` VALUES (1026, '捐赠管理', 'DONATE_VIEW', '查看', 1007, '2019-12-09 17:54:47', '2019-12-09 17:54:47', '捐赠查看权限');
INSERT INTO `m_authority` VALUES (1027, '捐赠管理', 'DONATE_ADD', '添加', 1007, '2019-12-09 17:55:28', '2019-12-09 17:55:28', '捐赠添加权限');
INSERT INTO `m_authority` VALUES (1028, '捐赠管理', 'DONATE_DELETE', '删除', 1007, '2019-12-09 17:55:46', '2019-12-09 17:55:46', '捐赠删除权限');
INSERT INTO `m_authority` VALUES (1029, '捐赠管理', 'DONATE_UPDATE', '修改', 1007, '2019-12-09 17:56:02', '2019-12-09 17:56:02', '捐赠修改权限');
INSERT INTO `m_authority` VALUES (1030, '平台权限', 'ADMIN_VIEW', '后台进入权限', 1008, '2019-12-13 22:36:16', '2019-12-13 22:36:16', '后台页面访问权限');

-- ----------------------------
-- Table structure for m_authority_group
-- ----------------------------
DROP TABLE IF EXISTS `m_authority_group`;
CREATE TABLE `m_authority_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1009 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_authority_group
-- ----------------------------
INSERT INTO `m_authority_group` VALUES (1000, '用户管理', '2019-11-25 20:22:00', '2019-11-25 20:22:00', '用户管理权限组');
INSERT INTO `m_authority_group` VALUES (1001, '权限管理', '2019-11-28 11:47:18', '2019-12-05 17:50:36', '权限管理');
INSERT INTO `m_authority_group` VALUES (1002, '权限组管理', '2019-12-05 17:50:50', '2019-12-05 17:50:50', '权限组管理');
INSERT INTO `m_authority_group` VALUES (1003, '角色管理', '2019-12-05 17:54:31', '2019-12-05 17:54:31', '角色管理权限');
INSERT INTO `m_authority_group` VALUES (1004, '平台参数管理', '2019-12-05 17:56:18', '2019-12-05 17:57:43', '平台参数权限');
INSERT INTO `m_authority_group` VALUES (1005, '更新日志管理', '2019-12-05 17:57:26', '2019-12-05 17:58:03', '更新日志管理权限');
INSERT INTO `m_authority_group` VALUES (1006, '书源管理', '2019-12-05 17:59:25', '2019-12-05 17:59:25', '书源管理权限');
INSERT INTO `m_authority_group` VALUES (1007, '捐赠管理', '2019-12-09 17:53:36', '2019-12-09 17:53:36', '捐赠管理权限');
INSERT INTO `m_authority_group` VALUES (1008, '平台权限', '2019-12-13 22:35:40', '2019-12-13 22:35:40', '');

-- ----------------------------
-- Table structure for m_book_info
-- ----------------------------
DROP TABLE IF EXISTS `m_book_info`;
CREATE TABLE `m_book_info`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_source_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_source_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_img_error` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `novel_des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `novel_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_update` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_new` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for m_book_source
-- ----------------------------
DROP TABLE IF EXISTS `m_book_source`;
CREATE TABLE `m_book_source`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `base_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image_error` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `search_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `search_result_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_name_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_url_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_image_url_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_des_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_author_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_type_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_last_update_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result_item_book_last_new_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_book_name_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_book_author_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_book_last_update_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_book_last_new_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_book_des_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_book_image_url_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_catalog_list_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_catalog_item_name_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_detail_catalog_item_link_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_content_catalog_name_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_content_name_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_content_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content_regex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_content_pre_catalog_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_content_catalog_list_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_content_next_catalog_selector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_book_source
-- ----------------------------
INSERT INTO `m_book_source` VALUES (1, '新笔趣阁', 'https://www.xsbiquge.com', '$(this).attr(\'src\', \'/imgs/nocover.jpg\')', '/search.php?keyword=%s', 'div.result-game-item', '.result-game-item-title-link', '.result-game-item-title-link', '.result-game-item-pic-link-img', '.result-game-item-desc', '.result-game-item-info-tag:eq(0) > span:eq(1)', '.result-game-item-info-tag:eq(1) > span:eq(1)', '.result-game-item-info-tag:eq(2) > span:eq(1)', '.result-game-item-info-tag:eq(3) > a', '#info > h1', '#info > p:eq(1)', '#info > p:eq(3)', '#info > p:eq(4) > a', '#intro', '#fmimg > img', '#list dd', 'a', 'a', '.bookname > h1', '.con_top > a:eq(3)', '#content', '[&]\\d*\\w*[;]*\\d*\\w*[;]', '.bottem2 > a:eq(0)', '.bottem2 > a:eq(1)', '.bottem2 > a:eq(2)', '2019-12-05 17:25:06', '2019-12-06 15:02:31');
INSERT INTO `m_book_source` VALUES (2, '顶点小说', 'https://www.dingdiann.com', '$(this).attr(\'src\', \'/imgs/nocover.jpg\')', '/searchbook.php?keyword=%s', '.novelslist2 li:gt(1)', '.s2 a', '.s2 a', '', '', '.s4', '.s1', '.s6', '.s3 a', '#info h1', '#info > p:eq(1)', '#info > p:eq(3)', '#info > p:eq(4) > a', '#intro', '#fmimg > img', '#list dd', 'a', 'a', '.bookname > h1', '.con_top > a:eq(2)', '#content', '', '.bottem2 > a:eq(1)', '.bottem2 > a:eq(2)', '.bottem2 > a:eq(3)', '2019-12-06 15:49:45', '2019-12-09 12:12:40');

-- ----------------------------
-- Table structure for m_donate
-- ----------------------------
DROP TABLE IF EXISTS `m_donate`;
CREATE TABLE `m_donate`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `donate_money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `donate_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `donate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_donate
-- ----------------------------
INSERT INTO `m_donate` VALUES (1, '*晨', '随便瞅瞅', '1RMB', '支付宝', '2019-12-09 17:46:11');

-- ----------------------------
-- Table structure for m_role
-- ----------------------------
DROP TABLE IF EXISTS `m_role`;
CREATE TABLE `m_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_super` int(11) NOT NULL DEFAULT 0,
  `authority` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `creator_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_role
-- ----------------------------
INSERT INTO `m_role` VALUES (1000, '超级管理员', '超级管理员', 1, '1000', '2019-11-25 19:52:48', '2019-11-25 19:53:22', 1);
INSERT INTO `m_role` VALUES (1001, '前端普通角色', '', 0, '', '2019-12-10 19:03:55', '2019-12-13 22:07:25', NULL);
INSERT INTO `m_role` VALUES (1002, '后台访客', '', 0, '1000,1004,1008,1012,1016,1018,1022,1026,1030', '2019-12-13 22:36:42', '2019-12-13 22:38:05', 1000);

-- ----------------------------
-- Table structure for m_system_setting
-- ----------------------------
DROP TABLE IF EXISTS `m_system_setting`;
CREATE TABLE `m_system_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sitename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_index_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `meta_keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `meta_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `copyright_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_system_setting
-- ----------------------------
INSERT INTO `m_system_setting` VALUES (1, '逗逼联盟 - 逗逼阅读', 'http://novel.luckymorning.cn', '逗逼阅读后台管理', '逗逼联盟,逗逼阅读,无广告,小说', '做最简洁，最干净的小说聚合网站', 'Copyright © 2019 逗逼联盟-逗逼阅读 All Rights Reserved.');

-- ----------------------------
-- Table structure for m_update_log
-- ----------------------------
DROP TABLE IF EXISTS `m_update_log`;
CREATE TABLE `m_update_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `update_des` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_update_log
-- ----------------------------
INSERT INTO `m_update_log` VALUES (1, '<p><span>完成初版网站，并上线，开启公测</span></p><p>目前数据源为：</p><p>1. <a target=\"_blank\" href=\"https://www.xbiquge6.com/\">新笔趣阁</a>、<a target=\"_blank\" href=\"https://www.xsbiquge.com/\">新笔趣阁</a></p><p>2. <a target=\"_blank\" href=\"https://www.dingdiann.com/\">顶点小说</a></p>', '2019-12-05 12:13:16', '2019-12-05 12:24:45');
INSERT INTO `m_update_log` VALUES (2, '<p>1.修改书源为动态书源</p><p>2.增加捐赠入口和捐赠列表</p><p><b>希望喜欢本网站的朋友能捐赠一下支持网站的基本运营</b></p>', '2019-12-09 17:58:04', '2019-12-09 17:58:04');

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat_token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `m_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `active` int(11) NOT NULL DEFAULT 1,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES (1000, '6c0d9e0d5389fae0af02cc0b0311348d', 'admin', 'e44b960b8f5893ef8cd8f54229527840', '超级管理员', '18512345678', NULL, NULL, 'lucky_morning@163.com', '整个平台超级管理员', NULL, 1, 1000, '2019-11-25 19:51:44', '2019-12-13 22:51:23');
INSERT INTO `m_user` VALUES (1001, '2159ee49373045fda115c748da17bf95', 'guest', 'e5be93e899d160f8c83692d222a3f367', '后台访客', '', NULL, NULL, '', '平台访客', NULL, 1, 1002, '2019-12-10 19:05:08', '2019-12-13 22:39:40');
INSERT INTO `m_user` VALUES (1002, '968dca2d516b4fbfa159d72cc9d8cc3e', '4bd14c4ee44c4c2f9d33e57dac4d188d', '0dac6c479a4ff030cb5eac6ca5ac9e83', '幸运小伙', NULL, NULL, NULL, '495709295@qq.com', '平台前端普通用户', NULL, 1, 1001, '2019-12-13 22:08:20', '2019-12-13 22:08:20');

SET FOREIGN_KEY_CHECKS = 1;
