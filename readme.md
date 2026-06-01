# 🛒 Java Console E-Commerce System

Sebuah aplikasi simulasi _e-commerce_ berbasis _Command Line Interface_ (CLI) berbasis bahasa java dan DBMS MySQL. Aplikasi ini mensimulasikan proses transaksi dan penjualan mulai dari manajemen produk oleh penjual, sistem keranjang belanja, hingga transisi status pesanan dan simulasi pembayaran bagi pembeli.

proyek ini menerapkan _*Object-Oriented Programming*_ (OOP) dengan 6 design pattern yaitu

- **Facade**: untuk menu konfigurasi pesanan, autentikasi login dan buat akun, menu penambahan produk penjual.
- **Singleton**: untuk koneksi database dengan JDBC berbasis MySQL.
- **Factory**: untuk melakukan fungsi konfigurasi produk dan membuat objek produk berdasarkan kategori.
- **Builder**: untuk melakukan fungsi konfigurasi pesanan dan membuat objek pesanan.
- **State**: untuk menyimpan status pesanan (diproses, dikirim, menunggu pembayaran, dibatalkan).
- **Strategy**: untuk melakukan fungsi berbagai jenis metode pembayaran pesanan (gopay, QRIS, Bank).

## ✨ Fitur Utama

### 🏪 Modul Penjual (Merchant)

- **Manajemen Produk:** Menambahkan berbagai kategori produk menggunakan _Factory Pattern_ (Elektronik, Makanan, Pakaian, Kecantikan, Perabotan) dengan atribut yang spesifik per kategori.

### 🛍️ Modul Pembeli (Customer)

- **Katalog Produk:** Melihat seluruh produk yang tersedia atau memfilter katalog berdasarkan nama toko.
- **Keranjang Belanja:** Memasukkan berbagai produk ke dalam keranjang dengan validasi stok _real-time_.
- **Sistem Checkout:** memproses setiap item dalam keranjang menjadi satu nota pesanan utuh menggunakan _Builder Pattern_.
- **Manajemen Riwayat Pesanan:** Melacak status pesanan dan riwayat pesanan.

### 💳 Modul Transaksi & Status

- **Simulasi Pembayaran:** Menyelesaikan pembayaran pesanan melalui berbagai metode pembayaran (GoPay, QRIS, Virtual Account Bank) dengan (_Strategy Pattern_).
- **State Pesanan:** Transisi status pesanan yang ketat (Menunggu Pembayaran ➡️ Diproses ➡️ Dikirim / Dibatalkan) dengan (_State Pattern_).

---

## 🏗️ Arsitektur & Design Patterns

Aplikasi ini dapat dikembangkan dengan _design pattern_ OOP untuk memisahkan logika bisnis, akses _database_, dan _user interface_ (TUI). _design pattern_ yang digunakan meliputi:

1. **Facade Pattern (`AuthFacade`, `PesananFacade`, dll):** untuk logika pemrosesan pesanan dan Login-Register dengan _backend_ dan kueri _database_ dari _User Interface_ (`MainTUI`).
2. **Factory Method (`ProductFactory`):** pembuatan berbagai variasi objek `Produk`.
3. **Builder Pattern (`PesananBuilder`):** membuat objek `Pesanan` berdasarkan data konfigurasi pesanan dari input pengguna.
4. **Strategy Pattern (`StrategiPembayaran`):** metode pembayaran yang dapat dikembangkan.
5. **State Pattern (`StatePesanan`):** status terhadap sebuah pesanan berdasarkan statusnya.

---

## 💻 Prasyarat (Prerequisites)

Pastikan Anda telah menginstal perangkat lunak berikut di sistem Anda:

- **Java Development Kit (JDK):** Versi 8 atau lebih baru.
- **MySQL / MariaDB:** (Disarankan menggunakan paket **XAMPP**).
- **JDBC Driver:** `mysql-connector-j`

```XML
  <dependencies>
  <dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.11</version>
  <scope>test</scope>
  </dependency>
  <dependency>
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <version>8.2.0</version>
  </dependency>
  </dependencies>
```

- **IDE/Editor:** VS Code, IntelliJ IDEA, atau Eclipse.

---

## 🚀 Cara Menyiapkan dan Menjalankan (Installation & Setup)

### 1. Menyiapkan Database

1. Nyalakan modul **MySQL** melalui XAMPP Control Panel.
2. Buka klien database (seperti phpMyAdmin atau DBeaver) dan buat _database_ baru bernama `ecommerce_db` (atau sesuai konfigurasi Anda).
3. Salin dan jalankan skrip SQL (_Data Definition Language_) yang berisi tabel `pembeli`, `penjual`, `produk`, `pesanan`, dan `detail_pesanan` ke dalam _database_ tersebut.

### 2. Konfigurasi Koneksi (JDBC)

Buka file `DatabaseConnection.java` dan pastikan URL basis data Anda sudah sesuai dengan pengaturan lokal Anda:

```java
// Contoh konfigurasi standar XAMPP:
String url = "jdbc:mysql://localhost:3306/ecommerce_db";
String user = "root";
String password = "";
```
