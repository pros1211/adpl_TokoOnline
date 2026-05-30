package com.tokoonline.model;

public class Perabotan extends Produk {
    private String bahan;
    private String kategori;
    private double panjang;
    private double lebar;
    private double tinggi;

    public Perabotan(int idProduk, int idPenjual, String nama, double harga, double berat, int stok, String bahan,
            String kategori,
            double panjang, double lebar, double tinggi) {
        super(idProduk, idPenjual, nama, harga, berat, stok, "Perabotan");
        this.bahan = bahan;
        this.kategori = kategori;
        this.panjang = panjang;
        this.lebar = lebar;
        this.tinggi = tinggi;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getVolume() {
        return panjang * lebar * tinggi;
    }

    public double getPanjang() {
        return panjang;
    }

    public void setPanjang(double panjang) {
        this.panjang = panjang;
    }

    public double getLebar() {
        return lebar;
    }

    public void setLebar(double lebar) {
        this.lebar = lebar;
    }

    public double getTinggi() {
        return tinggi;
    }

    public void setTinggi(double tinggi) {
        this.tinggi = tinggi;
    }

}
