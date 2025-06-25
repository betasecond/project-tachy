-- V5: Insert seed data for messages that failed in V4 due to a type error.
-- We use explicit casting to match the 'message_sender_type' enum.
INSERT INTO messages (id, conversation_id, sender_type, content) VALUES
(1, 1, 'user'::message_sender_type, 'Hi, can you help me analyze my sales data for Q1?'),
(2, 1, 'agent'::message_sender_type, 'Of course! Please upload your data source.'),
(3, 1, 'user'::message_sender_type, 'I have uploaded a CSV file named `q1_sales.csv`.')
ON CONFLICT (id) DO NOTHING; 