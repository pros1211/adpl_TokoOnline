package com.tokoonline.model;

public class Pakaian extends Produk {
    private String warna;
    private String gender;
    private String ukuran;

    public Pakaian(int idProduk, int idPenjual, String nama, double harga, double berat, int stok,
            String warna, String gender,
            String ukuran) {
        super(idProduk, idPenjual, nama, harga, berat, stok, "Pakaian");
        this.warna = warna;
        this.gender = gender;
        this.ukuran = ukuran;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

}
