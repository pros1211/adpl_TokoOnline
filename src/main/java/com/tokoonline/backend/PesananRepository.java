package com.tokoonline.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tokoonline.State.StatePesanan;
import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.factory.ProductFactory;
import com.tokoonline.model.ItemPesanan;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Pesanan;
import com.tokoonline.model.Produk;

public class PesananRepository {

    public boolean simpanPesanan(Pesanan pesanan) {
        // query untuk simpan informasi ringkasan pesanan (id pembeli, alamat,
        // ekspedisi, total harga, status pesanan)
        String queryPesanan = "INSERT INTO pesanan (id_pembeli, alamat_kirim, ekspedisi, total_harga, status_pesanan) VALUES (?, ?, ?, ?, ?)";
        // query untuk simpan detail pesanan (id pesanan, id produk, kuantitas dan
        // subtotal)
        String queryDetail = "INSERT INTO detail_pesanan (id_pesanan, id_produk, kuantitas, subtotal) VALUES (?, ?, ?, ?)";
        String queryUpdateStok = "UPDATE produk SET stok = stok - ? WHERE id_produk = ?";

        Connection conn = null;
        try {
            // ambil koneksi database singleton
            conn = DatabaseConnection.getInstance().getConnection();
            // autocommit false agar setiap query sql yang dieksekusi bisa dirollback
            conn.setAutoCommit(false);
            // simpan ringkasan pesanan
            PreparedStatement stmtPesanan = conn.prepareStatement(queryPesanan,
                    java.sql.Statement.RETURN_GENERATED_KEYS);
            stmtPesanan.setInt(1, pesanan.getPembeli().getId());
            stmtPesanan.setString(2, pesanan.getAlamatkirim());
            stmtPesanan.setString(3, pesanan.getEkspedisi());
            stmtPesanan.setDouble(4, pesanan.getTotalHarga());
            stmtPesanan.setString(5, pesanan.getCurrentState().getClass().getSimpleName());
            stmtPesanan.executeUpdate();

            ResultSet rs = stmtPesanan.getGeneratedKeys();
            if (rs.next()) {
                pesanan.setIdPesanan(rs.getInt(1));
            }
            // simpan detail pesanan
            PreparedStatement stmtDetail = conn.prepareStatement(queryDetail);
            PreparedStatement stmtStok = conn.prepareStatement(queryUpdateStok);

            for (ItemPesanan item : pesanan.getDaftaritem()) {
                stmtDetail.setInt(1, pesanan.getIdPesanan());
                stmtDetail.setInt(2, item.getProduk().getIdProduk());
                stmtDetail.setInt(3, item.getKuantitas());
                stmtDetail.setDouble(4, item.getSubtotal());
                stmtDetail.executeUpdate();

                stmtStok.setInt(1, item.getKuantitas());
                stmtStok.setInt(2, item.getProduk().getIdProduk());
                stmtStok.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Gagal simpan pesanan: " + e.getMessage());
            // jika gagal tersimpan maka rollback
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (Exception e) {
                System.out.println("Gagal melakukan transaksi: " + e.getMessage());
            }
        }
    }

    public List<Pesanan> getRiwayatPesanan(Pelanggan pembeli) {
        // arraylist untuk menyimpan seluruh objek pesanan yang pernah dibuat
        List<Pesanan> riwayat = new ArrayList<>();
        // query mengambil histori pesanan
        String query = "SELECT * FROM pesanan WHERE id_pembeli = ? ORDER BY id_pesanan DESC";
        // query ambil seluruh isi pesanan
        String queryItems = "SELECT dp.*, p.nama, p.jenis, p.harga, p.berat, p.id_penjual " +
                "FROM detail_pesanan dp JOIN produk p ON dp.id_produk = p.id_produk " +
                "WHERE dp.id_pesanan = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pembeli.getId());
            ResultSet rs = stmt.executeQuery();

            // Statement kedua untuk mengambil items agar tidak bentrok dengan Resultset
            // pertama
            PreparedStatement stmtItems = conn.prepareStatement(queryItems);
            while (rs.next()) {
                int idPesanan = rs.getInt("id_pesanan");
                Pesanan p = new Pesanan(
                        idPesanan, pembeli, rs.getString("alamat_kirim"),
                        rs.getString("ekspedisi"), rs.getDouble("total_harga"),
                        mapStatusToState(rs.getString("status_pesanan")));

                stmtItems.setInt(1, idPesanan);
                ResultSet rsItems = stmtItems.executeQuery();
                while (rsItems.next()) {
                    // ambil data ringkasan pesanan dari tabel pesanan
                    Produk prod = ProductFactory.buatProduk(
                            rsItems.getString("jenis"), rsItems.getInt("id_penjual"),
                            rsItems.getString("nama"), rsItems.getDouble("harga"),
                            rsItems.getDouble("berat"), 0);
                    if (prod != null) {
                        prod.setIdProduk(rsItems.getInt("id_produk"));
                        p.getDaftaritem().add(new ItemPesanan(prod, rsItems.getInt("kuantitas")));
                    }
                }
                riwayat.add(p);
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil riwayat: " + e.getMessage());
        }
        return riwayat;
    }

    public boolean updateStatusPesanan(Pesanan pesanan) {
        // update isi status pesanan di tabel pesanan
        String queryStatus = "UPDATE pesanan SET status_pesanan = ? WHERE id_pesanan = ?";
        // update stok produk
        String queryRestoreStok = "UPDATE produk SET stok = stok + ? WHERE id_produk = ?";
        Connection conn = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            String namaState = pesanan.getCurrentState().getClass().getSimpleName();
            PreparedStatement stmtStatus = conn.prepareStatement(queryStatus);
            stmtStatus.setString(1, namaState);
            stmtStatus.setInt(2, pesanan.getIdPesanan());
            stmtStatus.executeUpdate();

            if (namaState.equals("Dibatalkan") && !pesanan.getDaftaritem().isEmpty()) {
                PreparedStatement stmtRestore = conn.prepareStatement(queryRestoreStok);
                for (ItemPesanan item : pesanan.getDaftaritem()) {
                    stmtRestore.setInt(1, item.getKuantitas());
                    stmtRestore.setInt(2, item.getProduk().getIdProduk());
                    stmtRestore.executeUpdate();
                }
                System.out.println("Sistem: Stok produk telah dikembalikan.");
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Gagal update status: " + e.getMessage());
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (Exception e) {
            }
        }
    }

    private StatePesanan mapStatusToState(String status) {
        if (status == null)
            return new com.tokoonline.State.MenungguPembayaran();
        switch (status) {
            case "Diproses":
                return new com.tokoonline.State.Diproses();
            case "Dikirim":
                return new com.tokoonline.State.Dikirim();
            case "Dibatalkan":
                return new com.tokoonline.State.Dibatalkan();
            default:
                return new com.tokoonline.State.MenungguPembayaran();
        }
    }
}
