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

 Date: 12/02/2020 13:36:26
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
) ENGINE = InnoDB AUTO_INCREMENT = 1035 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `m_authority` VALUES (1031, '系统公告管理', 'SYSTEM_NOTIFICATION_Add', '增加', 1010, '2020-02-03 21:17:17', '2020-02-03 21:17:17', '');
INSERT INTO `m_authority` VALUES (1032, '系统公告管理', 'SYSTEM_NOTIFICATION_DELETE', '删除', 1010, '2020-02-03 21:17:41', '2020-02-03 21:17:41', '');
INSERT INTO `m_authority` VALUES (1033, '系统公告管理', 'SYSTEM_NOTIFICATION_UPDATE', '修改', 1010, '2020-02-03 21:17:56', '2020-02-03 21:17:56', '');
INSERT INTO `m_authority` VALUES (1034, '系统公告管理', 'SYSTEM_NOTIFICATION_VIEW', '查看', 1010, '2020-02-03 21:18:06', '2020-02-03 21:18:06', '');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1011 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `m_authority_group` VALUES (1009, '日志管理', '2019-12-18 17:00:36', '2019-12-18 17:00:36', '日志管理权限');
INSERT INTO `m_authority_group` VALUES (1010, '系统公告管理', '2020-02-03 21:16:35', '2020-02-03 21:16:35', '');

