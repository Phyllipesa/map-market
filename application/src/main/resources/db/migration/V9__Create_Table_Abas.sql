CREATE TABLE IF NOT EXISTS `Abas` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `id_prateleira` INT NOT NULL,
    `aba` INT NOT NULL,
    FOREIGN KEY (`id_prateleira`) REFERENCES `Prateleiras`(`id`)
);
