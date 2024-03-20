CREATE TABLE IF NOT EXISTS `Lados` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `id_estante` INT NOT NULL,
    `lado` VARCHAR(1) NOT NULL,
    FOREIGN KEY (`id_estante`) REFERENCES `Estantes`(`id`)
);
