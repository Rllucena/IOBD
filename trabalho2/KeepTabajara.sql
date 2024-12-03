DROP DATABASE IF EXISTS keeptabajara;


CREATE DATABASE keeptabajara;

\c keeptabajara;

CREATE TABLE autores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE anotacoes (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    descricao TEXT NOT NULL,
    cor VARCHAR(20),
    foto BYTEA,
    autor_id INT REFERENCES autores(id)
);

INSERT INTO autores (nome, senha) 
VALUES ('miquelangelo', 'senha321');
INSERT INTO autores (nome, senha) 
VALUES ('donatello', 'senha123');