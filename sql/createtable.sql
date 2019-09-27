create database wms_db
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

use wms_db;

# 创建系统用户信息表
create table wms_user
(
	USER_ID int not null auto_increment,
    USER_USERNAME varchar(30) not null,
    USER_PASSWORD varchar(40) not null,
    primary key (USER_ID)
)engine=innodb;

# 创建用户角色表
create table wms_roles
(
	ROLE_ID int not null auto_increment,
    ROLE_NAME varchar(20) not null,
    ROLE_DESC varchar(30),
    ROLE_URL_PREFIX varchar(20) not null,
    primary key(ROLE_ID)
)engine=innodb;

# 用户 - 角色关联表
create table wms_user_role
(
	ROLE_ID int not null,
    USER_ID int not null,
    primary key(ROLE_ID,USER_ID),
    foreign key(ROLE_ID) references wms_roles(ROLE_ID),
    foreign key(USER_ID) references wms_user(USER_ID)
)engine=innodb;

# 导入系统用户信息
INSERT INTO `wms_user` VALUES (1001,'Admin','Admin123'),(1002,'Dafang','Dafang123'),(1003,'Anker','Anker123');

# 导入系统角色信息
INSERT INTO `wms_roles` VALUES (1,'systemAdmin','最高权限管理员','systemAdmin'),(2,'commonsAdmin','普通仓库检测人员','commonsAdmin');

# 导入 系统用户 - 角色 信息
INSERT INTO `wms_user_role` VALUES (1,1001),(2,1002),(2,1003);

# 创建URL权限表
create table wms_action
(
	ACTION_ID int not null auto_increment,
    ACTION_NAME varchar(30) not null,
    ACTION_DESC varchar(30),
    ACTION_PARAM varchar(50) not null,
    primary key(ACTION_ID)
)engine=innodb;


# 角色 - URL权限关联表
create table wms_role_action
(
	ACTION_ID int not null,
    ROLE_ID int not null,
    primary key(ACTION_ID,ROLE_ID),
    foreign key(ROLE_ID) references wms_roles(ROLE_ID),
    foreign key(ACTION_ID) references wms_action(ACTION_ID)
)engine=innodb;

