CREATE TABLE IF NOT EXISTS `PartesAdicionais` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `id_estante` INT NOT NULL,
    `parte` VARCHAR(6) NOT NULL,
    FOREIGN KEY (`id_estante`) REFERENCES `Estantes`(`id`)
);
