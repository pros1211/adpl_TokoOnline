package com.tokoonline.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/ecommerce_db";
            String user = "root";
            String password = "";
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("[Singleton] Koneksi baru ke MySQL berhasil.");
        } catch (SQLException e) {
            System.out.println("gagal melakukan koneksi: " + e.getMessage());
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
