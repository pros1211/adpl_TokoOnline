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

                double beratDefault = 1.0;
                Produk produk = ProductFactory.buatProduk(jenis, idPenjual, nama, harga, beratDefault, stok);

                if (produk != null) {
                    produk.setIdProduk(idProduk);
                    listProduk.add(produk);
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil data produk dari database: " + e.getMessage());
        }

        return listProduk;
    }

    public List<Produk> getAllProdukByToko(String namaToko) {
        List<Produk> listProduk = new ArrayList<>();

        String query = "SELECT id_produk, id_penjual, jenis, nama, harga, stok FROM produk" +
                "JOIN penjual ON penjual.id_penjual = produk.id_penjual " +
                "WHERE penjual.username = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, namaToko);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idProduk = rs.getInt("id_produk");
                int idPenjual = rs.getInt("id_penjual");
                String jenis = rs.getString("jenis");
                String nama = rs.getString("nama");
                double harga = rs.getDouble("harga");
                int stok = rs.getInt("stok");

                double beratDefault = 1.0;
                Produk produk = ProductFactory.buatProduk(jenis, idPenjual, nama, harga, beratDefault, stok);

                if (produk != null) {
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
