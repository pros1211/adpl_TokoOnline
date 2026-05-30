package com.tokoonline.factory;

import java.time.LocalDate;

import com.tokoonline.model.Elektronik;
import com.tokoonline.model.Kecantikan;
import com.tokoonline.model.Makanan;
import com.tokoonline.model.Pakaian;
import com.tokoonline.model.Perabotan;
import com.tokoonline.model.Produk;

public class ProductFactory {
    public static Produk buatProduk(String jenis, int idPenjual, String nama, double harga, double berat, int stok) {

        int idProduk = 0;

        switch (jenis.toLowerCase()) {
            case "elektronik":
                return new Elektronik(idProduk, idPenjual, nama, harga, berat, stok, "", 0, 0.0);

            case "makanan":
                return new Makanan(idProduk, idPenjual, nama, harga, berat, stok, "", false, LocalDate.now(), "");

            case "pakaian":
                return new Pakaian(idProduk, idPenjual, nama, harga, berat, stok, "", "", "");

            case "kecantikan":
                return new Kecantikan(idProduk, idPenjual, nama, harga, berat, stok, "", "", LocalDate.now(),
                        0.0);

            case "perabotan":
                return new Perabotan(idProduk, idPenjual, nama, harga, berat, stok, "", "", 0.0, 0.0, 0.0);

            default:
                System.out.println("Kategori produk tidak dikenali oleh sistem.");
                return null;
        }
    }
}
