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

CREATE TABLE estante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    localizacao VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL
);

CREATE TABLE lote (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_semeadura DATE NOT NULL,
    status ENUM(
        'EM_GERMINACAO',
        'EM_CRESCIMENTO',
        'PRONTO_PARA_COLHEITA',
        'COLHIDO',
        'CANCELADO'
    ) NOT NULL DEFAULT 'EM_GERMINACAO',
    data_colheita_estimada DATE NOT NULL,
    qtd_bandejas INT NOT NULL,
    area_ocupada DOUBLE NOT NULL,
    id_cultura INT NOT NULL,
    id_estante INT NOT NULL,

    CONSTRAINT fk_lote_cultura
        FOREIGN KEY (id_cultura)
        REFERENCES cultura(id_cultura),

    CONSTRAINT fk_lote_estante
        FOREIGN KEY (id_estante)
        REFERENCES estante(id)
);