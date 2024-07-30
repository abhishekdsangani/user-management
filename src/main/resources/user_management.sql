CREATE DATABASE IF NOT EXISTS `user_management` 
USE `user_management`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `name`, `is_admin`) VALUES
	(1, 'admin', '$2a$10$GFFhUVriN0i1V6mpLLUGX.d5SoHnyOfW8I./MLx0lvAuWXWkNonxm', 'Admin@admin.com', '1234567789', 'John Doe', 1);