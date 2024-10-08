CREATE TABLE clothing_product
(
    id UUID NOT NULL,
    name VARCHAR(255) UNIQUE,
    season VARCHAR(255),
    brand VARCHAR(255),
    price FLOAT,
    stock int,
    PRIMARY KEY(id)
);

CREATE TABLE ingredient
(
    id UUID NOT NULL,
    name VARCHAR(255) UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE food_product
(
    id UUID NOT NULL,
    name VARCHAR(255) UNIQUE,
    price FLOAT,
    PRIMARY KEY(id)
);

CREATE TABLE food_product_ingredient
(
    id UUID NOT NULL,
    food_product_id UUID NOT NULL,
    ingredient_id UUID NOT NULL,
    primary key(id)
);

ALTER TABLE food_product_ingredient
ADD CONSTRAINT  ingredient_FK FOREIGN KEY(ingredient_id) REFERENCES ingredient(id),
ADD CONSTRAINT  food_product_FK FOREIGN KEY(food_product_id) REFERENCES food_product(id);

ALTER TABLE clothing_product
ADD CONSTRAINT brand_values check (brand in ('ZARA', 'BERSHKA', 'STRADIVARIUS')),
ADD CONSTRAINT season_values check (season in ('SPRING', 'SUMMER', 'AUTUMN', 'WINTER'));