'CREATE DATABASE restaurante;

USE restaurante;

CREATE TABLE produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    categoria VARCHAR(50)
);

CREATE TABLE venda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data_venda DATETIME DEFAULT CURRENT_TIMESTAMP,
    valor_total DECIMAL(10,2)
);

CREATE TABLE item_venda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    venda_id INT,
    produto_id INT,
    quantidade INT,
    subtotal DECIMAL(10,2),

    FOREIGN KEY (venda_id) REFERENCES venda(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);'
