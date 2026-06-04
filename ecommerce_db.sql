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

-- untuk reset database
-- SET FOREIGN_KEY_CHECKS = 0;

-- TRUNCATE TABLE detail_pesanan;
-- TRUNCATE TABLE pesanan;
-- TRUNCATE TABLE produk;
-- TRUNCATE TABLE pembeli;
-- TRUNCATE TABLE penjual;

SET FOREIGN_KEY_CHECKS = 1;