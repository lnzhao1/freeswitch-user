/*
 Navicat Premium Dump SQL

 Source Server         : 192.168.180.20
 Source Server Type    : MySQL
 Source Server Version : 50736 (5.7.36)
 Source Host           : 192.168.180.20:3306
 Source Schema         : freeswitch

 Target Server Type    : MySQL
 Target Server Version : 50736 (5.7.36)
 File Encoding         : 65001

 Date: 06/06/2025 16:33:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dial_tab
-- ----------------------------
DROP TABLE IF EXISTS `dial_tab`;
CREATE TABLE `dial_tab`  (
  `id` int(11) NOT NULL,
  `call_prefix` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被叫前缀',
  `gateway` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网关名称',
  `tranfer_prefix` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '转接前缀',
  `call_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主叫号码',
  `creat_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dial_tab
-- ----------------------------

-- ----------------------------
-- Table structure for sip
-- ----------------------------
DROP TABLE IF EXISTS `sip`;
CREATE TABLE `sip`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `pwd` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `creat_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sip
-- ----------------------------
INSERT INTO `sip` VALUES (1, '1000', '1234', NULL);
INSERT INTO `sip` VALUES (2, '1001', '1234', NULL);
INSERT INTO `sip` VALUES (3, '1002', '1234', NULL);
INSERT INTO `sip` VALUES (4, '1090', '1234', NULL);
INSERT INTO `sip` VALUES (5, '8088', '1234', NULL);
INSERT INTO `sip` VALUES (6, '253', '1234', NULL);
INSERT INTO `sip` VALUES (7, '80888', '1234', NULL);
INSERT INTO `sip` VALUES (8, '1081', '1234', NULL);

SET FOREIGN_KEY_CHECKS = 1;
