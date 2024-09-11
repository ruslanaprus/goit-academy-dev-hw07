INSERT INTO worker (name, birthday, email, level, salary)
VALUES (?, ?, ?, ?, ?)
ON CONFLICT DO NOTHING;

INSERT INTO client (name)
VALUES (?)
ON CONFLICT DO NOTHING;

INSERT INTO project (name, client_id, start_date, finish_date)
VALUES (?, ?, ?, ?)
ON CONFLICT DO NOTHING;