CREATE TABLE `Itens_Aba` (
    `id_aba` INT,
    `id_item` INT,
    FOREIGN KEY (`id_aba`) REFERENCES `Abas`(`id`),
    FOREIGN KEY (`id_item`) REFERENCES `Produtos`(`id`)
);