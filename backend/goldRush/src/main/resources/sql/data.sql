INSERT INTO `tbl_user` (`id`, `account_id`, `name`, `password`, `email`, `role`) VALUES ('1', 'admin', 'admin', '{bcrypt}$2a$10$f4aQLof9kgM8mzJIP7a.Vuc3WYcQK8brcL6hrHdCdkzTH8AppEpOm', NULL, 'admin');


INSERT INTO `tbl_album` (`id`, `title`, `cover`, `count`, `status`, `password`) VALUES ('1', '收藏', NULL, 0, 'normal', NULL);
INSERT INTO `tbl_album` (`id`, `title`, `cover`, `count`, `status`, `password`) VALUES ('2', '分享', NULL, 0, 'shared', NULL);
INSERT INTO `tbl_album` (`id`, `title`, `cover`, `count`, `status`, `password`) VALUES ('3', '私密空间', NULL, 0, 'private', NULL);

