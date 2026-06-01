package com.tokoonline.State;

import com.tokoonline.model.Pesanan;

public class MenungguPembayaran implements StatePesanan {
    @Override
    public void bayar(Pesanan pesanan) {
        // jika dibayar maka kembalikan pesan konfirmasi
        System.out.println(pesanan.getDetailPesanan() + " berhasil dibayar");
        pesanan.getMetodePembayaran().prosesPembayaran(pesanan.getTotalHarga());

        System.out.println("Pesanan ID #" + pesanan.getIdPesanan() + " berhasil dilunasi.");

        pesanan.setStatus(new Diproses());
    }

    public void proses(Pesanan pesanan) {
        // tidak bisa diproses sehingga menunggu pembayaran selesai
        System.out.println(pesanan.getDetailPesanan() + " menunggu pembayaran");

    }

    public void kirim(Pesanan pesanan) {

        // tidak bisa dikirim sehingga menunggu pembayaran selesai
        System.out.println(pesanan.getDetailPesanan() + " belum dibayar");

    }

    @Override
    public void batal(Pesanan pesanan) {
        System.out.println("Pesanan berhasil dibatalkan. Menghapus tagihan pembayaran...");
        pesanan.setStatus(new Dibatalkan());
    }
}
