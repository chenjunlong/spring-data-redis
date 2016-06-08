/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : demo_db

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-05-31 17:30:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MOBILE` varchar(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('222', '13121771001', 'test1001');
INSERT INTO `user` VALUES ('223', '13121771002', 'test1002');
INSERT INTO `user` VALUES ('224', '13121771003', 'test1003');
INSERT INTO `user` VALUES ('225', '13121771004', 'test1004');
INSERT INTO `user` VALUES ('226', '13121771005', 'test1005');
INSERT INTO `user` VALUES ('227', '13121771006', 'test1006');
INSERT INTO `user` VALUES ('228', '13121771007', 'test1007');
INSERT INTO `user` VALUES ('229', '13121771008', 'test1008');
INSERT INTO `user` VALUES ('230', '13121771009', 'test1009');
INSERT INTO `user` VALUES ('231', '13121771010', 'test1010');
