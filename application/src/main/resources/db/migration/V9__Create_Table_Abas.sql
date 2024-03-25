CREATE TABLE IF NOT EXISTS `abas` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `id_prateleira` INT NOT NULL,
    `aba` INT NOT NULL,
    FOREIGN KEY (`id_prateleira`) REFERENCES `prateleiras`(`id`)
);
