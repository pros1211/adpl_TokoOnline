package com.tokoonline.facade;

import com.tokoonline.backend.PelangganRepository;
import com.tokoonline.backend.PenjualRepository;
import com.tokoonline.model.Pelanggan;
import com.tokoonline.model.Penjual;

public class AuthFacade {
    private PenjualRepository repoPenjual = new PenjualRepository();
    private Penjual penjualAktif = null;
    private PelangganRepository pelangganRepo=new PelangganRepository();
    private Pelanggan pelangganAktif = null;
  
    public boolean login(String username, String password) {
        Penjual autentikasiPenjual = repoPenjual.verifikasiLogin(username, password);
        if (autentikasiPenjual != null) {
            this.penjualAktif = autentikasiPenjual;
            return true;
        }
        return false;
    }

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

     public boolean loginPelanggan(String username, String password) {
        Pelanggan autentikasiPelanggan = pelangganRepo.verifikasiLogin(username, password);
        if (autentikasiPelanggan != null) {
            this.pelangganAktif = autentikasiPelanggan;
            return true;
        }
        return false;
    }

    public boolean registerPelanggan(String username, String password) {
        Pelanggan prosesAkun = pelangganRepo.buatAkun(username, password);
        if (prosesAkun != null) {
            this.pelangganAktif = prosesAkun;
            return true;
        }
        return false;
    }

    public Pelanggan getPelangganAktif() {
        return pelangganAktif;
    }

}
