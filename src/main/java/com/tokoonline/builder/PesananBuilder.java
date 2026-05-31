package com.tokoonline.builder;

import java.util.List;

import com.tokoonline.model.Pesanan;
import com.tokoonline.model.Produk;
import com.tokoonline.State.StatePesanan;
import com.tokoonline.model.ItemPesanan;
import com.tokoonline.model.Pelanggan;
public class PesananBuilder {
    int id;
    Pelanggan pembeli;
    StatePesanan currentState;
    List<ItemPesanan> daftaritem;
    String alamatKirim;
    String ekpedisi;
    Double totalharga;
    
    
    public Double getTotalharga() {
        return totalharga;
    }
    public PesananBuilder(int id) {
        this.id = id;
    }
    public boolean setPelanggan(Pelanggan p) {
        if (p != null) {
            this.pembeli = p;
            return true;
        } else {
            return false;
        }
    }
    public boolean tambahitem(Produk item,int kuantitas){
        ItemPesanan items=new ItemPesanan(item, kuantitas);
        if(items!=null){
            daftaritem.add(items);
            this.totalharga+=items.getSubtotal();
            return true;

        }
        return false;
    }
    public boolean setAlamat(String alamat){
        if(alamat!=null){
            this.alamatKirim=alamat;
            return true;
        }
        return false;
    }
    public boolean setEkpedisiKurir(String ekpedisi){
        if(ekpedisi!=null){
            this.ekpedisi=ekpedisi;
            return true;
        }
        return false;
    }
    public Pesanan build(){
        return  new Pesanan(this);
    }
    public int getId() {
        return id;
    }
    public Pelanggan getPembeli() {
        return pembeli;
    }
    public Object getCurrentState() {
        return currentState;
    }
    public List<ItemPesanan> getDaftaritem() {
        return daftaritem;
    }
    public String getAlamatKirim() {
        return alamatKirim;
    }
    public String getEkpedisi() {
        return ekpedisi;
    }
}