# 导入URL权限信息
INSERT INTO `wms_action` VALUES (1,'getPacketList',NULL,'/packetManage/getPacketList'), (2,'addPacket',NULL,'/packetManage/addPacket'), (3,'updatePacket',NULL,'/packetManage/updatePacket'), (4,'deletePacket',NULL,'/packetManage/deletePacket'),  (5,'packetStockIn',NULL,'/packetManage/packetStockIn'),(6,'getStorageList',NULL,'/packetStorageManage/getStorageList'),(8,'selectCustomer',NULL,'/customerManage/getCustomerList'),(9,'addCustomer',NULL,'/customerManage/addCustomer'),(10,'getCustomerInfo',NULL,'/customerManage/getCustomerInfo'),(11,'updateCustomer',NULL,'/customerManage/updateCustomer'),(12,'deleteCustomer',NULL,'/customerManage/deleteCustomer'),(13,'importCustomer',NULL,'/customerManage/importCustomer'),(14,'exportCustomer',NULL,'/customerManage/exportCustomer'),(15,'selectGoods',NULL,'/goodsManage/getGoodsList'),(16,'addGoods',NULL,'/goodsManage/addGoods'),(17,'getGoodsInfo',NULL,'/goodsManage/getGoodsInfo'),(18,'updateGoods',NULL,'/goodsManage/updateGoods'),(19,'deleteGoods',NULL,'/goodsManage/deleteGoods'),(20,'importGoods',NULL,'/goodsManage/importGoods'),(21,'exportGoods',NULL,'/goodsManage/exportGoods'),(22,'selectRepository',NULL,'/repositoryManage/getRepositoryList'),(23,'addRepository',NULL,'/repositoryManage/addRepository'),(24,'getRepositoryInfo',NULL,'/repositoryManage/getRepository'),(25,'updateRepository',NULL,'/repositoryManage/updateRepository'),(26,'deleteRepository',NULL,'/repositoryManage/deleteRepository'),(27,'importRepository',NULL,'/repositoryManage/importRepository'),(28,'exportRepository',NULL,'/repositoryManage/exportRepository'),(29,'selectRepositoryAdmin',NULL,'/repositoryAdminManage/getRepositoryAdminList'),(30,'addRepositoryAdmin',NULL,'/repositoryAdminManage/addRepositoryAdmin'),(31,'getRepositoryAdminInfo',NULL,'/repositoryAdminManage/getRepositoryAdminInfo'),(32,'updateRepositoryAdmin',NULL,'/repositoryAdminManage/updateRepositoryAdmin'),(33,'deleteRepositoryAdmin',NULL,'/repositoryAdminManage/deleteRepositoryAdmin'),(34,'importRepositoryAd,om',NULL,'/repositoryAdminManage/importRepositoryAdmin'),(35,'exportRepository',NULL,'/repositoryAdminManage/exportRepositoryAdmin'),(36,'getUnassignRepository',NULL,'/repositoryManage/getUnassignRepository'),(37,'getStorageList',NULL,'/storageManage/getStorageList'),(39,'addStorageRecord',NULL,'/storageManage/addStorageRecord'),(40,'updateStorageRecord',NULL,'/storageManage/updateStorageRecord'),(41,'deleteStorageRecord',NULL,'/storageManage/deleteStorageRecord'),(42,'importStorageRecord',NULL,'/storageManage/importStorageRecord'),(43,'exportStorageRecord',NULL,'/storageManage/exportStorageRecord'),(44,' stockIn',NULL,'/stockRecordManage/stockIn'),(45,'stockOut',NULL,'/stockRecordManage/stockOut'),(46,'searchStockRecord',NULL,'/stockRecordManage/searchStockRecord'),(47,'getAccessRecords',NULL,'/systemLog/getAccessRecords'),(48,'selectUserOperationRecords',null,'/systemLog/selectUserOperationRecords'),(49,'getRepositoryBatchList',NULL,'/repositoryBatchManage/getRepositoryBatchList'), (50,'getActiveBatchList',NULL,'/repositoryBatchManage/getActiveBatchList'), (51,'addRepositoryBatch',NULL,'/repositoryBatchManage/addRepositoryBatch'), (52,'updateRepositoryBatch',NULL,'/repositoryBatchManage/updateRepositoryBatch'),  (53,'deleteRepositoryBatch',NULL,'/repositoryBatchManage/deleteRepositoryBatch'),(54,'importPacket',NULL,'/packetManage/importPacket'),(55,'exportPacket',NULL,'/packetManage/exportPacket');
# 导入 角色 - URL权限 信息
INSERT INTO `wms_role_action` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(34,1),(35,1),(36,1),(37,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1),(49,1),(50,1),(51,1),(52,1),(53,1),(54,1),(55,1);
# 系统登入登出记录表
create table wms_access_record
(
	RECORD_ID int auto_increment primary key,
    USER_ID int not null,
    USER_NAME varchar(50) not null,
    ACCESS_TYPE varchar(30) not null,
    ACCESS_TIME datetime not null,
    ACCESS_IP varchar(45) not null
);

# 用户系统操作记录表
create table wms_operation_record
(
	RECORD_ID int auto_increment primary key,
    USER_ID int not null,
    USER_NAME varchar(50) not null,
    OPERATION_NAME varchar(30) not null,
    OPERATION_TIME datetime not null,
    OPERATION_RESULT varchar(15) not null
);

 # 创建货物信息表
 create table wms_goods
 (
	GOOD_ID int not null auto_increment,
    GOOD_NAME varchar(30) not null,
    GOOD_TYPE varchar(20),
    GOOD_SIZE varchar(20),
    GOOD_WEIGHT float not null,
    primary key(GOOD_ID)
 )engine=innodb;

 # 导入货物信息
INSERT INTO `wms_goods` VALUES (1001,'A3233','耳机','10*5*5',0.1),(1002,'A3234','耳机','10*5*5',0.1),(1003,'A3235','耳机','10*5*5',0.1);

 # 创建客户信息表
