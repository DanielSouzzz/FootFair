CREATE DATABASE IF NOT EXISTS footflair;

USE footflair;

CREATE TABLE IF NOT EXISTS players (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role TINYINT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    token VARCHAR(100) NOT NULL UNIQUE,
    speed TINYINT CHECK (speed BETWEEN 0 AND 100),
    passing TINYINT CHECK (passing BETWEEN 0 AND 100),
    shooting TINYINT CHECK (shooting BETWEEN 0 AND 100),
    defense TINYINT CHECK (defense BETWEEN 0 AND 100),
    stamina TINYINT CHECK (stamina BETWEEN 0 AND 100)
);

CREATE TABLE IF NOT EXISTS squads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    create_player_id INT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (create_player_id) REFERENCES players(id)
    );

CREATE TABLE IF NOT EXISTS player_squad (
    player_id INT NOT NULL,
    squad_id INT NOT NULL,
    PRIMARY KEY (player_id, squad_id),
    FOREIGN KEY (player_id) REFERENCES players(id),
    FOREIGN KEY (squad_id) REFERENCES squads(id)
);

CREATE TABLE IF NOT EXISTS positions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS positions_player (
    player_id INT NOT NULL,
    position_id INT NOT NULL,
    PRIMARY KEY (player_id, position_id),

    FOREIGN KEY (player_id) REFERENCES players(id),
    FOREIGN KEY (position_id) REFERENCES positions(id)
);

CREATE TABLE IF NOT EXISTS games (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    location VARCHAR(100) NOT NULL,
    squad_id INT NOT NULL UNIQUE,

    FOREIGN KEY (squad_id) REFERENCES squads(id)
);

CREATE TABLE IF NOT EXISTS evaluations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    evaluator_id INT NOT NULL,
    evaluated_id INT NOT NULL,
    game_id INT,
    squad_id INT NOT NULL,
    speed TINYINT CHECK (speed BETWEEN 0 AND 100),
    passing TINYINT CHECK (passing BETWEEN 0 AND 100),
    shooting TINYINT CHECK (shooting BETWEEN 0 AND 100),
    defense TINYINT CHECK (defense BETWEEN 0 AND 100),
    stamina TINYINT CHECK (stamina BETWEEN 0 AND 100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (evaluator_id) REFERENCES players(id),
    FOREIGN KEY (evaluated_id) REFERENCES players(id),
    FOREIGN KEY (game_id) REFERENCES games(id),
    FOREIGN KEY (squad_id) REFERENCES squads(id)
    );





