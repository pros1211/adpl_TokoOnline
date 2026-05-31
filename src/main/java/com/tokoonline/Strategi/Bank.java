package com.tokoonline.Strategi;

import java.util.Locale;

public class Bank implements StrategiPembayaran {
    private String namaBank;
    private String nomorRekening;

    public Bank(String namaBank, String nomorRekening) {
        this.namaBank = namaBank;
        this.nomorRekening = nomorRekening;
    }

    @Override
    public void prosesPembayaran(Double jumlah) {
        String nominal = String.format(new Locale("id", "ID"), "%,.0f", jumlah);
        System.out.println("[BANK] Menampilkan nomor Virtual Account:" + namaBank + " : " + nomorRekening);
        System.out.println("[BANK] Dana transfer Rp" + nominal + " telah masuk ke rekening.");

    }
}
