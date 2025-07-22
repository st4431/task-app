CREATE TABLE IF NOT EXISTS task (
 id BIGSERIAL PRIMARY KEY,
 title TEXT NOT NULL,
 description TEXT,
 task_status VARCHAR(50) NOT NULL,
 due_date date,
 created_at TIMESTAMP NOT NULL DEFAULT NOW(),
 updated_at TIMESTAMP NOT NULL DEFAULT NOW()
)
;

