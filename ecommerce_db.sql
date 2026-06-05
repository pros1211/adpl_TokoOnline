CREATE DATABASE IF NOT EXISTS ecommerce_db;
USE ecommerce_db;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS detail_pesanan;
DROP TABLE IF EXISTS pesanan;
DROP TABLE IF EXISTS produk;
DROP TABLE IF EXISTS pembeli;
DROP TABLE IF EXISTS penjual;
CREATE TABLE penjual (
    id_penjual INT(11) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE produk (
    id_produk INT AUTO_INCREMENT PRIMARY KEY,
    id_penjual INT,
    jenis VARCHAR(30),
    nama VARCHAR(100),
    berat DOUBLE,
    harga DOUBLE,
    stok INT,
    FOREIGN KEY (id_penjual) REFERENCES penjual(id_penjual) ON DELETE CASCADE
);

CREATE TABLE pembeli (
    id_pembeli INT AUTO_INCREMENT PRIMARY KEY,
    alamat VARCHAR(255),
    noHp VARCHAR(20),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE pesanan(
    id_pesanan INT AUTO_INCREMENT PRIMARY KEY,
    id_pembeli INT NOT NULL,
    alamat_kirim TEXT NOT NULL,
    ekspedisi VARCHAR(50) NOT NULL,
    total_harga DOUBLE NOT NULL,
    status_pesanan VARCHAR(50) NOT NULL DEFAULT 'Menunggu Pembayaran',
    tanggal_transaksi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pesanan_pembeli 
        FOREIGN KEY (id_pembeli) REFERENCES pembeli(id_pembeli)
)

CREATE TABLE detail_pesanan (
    id_detail INT AUTO_INCREMENT PRIMARY KEY,
    id_pesanan INT NOT NULL,
    id_produk INT NOT NULL,
    kuantitas INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    
    CONSTRAINT fk_detail_pesanan
        FOREIGN KEY (id_pesanan) REFERENCES pesanan(id_pesanan) 
        ON DELETE CASCADE, 
        
    
    CONSTRAINT fk_detail_produk
        FOREIGN KEY (id_produk) REFERENCES produk(id_produk)
);

INSERT INTO penjual (username, password) VALUES 
('TokoElektronikJaya', 'pass123'),
('KulinerNusantara', 'pass123'),
('FashionStyle', 'pass123'),
('BeautyGlow', 'pass123'),
('MebelIndah', 'pass123');

-- B. Data Pembeli
INSERT INTO pembeli (alamat, noHp, username, password) VALUES 
('Jl. Kopo Bihbul No.53, Bandung', '08995666924', 'kendrick', 'ken123'),
('Jl. Merdeka No.1, Jakarta', '081234567890', 'budi_santoso', 'budi123');

INSERT INTO produk (id_penjual, jenis, nama, berat, harga, stok) VALUES 
(1, 'Elektronik', 'Laptop ASUS ProArt 14', 1.5, 18000000, 20),
(1, 'Elektronik', 'HP Pavilion 13', 1.3, 15000000, 10),
(1, 'Elektronik', 'Sony WH-1000XM5 Wireless Headphones', 0.5, 5500000, 15),

(2, 'Makanan', 'Indomie Mi Goreng (1 Dus)', 2.5, 115000, 50),
(2, 'Makanan', 'Kopi Kenangan Mantan 1 Liter', 1.0, 85000, 30),

(3, 'Pakaian', 'Uniqlo T-Shirt Pria Lengan Pendek', 0.2, 199000, 100),
(3, 'Pakaian', 'Erigo Coach Jacket Black', 0.6, 350000, 45),

(4, 'Kecantikan', 'COSRX Low pH Good Morning Gel Cleanser', 0.2, 145000, 40),
(4, 'Kecantikan', 'Skintific 5X Ceramide Barrier Moisture Gel', 0.1, 135000, 60),

(5, 'Perabotan', 'IKEA LINNMON Meja Kerja Putih', 12.0, 750000, 5),
(5, 'Perabotan', 'Kursi Kantor Ergonomis Punggung Jaring', 8.5, 650000, 12);


-- untuk reset database
-- SET FOREIGN_KEY_CHECKS = 0;

-- TRUNCATE TABLE detail_pesanan;
-- TRUNCATE TABLE pesanan;
-- TRUNCATE TABLE produk;
-- TRUNCATE TABLE pembeli;
-- TRUNCATE TABLE penjual;

SET FOREIGN_KEY_CHECKS = 1;