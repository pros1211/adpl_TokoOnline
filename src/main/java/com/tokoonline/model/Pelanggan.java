package com.tokoonline.model;

import java.util.ArrayList;
import java.util.List;

public class Pelanggan extends Pengguna {
    private String noHp;
    private String alamat;
    private List<Pesanan> historyPesanan;

    public Pelanggan(int idPelanggan, String username, String password) {
        super(idPelanggan, username, password);
        this.historyPesanan = new ArrayList<>();
    }

    public Pelanggan(String username, String password) {
        super(username, password);
    }

    public void tambahHistory(Pesanan pesanan) {
        this.historyPesanan.add(pesanan);
    }

    public List<Pesanan> getHistoryPesanan() {
        return historyPesanan;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setHistoryPesanan(List<Pesanan> historyPesanan) {
        this.historyPesanan = historyPesanan;
    }

}
