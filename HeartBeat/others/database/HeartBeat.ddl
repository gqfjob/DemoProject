-- ###############
--    create database , if need create, cancel the comment
-- ###############
-- create database if not exists heart_beat default character set utf8;
-- use heart_beat set default character = utf8;

-- ###############
--    grant privileges  to heart_beat/heart_beat
-- ###############
-- GRANT ALL PRIVILEGES ON heart_beat.* TO heart_beat@localhost IDENTIFIED BY "heart_beat";

-- ###############
-- Domain:  User
-- ###############
Drop table  if exists user_;
CREATE TABLE `user_` (
  `id` int(11) NOT NULL auto_increment,
  `guid` varchar(255) not null unique,
  `create_time` datetime ,
  `archived` tinyint(1) default '0',
  `version` int(11) DEFAULT 0,

  `username` varchar(255) not null unique,
  `password` varchar(255) not null,
  `phone` varchar(255),
  `email` varchar(255),
   `default_user` tinyint(1) default '0',
   `last_login_time` datetime ,
   `creator_id` int(11) ,
  PRIMARY KEY  (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `creator_id_index` (`creator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ###############
-- Domain:  ApplicationInstance
-- ###############
Drop table  if exists application_instance;
CREATE TABLE `application_instance` (
  `id` int(11) NOT NULL auto_increment,
  `guid` varchar(255) not null unique,
  `create_time` datetime ,
  `archived` tinyint(1) default '0',
  `version` int(11) DEFAULT 0,

  `instance_name` varchar(255),
  `monitor_url` varchar(255),
  `max_connection_seconds` int(11) default 0,
   `enabled` tinyint(1) default '0',
  `frequency` varchar(255) default 'THIRTY',
  `request_method` varchar(255) default 'GET',
  `content_type` varchar(255),
  `email` varchar(255),
  `job_name` varchar(255),
   `remark` text,
   `creator_id`  int(11),
  PRIMARY KEY  (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `creator_id_index` (`creator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ###############
-- Domain:  FrequencyMonitorLog
-- ###############
Drop table  if exists frequency_monitor_log;
CREATE TABLE `frequency_monitor_log` (
  `id` int(11) NOT NULL auto_increment,
  `guid` varchar(255) not null unique,
  `create_time` datetime ,
  `archived` tinyint(1) default '0',
  `version` int(11) DEFAULT 0,

  `instance_id` int(11),
   `normal` tinyint(1) default '0',
  `cost_time` int(11) default 0,
  `response_size` int(11) default 0,
   `remark` text,
  PRIMARY KEY  (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `instance_id_index` (`instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ###############
-- Domain:  MonitoringReminderLog
-- ###############
Drop table  if exists monitoring_reminder_log;
CREATE TABLE `monitoring_reminder_log` (
  `id` int(11) NOT NULL auto_increment,
  `guid` varchar(255) not null unique,
  `create_time` datetime ,
  `archived` tinyint(1) default '0',
  `version` int(11) DEFAULT 0,

  `instance_id` int(11),
  `monitor_log_id` int(11),
  `change_normal` tinyint(1) default '0',
  `receive_email` varchar(255),
  `email_content` text,
  PRIMARY KEY  (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `instance_id_index` (`instance_id`),
  INDEX `monitor_log_id_index` (`monitor_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ###############
-- Domain:  UserPrivilege
-- ###############
Drop table  if exists user_privilege;
CREATE TABLE `user_privilege` (
  `id` int(11) NOT NULL auto_increment,
  `guid` varchar(255) not null unique,
  `create_time` datetime ,
  `archived` tinyint(1) default '0',
  `version` int(11) DEFAULT 0,

  `user_id` int(11),
  `privilege` varchar(255),
  PRIMARY KEY  (`id`),
  INDEX `guid_index` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ###############
-- Domain:  SystemSetting
-- ###############
Drop table  if exists system_setting;
CREATE TABLE `system_setting` (
  `id` int(11) NOT NULL auto_increment,
  `guid` varchar(255) not null unique,
  `create_time` datetime ,
  `archived` tinyint(1) default '0',
  `version` int(11) DEFAULT 0,

  `allow_user_register` tinyint(1) default '1',
  PRIMARY KEY  (`id`),
  INDEX `guid_index` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ###############
-- Domain:  InstanceMonitorURLParameter
-- ###############
Drop table  if exists instance_monitor_url_parameter;
CREATE TABLE `instance_monitor_url_parameter` (
  `id` int(11) NOT NULL auto_increment,
  `guid` varchar(255) not null unique,
  `create_time` datetime ,
  `archived` tinyint(1) default '0',
  `version` int(11) DEFAULT 0,

  `random_value` tinyint(1) default '0',
  `key_` varchar(255),
  `value_` varchar(255),
  `instance_id` int(11),
  PRIMARY KEY  (`id`),
  INDEX `guid_index` (`guid`),
  INDEX `instance_id_index` (`instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;


