CREATE SCHEMA IF NOT EXISTS projetolpooii DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE projetolpooii;

CREATE TABLE IF NOT EXISTS cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    sobrenome VARCHAR(80) NOT NULL,
    rg VARCHAR(20) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    endereco VARCHAR(200) NOT NULL,
    UNIQUE KEY uk_cliente_rg (rg)
);

CREATE TABLE IF NOT EXISTS veiculo (
    id_veiculo INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    marca VARCHAR(40) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    modelo VARCHAR(60) NOT NULL,
    placa VARCHAR(8) NOT NULL UNIQUE,
    ano INT NOT NULL,
    valor_compra DECIMAL(12,2) NOT NULL,
    valor_locacao_diaria DECIMAL(12,2) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS locacao (
    id_locacao INT AUTO_INCREMENT PRIMARY KEY,
    id_veiculo INT NOT NULL,
    id_cliente INT NOT NULL,
    dias INT NOT NULL,
    valor_total DECIMAL(12,2) NOT NULL,
    data_locacao DATE NOT NULL,
    CONSTRAINT fk_locacao_cliente FOREIGN KEY (id_cliente)
        REFERENCES cliente (id_cliente)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_locacao_veiculo FOREIGN KEY (id_veiculo)
        REFERENCES veiculo (id_veiculo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    UNIQUE KEY uk_locacao_veiculo (id_veiculo)
);

CREATE TABLE IF NOT EXISTS historico_venda (
    id_venda INT AUTO_INCREMENT PRIMARY KEY,
    id_veiculo INT NOT NULL,
    data_venda DATE NOT NULL,
    valor_venda DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_venda_veiculo FOREIGN KEY (id_veiculo)
        REFERENCES veiculo (id_veiculo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    UNIQUE KEY uk_venda_veiculo (id_veiculo)
);
