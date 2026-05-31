package com.tokoonline.model;

public abstract class Produk {
    private int idProduk;
    private int idPenjual;
    private String nama;
    private double harga;
    private double berat;
    private int stok;
    private String jenis;

    public Produk(int idProduk, int idPenjual, String namaBarang, double harga, double berat, int stok, String jenis) {
        this.idProduk = idProduk;
        this.idPenjual = idPenjual;
        this.nama = namaBarang;
        this.harga = harga;
        this.berat = berat;
        this.stok = stok;
        this.jenis = jenis;
    }
    public void setIdProduk(int id){
        this.idProduk=id;
    }
    public int getIdProduk(){
    return this.idProduk;
    }

    public int getIdPenjual() {
        return this.idPenjual;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

}
