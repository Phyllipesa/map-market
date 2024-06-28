CREATE TABLE locations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    shelving_unit_id INT,
    side CHAR(1) NOT NULL,
    part INT NOT NULL,
    shelf INT NOT NULL,
    product_id INT,
    FOREIGN KEY (shelving_unit_id) REFERENCES shelving_units(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
