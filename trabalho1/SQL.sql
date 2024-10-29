-- Database: MediumFake

DROP DATABASE IF EXISTS MediumFake;

CREATE DATABASE MediumFake
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE postgres
    IS 'default administrative connection database';

-- Criação dos Schemas
CREATE SCHEMA portal_artigos;
CREATE SCHEMA analytics;

-- Tabela de Usuários
CREATE TABLE portal_artigos.usuario (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_nascimento DATE,
    telefone VARCHAR(15),
    endereco_bairro VARCHAR(255),
    endereco_complemento VARCHAR(255),
    endereco_numero VARCHAR(10),
    endereco_cep VARCHAR(8),
    endereco_rua VARCHAR(255)
);

-- Tabela de Categorias
CREATE TABLE portal_artigos.categoria (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) UNIQUE NOT NULL
);

-- Tabela de Artigos
CREATE TABLE portal_artigos.artigo (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    categoria_id INT NOT NULL,
    autor_principal_id INT NOT NULL,
    outros_autores INT[],
    FOREIGN KEY (categoria_id) REFERENCES portal_artigos.categoria(id),
    FOREIGN KEY (autor_principal_id) REFERENCES portal_artigos.usuario(id)
);

-- Tabela de Comentários
CREATE TABLE portal_artigos.comentario (
    id SERIAL PRIMARY KEY,
    conteudo TEXT NOT NULL,
    data_hora_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    artigo_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES portal_artigos.usuario(id),
    FOREIGN KEY (artigo_id) REFERENCES portal_artigos.artigo(id)
);

-- Tabela de Curtidas
CREATE TABLE portal_artigos.curtida (
    id SERIAL PRIMARY KEY,
    data_hora_curtida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    artigo_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES portal_artigos.usuario(id),
    FOREIGN KEY (artigo_id) REFERENCES portal_artigos.artigo(id)
);

--Inserção de dados para teste
-- Inserção de Usuários
INSERT INTO portal_artigos.usuario (email, nome, senha, data_nascimento, telefone, endereco_bairro, endereco_complemento, endereco_numero, endereco_cep, endereco_rua)
VALUES 
('joao@gmail.com', 'João Silva', 'senha123', '1990-01-01', '123456789', 'Centro', 'Apto 101', '50', '12345678', 'Rua Principal'),
('maria@yahoo.com', 'Maria Souza', 'senha456', '1985-02-15', '987654321', 'Jardim das Flores', 'Casa', '21', '87654321', 'Rua das Acácias'),
('carlos@hotmail.com', 'Carlos Oliveira', 'senha789', '1995-03-10', NULL, NULL, NULL, NULL, NULL, NULL),
('ana@gmail.com', 'Ana Costa', 'senha101', '1987-04-20', '111222333', 'Vila Nova', 'Apto 502', '85', '23456789', 'Avenida Brasil'),
('julia@outlook.com', 'Julia Pereira', 'senha102', '1992-07-18', NULL, NULL, NULL, NULL, NULL, NULL);

-- Inserção de Categorias
INSERT INTO portal_artigos.categoria (nome)
VALUES 
('Tecnologia'),
('Saúde'),
('Educação'),
('Esportes'),
('Cultura');

-- Inserção de Artigos
INSERT INTO portal_artigos.artigo (titulo, data_publicacao, categoria_id, autor_principal_id, outros_autores)
VALUES 
('As Novidades da IA em 2024', '2024-08-01 10:30:00', 1, 1, '{2}'), -- João com Maria como co-autora
('Como Manter uma Vida Saudável', '2024-07-15 08:20:00', 2, 2, '{3}'), -- Maria com Carlos como co-autor
('A Evolução da Educação Online', '2024-06-10 09:00:00', 3, 3, '{1, 4}'), -- Carlos com João e Ana como co-autores
('Esportes Radicais: Prós e Contras', '2024-05-22 11:00:00', 4, 4, NULL), -- Ana é a única autora
('A Influência da Música na Cultura', '2024-08-18 14:15:00', 5, 5, '{4}'); -- Julia com Ana como co-autora
('A Influência da Música na Moda', '2024-08-18 14:15:00', 1, 1, '{2}'); -- João com Maria como co-autora
('A Influência da Cultura na Música', '2024-08-18 14:15:00', 2, 2, '{3}'); -- Julia com Ana como co-autora

