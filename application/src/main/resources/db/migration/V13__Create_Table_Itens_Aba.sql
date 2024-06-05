CREATE TABLE `item_aba` (
    `id_aba` INT,
    `id_item` INT,
    FOREIGN KEY (`id_aba`) REFERENCES `abas`(`id`),
    FOREIGN KEY (`id_item`) REFERENCES `product`(`id`)
);