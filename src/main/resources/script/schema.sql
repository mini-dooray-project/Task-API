# DROP TABLE `project_authority`;
# DROP TABLE `project_status`;
# DROP TABLE `project`;


CREATE TABLE `project_authority`
(
    `authority_id`   BigInt AUTO_INCREMENT NOT NULL,
    `authority_name` varchar(20)           NOT NULL,
    primary key (`authority_id`)
);

CREATE TABLE `project_status`
(
    `status_id` BigInt AUTO_INCREMENT NOT NULL,
    `status_name`    varchar(20)           NOT NULL,
    primary key (`status_id`)
);

CREATE TABLE `project`
(
    `project_id`   BigInt AUTO_INCREMENT NOT NULL,
    `status_id`    BigInt                NULL,
    `project_name` varchar(100)          NOT NULL,
    primary key (`project_id`),
    foreign key (`status_id`) references project_status (`status_id`) on delete set null
);

CREATE TABLE `project_member`
(
    `account_id`   varchar(20) NOT NULL,
    `project_id`   BigInt      NOT NULL,
    `authority_id` BigInt      NULL,
    primary key (`account_id`, `project_id`),
    foreign key (`project_id`) references project (project_id) on delete cascade,
    foreign key (`authority_id`) references project_authority (`authority_id`) on delete set null
);

CREATE TABLE `milestone`
(
    `milestone_id`   BigInt AUTO_INCREMENT NOT NULL,
    `project_id`     BigInt                NOT NULL,
    `milestone_name` varchar(20)           NOT NULL,
    `start_date` datetime null ,
    `expire_date` datetime null ,
    primary key (milestone_id),
    foreign key (`project_id`) references project (`project_id`) on delete cascade
);

CREATE TABLE `task`
(
    `task_id`            BigInt AUTO_INCREMENT NOT NULL,
    `milestone_id`       BigInt                NULL,
    `project_id`         BigInt                NOT NULL,
    `title`              varchar(255)          NOT NULL,
    `content`            text                  NULL,
    `registrant_account` varchar(20)           NOT NULL,
    `created_date`       datetime              NOT NULL,
    `expire_date`        datetime              NULL,
    primary key (`task_id`),
    foreign key (`milestone_id`) references milestone (milestone_id) on delete set null,
    foreign key (`project_id`) references project (project_id) on delete cascade
);

CREATE TABLE `tag`
(
    `tag_id`     BigInt AUTO_INCREMENT NOT NULL,
    `project_id` BigInt                NOT NULL,
    `tag_name`   varchar(100)          NOT NULL,
    primary key (`tag_id`),
    foreign key (`project_id`) references project (`project_id`) on delete cascade
);

CREATE TABLE `comment`
(
    `comment_id`         BigInt AUTO_INCREMENT NOT NULL,
    `task_id`            BigInt                NOT NULL,
    `registrant_account` varchar(20)           NOT NULL,
    `created_date`       datetime              NOT NULL,
    `content`            text                  NOT NULL,
    primary key (`comment_id`),
    foreign key (`task_id`) references task (`task_id`) on delete cascade
);

CREATE TABLE `task_tag`
(
    `tag_id`  BigInt NOT NULL,
    `task_id` BigInt NOT NULL,
    primary key (`task_id`, `tag_id`),
    foreign key (`tag_id`) references tag (`tag_id`) on delete cascade ,
    foreign key (`task_id`) references task (`task_id`) on delete cascade
);