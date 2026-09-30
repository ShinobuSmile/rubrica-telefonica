-- ============================================
-- Schema database per Rubrica Telefonica
-- ============================================

CREATE DATABASE IF NOT EXISTS rubrica
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE rubrica;

-- Tabella delle persone della rubrica
CREATE TABLE IF NOT EXISTS persone (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    cognome   VARCHAR(100) NOT NULL,
    indirizzo VARCHAR(255),
    telefono  VARCHAR(20)  NOT NULL,
    eta       INT          NOT NULL
);

-- Tabella degli utenti (per il login)
CREATE TABLE IF NOT EXISTS utenti (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Utente di default (admin/admin)
INSERT INTO utenti (username, password)
SELECT 'admin', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM utenti WHERE username = 'admin');