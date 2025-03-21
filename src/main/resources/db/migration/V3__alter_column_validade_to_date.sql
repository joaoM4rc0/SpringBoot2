-- Corrige dados inválidos (opcional)
UPDATE remedios SET validade = '2025-01-01' WHERE validade NOT REGEXP '^\\d{4}-\\d{2}-\\d{2}$';

-- Altera o tipo da coluna
ALTER TABLE remedios MODIFY validade DATE NOT NULL;