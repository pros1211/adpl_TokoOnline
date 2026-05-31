package com.tokoonline.builder;

import java.util.ArrayList;
import java.util.List;

import com.tokoonline.State.StatePesanan;
import com.tokoonline.model.ItemPesanan;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Pesanan;

public class PesananBuilder {
    private int id;
    private Pelanggan pembeli;
    private StatePesanan currentState;
    private List<ItemPesanan> daftarItem = new ArrayList<>();
    private String alamatKirim;
    private String ekspedisi;
    private Double totalHarga = 0.0;

    public PesananBuilder setPelanggan(Pelanggan pembeli) {
        this.pembeli = pembeli;
        return this;
    }

    public PesananBuilder tambahItem(ItemPesanan item) {
        this.daftarItem.add(item);
        this.totalHarga += item.getSubtotal();
        return this;
    }

    public PesananBuilder setAlamat(String alamat) {
        this.alamatKirim = alamat;
        return this;
    }

    public PesananBuilder setEkspedisi(String kurir) {
        this.ekspedisi = kurir;
        return this;
    }

    public Pesanan build() {
        if (this.daftarItem.isEmpty()) {
            throw new IllegalStateException("Keranjang tidak boleh kosong!");
        }
        if (this.pembeli == null || this.alamatKirim == null) {
            throw new IllegalStateException("Data pembeli dan alamat harus lengkap!");
        }

        return new Pesanan(this);
    }

    public int getId() {
        return id;
    }

    public Pelanggan getPembeli() {
        return pembeli;
    }

    public StatePesanan getCurrentState() {
        return currentState;
    }

    public List<ItemPesanan> getDaftarItem() {
        return daftarItem;
    }

    public String getAlamatKirim() {
        return alamatKirim;
    }

    public String getEkspedisi() {
        return ekspedisi;
    }

    public Double getTotalHarga() {
        return totalHarga;
    }

}
