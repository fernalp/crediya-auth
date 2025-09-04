-- Create sequence for roles
CREATE SEQUENCE IF NOT EXISTS roles_id_seq START 1 INCREMENT 1;

-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY DEFAULT nextval('roles_id_seq'),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
);

-- Set ownership of the sequence to the roles table
ALTER SEQUENCE roles_id_seq OWNED BY roles.id;

-- Create sequence for users
CREATE SEQUENCE IF NOT EXISTS users_id_seq START 1 INCREMENT 1;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY DEFAULT nextval('users_id_seq'),
    id_number VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    address TEXT,
    phone VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    salary DECIMAL(15, 2) NOT NULL,
    role_id INTEGER NOT NULL REFERENCES roles(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_salary_positive CHECK (salary >= 0)
);

-- Set ownership of the sequence to the users table
ALTER SEQUENCE users_id_seq OWNED BY users.id;

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_id_number ON users(id_number);
CREATE INDEX IF NOT EXISTS idx_users_role_id ON users(role_id);

-- Insert default roles
INSERT INTO roles (id, name, description) VALUES
    (1, 'ADMIN', 'Administrator with full access'),
    (2, 'ADVISOR', 'Advisor with full access for users'),
    (3, 'CUSTOMER', 'Regular customer user')
ON CONFLICT (name) DO NOTHING;

-- Update the sequence to the latest ID
SELECT setval('roles_id_seq', (SELECT COALESCE(MAX(id), 1) FROM roles), true);
