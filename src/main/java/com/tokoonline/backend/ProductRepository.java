package com.tokoonline.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.factory.ProductFactory;
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
    public List<Produk> getAllProduk() {
        List<Produk> listProduk = new ArrayList<>();
        // Asumsikan di tabel database temanmu ada kolom 'id_produk' sebagai primary key auto_increment
        String query = "SELECT id_produk, id_penjual, jenis, nama, harga, stok FROM produk";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idProduk = rs.getInt("id_produk");
                int idPenjual = rs.getInt("id_penjual");
                String jenis = rs.getString("jenis");
                String nama = rs.getString("nama");
                double harga = rs.getDouble("harga");
                int stok = rs.getInt("stok");

                // MANFAATKAN FACTORY: Biarkan Factory yang melahirkan objek turunannya secara polimorfis
                // Kita gunakan berat tiruan atau default (misal: 0) karena tabel dasar baru menyimpan data umum
                double beratDefault = 1.0; 
                Produk produk = ProductFactory.buatProduk(jenis, idPenjual, nama, harga, beratDefault, stok);
                
                if (produk != null) {
                    // Set ID asli yang didapat dari auto_increment database ke objek produk
                    produk.setIdProduk(idProduk); 
                    listProduk.add(produk);
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil data produk dari database: " + e.getMessage());
        }

        return listProduk;
    }
}