create table wms_customer
(
	CUSTOMER_ID int not null auto_increment,
    CUSTOMER_NAME varchar(30) not null,
    CUSTOMER_PERSON varchar(10) not null,
    CUSTOMER_TEL varchar(20) not null,
    CUSTOMER_EMAIL varchar(20) not null,
    CUSTOMER_ADDRESS varchar(30) not null,
    primary key(CUSTOMER_ID)
 )engine=innodb;

 # 导入客户信息
INSERT INTO `wms_customer` VALUES (2001,'Anker','陈娟','17716786888','23369888@136.com','中国 湖南'),(2002,'深圳市松林达电子有限公司','刘明','85263335-820','85264958@126.com','中国 广东 深圳市宝安区'),(2003,'郑州绿之源饮品有限公司 ','赵志敬','87094196','87092165@qq.com','中国 河南 郑州市 郑州市嘉亿东方大厦609');

 # 创建仓库信息表
 create table wms_repository
 (
	REPO_ID int not null auto_increment,
    REPO_ADDRESS varchar(30) not null,
    REPO_STATUS varchar(20) not null,
    REPO_AREA varchar(20) not null,
    REPO_DESC varchar(50),
    primary key(REPO_ID)
 )engine=innodb;

  # 导入仓库信息
INSERT INTO `wms_repository` VALUES (3001,'德国','可用','11000㎡','提供服务完整'),(3002,'中国深圳','可用','5500㎡','备用测试');

 create table wms_packet
 (
	PACKET_ID int not null auto_increment,
    PACKET_TRACE varchar(30) not null,
    PACKET_TIME datetime not null,
    PACKET_STATUS varchar(15) not null,
    PACKET_DESC varchar(50),
    PACKET_REPOID int not null,
    primary key(PACKET_ID),
    foreign key(PACKET_REPOID) references wms_repository(REPO_ID)
 )engine=innodb;


INSERT INTO `wms_packet` VALUES (1,'DHL000','2019-09-17 08:59:55','发货中','DHL111,DHL222',3001),(2,'00340456','2019-08-22 08:59:55','发货中','', 3001);

 create table wms_packet_ref
(
    PACKET_ID int not null auto_increment,
    PACKET_TRACE varchar(30) not null,
    PACKET_REF_ID int not null,
    primary key(PACKET_ID),
    foreign key(PACKET_REF_ID) references wms_packet(PACKET_ID)
)engine=innodb;

INSERT INTO `wms_packet_ref` VALUES (1,'DHL000',1),(2,'DHL111',1),(3,'DHL222',1),(4,'00340456',2);

create table wms_packet_storage
(
    PRE_PACKETID int not null,
    PRE_GOODID int not null,
    PRE_REPOSITORYID int not null,
    PRE_NUMBER int not null,
    PRE_STORAGE int not null,
    primary key(PRE_PACKETID,PRE_GOODID),
    foreign key(PRE_PACKETID) references wms_packet(PACKET_ID),
    foreign key(PRE_GOODID) references  wms_goods(GOOD_ID),
    foreign key(PRE_REPOSITORYID) references wms_repository(REPO_ID)
)engine=innodb;

  # 创建仓库管理员信息表
 create table wms_repo_admin
 (
	REPO_ADMIN_ID int not null auto_increment,
    REPO_ADMIN_NAME varchar(10) not null,
    REPO_ADMIN_SEX varchar(10) not null,
    REPO_ADMIN_TEL varchar(20) not null,
    REPO_ADMIN_ADDRESS varchar(30) not null,
    REPO_ADMIN_BIRTH datetime not null,
    REPO_ADMIN_REPOID int,
    primary key(REPO_ADMIN_ID),
    foreign key (REPO_ADMIN_REPOID) references wms_repository(REPO_ID)
)engine=innodb;

 #创建仓库批次信息表
create table wms_repo_batch
(
    REPO_BATCH_ID int not null auto_increment,
    REPO_BATCH_CODE varchar(20) not null,
    REPO_BATCH_STATUS varchar(10) not null,
    REPO_BATCH_TIME datetime not null,
    REPO_BATCH_DESC varchar(50),
    REPO_BATCH_REPOID int not null,
    primary key (REPO_BATCH_ID),
    foreign key (REPO_BATCH_REPOID) references wms_repository(REPO_ID)
)engine=innodb;