-- Inserção de Comentários
INSERT INTO portal_artigos.comentario (conteudo, data_hora_comentario, usuario_id, artigo_id)
VALUES 
('Excelente artigo sobre IA!', '2024-08-01 11:00:00', 2, 1), -- Maria comenta no artigo de IA
('Muito interessante, obrigado por compartilhar!', '2024-07-15 09:00:00', 4, 2), -- Ana comenta no artigo de saúde
('Achei muito informativo.', '2024-06-10 10:15:00', 1, 3), -- João comenta no artigo de educação
('Parabéns pelo conteúdo!', '2024-05-22 12:30:00', 5, 4), -- Julia comenta no artigo de esportes
('Realmente a música nos influencia muito.', '2024-08-18 15:00:00', 3, 5); -- Carlos comenta no artigo de cultura

-- Inserção de Curtidas
INSERT INTO portal_artigos.curtida (data_hora_curtida, usuario_id, artigo_id)
VALUES 
('2024-08-01 12:00:00', 3, 1), -- Carlos curte o artigo de IA
('2024-07-15 10:00:00', 5, 2), -- Julia curte o artigo de saúde
('2024-06-10 11:30:00', 2, 3), -- Maria curte o artigo de educação
('2024-05-22 13:45:00', 1, 4), -- João curte o artigo de esportes
('2024-08-18 16:30:00', 4, 5), -- Ana curte o artigo de cultura
('2024-08-18 17:30:00', 3, 5); -- Carlos curte o artigo de cultura

--Consultas
-- Consulta aos usuários com mais artigos
SELECT u.nome, COUNT(a.id) AS total_artigos
FROM portal_artigos.usuario u
LEFT JOIN portal_artigos.artigo a ON u.id = a.autor_principal_id
GROUP BY u.nome
HAVING COUNT(a.id) = (
    SELECT MAX(artigos_por_usuario.total_artigos)
    FROM (
        SELECT COUNT(a.id) AS total_artigos
        FROM portal_artigos.usuario u
        LEFT JOIN portal_artigos.artigo a ON u.id = a.autor_principal_id
        GROUP BY u.id
    ) AS artigos_por_usuario
)
ORDER BY total_artigos DESC;

-- Consulta título e autor de cada artigo
SELECT titulo, autor
FROM (
    SELECT a.titulo, u.nome AS autor
    FROM portal_artigos.artigo a
    JOIN portal_artigos.usuario u ON u.id = a.autor_principal_id

    UNION ALL

    SELECT a.titulo, u.nome AS autor
    FROM portal_artigos.artigo a
    JOIN portal_artigos.usuario u ON u.id = ANY(a.outros_autores)
) AS todos_autores
ORDER BY titulo, autor;

-- Consulta usuarios com e sem endereços
SELECT 
    u.nome,
    CASE 
        WHEN u.endereco_rua IS NOT NULL THEN 
            CONCAT(u.endereco_rua, ', ', u.endereco_numero, ', ', u.endereco_bairro, ' - ', u.endereco_cep)
        ELSE 'Sem endereço cadastrado'
    END AS endereco
FROM portal_artigos.usuario u;

--Criação da View
-- View de listagem de artigos com categorias
CREATE VIEW portal_artigos.view_artigos_categorias AS
SELECT a.titulo, c.nome AS categoria
FROM portal_artigos.artigo a
JOIN portal_artigos.categoria c ON a.categoria_id = c.id;

-- Visualização da view
SELECT * FROM portal_artigos.view_artigos_categorias

