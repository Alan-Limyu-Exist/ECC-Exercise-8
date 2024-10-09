CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO role (uuid, name)
SELECT * FROM (
    SELECT gen_random_uuid() AS uuid, 'Project Manager' AS name
    UNION ALL
    SELECT gen_random_uuid(), 'Senior Developer'
    UNION ALL
    SELECT gen_random_uuid(), 'Junior Developer'
) AS new_roles
WHERE NOT EXISTS (SELECT 1 FROM role WHERE role.name = new_roles.name);

