package com.tokoonline.State;

import com.tokoonline.model.Pesanan;

public class Diproses implements StatePesanan {
    @Override
    public void bayar(Pesanan pesanan) {
        System.out.println(pesanan.getDetailPesanan() + " sudah dibayar");

    }

    public void proses(Pesanan pesanan) {
        System.out.println(pesanan.getIdPesanan() + " sedang diproses");

    }

    public void kirim(Pesanan pesanan) {
        System.out.println(pesanan.getIdPesanan() + " akan dikirim ke alamat" + pesanan.getAlamatkirim()
                + " menggunakan ekpedisi " + pesanan.getEkspedisi());
        pesanan.setStatus(new Dikirim());
    }

    public void batal(Pesanan pesanan) {
        System.out.println("Pesanan berhasil dibatalkan.");
        System.out.println("Sistem sedang memproses pengembalian dana ke metode pembayaran Anda.");
        pesanan.setStatus(new Dibatalkan());

    }
}
