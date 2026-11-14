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

CREATE TABLE sensor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    localizacao VARCHAR(100) NOT NULL,
    tipo ENUM('HIDROPONICO', 'AMBIENTAL') NOT NULL,
    id_lote INT NOT NULL,
    FOREIGN KEY (id_lote) REFERENCES lote(id)
);

CREATE TABLE leitura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_hora DATETIME NOT NULL,
    id_sensor INT NOT NULL,
    FOREIGN KEY (id_sensor) REFERENCES sensor(id)
);

CREATE TABLE leitura_hidroponica (
    id_leitura INT PRIMARY KEY,
    ph DECIMAL(4,2) NOT NULL,
    ec DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_leitura) REFERENCES leitura(id)
);

CREATE TABLE leitura_ambiental (
    id_leitura INT PRIMARY KEY,
    temp_ar DECIMAL(5,2) NOT NULL,
    umidade DECIMAL(5,2) NOT NULL,
    co2 DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_leitura) REFERENCES leitura(id)
);

CREATE TABLE alerta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    criticidade VARCHAR(20) NOT NULL,
    parametro VARCHAR(30) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    data_hora DATETIME NOT NULL,
    id_leitura INT NOT NULL,
    FOREIGN KEY (id_leitura) REFERENCES leitura(id)
);

CREATE TABLE colheita (
    id_lote INT PRIMARY KEY,
    data_colheita DATE NOT NULL,
    peso_biomassa DECIMAL(10,3) NOT NULL,
    peso_descarte DECIMAL(10,3) NOT NULL,

    CONSTRAINT fk_colheita_lote
        FOREIGN KEY (id_lote)
        REFERENCES lote(id),

    CONSTRAINT chk_peso_biomassa
        CHECK (peso_biomassa > 0),

    CONSTRAINT chk_peso_descarte
        CHECK (peso_descarte >= 0 AND peso_descarte <= peso_biomassa)
);