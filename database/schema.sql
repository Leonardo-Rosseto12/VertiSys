CREATE DATABASE vertisys;

USE vertisys;

CREATE TABLE receita (
    id_receita INT AUTO_INCREMENT PRIMARY KEY,
    densidade_semeadura DECIMAL(10,2) NOT NULL,
    ph_medio DECIMAL(4,2) NOT NULL,
    ec_medio DECIMAL(10,2) NOT NULL,
    ciclo_dias INT NOT NULL,
    temp_ar_alvo DECIMAL(5,2) NOT NULL,
    fotoperiodo DECIMAL(5,2) NOT NULL
);

CREATE TABLE cultura (
    id_cultura INT AUTO_INCREMENT PRIMARY KEY,
    nome_popular VARCHAR(100) NOT NULL,
    nome_cientifico VARCHAR(150) NOT NULL,
    id_receita INT NOT NULL UNIQUE,

    FOREIGN KEY (id_receita)
        REFERENCES receita(id_receita)
);