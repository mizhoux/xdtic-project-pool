
CREATE DATABASE IF NOT EXISTS `project_pool` DEFAULT CHARACTER SET utf8;

USE `project_pool`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `realname` varchar(20) DEFAULT NULL,
  `phone` char(11) DEFAULT NULL,
  `email` char(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`username`,`password`,`realname`,`phone`,`email`) values 
(1,'xdtic','xdtic','西电腾讯俱乐部',NULL,NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `gender` char(1) NOT NULL DEFAULT 'M' COMMENT '性别，M 男，F 女',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `phone` char(11) DEFAULT NULL COMMENT '电话',
  `realname` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `stu_num` char(20) DEFAULT NULL COMMENT '学号',
  `major` varchar(30) DEFAULT NULL COMMENT '专业',
  `skill` text COMMENT '技能',
  `experience` text COMMENT '项目经验',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `tag` varchar(30) NOT NULL COMMENT '标签',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `content` text NOT NULL COMMENT '内容',
  `contact` text COMMENT '联系方式',
  `recruit` text NOT NULL COMMENT '招聘信息',
  `creation_date` datetime DEFAULT NULL COMMENT '创建日期',
  `user_id` int(11) NOT NULL COMMENT '发布项目的用户的 id',
  `username` varchar(20) NOT NULL COMMENT '发布项目的用户的 username，冗余数据，方便查询',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `username` (`username`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息 ID',
  `user_id` int(11) NOT NULL COMMENT '消息所属用户 ID',
  `pro_id` int(11) NOT NULL COMMENT '项目 ID',
  `content` text NOT NULL COMMENT '消息内容',
  `creation_date` datetime DEFAULT NULL COMMENT '产生消息的日期',
  `read` tinyint(1) DEFAULT '0' COMMENT '是否已读',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `pro_id` (`pro_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`pro_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

/*Table structure for table `project_star` （收藏项目信息）*/

DROP TABLE IF EXISTS `project_star`;

CREATE TABLE `project_star` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `pro_id` int(11) NOT NULL COMMENT '用户收藏的项目的ID',
  PRIMARY KEY (`user_id`,`pro_id`),
  KEY `pro_id` (`pro_id`),
  CONSTRAINT `project_star_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_star_ibfk_2` FOREIGN KEY (`pro_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sign_info` （报名信息）*/

DROP TABLE IF EXISTS `sign_info`;

CREATE TABLE `sign_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报名信息ID-主键',
  `user_id` int(11) NOT NULL COMMENT '报名的用户 ID',
  `pro_id` int(11) NOT NULL COMMENT '报名的项目 ID',
  `apply` varchar(50) DEFAULT NULL COMMENT '报名的用户想要的职位',
  `skill` text COMMENT '报名的用户的技术能力',
  `experience` text COMMENT '报名的用户的项目经历',
  `sign_date` datetime DEFAULT NULL COMMENT '报名的时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `pro_id` (`pro_id`),
  CONSTRAINT `sign_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sign_info_ibfk_2` FOREIGN KEY (`pro_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
