package com.tokoonline.facade;

import java.util.ArrayList;
import java.util.List;

import com.tokoonline.Strategi.StrategiPembayaran;
import com.tokoonline.builder.PesananBuilder;
import com.tokoonline.model.Bank;
import com.tokoonline.model.Gopay;
import com.tokoonline.model.ItemPesanan;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Pesanan;
import com.tokoonline.model.Produk;
import com.tokoonline.model.Qris;

public class PesananFacade {
    private static int idCounter = 1000; // Auto-increment untuk ID Pesanan
    private List<ItemPesanan> keranjangSesiIni;

    public PesananFacade() {
        this.keranjangSesiIni = new ArrayList<>();
    }

    // 1. Membantu TUI memasukkan produk langsung ke keranjang belanja pembeli
    public void tambahKeKeranjang(Produk produk, int kuantitas) {
        ItemPesanan item = new ItemPesanan(produk, kuantitas);
        this.keranjangSesiIni.add(item);
    }

    public List<ItemPesanan> getKeranjangSesiIni() {
        return this.keranjangSesiIni;
    }

    public void kosongkanKeranjang() {
        this.keranjangSesiIni.clear();
    }

    // 2. Merakit pesanan secara otomatis menggunakan PesananBuilder
    public Pesanan buatPesananOtomatis(Pelanggan pembeli, String alamat, String ekspedisi) {
        if (keranjangSesiIni.isEmpty()) {
            return null;
        }

        idCounter++;
        PesananBuilder builder = new PesananBuilder(idCounter);
        builder.setPelanggan(pembeli);
        builder.setAlamat(alamat);
        builder.setEkpedisiKurir(ekspedisi);

        // Pindahkan semua item di keranjang ke dalam builder
        for (ItemPesanan item : keranjangSesiIni) {
            builder.tambahitem(item.getProduk(), item.getKuantitas());
        }

        // Kosongkan keranjang sesi setelah checkout sukses
        kosongkanKeranjang();

        return builder.build();
    }

    // 3. Menangani eksekusi pembayaran menggunakan Strategy Pattern
    public boolean bayarPesanan(Pesanan pesanan, String pilihanMetode) {
        // Factory sederhana untuk menentukan objek strategi pembayaran
        StrategiPembayaran strategi;
        switch (pilihanMetode) {
            case "1":
                strategi = new Gopay();
                break;
            case "2":
                strategi = new Qris();
                break;
            case "3":
                strategi = new Bank();
                break;
            default:
                System.out.println("❌ Metode pembayaran tidak dikenali!");
                return false;
        }

        // Suntikkan strategi ke dalam objek pesanan
        pesanan.setMetodePembayaran(strategi);
        
        // Eksekusi pembayaran (yang di dalamnya memicu pemotongan saldo & transisi State)
        pesanan.bayar();
        return true;
    }
}
