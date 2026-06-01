package com.tokoonline.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.model.Pelanggan;

public class PelangganRepository {
    // method verifikasi login penjual
    public Pelanggan verifikasiLogin(String username, String password) {
        String query = "SELECT * FROM pembeli WHERE username = ? AND password = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_pembeli");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                return new Pelanggan(id, dbUsername, dbPassword);
            }
        } catch (Exception e) {
            System.out.println("Gagal melakukan login : " + e.getMessage());
        }
        return null;
    }

    // method verifikasi login menggunakan nomor HP sebagai pengganti username
    public Pelanggan verifikasiLoginByPhone(String noHp, String password) {
        String query = "SELECT * FROM pembeli WHERE noHp = ? AND password = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, noHp);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_pembeli");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String dbAlamat = rs.getString("alamat");
                String dbNoHp = rs.getString("noHp");
                // jika ditemukan maka return akun pelanggan
                Pelanggan akunPelanggan = new Pelanggan(id, dbUsername, dbPassword);
                akunPelanggan.setAlamat(dbAlamat);
                akunPelanggan.setNoHp(dbNoHp);
                return akunPelanggan;
            }
        } catch (Exception e) {
            System.out.println("Nomor telepon atau password tidak cocok: " + e.getMessage());
        }
        return null;
    }

    // method untuk buat akun pembeli
    public Pelanggan buatAkun(String username, String password, String alamat, String nomorHP) {
        // query untuk insert data pembeli
        String query = "INSERT INTO pembeli (username, password, alamat, noHp) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, alamat);
            stmt.setString(4, nomorHP);

            int numOfRowUpdated = stmt.executeUpdate();
            if (numOfRowUpdated > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id_pelanggan = rs.getInt(1);
                    return new Pelanggan(id_pelanggan, username, password);
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal membuat akun : " + e.getMessage());
        }
        return null;
    }
}
