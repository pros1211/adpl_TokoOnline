package com.tokoonline.model;

import com.tokoonline.Strategi.StrategiPembayaran;

public class Bank implements StrategiPembayaran {
    @Override
    public void prosesPembayaran(Double jumlah) {
        System.out.println("[BANK] Menampilkan nomor Virtual Account...");
        System.out.println("[BANK] Dana transfer Rp" + jumlah + " telah masuk ke rekening.");
      
    }
}
