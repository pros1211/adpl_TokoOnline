package com.tokoonline.facade;

import java.time.LocalDate;

import com.tokoonline.backend.ProductRepository;
import com.tokoonline.factory.ProductFactory;
import com.tokoonline.model.Elektronik;
import com.tokoonline.model.Kecantikan;
import com.tokoonline.model.Makanan;
import com.tokoonline.model.Pakaian;
import com.tokoonline.model.Perabotan;
import com.tokoonline.model.Produk;

public class ProdukFacade {
    private ProductRepository dataProduk = new ProductRepository();

    public boolean tambahElektronik(int id_penjual, String nama, double harga, double berat,
            int stok, String merk, int garansi, double daya) {
        Produk p = ProductFactory.buatProduk("Elektronik", id_penjual, nama, harga, berat, stok);
        if (p == null)
            return false;

        if (p instanceof Elektronik) {
            Elektronik elektronik = (Elektronik) p;
            elektronik.setMerk(merk);
            elektronik.setGaransi(garansi);
            elektronik.setDaya(daya);
        }

        return dataProduk.simpanProduk(p);
    }

    public boolean tambahMakanan(int id_penjual, String nama, double harga, double berat,
            int stok, String kategori, boolean halal,
            LocalDate tanggalKadaluarsa, String bahan) {
        Produk p = ProductFactory.buatProduk("Makanan", id_penjual, nama, harga, berat, stok);
        if (p == null)
            return false;

        if (p instanceof Makanan) {
            Makanan makanan = (Makanan) p;
            makanan.setHalal(halal);
            makanan.setKategori(kategori);
            makanan.setTanggalKadaluarsa(tanggalKadaluarsa);
            makanan.setBahan(bahan);
        }

        return dataProduk.simpanProduk(p);
    }

    public boolean tambahPakaian(int id_penjual, String nama, double harga, double berat,
            int stok, String warna, String gender,
            String ukuran) {
        Produk p = ProductFactory.buatProduk("Pakaian", id_penjual, nama, harga, berat, stok);
        if (p == null)
            return false;

        if (p instanceof Pakaian) {
            Pakaian pakaian = (Pakaian) p;
            pakaian.setWarna(warna);
            pakaian.setGender(gender);
            pakaian.setUkuran(ukuran);
        }

        return dataProduk.simpanProduk(p);
    }

    public boolean tambahKecantikan(int id_penjual, String nama, double harga, double berat,
            int stok, String kategori, String bahan,
            LocalDate tanggalKadaluarsa, double volume) {
        Produk p = ProductFactory.buatProduk("Kecantikan", id_penjual, nama, harga, berat, stok);
        if (p == null)
            return false;

        if (p instanceof Kecantikan) {
            Kecantikan kecantikan = (Kecantikan) p;
            kecantikan.setBahan(bahan);
            kecantikan.setKategori(kategori);
            kecantikan.setTanggalKadaluarsa(tanggalKadaluarsa);
            kecantikan.setVolume(volume);
        }

        return dataProduk.simpanProduk(p);
    }

    public boolean tambahPerabotan(int id_penjual, String nama, double harga, double berat,
            int stok, String kategori, String bahan,
            double panjang, double lebar, double tinggi) {
        Produk p = ProductFactory.buatProduk("Perabotan", id_penjual, nama, harga, berat, stok);
        if (p == null)
            return false;

        if (p instanceof Perabotan) {
            Perabotan perabotan = (Perabotan) p;
            perabotan.setBahan(bahan);
            perabotan.setKategori(kategori);
            perabotan.setLebar(lebar);
            perabotan.setPanjang(panjang);
            perabotan.setTinggi(tinggi);
        }

        return dataProduk.simpanProduk(p);
    }

    private String getKategori(int pilihan) {
        switch (pilihan) {
            case 1:
                return "Elektronik";
            case 2:
                return "Makanan";
            case 3:
                return "Pakaian";
            case 4:
                return "Kecantikan";
            case 5:
                return "Perabotan";
            default:
                return null;
        }
    }

    public java.util.List<Produk> ambilSemuaProduk() {
        return dataProduk.getAllProduk();
    }

    public java.util.List<Produk> cariProdukByNamaToko(String namaToko) {
        return dataProduk.getAllProdukByToko(namaToko);
    }
}
