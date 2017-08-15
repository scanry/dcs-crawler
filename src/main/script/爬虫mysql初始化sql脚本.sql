CREATE TABLE `ex_crawler_platform_extract_item` (
  `jobName` varchar(50) NOT NULL,
  `serialNub` int(11) NOT NULL DEFAULT '1',
  `pathName` varchar(50) NOT NULL,
  `primary` int(1) NOT NULL DEFAULT '0' COMMENT '0 ?????? 1???? ????',
  `type` int(1) NOT NULL COMMENT '???????',
  `outputType` int(1) NOT NULL DEFAULT '1',
  `outputKey` varchar(50) NOT NULL COMMENT '????key',
  `mustHaveResult` int(1) NOT NULL DEFAULT '0' COMMENT '?Ƿ? ??????ֵ 1?ǵ?  0?',
  `describe` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`jobName`,`pathName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据抽取表';

CREATE TABLE `ex_crawler_platform_extract_path` (
  `name` varchar(45) NOT NULL COMMENT 'name 为一类 可以对应多个',
  `siteCode` varchar(20) NOT NULL COMMENT '网站code',
  `ranking` int(11) NOT NULL DEFAULT '1' COMMENT '排名',
  `path` varchar(300) NOT NULL COMMENT ' 1.普通抽取对应抽取元素的路径\n 2.表格抽取对应抽取表格的路径\n3.json抽取对应json的路径\n4.正则抽取对应正则的表达式路径',
  `tableHeadPath` varchar(300) DEFAULT NULL COMMENT '表格多条数据类型 列名path',
  `tableDataPath` varchar(300) DEFAULT NULL COMMENT '表格多条数据类型 数据path',
  `filterPath` varchar(45) DEFAULT NULL COMMENT '需要过滤的 path',
  `extractAttName` varchar(45) NOT NULL COMMENT '获取值得 属性名字',
  `substringStart` varchar(45) DEFAULT NULL,
  `substringEnd` varchar(45) DEFAULT NULL,
  `appendHead` varchar(45) DEFAULT NULL COMMENT '头部追加字符',
  `appendEnd` varchar(45) DEFAULT NULL COMMENT '尾部追加字符',
  `compareAttName` varchar(45) DEFAULT NULL COMMENT '比较 的att name',
  `containKeyWord` varchar(45) DEFAULT NULL COMMENT '包含某个 字词',
  `replaceWord` varchar(45) DEFAULT NULL COMMENT '需要替换的字词',
  `replaceValue` varchar(45) DEFAULT NULL COMMENT '替换的值',
  `extractEmptyCount` int(11) NOT NULL DEFAULT '0' COMMENT '抽取结果为空的次数',
  `describe` varchar(20) DEFAULT NULL COMMENT '规则描述',
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`,`siteCode`,`ranking`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_http_proxy` (
  `host` varchar(100) NOT NULL,
  `port` int(11) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '1',
  `userName` varchar(100) DEFAULT NULL,
  `passWord` varchar(100) DEFAULT NULL,
  `expire` varchar(100) DEFAULT NULL,
  `describe` varchar(200) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`host`,`port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_image` (
  `id` varchar(100) NOT NULL,
  `path` varchar(100) DEFAULT NULL,
  `result` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_job` (
  `name` varchar(100) NOT NULL COMMENT 'job名字',
  `level` int(1) NOT NULL,
  `designatedNodeName` varchar(100) NOT NULL,
  `needNodes` int(11) NOT NULL DEFAULT '1' COMMENT '执行节点数',
  `threads` int(11) NOT NULL DEFAULT '1' COMMENT '执行任务的线程数',
  `isScheduled` int(1) NOT NULL DEFAULT '0' COMMENT '是否调度',
  `cronTrigger` varchar(100) DEFAULT NULL,
  `workFrequency` int(11) NOT NULL DEFAULT '100' COMMENT 'job每次处理时间的阈值 默认100毫秒',
  `workerClass` varchar(100) NOT NULL,
  `workSpaceName` varchar(100) DEFAULT NULL COMMENT '工作空间名称',
  `user` varchar(45) NOT NULL DEFAULT 'admin',
  `describe` varchar(45) DEFAULT NULL COMMENT '任务描述',
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_job_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(50) NOT NULL,
  `name` varchar(45) NOT NULL,
  `value` text,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1841 DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_job_relationship` (
  `currentJobName` varchar(100) NOT NULL COMMENT '当前job ',
  `nextJobName` varchar(100) NOT NULL DEFAULT '' COMMENT '下一个job',
  `executeType` int(1) DEFAULT '1' COMMENT '触发类型',
  `version` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`currentJobName`,`nextJobName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_job_snapshot` (
  `id` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `tableName` varchar(200) DEFAULT NULL COMMENT '任务存储数据表名',
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态:\nREADY(1)//准备\nWAITING_EXECUTED(2)//等待执行\nEXECUTING(3)//执行\nSUSPEND(4)//暂停\nSTOP(5)//停止\nFINISHED(6)//完成',
  `totalProcessCount` int(11) NOT NULL DEFAULT '0',
  `totalResultCount` int(11) NOT NULL DEFAULT '0',
  `totalProcessTime` int(11) DEFAULT NULL,
  `avgProcessTime` int(11) NOT NULL DEFAULT '0',
  `maxProcessTime` int(11) NOT NULL DEFAULT '0',
  `minProcessTime` int(11) NOT NULL DEFAULT '0',
  `errCount` int(11) NOT NULL DEFAULT '0',
  `downloadState` int(11) DEFAULT NULL COMMENT '0-未下载,1-下载完成',
  `runtimeParams` text,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务执行快照信息表';

CREATE TABLE `ex_crawler_platform_job_worker_err` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobSnapshotId` varchar(200) NOT NULL,
  `jobName` varchar(200) NOT NULL,
  `workerName` varchar(200) NOT NULL,
  `startTime` datetime NOT NULL,
  `type` varchar(100) NOT NULL,
  `msg` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8184821 DEFAULT CHARSET=utf8 COMMENT='任务worker 执行快照信息表 异常信息表';

CREATE TABLE `ex_crawler_platform_job_worker_snapshot` (
  `jobSnapshotId` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `localNode` varchar(45) NOT NULL,
  `jobName` varchar(200) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `totalProcessCount` int(11) DEFAULT NULL,
  `totalResultCount` int(11) DEFAULT NULL,
  `totalProcessTime` int(11) DEFAULT NULL,
  `maxProcessTime` int(11) DEFAULT NULL,
  `minProcessTime` int(11) DEFAULT NULL,
  `avgProcessTime` int(11) DEFAULT NULL,
  `errCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`jobSnapshotId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务worker 执行快照信息表';

CREATE TABLE `ex_crawler_platform_node` (
  `name` varchar(50) NOT NULL,
  `clusterName` varchar(50) NOT NULL,
  `ip` varchar(45) NOT NULL COMMENT '?ڵ?ip',
  `port` int(11) NOT NULL COMMENT '?ڵ??????˿',
  `trafficPort` int(11) NOT NULL COMMENT '?ڵ???ͨ?? ?˿',
  `user` varchar(45) DEFAULT NULL COMMENT '?ڵ??û???',
  `passwd` varchar(45) DEFAULT NULL COMMENT '?ڵ????',
  `status` int(11) DEFAULT NULL COMMENT '״̬',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_page_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `describe` varchar(45) DEFAULT NULL COMMENT 'page????????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_paser_result` (
  `jobName` varchar(50) NOT NULL,
  `key` varchar(45) NOT NULL,
  `class` varchar(100) NOT NULL,
  PRIMARY KEY (`jobName`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_paser_result_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `describe` varchar(20) NOT NULL COMMENT '类型说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE `ex_crawler_platform_seed_page` (
  `siteCode` varchar(20) NOT NULL COMMENT '页面所属的 站点code',
  `urlMd5` varchar(32) NOT NULL COMMENT 'url md5 32位',
  `meta` varchar(500) DEFAULT NULL COMMENT '页面的 meta key value json',
  `update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `depth` int(11) NOT NULL DEFAULT '0' COMMENT '页面深度',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '列表也状态  1 表示有效 0表示无效',
  `originalUrl` varchar(500) DEFAULT NULL,
  `firstUrl` varchar(500) DEFAULT NULL,
  `finalUrl` varchar(500) DEFAULT NULL,
  `ancestorUrl` varchar(500) DEFAULT NULL,
  `referer` varchar(500) DEFAULT NULL,
  `pageNum` int(11) DEFAULT NULL,
  `charset` varchar(45) DEFAULT NULL,
  `downerType` int(11) NOT NULL DEFAULT '1' COMMENT '1为 httpclient 2为chrome 3为phantomjs(3暂时不支持)',
  `waitJsLoadElement` varchar(200) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`siteCode`,`urlMd5`),
  KEY `site_code_foreign_key_in` (`siteCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬虫平台,列表页面表';

CREATE TABLE `ex_crawler_platform_site` (
  `code` varchar(20) NOT NULL COMMENT '站点 code',
  `mainurl` varchar(45) NOT NULL COMMENT '主页 url',
  `visitFrequency` int(11) NOT NULL COMMENT '站点访问频率',
  `describe` varchar(200) DEFAULT NULL COMMENT '站点描述',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;