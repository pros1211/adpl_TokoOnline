package com.tokoonline.facade;

import com.tokoonline.backend.PelangganRepository;
import com.tokoonline.backend.PenjualRepository;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Penjual;

public class AuthFacade {
    // autentikasi penjual
    // atribut database penjual
    private PenjualRepository repoPenjual = new PenjualRepository();
    // menyimpan objek penjual yang sedang login
    private Penjual penjualAktif = null;
    // atribut database pembeli
    private PelangganRepository pelangganRepo = new PelangganRepository();
    // menyimpan objek pembeli yang sedang login
    private Pelanggan pelangganAktif = null;

    // login penjual
    public boolean login(String username, String password) {
        Penjual autentikasiPenjual = repoPenjual.verifikasiLogin(username, password);
        if (autentikasiPenjual != null) {
            // jika ditemukan data akun penjual maka set penjual aktif
            this.penjualAktif = autentikasiPenjual;
            return true;
        }
        return false;
    }

    // method register akun penjual
    public boolean registerPenjual(String username, String password) {
        Penjual prosesAkun = repoPenjual.buatAkun(username, password);
        if (prosesAkun != null) {
            this.penjualAktif = prosesAkun;
            return true;
        }
        return false;
    }

    public Penjual getPenjualAktif() {
        return penjualAktif;
    }

    // pelanggan section
    // method login pelanggan
    public boolean loginPelanggan(String username, String password) {
        Pelanggan autentikasiPelanggan = pelangganRepo.verifikasiLogin(username, password);
        if (autentikasiPelanggan != null) {
            this.pelangganAktif = autentikasiPelanggan;
            return true;
        }
        return false;
    }

    // login menggunakan nomor telepon
    public boolean loginPelangganByPhone(String nomorHP, String password) {
        Pelanggan autentikasiPelanggan = pelangganRepo.verifikasiLoginByPhone(nomorHP, password);
        if (autentikasiPelanggan != null) {
            this.pelangganAktif = autentikasiPelanggan;
            return true;
        }
        return false;
    }

    // method register akun pembeli
    public boolean registerPelanggan(String username, String password, String alamat, String nomorHP) {
        Pelanggan prosesPelanggan = pelangganRepo.buatAkun(username, password, alamat, nomorHP);
        if (prosesPelanggan != null) {
            this.pelangganAktif = prosesPelanggan;
            return true;
        }
        return false;
    }

    public Pelanggan getPelangganAktif() {
        return pelangganAktif;
    }

}
