CREATE TABLE `award` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `award_id` int(8) NOT NULL COMMENT '抽奖奖品ID - 内部流转使用',
  `award_key` varchar(64) NOT NULL COMMENT '奖品对接标识',
  `award_config` varchar(256) DEFAULT NULL COMMENT '奖品配置信息',
  `award_desc` varchar(128) NOT NULL COMMENT '奖品内容描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_award_id` (`award_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `award` (`id`, `award_id`, `award_key`, `award_config`, `award_desc`, `create_time`, `update_time`)
VALUES
  (1, 101, 'openai_use_count', '1,1000', '随机积分', '2023-12-09 09:38:31', '2023-12-09 09:38:31'),
  (2, 102, 'openai_use_count', '5', '5次使用', '2023-12-09 09:39:18', '2023-12-09 09:39:18'),
  (3, 103, 'openai_use_count', '10', '10次使用', '2023-12-09 09:42:36', '2023-12-09 09:42:36'),
  (4, 104, 'openai_use_count', '20', '20次使用', '2023-12-09 09:43:15', '2023-12-09 09:43:15'),
  (5, 105, 'chat_model', 'gpt-4', '增加gpt-4对话模型', '2023-12-09 09:43:47', '2023-12-09 09:43:47'),
  (6, 106, 'draw_model', 'dall-e-2', '增加dall-e-2画图模型', '2023-12-09 09:44:20', '2023-12-09 09:44:20'),
  (7, 107, 'draw_model', 'dall-e-3', '增加dall-e-3画图模型', '2023-12-09 09:45:38', '2023-12-09 09:45:38'),
  (8, 108, 'openai_use_count', '100', '增加100次使用', '2023-12-09 09:46:02', '2023-12-09 09:46:02'),
  (9, 109, 'unlock_all', 'all', '解锁全部模型', '2023-12-09 09:46:39', '2023-12-09 09:46:39');
