


set names utf8;


-- Initial database data
-- username: hb, password: heart_beat
truncate user_;
insert into user_(id,guid,create_time,archived,version, username, password,phone,email, default_user)
values
(21,'29F6004FB1B0466F9572B02BF2AC1BE8',now(),0,0,'hb','54bd6d2a3dec50058472137d97c9fd33','','hb@andaily.com',1);


