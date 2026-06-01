package com.tokoonline.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // instance untuk menyimpan objek koneksi ke database
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // koneksi url database
            String url = "jdbc:mysql://localhost:3306/ecommerce_db";
            String user = "root";
            String password = "";
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("[Singleton] Koneksi baru ke MySQL berhasil.");
        } catch (SQLException e) {
            System.out.println("gagal melakukan koneksi: " + e.getMessage());
        }
    }

    // method untuk mendapatkan koneksi database
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            // jika masih belum ada koneksi maka inisialisasi var instance
            instance = new DatabaseConnection();
        }
        // jika sudah maka return objek instance yang sama
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
