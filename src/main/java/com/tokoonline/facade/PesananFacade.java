package com.tokoonline.facade;

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
    private PesananBuilder keranjangBuilder;

    private PesananRepository pesananRepo;

    public PesananFacade() {
        this.keranjangBuilder = new PesananBuilder();
        this.pesananRepo = new PesananRepository();
    }

    public void tambahKeKeranjang(Produk produk, int qty) {
        ItemPesanan itemBaru = new ItemPesanan(produk, qty);

        keranjangBuilder.tambahItem(itemBaru);
    }

    public Pesanan buatPesanan(Pelanggan pembeli, String alamat, String ekspedisi) {
        try {
            Pesanan pesananBaru = keranjangBuilder
                    .setPelanggan(pembeli)
                    .setAlamat(alamat)
                    .setEkspedisi(ekspedisi)
                    .build();

            boolean suksesSimpan = pesananRepo.simpanPesanan(pesananBaru);

            if (suksesSimpan) {
                keranjangBuilder.reset(); // RESET KERANJANG
                return pesananBaru;
            }
        } catch (IllegalStateException e) {
            System.out.println("Checkout Gagal: " + e.getMessage());
        }
        return null;
    }

    public void bayarPesanan(Pesanan pesanan, String metodePilihan) {
        StrategiPembayaran strategi = null;

        switch (metodePilihan) {
            case "1":
                strategi = new Gopay(pesanan.getPembeli().getNoHp());
                break;
            case "2":
                strategi = new Qris("QRIS-PAYLOAD-" + pesanan.getIdPesanan());
                break;
            case "3":
                strategi = new Bank("BCA Virtual Account", "8077" + pesanan.getPembeli().getNoHp());
                break;
            default:
                System.out.println("Metode pembayaran tidak dikenali.");
                return;
        }

        pesanan.setMetodePembayaran(strategi);

        pesanan.bayar();
        pesananRepo.updateStatusPesanan(pesanan);
    }

    public java.util.List<Pesanan> ambilRiwayatPesanan(Pelanggan pembeli) {
        return pesananRepo.getRiwayatPesanan(pembeli);
    }

    public void batalkanPesanan(Pesanan pesanan) {
        pesanan.batal();
        pesananRepo.updateStatusPesanan(pesanan);
    }

    public void kirimPesanan(Pesanan pesanan) {
        pesanan.kirim();
        pesananRepo.updateStatusPesanan(pesanan);
    }
}
