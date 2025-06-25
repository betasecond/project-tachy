-- This migration changes foreign key columns from INTEGER to BIGINT to match the
-- BIGINT type of the primary keys they reference.

-- For conversations table
ALTER TABLE conversations ALTER COLUMN user_id TYPE BIGINT;
ALTER TABLE conversations ALTER COLUMN agent_id TYPE BIGINT;

-- For messages table
ALTER TABLE messages ALTER COLUMN conversation_id TYPE BIGINT;

-- For data_sources table
ALTER TABLE data_sources ALTER COLUMN user_id TYPE BIGINT;

-- For report_tasks table
ALTER TABLE report_tasks ALTER COLUMN user_id TYPE BIGINT;
ALTER TABLE report_tasks ALTER COLUMN data_source_id TYPE BIGINT; 