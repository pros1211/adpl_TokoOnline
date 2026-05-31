package com.tokoonline.model;

import com.tokoonline.State.StatePesanan;

public class Diproses implements StatePesanan {
     @Override
        public void bayar(Pesanan pesanan){
            System.out.println(pesanan.idPesanan+" sudah dibayar");

        }
 public void proses(Pesanan pesanan){
            System.out.println(pesanan.idPesanan+" sedang diproses");

        }
        public void kirim(Pesanan pesanan){
            System.out.println(pesanan.idPesanan+" akan dikirim ke alamat"+pesanan.alamatkirim+" menggunakan ekpedisi "+pesanan.ekspedisi);
            pesanan.setStatus(new Dikirim());
        }
        public void batal(Pesanan pesanan){
            System.out.println(pesanan.idPesanan+" tidak dapat dibatalkan");

        }
}
