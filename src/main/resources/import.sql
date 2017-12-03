INSERT INTO `organization` (`id`, `name`, `gmt_created`, `gmt_updated`) VALUES ('1', '芜湖', '2017-12-02 16:35:17', '2017-12-02 16:35:17');
INSERT INTO `department` (`id`, `name`, `organization_id`, `gmt_created`, `gmt_updated`) VALUES ('1', '文明办', '1', '2017-12-02 16:35:16', '2017-12-02 16:35:16');
INSERT INTO `sys_user` (`id`, `last_password_reset_date`, `password`, `username`, `department_id`, `gmt_created`, `gmt_updated`) VALUES ('ff808181601707450160170901610000', '2017-12-02 19:42:34', '$2a$10$NMoQkXfF9c0YnMug21GQUeNIKJ46E7vbiJe.bKsFpmrbyNNgGPyzS', '18705596666', '1', '2017-12-02 16:35:17', '2017-12-02 16:35:17');
INSERT INTO `sys_role` (`id`, `name`, `gmt_created`, `gmt_updated`) VALUES ('1', 'ROLE_ADMIN', '2017-12-02 16:35:17', '2017-12-02 16:35:17');

INSERT INTO `discovery` (`id`, `gmt_created`, `gmt_updated`, `department_id`, `description`, `latitude`, `location`, `longitude`, `user_id`) VALUES ('2c918083601bf07901601bf2cd970001', '2017-12-03 10:36:25', '2017-12-03 10:36:25', '1', '井盖破裂', '1', '芜湖市繁昌县xxx街道', '1', 'ff808181601707450160170901610000');
INSERT INTO `image` (`id`, `gmt_created`, `gmt_updated`, `url`, `user_id`) VALUES ('1', '2017-12-03 10:35:15', '2017-12-03 10:35:15', '/api/image/1/1/ff808181601707450160170901610000/1512269782379.jpg', 'ff808181601707450160170901610000');
INSERT INTO `discovery_image_set` (`discovery_id`, `image_set_id`) VALUES ('2c918083601bf07901601bf2cd970001', '1');
