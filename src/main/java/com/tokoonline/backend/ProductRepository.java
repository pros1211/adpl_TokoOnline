package com.tokoonline.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.model.Produk;

public class ProductRepository {
    public boolean simpanProduk(Produk produk) {
        String query = "INSERT INTO produk (id_penjual, jenis, nama, harga, stok) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, produk.getIdPenjual());
            stmt.setString(2, produk.getJenis());
            stmt.setString(3, produk.getNama());
            stmt.setDouble(4, produk.getHarga());
            stmt.setInt(5, produk.getStok());

            int numOfRowUpdated = stmt.executeUpdate();

            return numOfRowUpdated > 0;

        } catch (Exception e) {
            System.out.println("Gagal menyimpan produk ke database: " + e.getMessage());
        }

        return false;
    }
}
