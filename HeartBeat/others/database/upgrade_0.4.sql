

set names utf8;


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




alter table application_instance add `creator_id`  int(11);
alter table application_instance add INDEX `creator_id_index` (`creator_id`);
alter table application_instance add `content_type` varchar(255);


alter table user_ add `creator_id`  int(11);
alter table user_ add INDEX `creator_id_index` (`creator_id`);




-- Initial database data
-- username: hb, password: heart_beat
truncate user_;
insert into user_(id,guid,create_time,archived,version, username, password,phone,email, default_user)
values
(21,'29F6004FB1B0466F9572B02BF2AC1BE8',now(),0,0,'hb','54bd6d2a3dec50058472137d97c9fd33','','hb@andaily.com',1);

-- Set old application_instance default creator
update application_instance set creator_id = 21;
