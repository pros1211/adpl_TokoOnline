package com.tokoonline.Strategi;

import java.util.Locale;

public class Gopay implements StrategiPembayaran {
    private String nomorHp;

    public Gopay(String nomorHp) {
        this.nomorHp = nomorHp;
    }

    @Override
    public void prosesPembayaran(Double jumlah) {
        String nominal = String.format(new Locale("id", "ID"), "%,.0f", jumlah);
        System.out.println("[GOPAY] Mengoneksikan ke Gopay dengan nomor: " + this.nomorHp + "...");
        System.out.println("[GOPAY] Sukses memotong saldo Gopay sebesar Rp" + nominal);

    }
}
