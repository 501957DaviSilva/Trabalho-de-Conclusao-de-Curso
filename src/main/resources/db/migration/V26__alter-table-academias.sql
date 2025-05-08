ALTER TABLE academias ADD COLUMN tipo_acad VARCHAR(20) NOT NULL;

CREATE TABLE academia_estruturas (
    academia_id BIGINT NOT NULL,
    estrutura VARCHAR(50),
    FOREIGN KEY (academia_id) REFERENCES academias(id) ON DELETE CASCADE
);

CREATE TABLE academia_servicos (
    academia_id BIGINT NOT NULL,
    servico VARCHAR(50),
    FOREIGN KEY (academia_id) REFERENCES academias(id) ON DELETE CASCADE
);