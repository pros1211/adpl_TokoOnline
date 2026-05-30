package com.tokoonline.model;

import java.time.LocalDate;

public class Kecantikan extends Produk {
    private String kategori;
    private String bahan;
    private LocalDate tanggalKadaluarsa;
    private double volume;

    public Kecantikan(int idProduk, int idPenjual, String nama, double harga, double berat, int stok,
            String kategori, String bahan,
            LocalDate tanggalKadaluarsa, double volume) {
        super(idProduk, idPenjual, nama, harga, berat, stok, "Kecantikan");
        this.kategori = kategori;
        this.bahan = bahan;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.volume = volume;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public LocalDate getTanggalKadaluarsa() {
        return tanggalKadaluarsa;
    }

    public void setTanggalKadaluarsa(LocalDate tanggalKadaluarsa) {
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
