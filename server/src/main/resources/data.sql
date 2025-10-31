-- ==============================================================
-- 🏦 SECURE PAYMENT GATEWAY DATABASE (v2)
-- Author: K. Surya Teja
-- Description: Updated with role column, password hashing, PDF-ready transactions
-- ==============================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- DROP TABLES (development only)
-- DROP TABLE IF EXISTS audit_logs, transactions, merchants, users CASCADE;

-- ==============================================================
-- USERS TABLE
-- ==============================================================
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    password_hash VARCHAR(200) NOT NULL,
    card_token VARCHAR(50) UNIQUE NOT NULL,
    balance NUMERIC(15,2) DEFAULT 0.00 CHECK (balance >= 0),
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================================
-- MERCHANTS TABLE
-- ==============================================================
CREATE TABLE IF NOT EXISTS merchants (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    balance NUMERIC(15,2) DEFAULT 0.00 CHECK (balance >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================================
-- TRANSACTIONS TABLE
-- ==============================================================
CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    transaction_ref VARCHAR(30) UNIQUE NOT NULL,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    merchant_id INT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    amount NUMERIC(15,2) CHECK (amount > 0),
    status VARCHAR(20) CHECK (status IN ('AUTHORIZED', 'DECLINED')),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_transactions_user ON transactions(user_id);

-- ==============================================================
-- AUDIT LOGS TABLE
-- ==============================================================
CREATE TABLE IF NOT EXISTS audit_logs (
    id SERIAL PRIMARY KEY,
    txn_id INT REFERENCES transactions(id) ON DELETE CASCADE,
    event TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================================================
-- SEED DATA
-- ==============================================================
INSERT INTO users (name, email, password_hash, card_token, balance, role)
VALUES
('Surya Teja', 'surya@gmail.com', crypt('surya123', gen_salt('bf')), 'TOK-1111-2222', 100000.00, 'USER'),
('Ravi Kumar', 'ravi@gmail.com', crypt('ravi123', gen_salt('bf')), 'TOK-3333-4444', 65000.00, 'USER'),
('Anjali Sharma', 'anjali@gmail.com', crypt('anjali123', gen_salt('bf')), 'TOK-5555-6666', 48000.00, 'USER');

INSERT INTO merchants (name, category, balance) VALUES
('Amazon India', 'E-commerce', 0.00),
('Flipkart', 'E-commerce', 0.00),
('BigBasket', 'Groceries', 0.00),
('IRCTC', 'Travel', 0.00);

-- SAMPLE TRANSACTIONS
INSERT INTO transactions (transaction_ref, user_id, merchant_id, amount, status)
VALUES
('TXN-0001', 1, 1, 2500.00, 'AUTHORIZED'),
('TXN-0002', 1, 2, 48000.00, 'AUTHORIZED');

SELECT password_hash = crypt('surya123', password_hash) AS is_valid
FROM users
WHERE email = 'surya@example.com';
