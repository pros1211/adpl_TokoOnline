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
        String hasil = "";
        for (ItemPesanan isi : daftaritem) {
            hasil += isi.getProduk().getNama()+", ";
        }

        return "PESANAN #ID "+this.idPesanan+" Barang:"+hasil + "dengan Ekpedisi:" + this.ekspedisi + "dan Alamat Kirim: " + this.alamatkirim;
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