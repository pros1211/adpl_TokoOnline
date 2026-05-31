package com.tokoonline.model;

import com.tokoonline.State.StatePesanan;

public class MenungguPembayaran implements StatePesanan {
      @Override
        public void bayar(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+" berhasil dibayar");
            pesanan.metodePembayaran.prosesPembayaran(pesanan.totalHarga);
        
      
            System.out.println("Pesanan ID #" + pesanan.idPesanan + " berhasil dilunasi.");
            
        
            pesanan.setStatus(new Diproses());
        }
 public void proses(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+" menunggu pembayaran");
            
        }
        public void kirim(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+"belum dibayar");

        }
        public void batal(Pesanan pesanan){
            System.out.println(pesanan.getDetailPesanan()+" Dibatalkan");

        }
}
