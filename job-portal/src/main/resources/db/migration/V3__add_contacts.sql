CREATE TABLE IF NOT EXISTS contacts (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'NEW',
    subject VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(20),
    user_type VARCHAR(50) NOT NULL
);
