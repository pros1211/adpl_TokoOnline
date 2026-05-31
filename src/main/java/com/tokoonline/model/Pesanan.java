package com.tokoonline.model;

import java.util.ArrayList;
import java.util.List;

import com.tokoonline.State.MenungguPembayaran;
import com.tokoonline.State.StatePesanan;
import com.tokoonline.Strategi.StrategiPembayaran;
import com.tokoonline.builder.PesananBuilder;

public class Pesanan {
    int idPesanan;
    StatePesanan currentState;
    Pelanggan pembeli;
    List<ItemPesanan> daftaritem;
    String alamatkirim;
    String ekspedisi;
    StrategiPembayaran metodePembayaran;
    Double totalHarga;

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
        String hasil = "";
        for (ItemPesanan isi : daftaritem) {
            hasil += isi.produk.getNama();
        }

        return hasil + " " + this.ekspedisi + " " + this.alamatkirim;
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

    public double getTotalHarga() {
        return totalHarga;
    }

}