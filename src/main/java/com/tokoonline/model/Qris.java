package com.tokoonline.model;

import com.tokoonline.Strategi.StrategiPembayaran;

public class Qris implements StrategiPembayaran {
    @Override
    public void prosesPembayaran(Double jumlah) {
        System.out.println("[QRIS] Menampilkan QR Code dinamis senilai Rp" + jumlah);
        System.out.println("[QRIS] Pembeli melakukan scan, pembayaran BERHASIL.");
        
    }
}
