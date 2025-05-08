INSERT INTO academias (id, nome, endereco, telefone, tipo_acad) VALUES
(1, 'Academia Alpha', 'Rua A, 123', '(11) 99999-0001', 'CONVENCIONAL'),
(2, 'CrossFit Beta', 'Avenida B, 456', '(11) 99999-0002', 'CROSSFIT'),
(3, 'Gym Evolution', 'Rua C, 789', '(11) 99999-0003', 'CONVENCIONAL'),
(4, 'Box Force', 'Travessa D, 101', '(11) 99999-0004', 'CROSSFIT'),
(5, 'Power Fit', 'Rua E, 202', '(11) 99999-0005', 'CONVENCIONAL'),
(6, 'Cross Training Pro', 'Avenida F, 303', '(11) 99999-0006', 'CROSSFIT'),
(7, 'Fitness Top', 'Rua G, 404', '(11) 99999-0007', 'CONVENCIONAL'),
(8, 'WOD Zone', 'Alameda H, 505', '(11) 99999-0008', 'CROSSFIT'),
(9, 'Max Gym', 'Rua I, 606', '(11) 99999-0009', 'CONVENCIONAL'),
(10, 'Beast Box', 'Rua J, 707', '(11) 99999-0010', 'CROSSFIT');

INSERT INTO academia_estruturas (academia_id, estrutura) VALUES
(1, 'ESTACIONAMENTO'),
(1, 'VESTIARIO'),
(1, 'AR_CONDICIONADO'),
(2, 'ACESSIBILIDADE'),
(2, 'BEBEDOURO'),
(2, 'WIFI');

INSERT INTO academia_servicos (academia_id, servico) VALUES
(1, 'AVALIACAO_INICIAL'),
(1, 'APP'),
(1, 'WHATSAPP'),
(2, 'PERSONAL'),
(2, 'TREINOS'),
(2, 'VINT_QUATRO_HORAS');
