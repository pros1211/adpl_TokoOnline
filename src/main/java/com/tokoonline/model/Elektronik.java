package com.tokoonline.model;

public class Elektronik extends Produk {
    private double daya;
    private int garansi;
    private String merk;

    public Elektronik(int idProduk, int idPenjual, String nama, double harga, double berat, int stok, String merk,
            int garansi,
            double daya) {
        super(idProduk, idPenjual, nama, harga, berat, stok, "Elektronik");
        this.daya = daya;
        this.garansi = garansi;
        this.merk = merk;
    }

    public double getDaya() {
        return daya;
    }

    public void setDaya(double daya) {
        this.daya = daya;
    }

    public int getGaransi() {
        return garansi;
    }

    public void setGaransi(int garansi) {
        this.garansi = garansi;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

}
