package com.tokoonline.model;

public class Penjual extends Pengguna {

    public Penjual(int idPenjual, String username, String password) {
        super(idPenjual, username, password);
    }

    public Penjual(String username, String password) {
        super(username, password);
    }
}
