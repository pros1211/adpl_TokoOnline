package com.tokoonline.model;

import com.tokoonline.Strategi.StrategiPembayaran;

public class Gopay implements StrategiPembayaran {
    @Override
    public void prosesPembayaran(Double jumlah) {
        System.out.println("[GOPAY] Mengoneksikan ke dompet digital...");
        System.out.println("[GOPAY] Sukses memotong saldo Gopay sebesar Rp" + jumlah);
       
    }
}
