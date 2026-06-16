BEGIN;

ALTER TABLE employees RENAME COLUMN departement_id TO department_id;

ALTER TABLE departements RENAME TO departments;

ALTER TABLE departments RENAME COLUMN departement_name TO department_name;

COMMIT;
