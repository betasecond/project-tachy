-- This migration changes the primary key columns from SERIAL (integer) to BIGSERIAL (bigint)
-- to match the `Long` type in the JPA entities.

-- For users table
ALTER TABLE users ALTER COLUMN id TYPE BIGINT;
ALTER SEQUENCE users_id_seq AS BIGINT;

-- For agents table
ALTER TABLE agents ALTER COLUMN id TYPE BIGINT;
ALTER SEQUENCE agents_id_seq AS BIGINT;

-- For conversations table
ALTER TABLE conversations ALTER COLUMN id TYPE BIGINT;
ALTER SEQUENCE conversations_id_seq AS BIGINT;

-- For messages table
ALTER TABLE messages ALTER COLUMN id TYPE BIGINT;
ALTER SEQUENCE messages_id_seq AS BIGINT;

-- For data_sources table
ALTER TABLE data_sources ALTER COLUMN id TYPE BIGINT;
ALTER SEQUENCE data_sources_id_seq AS BIGINT;

-- For report_tasks table
ALTER TABLE report_tasks ALTER COLUMN id TYPE BIGINT;
ALTER SEQUENCE report_tasks_id_seq AS BIGINT; 