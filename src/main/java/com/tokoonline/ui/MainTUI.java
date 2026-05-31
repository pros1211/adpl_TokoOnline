package com.tokoonline.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.tokoonline.facade.AuthFacade;
import com.tokoonline.facade.PesananFacade;
import com.tokoonline.facade.ProdukFacade;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Pesanan;
import com.tokoonline.model.Produk;

public class MainTUI {
    private Scanner scanner;
    private AuthFacade autentikasi;
    private ProdukFacade tambahProdukFacade;
    private PesananFacade pesananFacade;
    public MainTUI() {
        this.scanner = new Scanner(System.in);
        this.autentikasi = new AuthFacade();
        this.tambahProdukFacade = new ProdukFacade();
        this.pesananFacade=new PesananFacade();
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=======================================");
            System.out.println("   Selamat Datang di Toko Online");
            System.out.println("=======================================");
            System.out.println("1. Masuk sebagai Penjual");
            System.out.println("2. Masuk sebagai Pembeli (Belum tersedia)");
            System.out.println("3. Buat Akun Baru");
            System.out.println("0. Keluar dari Aplikasi");
            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    menuLoginPenjual();
                    break;
                case "2":
                   menuLoginPembeli();
                    break;
                case "3":
                    menuBuatAkun();
                    break;
                case "0":
                    running = false;
                    System.out.println("Terima kasih telah menggunakan layanan kami. Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private void menuLoginPenjual() {
        System.out.println("\n--- LOGIN PENJUAL ---");
        System.out.print("Username : ");
        String username = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        System.out.println("Mencocokkan data ke database...");
        boolean berhasil = autentikasi.login(username, password);

        if (berhasil) {
            System.out.println("Login Berhasil! Selamat datang, " + username);
            dashboardPenjual();
        } else {
            System.out.println("Login Gagal! Username atau password salah.");
        }
    }

    private void menuBuatAkun() {
        System.out.println("\n--- BUAT AKUN BARU ---");
        System.out.println("Daftar sebagai:");
        System.out.println("1. Penjual");
        System.out.println("2. Pembeli");
        System.out.print("Pilih menu (1-2): ");

        String peran = scanner.nextLine();

        if (peran.equals("1")) {
            System.out.println("\n[Registrasi Penjual]");
            System.out.print("Masukkan Username baru : ");
            String newUsername = scanner.nextLine();
            System.out.print("Masukkan Password baru : ");
            String newPassword = scanner.nextLine();

            boolean hasilRegister = autentikasi.registerPenjual(newUsername, newPassword);
            System.out.println("Mengirim data registrasi ke database...");
            if (hasilRegister) {
                System.out.println("Pembuatan akun penjual berhasil! Selamat datang, " + newUsername);

            }

        } else if (peran.equals("2")) {
            System.out.println("Fitur Registrasi Pembeli belum tersedia.");
        } else {
            System.out.println("menu tidak dikenali. Batal membuat akun.");
        }
    }

    private void menuTambahProduk(int idPenjual) {
        System.out.println("\n--- TAMBAH PRODUK BARU ---");
        System.out.println("Pilih Kategori Produk:");
        System.out.println("1. Elektronik");
        System.out.println("2. Makanan");
        System.out.println("3. Pakaian");
        System.out.println("4. Kecantikan");
        System.out.println("5. Perabotan");
        System.out.print("Kategori (1-5): ");
        int kodeBarang = Integer.parseInt(scanner.nextLine());

        System.out.print("Nama Produk : ");
        String nama = scanner.nextLine();

        System.out.print("Harga (Rp)  : ");
        double harga = Double.parseDouble(scanner.nextLine());

        System.out.print("Berat (kg)  : ");
        double berat = Double.parseDouble(scanner.nextLine());

        System.out.print("Stok Awal   : ");
        int stok = Integer.parseInt(scanner.nextLine());
        boolean sukses = false;
        switch (kodeBarang) {
            case 1:
                System.out.print("Merk        : ");
                String merk = scanner.nextLine();
                System.out.print("Garansi (bln): ");
                int garansi = Integer.parseInt(scanner.nextLine());
                System.out.print("Daya (Watt) : ");
                sukses = tambahProdukFacade.tambahElektronik(idPenjual, nama, harga, berat, stok, merk, garansi,
                        harga);

                if (sukses) {
                    System.out.println("Produk '" + nama + "' berhasil masuk ke database toko Anda.");
                } else {
                    System.out.println("Gagal menambahkan produk.");
                }
                break;
            case 2:
                System.out.print("kategori (makanan/minuman): ");
                String kategori = scanner.nextLine();
                System.out.print("bahan : ");
                String bahan = scanner.nextLine();
                System.out.print("Apakah Halal? (Y/N): ");
                String inputHalal = scanner.nextLine();
                boolean isHalal = inputHalal.equalsIgnoreCase("Y");

                System.out.print("Tanggal Kadaluarsa (Format: YYYY-MM-DD): ");
                String inputTanggal = scanner.nextLine();
                LocalDate tanggalKadaluarsa = null;
                try {
                    tanggalKadaluarsa = LocalDate.parse(inputTanggal);
                } catch (Exception e) {
                    System.out.println("Format tanggal salah! Menggunakan tanggal hari ini sebagai default.");
                    tanggalKadaluarsa = LocalDate.now();
                }
                sukses = tambahProdukFacade.tambahMakanan(idPenjual, nama, harga, berat, stok, kategori,
                        isHalal, tanggalKadaluarsa, bahan);

                if (sukses) {
                    System.out.println("Produk '" + nama + "' berhasil masuk ke database toko Anda.");
                } else {
                    System.out.println("Gagal menambahkan produk.");
                }
                break;
            case 3:
                System.out.print("Warna       : ");
                String warna = scanner.nextLine();
                System.out.print("Gender (L/P): ");
                String gender = scanner.nextLine();
                System.out.print("Ukuran (S/M/L/XL/XXL): ");
                String ukuran = scanner.nextLine();

                sukses = tambahProdukFacade.tambahPakaian(idPenjual, nama, harga, berat, stok, warna, gender, ukuran);
                if (sukses) {
                    System.out.println("Produk '" + nama + "' berhasil masuk ke database toko Anda.");
                } else {
                    System.out.println("Gagal menambahkan produk.");
                }
                break;
            case 4:
                System.out.print("kategori :");
                kategori = scanner.nextLine();
                System.out.print("bahan : ");
                bahan = scanner.nextLine();
                System.out.print("Tanggal Kadaluarsa (Format: YYYY-MM-DD): ");
                inputTanggal = scanner.nextLine();
                tanggalKadaluarsa = null;
                try {
                    tanggalKadaluarsa = LocalDate.parse(inputTanggal);
                } catch (Exception e) {
                    System.out.println("Format tanggal salah! Menggunakan tanggal hari ini sebagai default.");
                    tanggalKadaluarsa = LocalDate.now();
                }
                System.out.print("Volume (ml) : ");
                double volume = Double.parseDouble(scanner.nextLine());
                sukses = tambahProdukFacade.tambahKecantikan(idPenjual, nama, harga, berat, stok, kategori, bahan,
                        tanggalKadaluarsa, volume);
                if (sukses) {
                    System.out.println("Produk '" + nama + "' berhasil masuk ke database toko Anda.");
                } else {
                    System.out.println("Gagal menambahkan produk.");
                }
                break;
            case 5:
                System.out.print("Kategori (Contoh: Meja/Kursi) : ");
                String kategoriPerabotan = scanner.nextLine();

                System.out.print("Bahan (Contoh: Kayu Jati)   : ");
                bahan = scanner.nextLine();

                System.out.print("Panjang (cm)                : ");
                double panjang = Double.parseDouble(scanner.nextLine());

                System.out.print("Lebar (cm)                  : ");
                double lebar = Double.parseDouble(scanner.nextLine());

                System.out.print("Tinggi (cm)                 : ");
                double tinggi = Double.parseDouble(scanner.nextLine());

                sukses = tambahProdukFacade.tambahPerabotan(idPenjual, nama, harga, berat, stok,
                        bahan, kategoriPerabotan, panjang, lebar, tinggi);
                if (sukses) {
                    System.out.println("Produk '" + nama + "' berhasil masuk ke database toko Anda.");
                } else {
                    System.out.println("Gagal menambahkan produk.");
                }
                break;
        }

    }

  
    private void dashboardPenjual() {
        boolean diDashboard = true;

        // Ambil ID penjual yang sedang login dari AuthFacade
        int idPenjualAktif = autentikasi.getPenjualAktif().getId();

        while (diDashboard) {
            System.out.println("\n=== TOKO SAYA (" + autentikasi.getPenjualAktif().getUsername() + ") ===");
            System.out.println("1. Tambah Produk Baru");
            System.out.println("0. Logout");
            System.out.print("Pilih (0-1): ");

            String pilihan = scanner.nextLine();

            if (pilihan.equals("1")) {
                menuTambahProduk(idPenjualAktif);
            } else if (pilihan.equals("0")) {
                System.out.println("Berhasil Logout.");
                diDashboard = false;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
      private void menuLoginPembeli() {
        System.out.println("\n--- LOGIN PEMBELI / PELANGGAN ---");
        System.out.print("Username : ");
        String username = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        System.out.println("Mencocokkan data pembeli ke database...");
        // Memanggil fungsi login khusus pelanggan di AuthFacade
        boolean berhasil = autentikasi.loginPelanggan(username, password);

        if (berhasil) {
            System.out.println("Login Berhasil! Selamat datang, " + username);
            dashboardPembeli(); // Buka dashboard jika sukses
        } else {
            System.out.println("❌ Login Gagal! Username atau password salah.");
        }
    }
    private void dashboardPembeli() {
        Pelanggan pelangganaktif=autentikasi.getPelangganAktif();
    
 System.out.println("\n=======================================");
        System.out.println("     DASHBOARD UTAMA PEMBELI      ");
        System.out.println("=======================================");
        System.out.println("Selamat Datang, " + pelangganaktif.getUsername().toUpperCase());
    
   System.out.println("\n--- KATALOG PRODUK REAL-TIME ---");
        List<Produk> katalogProduk = tambahProdukFacade.ambilSemuaProduk();

        if (katalogProduk == null || katalogProduk.isEmpty()) {
            System.out.println("Toko saat ini kosong, belum ada produk yang dijual.");
            return;
        }       
        for (Produk p : katalogProduk) {
            System.out.println("ID: [" + p.getIdProduk() + "] " + p.getNama() + " | Harga: Rp" + p.getHarga() + " | Stok: " + p.getStok());
        }
        System.out.println("=======================================");

        System.out.print("Masukkan ID Produk yang ingin dibeli: ");
        int idPilihan = Integer.parseInt(scanner.nextLine());
        System.out.print("Masukkan Jumlah Kuantitas (Qty)     : ");
        int qty = Integer.parseInt(scanner.nextLine());

        Produk produkPilihan = null;
        for (Produk p : katalogProduk) {
            if (p.getIdProduk()== idPilihan) {
                produkPilihan = p;
                break;
            }
        }

        if (produkPilihan == null) {
            System.out.println("ID Produk tidak ditemukan. Transaksi dibatalkan.");
            return;
        }

        if (produkPilihan.getStok() < qty) {
            System.out.println("Stok tidak mencukupi! Stok tersedia saat ini: " + produkPilihan.getStok());
            return;
        }

        // Masukkan item ke dalam keranjang belanja temporary
        pesananFacade.tambahKeKeranjang(produkPilihan, qty);

        System.out.print("\nMasukkan Alamat Pengiriman: ");
        String alamat = scanner.nextLine();
        System.out.print("Masukkan Ekspedisi Kirim (JNE/J&T): ");
        String ekspedisi = scanner.nextLine();

        // Facade memicu perakitan objek pesanan serta mengikat relasi dengan pelangganAktif
        Pesanan pesananSaya = pesananFacade.buatPesananOtomatis(pelangganaktif, alamat, ekspedisi);
        if (pesananSaya != null) {
            System.out.println("\n Pesanan Berhasil Dibuat! Total Tagihan: Rp" + pesananSaya.getTotalHarga());
            System.out.println(" Total transaksi di akun Anda: " + pelangganaktif.getHistoryPesanan().size() + " pesanan.");
            
            boolean diMenuPesanan = true;
            while (diMenuPesanan) {
                System.out.println("\n--- AKSI PESANAN #" + pesananSaya.getIdPesanan() + " ---");
                System.out.println("1. Bayar Sekarang (Strategy & State Pattern)");
                System.out.println("2. Kirim Barang (Simulasi Penjual/Kurir)");
                System.out.println("3. Batalkan Pesanan (Cancel State)");
                System.out.println("0. Selesai / Kembali ke Menu Utama");
                System.out.print("Pilih Aksi (0-3): ");
                String aksi = scanner.nextLine();

                switch (aksi) {
                    case "1":
                        menuBayarPesanan(pesananSaya);
                        break;
                    case "2":
                        pesananSaya.kirim(); // mengirim pesanan
                        break;
                    case "3":
                        pesananSaya.batal(); //  membatalkan pesanan
                        break;
                    case "0":
                        diMenuPesanan = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            }
        } else {
            System.out.println("Gagal memproses transaksi pesanan.");
        }
    }

private void menuBayarPesanan(Pesanan pesanan) {
    System.out.println("\n--- PILIH METODE PEMBAYARAN (STRATEGY PATTERN) ---");
    System.out.println("1. GoPay");
    System.out.println("2. QRIS");
    System.out.println("3. Transfer Bank Virtual Account");
    System.out.print("Pilih metode (1-3): ");
    String metode = scanner.nextLine();

    // FACADE BEKERJA: Menembakkan instruksi bayar ke objek strategi yang cocok
    pesananFacade.bayarPesanan(pesanan, metode);
}
}
