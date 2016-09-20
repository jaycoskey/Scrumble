SELECT t.task_id, own.owner_name AS owner, stat.status_name AS status, t.priority,
    t.title, t.description, t.min_start, t.max_finish
FROM task AS t 
JOIN task_owner  AS town  ON town.task_owner_id   = t.task_owner_id
JOIN owner       AS own   ON own.owner_id         = town.owner_id
JOIN task_status AS tstat ON tstat.task_status_id = t.task_status_id
JOIN status      AS stat  ON stat.status_id       = tstat.status_id