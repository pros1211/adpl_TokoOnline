package com.tokoonline.Strategi;

import java.util.Locale;

public class Qris implements StrategiPembayaran {
    private String payload;

    public Qris(String qrisPayload) {
        this.payload = payload;
    }

    @Override
    public void prosesPembayaran(Double jumlah) {
        String nominal = String.format(new Locale("id", "ID"), "%,.0f", jumlah);
        System.out.println("[QRIS] Menampilkan kode QR Code senilai Rp" + nominal);
        System.out.println("[QRIS] Pembeli melakukan scan, pembayaran BERHASIL.");

    }
}
