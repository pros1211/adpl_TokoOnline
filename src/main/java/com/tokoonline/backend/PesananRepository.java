package com.tokoonline.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.factory.ProductFactory;
import com.tokoonline.model.ItemPesanan;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Pesanan;
import com.tokoonline.model.Produk;

public class PesananRepository {
    // method untuk simpan pesanan
    public boolean simpanPesanan(Pesanan pesanan) {
        String queryPesanan = "INSERT INTO pesanan (id_pembeli, alamat_kirim, ekspedisi, total_harga, status_pesanan) VALUES (?, ?, ?, ?, ?)";
        String queryDetail = "INSERT INTO detail_pesanan (id_pesanan, id_produk, kuantitas, subtotal) VALUES (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);
            
            PreparedStatement stmtPesanan = conn.prepareStatement(queryPesanan, java.sql.Statement.RETURN_GENERATED_KEYS);
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
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {}
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (Exception e) {}
        }
    }

    public List<Pesanan> getRiwayatPesanan(Pelanggan pembeli) {
        List<Pesanan> riwayat = new ArrayList<>();
        String query = "SELECT * FROM pesanan WHERE id_pembeli = ? ORDER BY id_pesanan DESC";
        String queryItems = "SELECT dp.*, p.nama, p.jenis, p.harga, p.berat, p.id_penjual " +
                           "FROM detail_pesanan dp " +
                           "JOIN produk p ON dp.id_produk = p.id_produk " +
                           "WHERE dp.id_pesanan = ?";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pembeli.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idPesanan = rs.getInt("id_pesanan");
                String alamat = rs.getString("alamat_kirim");
                String ekspedisi = rs.getString("ekspedisi");
                double total = rs.getDouble("total_harga");
                String statusDb = rs.getString("status_pesanan");

                com.tokoonline.State.StatePesanan stateObj = mapStatusToState(statusDb);
                Pesanan p = new Pesanan(idPesanan, pembeli, alamat, ekspedisi, total, stateObj);

                // Load items for this order
                PreparedStatement stmtItems = conn.prepareStatement(queryItems);
                stmtItems.setInt(1, idPesanan);
                ResultSet rsItems = stmtItems.executeQuery();
                while (rsItems.next()) {
                    Produk prod = ProductFactory.buatProduk(
                        rsItems.getString("jenis"), rsItems.getInt("id_penjual"),
                        rsItems.getString("nama"), rsItems.getDouble("harga"),
                        rsItems.getDouble("berat"), 0
                    );
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
        String queryStatus = "UPDATE pesanan SET status_pesanan = ? WHERE id_pesanan = ?";
        String queryRestoreStok = "UPDATE produk SET stok = stok + ? WHERE id_produk = ?";
        Connection conn = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            String namaState = pesanan.getCurrentState().getClass().getSimpleName();

            // 1. Update status
            PreparedStatement stmtStatus = conn.prepareStatement(queryStatus);
            stmtStatus.setString(1, namaState);
            stmtStatus.setInt(2, pesanan.getIdPesanan());
            stmtStatus.executeUpdate();

            // 2. Restore stock jika statenya dibatalkan
            if (namaState.equals("Dibatalkan")) {
                PreparedStatement stmtRestore = conn.prepareStatement(queryRestoreStok);
                for (ItemPesanan item : pesanan.getDaftaritem()) {
                    stmtRestore.setInt(1, item.getKuantitas());
                    stmtRestore.setInt(2, item.getProduk().getIdProduk());
                    stmtRestore.executeUpdate();
                }
                System.out.println("Stok produk berhasil dikembalikan.");
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Gagal update status: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {}
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (Exception e) {}
        }
    }

    private com.tokoonline.State.StatePesanan mapStatusToState(String status) {
        switch (status) {
            case "Diproses": return new com.tokoonline.State.Diproses();
            case "Dikirim": return new com.tokoonline.State.Dikirim();
            case "Dibatalkan": return new com.tokoonline.State.Dibatalkan();
            default: return new com.tokoonline.State.MenungguPembayaran();
        }
    }
}
