package com.tokoonline.State;

import com.tokoonline.model.Pesanan;

public class Dibatalkan implements StatePesanan {
    @Override
    public void bayar(Pesanan pesanan) {
        System.out.println("Pesanan sukses dibatalkan");
    }

    @Override
    public void proses(Pesanan pesanan) {
        System.out.println("Pesanan dibatalkan, tidak bisa diproses.");
    }

    @Override
    public void kirim(Pesanan pesanan) {
        System.out.println("Pesanan dibatalkan, tidak bisa dikirim.");
    }

    @Override
    public void batal(Pesanan pesanan) {
        System.out.println("Pesanan ini sudah berstatus Dibatalkan.");
    }
}
