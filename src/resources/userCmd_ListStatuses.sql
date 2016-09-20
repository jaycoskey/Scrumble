SELECT stat.status_id, stat.status_name,
    CASE WHEN fstat.status_id IS NOT NULL THEN 'Y' ELSE 'N' END AS is_feature_status,
    CASE WHEN tstat.status_id IS NOT NULL THEN 'Y' ELSE 'N' END AS is_task_status
FROM status AS stat
LEFT OUTER JOIN feature_status AS fstat ON fstat.feature_status_id = stat.status_id
LEFT OUTER JOIN task_status    AS tstat ON tstat.task_status_id = stat.status_id
ORDER BY stat.status_id