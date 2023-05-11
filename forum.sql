/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : forum

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 11/05/2023 17:29:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id评论id',
  `pid` int(11) NOT NULL COMMENT '帖子id',
  `floor` int(11) NULL DEFAULT NULL COMMENT '楼层',
  `reply_id` int(11) NOT NULL COMMENT '回复人',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '回复时间',
  `is_delete` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `likes` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 2, 1, '这是回复的内容', '2023-03-14 19:31:52', '1', 1);
INSERT INTO `comment` VALUES (2, 2, 2, 2, '<h1>hjll😀</h1><blockquote>的风格和计划经济</blockquote><p>是德国<img src=\"http://localhost:8080/image/220ec84dc69d47dbad55d44beec83bc0.png\" alt=\"\" data-href=\"http://localhost:8080/image/220ec84dc69d47dbad55d44beec83bc0.png\" style=\"\"/></p><p>这是回复的内容</<p>这是回复的内容</p><p>这是回复的内容</p><p>这是回复的内容</p><p>这是回复的内容</p>', '2023-03-29 18:33:42', '1', 1);
INSERT INTO `comment` VALUES (3, 2, 3, 3, '333333', '2023-03-29 18:18:08', '1', 1);
INSERT INTO `comment` VALUES (4, 2, 4, 2, '4444444444', '2023-03-29 18:19:40', '1', 1);
INSERT INTO `comment` VALUES (5, 3, 2, 2, '32464656', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (12, 2, 5, 1, 'test add comment new', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (16, 15, 2, 3, '<p>😉😅😨</p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (25, 15, 11, 3, '<p><img src=\"http://localhost:8080/image/c234b39a725746dcb09e6afbc1b86117.png\" alt=\"\" data-href=\"http://localhost:8080/image/c234b39a725746dcb09e6afbc1b86117.png\" style=\"\"/></p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (32, 15, 6, 3, '<p>sb</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (33, 15, 12, 3, '<p>sb</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (34, 15, 13, 3, '<p>ttt</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (35, 15, 14, 3, '<p>db</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (36, 16, 2, 2, '<p><img src=\"http://localhost:8080/image/65bde2e0669c474a997a75729e441108.jpg\" alt=\"\" data-href=\"http://localhost:8080/image/65bde2e0669c474a997a75729e441108.jpg\" style=\"\"/></p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (37, 16, 3, 2, '<p><img src=\"http://localhost:8080/image/dfd399d15cab444aac67c43b3972cba5.png\" alt=\"\" data-href=\"http://localhost:8080/image/dfd399d15cab444aac67c43b3972cba5.png\" style=\"\"/></p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (39, 4, 2, 2, '<blockquote>sssssssssssssssss</blockquote>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (40, 4, 3, 2, '<p>fgjjj</p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (41, 2, 6, 1, '<p>测试分页用</p>', '2023-04-15 01:24:18', '1', 1);
INSERT INTO `comment` VALUES (42, 2, 7, 1, '<p>测试分页用</p>', '2023-04-15 01:24:15', '1', 1);
INSERT INTO `comment` VALUES (43, 2, 8, 1, '<p>测试分页用</p>', '2023-04-15 01:24:12', '1', 1);
INSERT INTO `comment` VALUES (44, 2, 9, 1, '<p>测试分页用</p>', '2023-04-15 01:24:09', '1', 1);
INSERT INTO `comment` VALUES (45, 2, 10, 1, '<p>测试分页用</p>', '2023-04-15 01:24:07', '1', 1);
INSERT INTO `comment` VALUES (46, 2, 11, 1, '<p>测试分页用</p>', '2023-04-15 01:24:05', '1', 1);
INSERT INTO `comment` VALUES (47, 15, 14, 1, '<p>杀杀杀</p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (48, 15, 15, 1, '<p>杀杀杀</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (49, 15, 15, 1, '<p>上手上手</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (50, 15, 15, 1, '<p>三十分</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (51, 15, 15, 1, '<p>施工方</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (52, 15, 15, 2, '<p>房价高</p>', '2023-03-14 19:31:58', '1', 1);
INSERT INTO `comment` VALUES (53, 15, 16, 2, '<p>你。 </p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (54, 19, 2, 2, '<p>123</p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (55, 1, 3, 1, '<p>测试帖子回复时间是否更新</p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (56, 10, 2, 1, '<p>呃呃</p>', '2023-03-14 19:31:58', '0', 1);
INSERT INTO `comment` VALUES (57, 26, 2, 2, '<p>test</p>', '2023-03-18 15:13:24', '0', NULL);
INSERT INTO `comment` VALUES (58, 27, 2, 2, '<p>回复😀</p>', '2023-03-18 15:18:27', '0', NULL);
INSERT INTO `comment` VALUES (59, 17, 2, 2, '<p>ee</p>', '2023-03-18 20:46:25', '0', NULL);
INSERT INTO `comment` VALUES (60, 2, 12, 1, 'test', '2023-04-15 01:24:03', '1', NULL);
INSERT INTO `comment` VALUES (61, 15, 17, 1, '<p> </p>', '2023-04-14 20:21:21', '1', 0);
INSERT INTO `comment` VALUES (62, 15, 17, 1, '<p> </p>', '2023-04-14 20:24:38', '1', 0);
INSERT INTO `comment` VALUES (63, 15, 17, 1, '<p> &nbsp;</p>', '2023-04-14 20:26:59', '1', 0);
INSERT INTO `comment` VALUES (64, 15, 17, 1, '<p> </p>', '2023-04-14 20:27:03', '1', 0);
INSERT INTO `comment` VALUES (65, 15, 17, 1, '<p> </p>', '2023-04-14 20:27:28', '1', 0);
INSERT INTO `comment` VALUES (66, 15, 17, 1, '<p> </p>', '2023-04-14 20:27:49', '1', 0);
INSERT INTO `comment` VALUES (67, 15, 17, 1, '<p> &nbsp;</p>', '2023-04-14 20:29:24', '1', 0);
INSERT INTO `comment` VALUES (68, 15, 17, 1, '<p> </p>', '2023-04-14 20:34:12', '1', 0);
INSERT INTO `comment` VALUES (69, 15, 17, 1, '<p> &nbsp;</p>', '2023-04-14 20:34:28', '1', 0);
INSERT INTO `comment` VALUES (70, 15, 17, 1, '<p> &nbsp;</p>', '2023-04-14 20:39:17', '1', 0);
INSERT INTO `comment` VALUES (71, 15, 17, 1, '<p> &nbsp;</p>', '2023-04-14 20:40:48', '1', 0);
INSERT INTO `comment` VALUES (72, 15, 17, 1, '<p> &nbsp;</p>', '2023-04-14 20:44:18', '0', 0);
INSERT INTO `comment` VALUES (73, 15, 18, 1, '<p> </p>', '2023-04-14 20:46:08', '1', 0);
INSERT INTO `comment` VALUES (74, 15, 19, 1, '<p> &nbsp;</p>', '2023-04-14 20:45:07', '1', 0);
INSERT INTO `comment` VALUES (75, 15, 19, 1, '<p> &nbsp; &nbsp;1</p>', '2023-04-14 20:46:05', '1', 0);
INSERT INTO `comment` VALUES (76, 4, 4, 19, '<p>hhh</p>', '2023-04-14 22:12:55', '0', 0);
INSERT INTO `comment` VALUES (77, 2, 6, 12, '<p>hao</p>', '2023-04-15 15:32:45', '0', 0);
INSERT INTO `comment` VALUES (78, 33, 2, 12, '<p>呃呃</p>', '2023-04-15 15:33:10', '0', 0);
INSERT INTO `comment` VALUES (79, 35, 2, 12, '<p>haha</p>', '2023-04-15 22:30:55', '0', 0);
INSERT INTO `comment` VALUES (80, 21, 2, 2, '<p>hhh</p>', '2023-04-15 22:32:17', '0', 0);
INSERT INTO `comment` VALUES (81, 42, 2, 1, '<p>iaho</p>', '2023-04-16 09:44:08', '0', 0);

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NULL DEFAULT NULL,
  `cid` int(11) NULL DEFAULT NULL,
  `sub_id` int(11) NULL DEFAULT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of like
-- ----------------------------
INSERT INTO `like` VALUES (1, 1, NULL, NULL, 1);
INSERT INTO `like` VALUES (2, 2, NULL, NULL, 2);
INSERT INTO `like` VALUES (3, 1, NULL, NULL, 2);
INSERT INTO `like` VALUES (4, 1, NULL, NULL, 3);
INSERT INTO `like` VALUES (5, 2, NULL, NULL, 1);
INSERT INTO `like` VALUES (6, 2, NULL, NULL, 3);
INSERT INTO `like` VALUES (7, 3, NULL, NULL, 1);
INSERT INTO `like` VALUES (8, 3, NULL, NULL, 2);
INSERT INTO `like` VALUES (9, 3, NULL, NULL, 3);
INSERT INTO `like` VALUES (10, 17, NULL, NULL, 1);
INSERT INTO `like` VALUES (11, 8, NULL, NULL, 1);
INSERT INTO `like` VALUES (12, 5, NULL, NULL, 1);
INSERT INTO `like` VALUES (13, 12, NULL, NULL, 1);
INSERT INTO `like` VALUES (14, 21, NULL, NULL, 1);
INSERT INTO `like` VALUES (15, NULL, 1, NULL, 1);
INSERT INTO `like` VALUES (16, NULL, NULL, 1, 1);
INSERT INTO `like` VALUES (17, NULL, 2, NULL, 2);
INSERT INTO `like` VALUES (18, NULL, 2, NULL, 1);
INSERT INTO `like` VALUES (19, NULL, 3, NULL, 1);
INSERT INTO `like` VALUES (20, NULL, 4, NULL, 1);
INSERT INTO `like` VALUES (21, NULL, 12, NULL, 1);
INSERT INTO `like` VALUES (22, NULL, 45, NULL, 1);
INSERT INTO `like` VALUES (23, NULL, 44, NULL, 1);
INSERT INTO `like` VALUES (24, NULL, 16, NULL, 1);
INSERT INTO `like` VALUES (25, NULL, 53, NULL, 1);
INSERT INTO `like` VALUES (26, NULL, NULL, 8, 1);
INSERT INTO `like` VALUES (27, NULL, NULL, 9, 1);
INSERT INTO `like` VALUES (28, NULL, NULL, 5, 1);
INSERT INTO `like` VALUES (29, 27, NULL, NULL, 1);
INSERT INTO `like` VALUES (30, NULL, 57, NULL, 2);
INSERT INTO `like` VALUES (31, 26, NULL, NULL, 2);
INSERT INTO `like` VALUES (32, NULL, NULL, 32, 1);
INSERT INTO `like` VALUES (33, NULL, 40, NULL, 19);
INSERT INTO `like` VALUES (34, NULL, 12, NULL, 12);
INSERT INTO `like` VALUES (35, NULL, 80, NULL, 2);
INSERT INTO `like` VALUES (36, 21, NULL, NULL, 2);
INSERT INTO `like` VALUES (37, 42, NULL, NULL, 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_delete` int(11) NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `perm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '帖子管理', '/management/post', NULL, 0, '2022-11-07 22:26:19', 'system:post:manage');
INSERT INTO `menu` VALUES (3, '帖子发布', NULL, 'btnPostAdd', 0, '2023-03-03 22:50:29', 'system:post:post');
INSERT INTO `menu` VALUES (4, '帖子删除', NULL, 'btnPostDelete', 0, '2023-03-03 22:50:32', 'system:post:delete');
INSERT INTO `menu` VALUES (5, '帖子获取', NULL, NULL, 0, '2023-03-03 22:50:34', 'system:post:get');
INSERT INTO `menu` VALUES (6, '回复获取', NULL, NULL, 0, '2023-03-03 22:50:37', 'system:comment:get');
INSERT INTO `menu` VALUES (7, '回复删除', NULL, 'btnCommentDelete', 0, '2023-03-03 22:50:39', 'system:comment:delete');
INSERT INTO `menu` VALUES (8, '回复发布', NULL, 'btnCommentAdd', 0, '2023-03-03 22:50:43', 'system:comment:post');
INSERT INTO `menu` VALUES (9, '邮箱获取', NULL, NULL, 0, '2023-03-03 22:50:46', 'system:email:get');
INSERT INTO `menu` VALUES (10, '图像上传', NULL, NULL, 0, '2023-03-03 22:50:48', 'system:image:upload');
INSERT INTO `menu` VALUES (11, '后台管理', '/management/home', NULL, 0, '2023-03-05 01:15:07', 'system:management');
INSERT INTO `menu` VALUES (12, '首页', '/home', NULL, 0, '2023-03-05 01:15:40', 'system:front');
INSERT INTO `menu` VALUES (14, '用户管理', '/management/user', NULL, 0, '2023-03-05 16:28:42', 'system:user:manage');
INSERT INTO `menu` VALUES (15, '角色管理', '/management/role', NULL, 0, '2023-04-14 23:21:42', 'system:role:manage');
INSERT INTO `menu` VALUES (16, '权限菜单管理', '/management/menu', NULL, 0, '2023-04-14 23:24:38', 'system:menu:manage');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `uid` int(11) NULL DEFAULT NULL COMMENT '楼主',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发帖时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一条回复时间',
  `is_top` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否置顶，1为置顶',
  `is_delete` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除，1为删除',
  `type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `likes` bigint(20) NULL DEFAULT NULL COMMENT '点赞数',
  `views` bigint(20) NULL DEFAULT NULL COMMENT '浏览量',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, 1, 'Test title', '这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容', '2022-11-05 20:57:29', '2023-04-14 21:04:05', '0', '1', '学习', 3, 15);
INSERT INTO `post` VALUES (2, 1, '这是第二条帖子', '111111111111这是帖子内容这是帖子内容这是帖子内容', '2022-11-12 22:40:27', '2023-04-15 22:36:30', '0', '0', '生活', 3, 56977);
INSERT INTO `post` VALUES (3, 3, '三三三三', 'ああああああ这是帖子内容这是帖子内容这是帖子内容', '2022-11-12 22:41:31', '2023-04-15 01:22:15', '0', '0', '生活', 3, 26);
INSERT INTO `post` VALUES (4, 1, 'si', '这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容这是帖子内容', '2022-11-12 23:55:10', '2023-04-16 09:45:08', '0', '0', '学习', 0, 3);
INSERT INTO `post` VALUES (5, 2, '呃呃呃呃呃呃呃', '水水水水水水水水水水', '2022-11-15 22:18:52', '2023-04-15 22:36:40', '0', '0', '学习', 0, 5);
INSERT INTO `post` VALUES (6, 2, 'dghgjjhj', 'dasfgdg', '2022-11-15 22:21:54', '2023-04-15 22:35:07', '0', '1', '学习', 0, 3);
INSERT INTO `post` VALUES (7, 3, 'ghklllk;', 'fgdkmkjk', '2022-11-15 22:22:07', '2023-04-14 22:41:19', '0', '1', '学习', 0, 3);
INSERT INTO `post` VALUES (8, 1, 'jhlk;l;l;', 'awtretrytytyyu', '2022-11-15 22:22:28', '2023-03-15 00:40:55', '0', '0', '学习', 0, 3);
INSERT INTO `post` VALUES (9, 3, 'gfhkjkhkl', 'jklkjlkjl;kj;', '2022-11-15 22:22:37', '2023-03-18 19:15:15', '0', '0', '欢乐', 0, 7);
INSERT INTO `post` VALUES (10, 3, 'sdgg', 'dfhgfjj', '2022-12-09 18:48:51', '2023-03-15 00:40:35', '0', '0', '欢乐', 0, 2);
INSERT INTO `post` VALUES (11, 1, 'dhjh', 'fgjjj', '2022-12-09 18:49:00', '2023-03-29 18:33:04', '0', '1', '欢乐', 0, 2);
INSERT INTO `post` VALUES (12, 2, 'fdgjjk', 'hfgjkkk', '2022-12-09 18:49:09', '2023-03-15 00:41:00', '0', '0', '欢乐', 0, 6);
INSERT INTO `post` VALUES (13, 3, 'fhgjhk', '<h1>hjll😀</h1>', '2022-12-11 17:54:41', '2023-03-14 19:30:00', '0', '1', '欢乐', 1, 1);
INSERT INTO `post` VALUES (14, 3, 'fhgjhk', '<h1>hjll😀</h1><blockquote>的风格和计划经济</blockquote><p>是德国<img src=\"http://localhost:8080/image/220ec84dc69d47dbad55d44beec83bc0.png\" alt=\"\" data-href=\"http://localhost:8080/image/220ec84dc69d47dbad55d44beec83bc0.png\" style=\"\"/></p>', '2022-12-11 17:56:07', '2023-03-14 19:30:00', '0', '1', '欢乐', 1, 1);
INSERT INTO `post` VALUES (15, 3, '测试富文本', '<h1><u><em>测试</em></u></h1><blockquote>😘😘😘</blockquote><p><img src=\"http://localhost:8080/image/a45c955eacff4c56b5505ae0daa22905.png\" alt=\"\" data-href=\"http://localhost:8080/image/a45c955eacff4c56b5505ae0daa22905.png\" style=\"\"/></p>', '2022-12-11 18:00:33', '2023-04-16 08:53:05', '1', '0', '欢乐', 0, 99);
INSERT INTO `post` VALUES (16, 3, 'test Redis cache', '<p>😤😤😤</p>', '2023-01-06 16:08:52', '2023-04-15 01:22:45', '0', '0', '生活', 0, 30);
INSERT INTO `post` VALUES (17, 3, '是v', '<p>是否</p>', '2023-01-06 16:15:37', '2023-04-14 22:40:07', '0', '0', '学习', 1, 7);
INSERT INTO `post` VALUES (18, 3, 'fdghj', '<p>h,</p>', '2023-02-28 19:51:58', '2023-03-14 19:30:00', '0', '1', '学习', 1, 1);
INSERT INTO `post` VALUES (19, 3, 'dnlm', '<p><img src=\"http://localhost:8080/image/ad6088ddbc494ea780cebcd6e42ff2f1.png\" alt=\"\" data-href=\"http://localhost:8080/image/ad6088ddbc494ea780cebcd6e42ff2f1.png\" style=\"\"/></p>', '2023-03-01 23:53:24', '2023-03-29 18:24:40', '0', '0', '学习', 0, 5);
INSERT INTO `post` VALUES (20, 3, 'dnlm', '<p><img src=\"http://localhost:8080/image/ad6088ddbc494ea780cebcd6e42ff2f1.png\" alt=\"\" data-href=\"http://localhost:8080/image/ad6088ddbc494ea780cebcd6e42ff2f1.png\" style=\"\"/></p>', '2023-03-01 23:53:36', '2023-03-14 19:30:00', '0', '1', '学习', 1, 1);
INSERT INTO `post` VALUES (21, 1, 'postFromAdmin', '<p><img src=\"http://localhost:8080/image/2f8a4e42d01e4629ab378bcb867ae101.png\" alt=\"\" data-href=\"http://localhost:8080/image/2f8a4e42d01e4629ab378bcb867ae101.png\" style=\"\"/></p>', '2023-03-06 17:03:58', '2023-04-15 22:35:30', '0', '0', '其他', 0, 10);
INSERT INTO `post` VALUES (22, 1, '从后台发的贴', '<pre><code >    @PutMapping(\"/top\")\r\n    public Result changeIsTop(@RequestBody Post post){\r\n        System.out.println(post);\r\n        if (post.getIsTop().equals(\"0\")){\r\n            post.setIsTop(\"1\");\r\n        }else if (post.getIsTop().equals(\"1\")){\r\n            post.setIsTop(\"0\");\r\n        }\r\n        postService.updateById(post);\r\n        return Result.success(Code.SUCCESS.getCode(),\"\");\r\n    }</code></pre><p><br></p>', '2023-03-06 17:05:39', '2023-04-15 01:22:40', '0', '0', '其他', 0, 2);
INSERT INTO `post` VALUES (23, 1, '测试', '<pre><code class=\"language-markdown\">    addPost(){\r\n      let title = this.form.title\r\n      let type = this.form.type\r\n      let content = this.html\r\n      this.dialogFormVisible = false\r\n      this.request.post(\'/post\',{\"title\":title,\"content\":content,\"type\":type}).then((res) =&gt; {\r\n        if(res.code===2000){\r\n          this.$message({\r\n            type:\'success\',\r\n            message:\'发帖成功\'\r\n          })\r\n          this.load()\r\n        }\r\n      })\r\n    },</code></pre><p>js</p>', '2023-03-06 17:13:05', '2023-04-14 22:34:46', '0', '1', '学习', 0, 3);
INSERT INTO `post` VALUES (24, 1, '三', '<p>发送</p>', '2023-03-07 23:08:14', '2023-03-14 19:30:00', '0', '1', '欢乐', 1, 1);
INSERT INTO `post` VALUES (25, 1, '是', '<p>闪光灯</p>', '2023-03-07 23:11:37', '2023-03-14 19:30:00', '0', '1', '生活', 1, 1);
INSERT INTO `post` VALUES (26, 2, '测试发帖', '<p><img src=\"http://localhost:8080/image/c1fb2b5a673849eda3963e65f4b4e424.png\" alt=\"\" data-href=\"http://localhost:8080/image/c1fb2b5a673849eda3963e65f4b4e424.png\" style=\"\"/></p>', '2023-03-18 15:13:08', '2023-04-14 22:34:37', '0', '1', '其他', 0, 22);
INSERT INTO `post` VALUES (27, 2, '测试发帖', '<p><em>测试富文本</em></p><p><s>测试富文本</s></p><p><strong>测试富文本</strong></p><p><strong>😊😊😊</strong></p>', '2023-03-18 15:15:03', '2023-03-29 15:54:36', '0', '0', '其他', 0, 4);
INSERT INTO `post` VALUES (28, 2, 'sad', '<p><br></p>', '2023-03-29 13:51:55', '2023-04-14 22:34:46', '0', '1', '学习', 0, 0);
INSERT INTO `post` VALUES (29, 2, '是德国', '<p>是</p>', '2023-03-29 14:09:31', '2023-03-29 18:32:22', '0', '1', '学习', 0, 7);
INSERT INTO `post` VALUES (30, 1, '测试', '<p>啊啊啊啊</p>', '2023-03-29 17:28:15', '2023-03-29 17:28:15', '0', '0', '欢乐', 0, 1);
INSERT INTO `post` VALUES (31, 1, '测试啊啊啊啊', '<p>啊啊啊</p>', '2023-04-14 21:03:03', '2023-04-15 01:22:05', '0', '0', '生活', 0, 10);
INSERT INTO `post` VALUES (32, 1, '正确的', '<blockquote>111111111</blockquote>', '2023-04-14 21:04:33', '2023-04-14 21:04:33', '0', '0', '生活', 0, 1);
INSERT INTO `post` VALUES (33, 1, '111', '<p>😇</p>', '2023-04-14 21:22:42', '2023-04-15 15:33:15', '1', '0', '其他', 0, 4);
INSERT INTO `post` VALUES (34, 1, 'close-on-click-modal', '<p><br></p><pre><code >:close-on-click-modal=\"false\"</code></pre><p><img src=\"http://localhost:8080/image/2f073055f1d449479a4ae964ea583ae8.jpg\" alt=\"\" data-href=\"http://localhost:8080/image/2f073055f1d449479a4ae964ea583ae8.jpg\" style=\"\"/></p>', '2023-04-14 22:37:50', '2023-04-15 22:31:35', '0', '0', '其他', 0, 4);
INSERT INTO `post` VALUES (35, 12, '20230415', '<p>dabianqian</p>', '2023-04-15 22:30:38', '2023-04-15 22:31:15', '0', '0', '学习', 0, 2);
INSERT INTO `post` VALUES (36, 1, 'testdel', '<p>testdel</p>', '2023-04-15 22:36:49', '2023-04-15 22:36:53', '0', '1', '欢乐', 0, 1);
INSERT INTO `post` VALUES (37, 1, 'test', '<p> &nbsp;</p>', '2023-04-15 22:37:21', '2023-04-15 22:37:36', '1', '1', '生活', 0, 1);
INSERT INTO `post` VALUES (38, 1, 'ee', '<p><br></p>', '2023-04-15 22:51:28', '2023-04-15 22:58:04', '0', '1', '生活', 0, 1);
INSERT INTO `post` VALUES (39, 1, 'ee', '<p><br></p>', '2023-04-15 22:51:48', '2023-04-15 22:58:04', '0', '1', '生活', 0, 1);
INSERT INTO `post` VALUES (40, 1, 'sss', '<p> &nbsp;sdg </p>', '2023-04-15 22:56:32', '2023-04-15 22:58:04', '0', '1', '欢乐', 0, 1);
INSERT INTO `post` VALUES (41, 1, 'sdg', '<p>fgjh </p>', '2023-04-15 22:57:48', '2023-04-16 09:45:17', '0', '1', '欢乐', 0, 1);
INSERT INTO `post` VALUES (42, 1, '测试', '<p>啊啊啊啊</p>', '2023-04-16 09:43:47', '2023-04-16 09:45:07', '1', '0', '其他', 0, 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(11) NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', 0, '2022-11-07 22:25:00');
INSERT INTO `role` VALUES (2, '普通用户', 0, '2022-11-07 22:25:38');
INSERT INTO `role` VALUES (3, '管理员', 0, '2023-03-12 22:34:59');
INSERT INTO `role` VALUES (4, '测试用', 0, '2023-03-14 00:11:50');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1);
INSERT INTO `role_menu` VALUES (3, 1, 3);
INSERT INTO `role_menu` VALUES (4, 1, 4);
INSERT INTO `role_menu` VALUES (5, 1, 5);
INSERT INTO `role_menu` VALUES (6, 1, 6);
INSERT INTO `role_menu` VALUES (7, 1, 7);
INSERT INTO `role_menu` VALUES (8, 1, 8);
INSERT INTO `role_menu` VALUES (9, 1, 9);
INSERT INTO `role_menu` VALUES (10, 1, 10);
INSERT INTO `role_menu` VALUES (12, 2, 3);
INSERT INTO `role_menu` VALUES (14, 2, 5);
INSERT INTO `role_menu` VALUES (15, 2, 6);
INSERT INTO `role_menu` VALUES (16, 2, 7);
INSERT INTO `role_menu` VALUES (17, 2, 8);
INSERT INTO `role_menu` VALUES (18, 2, 9);
INSERT INTO `role_menu` VALUES (19, 2, 10);
INSERT INTO `role_menu` VALUES (20, 1, 11);
INSERT INTO `role_menu` VALUES (21, 1, 12);
INSERT INTO `role_menu` VALUES (22, 2, 12);
INSERT INTO `role_menu` VALUES (23, 3, 1);
INSERT INTO `role_menu` VALUES (24, 3, 3);
INSERT INTO `role_menu` VALUES (25, 3, 4);
INSERT INTO `role_menu` VALUES (26, 3, 5);
INSERT INTO `role_menu` VALUES (27, 3, 6);
INSERT INTO `role_menu` VALUES (28, 3, 7);
INSERT INTO `role_menu` VALUES (29, 3, 8);
INSERT INTO `role_menu` VALUES (30, 3, 9);
INSERT INTO `role_menu` VALUES (31, 3, 10);
INSERT INTO `role_menu` VALUES (32, 3, 11);
INSERT INTO `role_menu` VALUES (33, 3, 12);
INSERT INTO `role_menu` VALUES (37, 4, 1);
INSERT INTO `role_menu` VALUES (40, 4, 4);
INSERT INTO `role_menu` VALUES (41, 1, 15);
INSERT INTO `role_menu` VALUES (43, 2, 4);

-- ----------------------------
-- Table structure for sub_comment
-- ----------------------------
DROP TABLE IF EXISTS `sub_comment`;
CREATE TABLE `sub_comment`  (
  `sub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `root_id` int(11) NOT NULL COMMENT '从属评论的id',
  `reply_id` int(11) NOT NULL COMMENT '回复人的id',
  `target_id` int(11) NULL DEFAULT NULL COMMENT '被回复人的id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `id_deleted` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0：未删除，1：删除',
  `likes` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sub_comment
-- ----------------------------
INSERT INTO `sub_comment` VALUES (1, 1, 2, NULL, 'ceshi', '2023-03-06 23:16:44', '0', 1);
INSERT INTO `sub_comment` VALUES (2, 1, 2, 1, '测试', '2023-03-06 23:17:31', '0', 1);
INSERT INTO `sub_comment` VALUES (5, 16, 1, NULL, '222', '2023-03-07 01:02:25', '0', 1);
INSERT INTO `sub_comment` VALUES (6, 3, 2, 1, 's', '2023-03-07 18:13:19', '0', 1);
INSERT INTO `sub_comment` VALUES (9, 16, 1, NULL, '哈哈', '2023-03-07 21:27:34', '0', 1);
INSERT INTO `sub_comment` VALUES (11, 16, 1, NULL, '呃呃', '2023-03-07 21:37:02', '0', 1);
INSERT INTO `sub_comment` VALUES (12, 16, 1, 1, '测试', '2023-03-07 21:49:30', '0', 1);
INSERT INTO `sub_comment` VALUES (13, 16, 2, 1, '测试', '2023-03-07 22:00:53', '0', 1);
INSERT INTO `sub_comment` VALUES (14, 54, 2, NULL, '321', '2023-03-07 22:13:32', '0', 1);
INSERT INTO `sub_comment` VALUES (15, 54, 2, 2, '15415', '2023-03-07 22:13:41', '0', 1);
INSERT INTO `sub_comment` VALUES (16, 54, 2, 2, '213546', '2023-03-07 22:13:50', '0', 1);
INSERT INTO `sub_comment` VALUES (17, 54, 1, 2, '888', '2023-03-07 22:14:06', '0', 1);
INSERT INTO `sub_comment` VALUES (18, 56, 1, NULL, '测试', '2023-03-07 22:42:47', '0', 1);
INSERT INTO `sub_comment` VALUES (19, 1, 1, 2, '测试', '2023-03-07 22:43:10', '0', 1);
INSERT INTO `sub_comment` VALUES (20, 1, 1, 2, '123', '2023-03-07 22:44:46', '0', 1);
INSERT INTO `sub_comment` VALUES (21, 1, 1, 2, '241', '2023-03-07 22:46:41', '0', 1);
INSERT INTO `sub_comment` VALUES (22, 58, 2, NULL, '好好好', '2023-03-18 15:18:36', '0', NULL);
INSERT INTO `sub_comment` VALUES (23, 5, 1, NULL, '哈哈', '2023-03-29 17:46:12', '0', 0);
INSERT INTO `sub_comment` VALUES (24, 5, 1, 1, '好的', '2023-03-29 17:47:36', '0', 0);
INSERT INTO `sub_comment` VALUES (25, 5, 1, NULL, '是', '2023-03-29 17:49:31', '0', 0);
INSERT INTO `sub_comment` VALUES (26, 57, 1, NULL, '的', '2023-03-29 17:50:10', '0', 0);
INSERT INTO `sub_comment` VALUES (27, 57, 1, 1, '的', '2023-03-29 17:50:15', '0', 0);
INSERT INTO `sub_comment` VALUES (28, 57, 1, NULL, '的', '2023-03-29 17:50:21', '0', 0);
INSERT INTO `sub_comment` VALUES (29, 57, 1, NULL, '呃呃', '2023-03-29 17:50:28', '0', 0);
INSERT INTO `sub_comment` VALUES (30, 57, 1, 1, '呃呃', '2023-03-29 17:52:34', '0', 0);
INSERT INTO `sub_comment` VALUES (31, 57, 1, 1, '额', '2023-03-29 17:53:23', '0', 0);
INSERT INTO `sub_comment` VALUES (32, 55, 1, NULL, '啊啊', '2023-03-29 17:55:27', '0', 0);
INSERT INTO `sub_comment` VALUES (33, 55, 1, 1, '啊啊', '2023-03-29 17:55:32', '0', 0);
INSERT INTO `sub_comment` VALUES (35, 55, 1, 1, '试一下', '2023-03-29 18:09:18', '0', 0);
INSERT INTO `sub_comment` VALUES (36, 55, 1, 1, '呃呃', '2023-03-29 18:11:39', '0', 0);
INSERT INTO `sub_comment` VALUES (37, 47, 1, NULL, '呃呃', '2023-04-14 20:19:40', '0', 0);
INSERT INTO `sub_comment` VALUES (38, 47, 1, 1, '好好好', '2023-04-14 20:21:07', '0', 0);
INSERT INTO `sub_comment` VALUES (39, 40, 19, NULL, 'nihao', '2023-04-14 22:13:02', '0', 0);
INSERT INTO `sub_comment` VALUES (40, 40, 19, 19, 'nihao', '2023-04-14 22:13:07', '0', 0);
INSERT INTO `sub_comment` VALUES (41, 12, 12, NULL, 'nihao', '2023-04-15 01:23:27', '0', 0);
INSERT INTO `sub_comment` VALUES (42, 12, 1, 12, '好好好', '2023-04-15 01:23:46', '0', 0);
INSERT INTO `sub_comment` VALUES (44, 79, 12, 12, 'ee', '2023-04-15 22:31:14', '0', 0);
INSERT INTO `sub_comment` VALUES (45, 81, 1, NULL, '测试', '2023-04-16 09:44:16', '0', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'uid',
  `username` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `identity` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定学号或工号',
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$acfdI7qHTw8VvvROgXBM3eKtaxuWp4xw5mt9b7nJ8WZQw7/PAFtiq', '超级管理员', '女', '15989777444', '2023-02-25 16:11:12', '0', 'http://localhost:8080/image/f6ed73a8f4194ce29fd7d0a6a0d3eab6.jpg', '20001', '2541994309@qq.com', '2000-10-24');
INSERT INTO `user` VALUES (2, 'sss', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '普通用户', '女', '18312888888', '2023-02-25 16:11:12', '0', 'http://localhost:8080/image/b518631a52d64adab73e7d342b395329.png', '19001', '2541994309@qq.com', '2000-10-01');
INSERT INTO `user` VALUES (3, 'sec', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '管理员', '女', '18312888888', '2023-02-25 16:11:12', '0', 'http://localhost:8080/image/jiaran.png', '19002', '2541994309@qq.com', '2000-10-01');
INSERT INTO `user` VALUES (4, 'qwe', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '用户4', '女', '18312888888', '2023-02-25 16:11:12', '0', 'http://localhost:8080/image/caomei.png', '19003', '2541994309@qq.com', '2000-10-01');
INSERT INTO `user` VALUES (5, 'asd', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '用户5', '女', '18312888888', '2023-02-25 16:11:12', '0', 'http://localhost:8080/image/jiaran.png', '19004', '2541994309@qq.com', '2000-10-01');
INSERT INTO `user` VALUES (9, 'sssb', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '231', '保密', '15989777777', '2023-03-01 23:49:13', '0', NULL, '19005', '2541994309@qq.com', '2023-03-06');
INSERT INTO `user` VALUES (10, 'testadminadd', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', 'testadminadd', '保密', '18398989889', '2023-03-06 14:43:28', '0', 'http://localhost:8080/image/2441f767146c4b688727ca05ebfd90d4.png', '19007', '2541994309@qq.comv', '2023-03-28');
INSERT INTO `user` VALUES (11, 'quanxian', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', 'testrigster', '保密', '13745687541', '2023-03-06 14:49:18', '0', 'http://localhost:8080/image/f83da47f13d148aab0af0af36b9e9ce7.png', '19010', '2541994309@qq.com', '2023-03-28');
INSERT INTO `user` VALUES (12, 'ceshi', '$2a$10$HnPnIBXpWYWIVfZvZBb/eegxW5ySWh6Rr1QjrKIMDjtuPo6Mr71P.', 'ceshi', '男', '18377777777', '2023-03-08 18:07:08', '0', 'http://localhost:8080/image/5cf545af61a84f53a42d3cd22a59b7ed.png', '19009', '2541994309@qq.com', '2023-03-27');
INSERT INTO `user` VALUES (13, '测试', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '测试', '保密', '18346879415', '2023-03-14 17:57:43', '0', NULL, '19013', '2541994309@qq.com', '2023-03-09');
INSERT INTO `user` VALUES (14, '犬瘟热', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '安抚', '男', 'add给', '2023-03-14 18:14:02', '0', NULL, '是否会', '2541994309@qq.com', '2023-03-28');
INSERT INTO `user` VALUES (15, 'ceshia', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', 'ceshia', '男', 'ceshia', '2023-03-14 18:17:27', '0', NULL, 'ceshia', '2541994309@qq.com', '2023-03-28');
INSERT INTO `user` VALUES (16, 'ceshiaa', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', 'ceshiaa', '男', 'ceshia', '2023-03-14 18:24:31', '0', NULL, '12345', '2541994309@qq.com', '2023-03-28');
INSERT INTO `user` VALUES (17, 'haha', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', 'gaga', '男', '21346777', '2023-03-27 16:43:26', '1', NULL, '12346567', '2541994309@qq.com', '2023-03-28');
INSERT INTO `user` VALUES (18, '12345', '$2a$10$YeEMl3UfIfjGUnk1136nUOZsWblp4zuKt7hH4EpDrIgGwxkqiEO7i', '124555', '保密', '', '2023-03-27 16:51:27', '1', NULL, '12452', '2541994309@qq.com', NULL);
INSERT INTO `user` VALUES (19, 'www', '$2a$10$461hFNcWQwD1qqm9ObJuPunWHeZcpa2qskIUWreVp4fk.iTajJj2O', 'www', '保密', '15989777777', '2023-04-14 22:02:15', '0', 'http://localhost:8080/image/8b9fc8cb8ea14384be1f00b92ab1499d.jpg', '19001', '2541994309@qq.com', '2023-04-26');
INSERT INTO `user` VALUES (20, 'wang', '$2a$10$o.DlQxigc3ThHufM6CSu5uLAgVjodbEeyHHsGcU/Odt20MERFRIBy', 'wang', '保密', '18312888887', '2023-04-14 22:32:23', '0', 'http://localhost:8080/image/6355457fe313476da4586018930ecfa4.jpg', '19002', '2541994309@qq.com', '2023-04-01');
INSERT INTO `user` VALUES (21, 'aaa', '$2a$10$ei3lpNwxILrqaBrHnKuTUe57nRXcvTn3bRQkGwpiCB59RTDW90ZSe', 'aaaa', '保密', '18888888888', '2023-04-15 22:38:12', '1', NULL, '19011', '2541994309@qq.com', '2023-04-25');
INSERT INTO `user` VALUES (22, 'aaaaaaa', '$2a$10$xP9dby4SDEr85jsw4iHaKOlg02eTg2eQ6qyIipOIEy3/Aal45qZGO', 'aaaaaa', '保密', NULL, '2023-04-16 09:42:24', '0', NULL, '19002', '2541994309@qq.comm', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 3, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (3, 1, 1);
INSERT INTO `user_role` VALUES (13, 12, 3);
INSERT INTO `user_role` VALUES (14, 5, 2);
INSERT INTO `user_role` VALUES (16, 4, 2);
INSERT INTO `user_role` VALUES (18, 14, 2);
INSERT INTO `user_role` VALUES (20, 16, 2);
INSERT INTO `user_role` VALUES (22, 17, 2);
INSERT INTO `user_role` VALUES (23, 18, 2);
INSERT INTO `user_role` VALUES (24, 19, 2);
INSERT INTO `user_role` VALUES (25, 20, 2);
INSERT INTO `user_role` VALUES (26, 15, 3);
INSERT INTO `user_role` VALUES (27, 13, 2);
INSERT INTO `user_role` VALUES (28, 9, 2);
INSERT INTO `user_role` VALUES (29, 10, 3);
INSERT INTO `user_role` VALUES (31, 21, 2);
INSERT INTO `user_role` VALUES (33, 22, 2);

SET FOREIGN_KEY_CHECKS = 1;
