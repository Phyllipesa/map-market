CREATE TABLE IF NOT EXISTS `Prateleiras` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `id_lado` INT NOT NULL,
    `lance` INT NOT NULL,
    FOREIGN KEY (`id_lado`) REFERENCES `Lados`(`id`)
);
