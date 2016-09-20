CREATE DATABASE IF NOT EXISTS scrumble;

USE scrumble;

 -- ====================
 -- COMMON TABLES
  -- ====================
-- Owner table is shared by feature_owner and task_owner.
CREATE TABLE `owner` (
   `owner_id` int(11) NOT NULL,
   `owner_name` varchar(50) NOT NULL,
   PRIMARY KEY (`owner_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
 -- Status table is shared by feature_status and task_status.
 CREATE TABLE `status` (
   `status_id` int(11) NOT NULL,
   `status_name` varchar(10) NOT NULL,
   `description` varchar(1000) NULL,
   PRIMARY KEY (`status_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
-- ====================
-- TASK TABLES
-- ====================
CREATE TABLE `task_owner` (
   `task_owner_id` int(11) NOT NULL,
   `owner_id` int(11) NOT NULL,
   PRIMARY KEY (`task_owner_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
 CREATE TABLE `task_status` (
   `task_status_id` int(11) NOT NULL,
   `status_id` int(11) NOT NULL,
   PRIMARY KEY (`task_status_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
CREATE TABLE `task` (
   `task_id` int(11) NOT NULL,
   `task_owner_id` int(11) NOT NULL DEFAULT '0',
   `task_status_id` int(11) NOT NULL DEFAULT '0',
   `priority` decimal(20,2) NOT NULL DEFAULT '0',
   `title` varchar(100) NOT NULL,
   `description` varchar(1000) NULL,
   `min_start` date NULL,
   `max_finish` date NULL,
   PRIMARY KEY (`task_id`),
   FOREIGN KEY (`task_owner_id`) 
        REFERENCES task_owner(`task_owner_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT, 
   FOREIGN KEY (`task_status_id`)
        REFERENCES task_status(`task_status_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
  CREATE TABLE `task_dependency` (
   `dep_type` char(2) NOT NULL,
   `task1_id` int NOT NULL,
   `task2_id` int NOT NULL,
   PRIMARY KEY (`dep_type`,`task1_id`, `task2_id`),
   FOREIGN KEY (task1_id) 
        REFERENCES task(task_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
   FOREIGN KEY (task2_id)
        REFERENCES task(task_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
 CREATE TABLE `task_effort` (
   `record_date` date NOT NULL,
   `task_id` int(11) NOT NULL,
   `tot_eff` decimal(10,2) NOT NULL,
   `inv_eff` decimal(10,2) NOT NULL,
   `rem_eff` decimal(10,2) NOT NULL,
   PRIMARY KEY (`record_date`,`task_id`),
   FOREIGN KEY (task_id) 
        REFERENCES task(task_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
 -- ====================
 -- PROJECT TABLES
  -- ====================
CREATE TABLE `feature_owner` (
   `feature_owner_id` int(11) NOT NULL,
   `owner_id` int(11) NOT NULL,
   PRIMARY KEY (`feature_owner_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
 CREATE TABLE `feature_status` (
   `feature_status_id` int(11) NOT NULL,
   `status_id` int(11) NOT NULL,
   PRIMARY KEY (`feature_status_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
CREATE TABLE `feature` (
   `feature_id` int(11) NOT NULL,
   `feature_owner_id` int(11) NOT NULL DEFAULT '0',
   `feature_status_id` int(11) NOT NULL DEFAULT '0',
   `priority` decimal(20,2) NOT NULL DEFAULT '0',
   `title` varchar(100) NOT NULL,
   `description` varchar(1000) NULL,
   `min_start` date NULL,
   `max_finish` date NULL,
   PRIMARY KEY (`feature_id`),
   FOREIGN KEY (`feature_owner_id`)
        REFERENCES feature_owner(`feature_owner_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT,
   FOREIGN KEY (`feature_status_id`)
        REFERENCES feature_status(`feature_status_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
  CREATE TABLE `feature_dependency` (
   `dep_type` char(2) NOT NULL,
   `feature1_id` int NOT NULL,
   `feature2_id` int NOT NULL,
   PRIMARY KEY (`dep_type`,`feature1_id`, `feature2_id`),
   FOREIGN KEY (`feature1_id`) 
        REFERENCES feature(`feature_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT, 
   FOREIGN KEY (`feature2_id`) 
        REFERENCES feature(`feature_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;
 
  -- Note: No table `task_effort`.  Effort is entirely tracked via task.
  
 -- ====================
 -- PROJECT-TASK TABLE
 -- ====================
 CREATE TABLE `feature_task` (
   `feature_id` int NOT NULL,
   `task_id` int NOT NULL,
   PRIMARY KEY (`feature_id`,`task_id`),
   FOREIGN KEY (`feature_id`) 
        REFERENCES feature(`feature_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT, 
   FOREIGN KEY (`task_id`) 
        REFERENCES task(`task_id`)
        ON UPDATE CASCADE ON DELETE RESTRICT
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 ;