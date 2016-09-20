USE scrumble;

INSERT INTO owner (`owner_id`, `owner_name`) VALUES (1, "Amy");
INSERT INTO owner (`owner_id`, `owner_name`) VALUES (2, "Bob");
INSERT INTO owner (`owner_id`, `owner_name`) VALUES (8, "Hal");
INSERT INTO owner (`owner_id`, `owner_name`) VALUES (9, "Ida");
INSERT INTO owner (`owner_id`, `owner_name`) VALUES (11, "Kim");
INSERT INTO owner (`owner_id`, `owner_name`) VALUES (16, "Pam");
INSERT INTO owner (`owner_id`, `owner_name`) VALUES (18, "Rex");

INSERT INTO status (`status_id`, `status_name`, `description`)
    VALUES
        (1, "DAWN", "Not started, due to time constraint"),
        (2, "DONE", "Completed"),
        (3, "DUSK", "Stopped, due to time constraint"),
        (4, "PROG", "In progress"),
        (5, "HOLD", "Paused for internal reasons"),
        (6, "WAIT", "Paused for external reasons"),
        (7, "ABRT", "Stopped, for any other reason")
;

INSERT INTO task_owner (task_owner_id, owner_id)
    VALUES (1, 1), (2, 2), (8, 8), (9, 9), (10, 10), (11, 11)
;

INSERT INTO task_status (task_status_id, status_id)
    VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7)
;

INSERT INTO task (task_id, task_owner_id, task_status_id, priority, title, description, min_start, max_finish)
    VALUES
        (1, 1, 2, 100, "Solve world hunger", null, null, null),
        (2, 1, 4, 200, "Swords to plowshares", null, null, null),
        (3, 1, 3, 300, "Cure cancer", null, null, null),
        (4, 2, 2, 10, "Brush teeth", null, null, null),
        (5, 2, 4, 30, "Get dressed", null, null, null),
        (6, 2, 3, 50, "Eat breakfast", null, null, null)
;

INSERT INTO feature_owner (feature_owner_id, owner_id)
    VALUES (16, 16), (18, 18)
;

INSERT INTO feature_status (feature_status_id, status_id)
    VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7)
;

INSERT INTO feature (feature_id, feature_owner_id, feature_status_id, priority, title, description, min_start, max_finish)
    VALUES
        (1, 16, 4, 25, "Kickstart morning", null, null, null),
        (2, 18, 4, 75, "Change world", null, null, null)
;

INSERT INTO feature_task (feature_id, task_id)
    VALUES
        (1, 1), (1, 2), (1, 3),
        (2, 4), (2, 5), (2, 6)
;
