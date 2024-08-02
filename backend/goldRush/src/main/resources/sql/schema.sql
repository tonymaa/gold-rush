CREATE TABLE `tbl_image` (
     `id` varchar(120) NOT NULL,
     `thumbnail` longblob,
     `name` varchar(255) NOT NULL,
     `longitude` decimal(30,20) DEFAULT NULL COMMENT '经度',
     `latitude` decimal(30,20) DEFAULT NULL COMMENT '纬度',
     `created_time` datetime DEFAULT NULL,
     `updated_time` datetime DEFAULT NULL,
     `description` varchar(255) DEFAULT NULL,
     `size` double(40,10) DEFAULT NULL,
  `mine_type` varchar(255) DEFAULT NULL,
  `ext` varchar(255) DEFAULT NULL,
  `identity` varchar(120) DEFAULT NULL,
  `height` int(20) DEFAULT NULL,
  `width` int(20) DEFAULT NULL,
  `key_words` varchar(100) DEFAULT NULL,
  `duration` int(30) DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL,
  `address_component` longtext,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_longitude` (`longitude`),
  KEY `index_latitude` (`latitude`),
  KEY `index_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tbl_user` (
    `id` varchar(100) NOT NULL,
    `account_id` varchar(100) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `role` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_account_id` (`account_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tbl_album` (
                             `id` varchar(255) CHARACTER SET utf8 NOT NULL,
                             `title` varchar(255) DEFAULT NULL,
                             `cover` varchar(255) DEFAULT NULL,
                             `count` int(11) DEFAULT NULL,
                             `status` varchar(255) DEFAULT NULL,
                             `password` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table tbl_album modify column cover LONGBLOB;
alter table tbl_album add `created_time` datetime;
alter table tbl_album add `updated_time` datetime;

CREATE TABLE `tbl_album_image` (
                                   `id` varchar(255) NOT NULL,
                                   `image_id` varchar(255) DEFAULT NULL,
                                   `album_id` varchar(255) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table tbl_album_image add `created_time` datetime;
alter table tbl_album_image add `updated_time` datetime;


ALTER TABLE tbl_image ALTER `status` SET DEFAULT 'normal';
ALTER TABLE tbl_album ALTER `status` SET DEFAULT 'normal';


CREATE TABLE `tbl_comments` (
                                `id` varchar(255) NOT NULL,
                                `content` varchar(1000) DEFAULT NULL,
                                `created_time` datetime DEFAULT NULL,
                                `updated_time` datetime DEFAULT NULL,
                                `is_delete` varchar(10) DEFAULT NULL,
                                `user_id` varchar(255) DEFAULT NULL,
                                `like_count` int(11) DEFAULT NULL,
                                `root_comment_id` varchar(255) DEFAULT NULL,
                                `to_comment_id` varchar(255) DEFAULT NULL,
                                `group_id` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table tbl_comments add `ip_address` varchar(100);
alter table tbl_comments add `region` varchar(100);
alter table tbl_comments add `status` varchar(20);
alter table tbl_comments add `anonymous` varchar(20);
ALTER TABLE tbl_comments CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
UPDATE tbl_comments set `like_count` = 0 where `like_count` is null;

CREATE TABLE `tbl_pano_hot_spots` (
                                      `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                      `scene_id` varchar(255) DEFAULT NULL,
                                      `scene_identity` varchar(255) DEFAULT NULL,
                                      `type` varchar(255) DEFAULT NULL,
                                      `url` varchar(255) DEFAULT NULL,
                                      `text` varchar(255) DEFAULT NULL,
                                      `yaw` int(10) DEFAULT NULL,
                                      `pitch` int(10) DEFAULT NULL,
                                      `created_time` datetime DEFAULT NULL,
                                      `updated_time` datetime DEFAULT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_pano_scene` (
                                  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                  `identity` varchar(255) DEFAULT NULL,
                                  `title` varchar(255) DEFAULT NULL,
                                  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `yaw` int(10) DEFAULT NULL,
                                  `pitch` int(10) DEFAULT NULL,
                                  `hfov` int(10) DEFAULT NULL,
                                  `created_time` datetime DEFAULT NULL,
                                  `updated_time` datetime DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tbl_pano_hot_spots_images` (
                                            `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                            `hot_spots_id` varchar(255) DEFAULT NULL,
                                            `image_id` varchar(255) DEFAULT NULL,
                                            `created_time` datetime DEFAULT NULL,
                                            `updated_time` datetime DEFAULT NULL,
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tbl_pano_hot_spots add `portal_type` varchar(20);
alter table tbl_pano_hot_spots add `size` int(10);

ALTER TABLE tbl_pano_hot_spots CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

alter table tbl_pano_scene add `north_offset` int(10) DEFAULT NULL;

alter table tbl_image add `exif_info` longtext DEFAULT NULL;