package com.tokoonline.facade;

import java.util.List;

import com.tokoonline.Strategi.Bank;
import com.tokoonline.Strategi.Gopay;
import com.tokoonline.Strategi.Qris;
import com.tokoonline.Strategi.StrategiPembayaran;
import com.tokoonline.backend.PesananRepository;
import com.tokoonline.builder.PesananBuilder;
import com.tokoonline.model.ItemPesanan;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Pesanan;
import com.tokoonline.model.Produk;

public class PesananFacade {
    // atribut builder pesanan
    private PesananBuilder keranjangBuilder;
    // atribut database pesanan untuk melakukan request data dengan query
    private PesananRepository pesananRepo;

    public PesananFacade() {
        this.keranjangBuilder = new PesananBuilder();
        this.pesananRepo = new PesananRepository();
    }

    // method tambah item ke keranjang
    public void tambahKeKeranjang(Produk produk, int qty) {
        ItemPesanan itemBaru = new ItemPesanan(produk, qty);

        keranjangBuilder.tambahItem(itemBaru);
    }

    // method buat pesanan baru
    public Pesanan buatPesanan(Pelanggan pembeli, String alamat, String ekspedisi) {
        try {
            Pesanan pesananBaru = keranjangBuilder
                    .setPelanggan(pembeli)
                    .setAlamat(alamat)
                    .setEkspedisi(ekspedisi)
                    .build();

            boolean suksesSimpan = pesananRepo.simpanPesanan(pesananBaru);
            // jika true maka reset keranjang agar tidak sama seperti sebelumnya
            if (suksesSimpan) {
                keranjangBuilder.reset();
                return pesananBaru;
            }
        } catch (IllegalStateException e) {
            System.out.println("Checkout Gagal: " + e.getMessage());
        }
        return null;
    }

    // method bayar pesanan
    public void bayarPesanan(Pesanan pesanan, String metodePilihan) {
        // atribut untuk menyimpan metode pembayaran
        StrategiPembayaran strategi = null;
        switch (metodePilihan) {
            // jika method pembayaran = 1 maka gopay
            case "1":
                strategi = new Gopay(pesanan.getPembeli().getNoHp());
                break;
            case "2":
                // jika method pembayaran = 2 maka qris
                strategi = new Qris("QRIS-PAYLOAD-" + pesanan.getIdPesanan());
                break;
            // jika method pembayaran = 2 maka BCA VA
            case "3":
                strategi = new Bank("BCA Virtual Account", "8077" + pesanan.getPembeli().getNoHp());
                break;
            default:
                System.out.println("Metode pembayaran tidak dikenali.");
                return;
        }
        // set metode pembayaran di objek pesanan
        pesanan.setMetodePembayaran(strategi);
        // eksekusi pembayaran
        pesanan.bayar();
        // update status pesanan menjadi dikirim
        pesananRepo.updateStatusPesanan(pesanan);
    }

    public List<Pesanan> ambilRiwayatPesanan(Pelanggan pembeli) {
        return pesananRepo.getRiwayatPesanan(pembeli);
    }

    // method pembatalan pesanan
    public void batalkanPesanan(Pesanan pesanan) {
        pesanan.batal();
        pesananRepo.updateStatusPesanan(pesanan);
    }

    // method update status menjadi kirim pesanan
    public void kirimPesanan(Pesanan pesanan) {
        pesanan.kirim();
        pesananRepo.updateStatusPesanan(pesanan);
    }
}
