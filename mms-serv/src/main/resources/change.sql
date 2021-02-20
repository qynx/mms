/* 添加 日期字段 */
ALTER TABLE `todo_list`
    ADD COLUMN `start_day` VARCHAR(16) NOT NULL DEFAULT '' AFTER `notify_status`,
ADD COLUMN `end_day` VARCHAR(16) NOT NULL DEFAULT '' AFTER `start_day`;

/* 添加 is_deleted字段 */
ALTER TABLE `todo_list`
    ADD COLUMN `is_deleted` TINYINT(3) NOT NULL DEFAULT 0 AFTER `end_day`;

