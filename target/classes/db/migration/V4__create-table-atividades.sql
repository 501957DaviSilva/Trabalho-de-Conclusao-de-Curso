CREATE TABLE atividades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    academia_id BIGINT NOT NULL,
    FOREIGN KEY (academia_id) REFERENCES academias(id) ON DELETE CASCADE
);