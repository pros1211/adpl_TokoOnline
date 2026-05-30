package com.tokoonline.model;

import java.time.LocalDate;

public class Makanan extends Produk {
    private String kategori;
    private boolean halal;
    private String bahan;
    private LocalDate tanggalKadaluarsa;

    public Makanan(int idProduk, int idPenjual, String nama, double harga, double berat, int stok, String kategori,
            boolean halal,
            LocalDate tanggalKadaluarsa, String bahan) {
        super(idProduk, idPenjual, nama, harga, berat, stok, "Makanan");
        this.kategori = kategori;
        this.halal = halal;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.bahan = bahan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public boolean isHalal() {
        return halal;
    }

    public void setHalal(boolean halal) {
        this.halal = halal;
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

}
