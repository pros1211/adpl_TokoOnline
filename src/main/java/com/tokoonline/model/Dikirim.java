package com.tokoonline.model;

import com.tokoonline.State.StatePesanan;

public class Dikirim implements StatePesanan {
        @Override
        public void bayar(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+" sudah dibayar");
        
        }
 public void proses(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+" sudah diproses");
            
        }
        public void kirim(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+" Sudah dikirim dengan alamat dan ekpedisi yang tercantum");

        }
        public void batal(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+" tidak dapat dibatalkan");

        }
}
