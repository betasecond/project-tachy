-- V6__Update_user_passwords_with_bcrypt.sql
-- 更新用户密码为BCrypt哈希值

-- 更新默认用户密码：password123
-- BCrypt哈希: $2a$10$N.ZI.WQV5eKPJlyEaDqvxOl5rqzVwmF3DSJS.QLqSs.uLuXjF6Qh2
UPDATE users 
SET password_hash = '$2a$10$N.ZI.WQV5eKPJlyEaDqvxOl5rqzVwmF3DSJS.QLqSs.uLuXjF6Qh2'
WHERE email = 'default@example.com';

-- 更新测试用户密码：test123
-- BCrypt哈希: $2a$10$PQzlcM8g1k8RvFPKo.8mEu6YBL6FX0cQ2D6YXpYqKJgHZhYZqYfLy
UPDATE users 
SET password_hash = '$2a$10$PQzlcM8g1k8RvFPKo.8mEu6YBL6FX0cQ2D6YXpYqKJgHZhYZqYfLy'
WHERE email = 'test@example.com'; 