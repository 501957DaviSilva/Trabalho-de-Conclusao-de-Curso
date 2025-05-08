CREATE TABLE IF NOT EXISTS treinos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_academia(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS treino_cardio (
    treino_id BIGINT NOT NULL,
    cardio VARCHAR(50) NOT NULL,
    FOREIGN KEY (treino_id) REFERENCES treinos(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS treino_hipertrofia (
    treino_id BIGINT NOT NULL,
    hipertrofia VARCHAR(50) NOT NULL,
    FOREIGN KEY (treino_id) REFERENCES treinos(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS treino_tipos (
    treino_id BIGINT NOT NULL,
    tipo_treino VARCHAR(50) NOT NULL,
    FOREIGN KEY (treino_id) REFERENCES treinos(id) ON DELETE CASCADE
);

INSERT INTO treinos (id, nome, descricao, user_id) VALUES
(1, 'Treino de Performance Avançado', 'Força combinada com cardio intenso', 1);

INSERT INTO treino_cardio (treino_id, cardio) VALUES
(1, 'CORRIDA'),
(1, 'BICICLETA');

INSERT INTO treino_hipertrofia (treino_id, hipertrofia) VALUES
(1, 'PESO_LIVRE'),
(1, 'SUPINO');

INSERT INTO treino_tipos (treino_id, tipo_treino) VALUES
(1, 'MUSCULACAO'),
(1, 'FUNCIONAL');

-- Treino 2
INSERT INTO treinos (id, nome, descricao, user_id) VALUES
(2, 'Cardio para Iniciantes', 'Foco em resistência e leve intensidade', 1);

INSERT INTO treino_cardio (treino_id, cardio) VALUES
(2, 'ELIPTICO'),
(2, 'REMO');

INSERT INTO treino_hipertrofia (treino_id, hipertrofia) VALUES
(2, 'MAQUINAS');

INSERT INTO treino_tipos (treino_id, tipo_treino) VALUES
(2, 'HIIT'),
(2, 'CROSSFIT');
