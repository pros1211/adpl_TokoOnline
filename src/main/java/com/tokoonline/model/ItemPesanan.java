package com.tokoonline.model;

public class ItemPesanan {
    private Produk produk;
    private int kuantitas;
    private Double subtotal;

    public ItemPesanan(Produk produk, int kuantitas) {
        this.produk = produk;
        this.kuantitas = kuantitas;
        this.subtotal = produk.getHarga() * kuantitas;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public Double getSubtotal() {
        return this.subtotal;

    }

}
