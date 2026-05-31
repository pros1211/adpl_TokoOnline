package com.tokoonline.State;

import com.tokoonline.model.Pesanan;

public class Dikirim implements StatePesanan {
    @Override
    public void bayar(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + " sudah dibayar");

    }

    public void proses(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + " sudah diproses");

    }

    public void kirim(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + " Sudah dikirim dengan alamat dan ekpedisi yang tercantum");

    }

    public void batal(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + " tidak dapat dibatalkan");
        System.out.println("Barang sedang dikirim dan sedang dalam perjalanan ke alamat Anda.");
    }
}
