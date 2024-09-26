INSERT INTO role (name)
SELECT * FROM (SELECT 'Project Manager' AS name
               UNION ALL
               SELECT 'Senior Developer'
               UNION ALL
               SELECT 'Junior Developer') AS new_roles
WHERE NOT EXISTS (SELECT 1 FROM role);
