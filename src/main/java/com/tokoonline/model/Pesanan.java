package com.tokoonline.model;

import java.util.ArrayList;
import java.util.List;

import com.tokoonline.State.MenungguPembayaran;
import com.tokoonline.State.StatePesanan;
import com.tokoonline.Strategi.StrategiPembayaran;
import com.tokoonline.builder.PesananBuilder;

public class Pesanan {
    private int idPesanan;
    private StatePesanan currentState;
    private Pelanggan pembeli;
    private List<ItemPesanan> daftaritem;
    private String alamatkirim;
    private String ekspedisi;
    private StrategiPembayaran metodePembayaran;
    private Double totalHarga;

    public Pesanan(PesananBuilder builder) {
        this.idPesanan = builder.getId();
        this.currentState = new MenungguPembayaran();
        this.pembeli = builder.getPembeli();
        this.daftaritem = builder.getDaftarItem();
        this.alamatkirim = builder.getAlamatKirim();
        this.ekspedisi = builder.getEkspedisi();
        this.totalHarga = builder.getTotalHarga();
    }

    // constructor untuk cek status pesanan
    public Pesanan(int idPesanan, Pelanggan pembeli, String alamat, String ekspedisi, double totalHarga,
            StatePesanan state) {
        this.idPesanan = idPesanan;
        this.pembeli = pembeli;
        this.alamatkirim = alamat;
        this.ekspedisi = ekspedisi;
        this.totalHarga = totalHarga;
        this.currentState = state;
        this.daftaritem = new ArrayList<>();
    }

    public String getDetailPesanan() {
        StringBuilder detailBarang = new StringBuilder();
        if (daftaritem.size() > 1) {
            detailBarang.append("\n");
            for (int i = 0; i < daftaritem.size(); i++) {
                ItemPesanan item = daftaritem.get(i);
                detailBarang.append("  ").append(i + 1).append(". ")
                        .append(item.getProduk().getNama())
                        .append(" (Qty: ").append(item.getKuantitas()).append(")");

                if (i < daftaritem.size() - 1) {
                    detailBarang.append("\n");
                }
            }
        } else if (daftaritem.size() == 1) {
            ItemPesanan item = daftaritem.get(0);
            detailBarang.append(item.getProduk().getNama())
                    .append(" (Qty: ").append(item.getKuantitas()).append(")");
        } else {
            detailBarang.append("-");
        }

        return "PESANAN #ID " + this.idPesanan + "\n" +
                "Barang: " + detailBarang.toString() + "\n" +
                "Ekspedisi: " + this.ekspedisi + "\n" +
                "Alamat Kirim: " + this.alamatkirim + "\n";
    }

    public void setStatus(StatePesanan newstate) {
        this.currentState = newstate;
    }

    public void bayar() {
        if (this.metodePembayaran == null) {
            System.out.println("Gagal Bayar: Silahkan pilih metode pembayaran terlebih dahulu!");
            return;
        }

        this.currentState.bayar(this);
    }

    public void kirim() {
        this.currentState.kirim(this);
    }

    public void setMetodePembayaran(StrategiPembayaran metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public void batal() {
        this.currentState.batal(this);
    }

    public void setIdPesanan(int idPesanan) {
        this.idPesanan = idPesanan;
    }

    public int getIdPesanan() {
        return idPesanan;
    }

    public StatePesanan getCurrentState() {
        return currentState;
    }

    public Pelanggan getPembeli() {
        return pembeli;
    }

    public List<ItemPesanan> getDaftaritem() {
        return daftaritem;
    }

    public String getAlamatkirim() {
        return alamatkirim;
    }

    public String getEkspedisi() {
        return ekspedisi;
    }

    public StrategiPembayaran getMetodePembayaran() {
        return metodePembayaran;
    }

    public Double getTotalHarga() {
        return totalHarga;
    }

}