package com.tokoonline.backend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Penjual;
public class PelangganRepository {
    public Pelanggan verifikasiLogin(String username, String password) {
        String query = "SELECT * FROM pelanggan WHERE username = ? AND password = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id_pelanggan");
                 String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                return new Pelanggan(id, dbUsername, dbPassword);
            }
        } catch (Exception e) {
            System.out.println("❌ Gagal melakukan login pelanggan: " + e.getMessage());
        }
        return null;
    }

    // Pola Buat Akun Baru Pelanggan ke Database
    public Pelanggan buatAkun(String username, String password) {
        String query = "INSERT INTO pelanggan (username, password) VALUES (?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            int numOfRowUpdated = stmt.executeUpdate();
            if (numOfRowUpdated > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idBaru = rs.getInt(1);
                    return new Pelanggan(idBaru, username,password);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Gagal membuat akun pelanggan: " + e.getMessage());
        }
        return null;
    }
}
