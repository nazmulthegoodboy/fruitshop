-- Create database and tables
CREATE DATABASE IF NOT EXISTS fruitshopdb;
USE fruitshopdb;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS fruits (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price DOUBLE NOT NULL,
  stock INT NOT NULL
);

-- Sample users
INSERT IGNORE INTO users (username, password, role) VALUES
('admin','admin','Admin'),
('user','user','Customer');

-- Sample fruits
INSERT IGNORE INTO fruits (name, price, stock) VALUES
('Apple', 1.20, 100),
('Banana', 0.50, 200),
('Orange', 0.80, 150),
('Mango', 1.50, 80);