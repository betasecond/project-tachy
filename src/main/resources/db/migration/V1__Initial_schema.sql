-- For messages.sender_type
CREATE TYPE message_sender_type AS ENUM ('user', 'agent');

-- For data_sources.type
CREATE TYPE data_source_type AS ENUM ('CSV', 'PostgreSQL', 'API');

-- For data_sources.status
CREATE TYPE data_source_status AS ENUM ('Active', 'Inactive', 'Error');

-- For report_tasks.status
CREATE TYPE task_status AS ENUM ('Completed', 'Processing', 'Pending', 'Failed');

-- users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    avatar_url TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- agents table
CREATE TABLE agents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    avatar_url TEXT,
    compatible_sources TEXT[] -- 字符串数组, 例如: {'Sales Data', 'CRM Exports'}
);

-- conversations table
CREATE TABLE conversations (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    agent_id INTEGER REFERENCES agents(id) ON DELETE SET NULL,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- messages table
CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    conversation_id INTEGER NOT NULL REFERENCES conversations(id) ON DELETE CASCADE,
    sender_type message_sender_type NOT NULL,
    content TEXT NOT NULL,
    metadata JSONB, -- 用于存储额外数据，如 'isTable: true'
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- data_sources table
CREATE TABLE data_sources (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    type data_source_type NOT NULL,
    status data_source_status NOT NULL DEFAULT 'Inactive',
    description TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- report_tasks table
CREATE TABLE report_tasks (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    data_source_id INTEGER NOT NULL REFERENCES data_sources(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    status task_status NOT NULL DEFAULT 'Pending',
    report_content TEXT, -- 存储最终报告，例如Markdown格式
    duration_ms INTEGER,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    completed_at TIMESTAMPTZ
); 