INSERT INTO `wms_repo_batch` VALUES (1,'Anker','可用','2019-09-17 08:59:55','', 3001),(2,'其他客户','可用','2019-08-20 08:59:55','', 3001);

# 创建入库记录表
create table wms_record_in
(
	RECORD_ID int not null auto_increment,
	RECORD_GOODID int not null,
	RECORD_BATCHID int not null,
    RECORD_CUSTOMERID int not null,
    RECORD_NUMBER int not null,
    RECORD_TIME datetime not null,
    RECORD_PERSON varchar(10) not null,
    RECORD_REPOSITORYID int not null,
    primary key(RECORD_ID),
    foreign key(RECORD_GOODID) references wms_goods(GOOD_ID),
    foreign key(RECORD_BATCHID) references wms_repo_batch(REPO_BATCH_ID),
    foreign key(RECORD_CUSTOMERID) references wms_customer(CUSTOMER_ID),
    foreign key(RECORD_REPOSITORYID) references wms_repository(REPO_ID)
)engine=innodb;

# 创建出库记录表
create table wms_record_out
(
	RECORD_ID int not null auto_increment,
	RECORD_GOODID int not null,
	RECORD_BATCHID int not null,
    RECORD_CUSTOMERID int not null,
    RECORD_NUMBER int not null,
    RECORD_TIME datetime not null,
    RECORD_PERSON varchar(10) not null,
    RECORD_REPOSITORYID int not null,
    primary key(RECORD_ID),
    foreign key(RECORD_GOODID) references wms_goods(GOOD_ID),
    foreign key(RECORD_BATCHID) references wms_repo_batch(REPO_BATCH_ID),
    foreign key(RECORD_CUSTOMERID) references wms_customer(CUSTOMER_ID),
    foreign key(RECORD_REPOSITORYID) references wms_repository(REPO_ID)
)engine=innodb;

# 创建库存记录表
create table wms_record_storage
(
	RECORD_GOODID int not null,
	RECORD_BATCHID int not null,
	RECORD_REPOSITORY int not null,
    RECORD_NUMBER int not null,
    primary key(RECORD_GOODID, RECORD_BATCHID),
    foreign key (RECORD_GOODID) references wms_goods(GOOD_ID),
    foreign key (RECORD_BATCHID) references wms_repo_batch(REPO_BATCH_ID),
    foreign key (RECORD_REPOSITORY) references wms_repository(REPO_ID)
)engine=innodb;

#创建检测信息表
create table wms_detect
(
    DETECT_ID int not null auto_increment,
    DETECT_GOODID int not null,
    DETECT_BATCHID int not null,
    DETECT_REPOSITORYID int not null,
    DETECT_NUMBER int not null,
    DETECT_PASSED int not null,
    DETECT_SCRATCH int not null,
    DETECT_DAMAGE int not null,
    DETECT_TIME datetime not null,
    DETECT_PERSON varchar(10) not null,
    DETECT_DESC varchar(50),
    primary key(DETECT_ID),
    foreign key (DETECT_GOODID) references wms_goods(GOOD_ID),
    foreign key (DETECT_BATCHID) references wms_repo_batch(REPO_BATCH_ID),
    foreign key (DETECT_REPOSITORYID) references wms_repository(REPO_ID)
)engine=innodb;

#创建检测库存
create table wms_detect_storage
(
    DETECT_GOODID int not null,
    DETECT_BATCHID int not null,
    DETECT_REPOSITORY int not null,
    DETECT_NUMBER int not null,
    DETECT_PASSED int not null,
    DETECT_SCRATCH int not null,
    DETECT_DAMAGE int not null,
    primary key(DETECT_GOODID, DETECT_BATCHID),
    foreign key (DETECT_GOODID) references wms_goods(GOOD_ID),
    foreign key (DETECT_BATCHID) references wms_repo_batch(REPO_BATCH_ID),
    foreign key (DETECT_REPOSITORY) references wms_repository(REPO_ID)
)engine=innodb;
