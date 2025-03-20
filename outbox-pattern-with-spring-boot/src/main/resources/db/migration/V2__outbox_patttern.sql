CREATE TABLE outbox_messages (
    id character varying(60) PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    content JSONB NOT NULL,
    generated_at_utc TIMESTAMP WITH TIME ZONE NOT NULL,
    consumed_at_utc TIMESTAMP WITH TIME ZONE NULL,
    error TEXT NULL
);