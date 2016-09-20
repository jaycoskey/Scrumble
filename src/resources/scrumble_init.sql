USE scrumble;

INSERT INTO owner (`owner_id`, `name`) VALUES (0, `None`);
INSERT INTO status (`status_id`, `name`, `description`) VALUES (0, `None`, null);

INSERT INTO task_owner (`task_owner_id`, `owner_id`) VALUES (0, 0);
INSERT INTO task_status (`task_status_id`, `status_id`) VALUES (0, 0);
INSERT INTO feature_owner (`feature_owner_id`, `owner_id`) VALUES (0, 0);
INSERT INTO feature_status (`feature_status_id`, `status_id`) VALUES (0, 0);