package com.tokoonline.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.model.ItemPesanan;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Pesanan;

public class PesananRepository {
    // method untuk simpan pesanan
    public boolean simpanPesanan(Pesanan pesanan) {
        // query untuk simpan informasi ringkasan pesanan (id pembeli, alamat,
        // ekspedisi, total harga, status pesanan)
        String queryPesanan = "INSERT INTO pesanan (id_pembeli, alamat_kirim, ekspedisi, total_harga, status_pesanan) VALUES (?, ?, ?, ?, ?)";
        // query untuk simpan detail pesanan (id pesanan, id produk, kuantitas dan
        // subtotal)
        String queryDetail = "INSERT INTO detail_pesanan (id_pesanan, id_produk, kuantitas, subtotal) VALUES (?, ?, ?, ?)";

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

            String namaState = pesanan.getCurrentState().getClass().getSimpleName();
            stmtPesanan.setString(5, namaState);

            stmtPesanan.executeUpdate();

            ResultSet rs = stmtPesanan.getGeneratedKeys();
            int idPesananBaru = 0;
            if (rs.next()) {
                idPesananBaru = rs.getInt(1);
                pesanan.setIdPesanan(idPesananBaru);
            } else {
                throw new Exception("Gagal mendapatkan ID Pesanan dari database.");
            }
            // simpan detail pesanan
            PreparedStatement stmtDetail = conn.prepareStatement(queryDetail);
            String queryUpdateStok = "UPDATE produk SET stok = stok - ? WHERE id_produk = ?";
            PreparedStatement stmtStok = conn.prepareStatement(queryUpdateStok);
            for (ItemPesanan item : pesanan.getDaftaritem()) {

                stmtDetail.setInt(1, idPesananBaru);
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
            // jika gagal tersimpan maka rollback
            System.out.println("Gagal menyimpan transaksi. Membatalkan pesanan... Error: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Gagal melakukan rollback: " + ex.getMessage());
            }
            return false;

        } finally {
            // set autocommit true setelah selesai
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
            }
        }
    }

    public java.util.List<Pesanan> getRiwayatPesanan(Pelanggan pembeli) {
        // arraylist untuk menyimpan seluruh objek pesanan yang pernah dibuat
        java.util.List<Pesanan> riwayat = new java.util.ArrayList<>();
        // query mengambil histori pesanan
        String query = "SELECT * FROM pesanan WHERE id_pembeli = ? ORDER BY id_pesanan DESC";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pembeli.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // ambil data ringkasan pesanan dari tabel pesanan
                int idPesanan = rs.getInt("id_pesanan");
                String alamat = rs.getString("alamat_kirim");
                String ekspedisi = rs.getString("ekspedisi");
                double total = rs.getDouble("total_harga");
                String statusDb = rs.getString("status_pesanan");

                com.tokoonline.State.StatePesanan stateObj;
                switch (statusDb) {
                    case "Diproses":
                        stateObj = new com.tokoonline.State.Diproses();
                        break;
                    case "Dikirim":
                        stateObj = new com.tokoonline.State.Dikirim();
                        break;
                    case "Dibatalkan":
                        stateObj = new com.tokoonline.State.Dibatalkan();
                        break;
                    default:
                        stateObj = new com.tokoonline.State.MenungguPembayaran();
                        break;
                }

                Pesanan p = new Pesanan(idPesanan, pembeli, alamat, ekspedisi, total, stateObj);
                riwayat.add(p);
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil riwayat: " + e.getMessage());
        }
        return riwayat;
    }

    public boolean updateStatusPesanan(Pesanan pesanan) {
        // update isi status pesanan di tabel pesanan
        String query = "UPDATE pesanan SET status_pesanan = ? WHERE id_pesanan = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            String namaState = pesanan.getCurrentState().getClass().getSimpleName();

            stmt.setString(1, namaState);
            stmt.setInt(2, pesanan.getIdPesanan());

            int rowUpdated = stmt.executeUpdate();
            return rowUpdated > 0;

        } catch (Exception e) {
            System.out.println("Gagal mengupdate status pesanan di database: " + e.getMessage());
            return false;
        }
    }
}
