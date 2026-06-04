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
        //query insert data produk ditambahkan berat
        String query = "INSERT INTO produk (id_penjual, jenis, nama, harga, berat, stok) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, produk.getIdPenjual());
            stmt.setString(2, produk.getJenis());
            stmt.setString(3, produk.getNama());
            stmt.setDouble(4, produk.getHarga());
            stmt.setDouble(5, produk.getBerat());
            stmt.setInt(6, produk.getStok());
           int numOfRowUpdated = stmt.executeUpdate();
            // return true jika row terupdate
            return numOfRowUpdated > 0;
        } catch (Exception e) {
            System.out.println("Gagal simpan produk: " + e.getMessage());
            return false;
        }
    }
// method untuk menampilkan katalog produk tersedia
    public List<Produk> getAllProduk() {
        List<Produk> listProduk = new ArrayList<>();
        // query untuk mendapatkan data produk (mengambil semua karena di table db nya menambahkan berat)
        String query = "SELECT * FROM produk";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produk p = ProductFactory.buatProduk(
                    rs.getString("jenis"), rs.getInt("id_penjual"),
                    rs.getString("nama"), rs.getDouble("harga"),
                    rs.getDouble("berat"), rs.getInt("stok")
                );
                if (p != null) {
                    p.setIdProduk(rs.getInt("id_produk"));
                    listProduk.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal ambil produk: " + e.getMessage());
        }
        // return array list produk tersedia
        return listProduk;
    }
// method untuk mendapatkan katalog barang dari sebuah toko
    public List<Produk> getAllProdukByToko(String namaToko) {
        List<Produk> listProduk = new ArrayList<>();
        // query untuk mendapatkan produk dengan inner join berdasarkan id penjual dan
        // username
        String query = "SELECT p.* FROM produk p JOIN penjual j ON j.id_penjual = p.id_penjual WHERE j.username = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, namaToko);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produk p = ProductFactory.buatProduk(
                    rs.getString("jenis"), rs.getInt("id_penjual"),
                    rs.getString("nama"), rs.getDouble("harga"),
                    rs.getDouble("berat"), rs.getInt("stok")
                );
                if (p != null) {
                    p.setIdProduk(rs.getInt("id_produk"));
                    listProduk.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal ambil produk toko: " + e.getMessage());
        }
         // kembalikan katalog produk
        return listProduk;
    }
}
