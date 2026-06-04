package com.tokoonline.State;

import com.tokoonline.model.Pesanan;

public class Dikirim implements StatePesanan {
    @Override
    public void bayar(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + "[SUDAH DIBAYAR]");

    }

    public void proses(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + "[SUDAH DIPROSES]");

    }

    public void kirim(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + "[Sudah dikirim dengan alamat dan ekpedisi yang tercantum]");

    }

    public void batal(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + "[TIDAK DAPAT DIBATALKAN]:Barang sedang dikirim dan sedang dalam perjalanan ke alamat Anda.");

    }
}
