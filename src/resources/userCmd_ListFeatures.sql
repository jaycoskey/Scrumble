SELECT f.feature_id, own.owner_name AS owner, stat.status_name AS status,
    f.priority, f.title, f.description, f.min_start, f.max_finish
FROM feature AS f
JOIN feature_owner  AS fown  ON fown.feature_owner_id   = f.feature_owner_id
JOIN owner          AS own   ON own.owner_id            = fown.owner_id
JOIN feature_status AS fstat ON fstat.feature_status_id = f.feature_status_id
JOIN status         AS stat  ON stat.status_id          = fstat.status_id