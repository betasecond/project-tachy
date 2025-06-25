-- V4__Insert_seed_data.sql

-- Reset sequences to ensure consistent IDs for seed data, especially for `users` which we manually set ID 1
ALTER SEQUENCE users_id_seq RESTART WITH 3;
ALTER SEQUENCE agents_id_seq RESTART WITH 3;
ALTER SEQUENCE conversations_id_seq RESTART WITH 2;
ALTER SEQUENCE messages_id_seq RESTART WITH 4;
ALTER SEQUENCE data_sources_id_seq RESTART WITH 2;


-- Insert Users
-- Insert a default user with a specific ID for predictability in development
INSERT INTO users (id, name, email, password_hash) VALUES (1, 'Default User', 'default@example.com', 'default_password_hash')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, email = EXCLUDED.email;

INSERT INTO users (id, name, email, password_hash) VALUES (2, 'Test User', 'test@example.com', 'test_password_hash')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, email = EXCLUDED.email;


-- Insert Agents
INSERT INTO agents (id, name, description, avatar_url, compatible_sources) VALUES
(1, 'Data Analyst', 'An agent specialized in analyzing structured data from CSVs and databases.', 'https://i.imgur.com/3s7iT1S.png', ARRAY['CSV', 'PostgreSQL']),
(2, 'Creative Writer', 'An agent that helps with creative writing tasks.', 'https://i.imgur.com/Q2eB3s3.png', NULL)
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description;


-- Insert a sample conversation
INSERT INTO conversations (id, user_id, agent_id, title) VALUES
(1, 1, 1, 'Initial analysis of sales data')
ON CONFLICT (id) DO UPDATE SET title = EXCLUDED.title;


-- Insert sample messages for the conversation
INSERT INTO messages (id, conversation_id, sender_type, content) VALUES
(1, 1, 'user', 'Hi, can you help me analyze my sales data for Q1?'),
(2, 1, 'agent', 'Of course! Please upload your data source.'),
(3, 1, 'user', 'I have uploaded a CSV file named `q1_sales.csv`.')
ON CONFLICT (id) DO NOTHING;


-- Insert a sample data source
INSERT INTO data_sources (id, user_id, name, type, status, description) VALUES
(1, 1, 'Q1 Sales Data', 'CSV', 'Active', 'First quarter sales data for 2024.')
ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, status = EXCLUDED.status; 