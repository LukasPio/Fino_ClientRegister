CREATE DATABASE client;

-- Use a base de dados client
\c client;

-- Crie a tabela clients
CREATE TABLE IF NOT EXISTS clients(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crie a tabela disabled_clients
CREATE TABLE IF NOT EXISTS disabled_clients(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    disabled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Mostre os dados das tabelas
SELECT * FROM clients;

SELECT * FROM disabled_clients;
