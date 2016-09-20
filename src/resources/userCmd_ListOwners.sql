SELECT own.owner_id, own.owner_name,
    CASE WHEN fown.owner_id IS NOT NULL THEN 'Y' ELSE 'N' END AS is_feature_owner,
    CASE WHEN town.owner_id IS NOT NULL THEN 'Y' ELSE 'N' END AS is_task_owner
FROM owner AS own
LEFT OUTER JOIN feature_owner  AS fown ON fown.feature_owner_id = own.owner_id
LEFT OUTER JOIN task_owner     AS town ON town.task_owner_id = own.owner_id
ORDER BY own.owner_id