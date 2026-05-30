package com.tokoonline.facade;

import com.tokoonline.backend.PenjualRepository;
import com.tokoonline.model.Penjual;

public class AuthFacade {
    private PenjualRepository repoPenjual = new PenjualRepository();
    private Penjual penjualAktif = null;

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

}
