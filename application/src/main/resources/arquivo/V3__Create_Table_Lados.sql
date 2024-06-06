CREATE TABLE IF NOT EXISTS `lados` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `id_estante` INT NOT NULL,
    `lado` VARCHAR(1) NOT NULL,
    FOREIGN KEY (`id_estante`) REFERENCES `estantes`(`id`)
);
