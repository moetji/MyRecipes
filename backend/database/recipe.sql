CREATE DATABASE recipe;

USE recipe;

CREATE TABLE admins (
    id INT AUTO_INCREMENT,
    a_name VARCHAR(100) NOT NULL,
    a_email VARCHAR(100) NOT NULL UNIQUE,
    a_password VARCHAR(255) NOT NULL,
    a_phoneNumber CHAR(10),

    PRIMARY KEY (id)
);

CREATE TABLE category (
    c_id INT AUTO_INCREMENT,
    c_name VARCHAR(200) NOT NULL UNIQUE,

    PRIMARY KEY (c_id)
);

CREATE TABLE recipe (
    id INT AUTO_INCREMENT,
    r_name VARCHAR(200) NOT NULL,
    r_description TEXT,
    r_instructions TEXT NOT NULL,
    prepTime INT,
    cookTime INT,
    servings INT,
    datecreated DATETIME DEFAULT CURRENT_TIMESTAMP,
    image VARCHAR(255),
    c_id INT,
    a_id INT,

    PRIMARY KEY (id),

    FOREIGN KEY (c_id) REFERENCES category(c_id),
    FOREIGN KEY (a_id) REFERENCES admins(id)
);

CREATE TABLE ingredient (
    i_id INT AUTO_INCREMENT,
    i_name VARCHAR(200) NOT NULL UNIQUE,
    image VARCHAR(255),

    PRIMARY KEY (i_id)
);
CREATE TABLE recipe_ingredient (
    ri_id INT AUTO_INCREMENT,
    recipe_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantity DECIMAL(10,2),
    unit VARCHAR(50),

    PRIMARY KEY (ri_id),

    FOREIGN KEY (recipe_id) REFERENCES recipe(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient(i_id)
);