-- ----------------------------
-- Table structure for m_book_info
-- ----------------------------
DROP TABLE IF EXISTS `m_book_info`;
CREATE TABLE `m_book_info`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `book_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_source_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_source_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_img_error` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `novel_des` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `novel_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_update` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_new` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator_id` bigint(20) NULL DEFAULT NULL,
  `last_read_catalog_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_read_catalog_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `book_content_ads` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_book_source
-- ----------------------------
INSERT INTO `m_book_source` VALUES (1, '新笔趣阁', 'https://www.xsbiquge.com', '$(this).attr(\'src\', \'/imgs/nocover.jpg\')', '/search.php?keyword=%s', 'div.result-game-item', '.result-game-item-title-link', '.result-game-item-title-link', '.result-game-item-pic-link-img', '.result-game-item-desc', '.result-game-item-info-tag:eq(0) > span:eq(1)', '.result-game-item-info-tag:eq(1) > span:eq(1)', '.result-game-item-info-tag:eq(2) > span:eq(1)', '.result-game-item-info-tag:eq(3) > a', '#info > h1', '#info > p:eq(1)', '#info > p:eq(3)', '#info > p:eq(4) > a', '#intro', '#fmimg > img', '#list dd', 'a', 'a', '.bookname > h1', '.con_top > a:eq(3)', '#content', '[&]\\d*\\w*[;]*\\d*\\w*[;]', '.bottem2 > a:eq(0)', '.bottem2 > a:eq(1)', '.bottem2 > a:eq(2)', '一秒记住，精彩小说无弹窗免费阅读！<br>', '2019-12-05 17:25:06', '2019-12-18 16:10:47');
INSERT INTO `m_book_source` VALUES (2, '顶点小说', 'https://www.dingdiann.com', '$(this).attr(\'src\', \'/imgs/nocover.jpg\')', '/searchbook.php?keyword=%s', '.novelslist2 li:gt(1)', '.s2 a', '.s2 a', '', '', '.s4', '.s1', '.s6', '.s3 a', '#info h1', '#info > p:eq(1)', '#info > p:eq(3)', '#info > p:eq(4) > a', '#intro', '#fmimg > img', '#list dd', 'a', 'a', '.bookname > h1', '.con_top > a:eq(2)', '#content', '', '.bottem2 > a:eq(1)', '.bottem2 > a:eq(2)', '.bottem2 > a:eq(3)', NULL, '2019-12-06 15:49:45', '2019-12-09 12:12:40');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_donate
-- ----------------------------
INSERT INTO `m_donate` VALUES (2, '*黍', '黄蜀黍（52破解）', '1RMB', '微信', '2019-12-17 14:42:26');
INSERT INTO `m_donate` VALUES (3, '*黍', '黄蜀黍（52破解）', '2RMB', '微信', '2019-12-17 14:42:35');
INSERT INTO `m_donate` VALUES (4, '*黍', '黄蜀黍（52破解）', '1RMB', '微信', '2019-12-17 14:42:47');
INSERT INTO `m_donate` VALUES (5, '*黍', '黄蜀黍（52破解）', '1RMB', '微信', '2019-12-17 15:52:43');

-- ----------------------------
-- Table structure for m_login_log
-- ----------------------------
DROP TABLE IF EXISTS `m_login_log`;
CREATE TABLE `m_login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `login_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_type` int(11) NOT NULL DEFAULT 0,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_login_log
-- ----------------------------
INSERT INTO `m_login_log` VALUES (1, '超级管理员', 1000, '127.0.1.1', 0, '2019-12-18 17:35:36');
INSERT INTO `m_login_log` VALUES (2, '超级管理员', 1000, '127.0.1.1', 0, '2019-12-25 16:58:01');
INSERT INTO `m_login_log` VALUES (3, '超级管理员', 1000, '127.0.1.1', 0, '2019-12-25 16:59:01');
INSERT INTO `m_login_log` VALUES (4, '超级管理员', 1000, '127.0.1.1', 0, '2019-12-25 17:01:36');
INSERT INTO `m_login_log` VALUES (5, '超级管理员', 1000, '127.0.1.1', 0, '2019-12-25 17:02:54');
INSERT INTO `m_login_log` VALUES (6, '超级管理员', 1000, '127.0.1.1', 1, '2019-12-25 17:03:24');
INSERT INTO `m_login_log` VALUES (7, '超级管理员', 1000, '127.0.1.1', 0, '2019-12-25 17:05:20');
INSERT INTO `m_login_log` VALUES (8, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-26 17:21:17');
INSERT INTO `m_login_log` VALUES (9, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-26 17:37:20');
INSERT INTO `m_login_log` VALUES (10, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-26 17:40:29');
INSERT INTO `m_login_log` VALUES (11, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-27 09:36:35');
INSERT INTO `m_login_log` VALUES (12, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-27 09:42:14');
INSERT INTO `m_login_log` VALUES (13, '超级管理员', 1000, '127.0.1.1', 0, '2019-12-27 09:44:04');
INSERT INTO `m_login_log` VALUES (14, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-27 09:47:23');
INSERT INTO `m_login_log` VALUES (15, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-31 16:54:34');
INSERT INTO `m_login_log` VALUES (16, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-31 17:00:23');
INSERT INTO `m_login_log` VALUES (17, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-31 17:06:10');
INSERT INTO `m_login_log` VALUES (18, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-31 17:11:10');
INSERT INTO `m_login_log` VALUES (19, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-31 17:31:13');
INSERT INTO `m_login_log` VALUES (20, '幸运小伙', 1002, '127.0.1.1', 1, '2019-12-31 17:32:36');
INSERT INTO `m_login_log` VALUES (21, '超级管理员', 1000, '192.168.0.108', 1, '2020-02-03 14:19:04');
INSERT INTO `m_login_log` VALUES (22, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 14:46:32');
INSERT INTO `m_login_log` VALUES (23, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 15:01:17');
INSERT INTO `m_login_log` VALUES (24, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 15:08:35');
INSERT INTO `m_login_log` VALUES (25, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 15:09:14');
INSERT INTO `m_login_log` VALUES (26, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 18:48:09');
INSERT INTO `m_login_log` VALUES (27, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 18:57:27');
INSERT INTO `m_login_log` VALUES (28, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 20:47:53');
INSERT INTO `m_login_log` VALUES (29, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 20:50:55');
INSERT INTO `m_login_log` VALUES (30, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 21:00:04');
INSERT INTO `m_login_log` VALUES (31, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 21:03:07');
INSERT INTO `m_login_log` VALUES (32, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 21:08:55');
INSERT INTO `m_login_log` VALUES (33, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 21:11:29');
INSERT INTO `m_login_log` VALUES (34, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 21:13:09');
INSERT INTO `m_login_log` VALUES (35, '超级管理员', 1000, '192.168.0.108', 0, '2020-02-03 21:15:06');

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
INSERT INTO `m_role` VALUES (1002, '后台访客', '', 0, '1004,1008,1012,1016,1018,1022,1026,1030,1034', '2019-12-13 22:36:42', '2020-02-03 21:18:52', 1000);

-- ----------------------------
-- Table structure for m_system_notification
-- ----------------------------
DROP TABLE IF EXISTS `m_system_notification`;
CREATE TABLE `m_system_notification`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of m_update_log
-- ----------------------------
INSERT INTO `m_update_log` VALUES (1, '<p><span>完成初版网站，并上线，开启公测</span></p><p>目前数据源为：</p><p>1. <a target=\"_blank\" href=\"https://www.xbiquge6.com/\">新笔趣阁</a>、<a target=\"_blank\" href=\"https://www.xsbiquge.com/\">新笔趣阁</a></p><p>2. <a target=\"_blank\" href=\"https://www.dingdiann.com/\">顶点小说</a></p>', '2019-12-05 12:13:16', '2019-12-05 12:24:45');
INSERT INTO `m_update_log` VALUES (2, '<p>1.修改书源为动态书源</p><p>2.增加捐赠入口和捐赠列表</p><p><b>希望喜欢本网站的朋友能捐赠一下支持网站的基本运营</b></p>', '2019-12-09 17:58:04', '2019-12-09 17:58:04');
INSERT INTO `m_update_log` VALUES (3, '<p>1.新增本地缓存，书籍目录页面可点击<b>继续阅读</b>进入上次阅读界面</p><p>2.增加登录状态记录功能</p><p><br></p>', '2019-12-25 17:09:07', '2019-12-25 17:09:07');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES (1000, '6c0d9e0d5389fae0af02cc0b0311348d', 'admin', 'e44b960b8f5893ef8cd8f54229527840', '超级管理员', '18512345678', NULL, NULL, 'lucky_morning@163.com', '整个平台超级管理员', NULL, 1, 1000, '2019-11-25 19:51:44', '2019-12-13 22:51:23');
INSERT INTO `m_user` VALUES (1001, '2159ee49373045fda115c748da17bf95', 'guest', 'e5be93e899d160f8c83692d222a3f367', '后台访客', '', NULL, NULL, '', '平台访客', NULL, 1, 1002, '2019-12-10 19:05:08', '2019-12-13 22:39:40');

SET FOREIGN_KEY_CHECKS = 1;
