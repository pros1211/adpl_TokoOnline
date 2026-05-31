package com.tokoonline.model;

import java.util.*;

import com.tokoonline.State.StatePesanan;
import com.tokoonline.Strategi.StrategiPembayaran;
import com.tokoonline.builder.PesananBuilder;
public class Pesanan{
    int idPesanan;
    StatePesanan currentState;
    Pelanggan pembeli;
    List<ItemPesanan> daftaritem;
    String alamatkirim;
    String ekspedisi;
    StrategiPembayaran metodePembayaran;
    Double totalHarga;

    
    public Pesanan(PesananBuilder builder){
        this.idPesanan=builder.getId();
        this.currentState=new Diproses();
        this.pembeli=builder.getPembeli();
        this.daftaritem=builder.getDaftaritem();
        this.alamatkirim=builder.getAlamatKirim();
        this.ekspedisi=builder.getEkpedisi();
        this.totalHarga=builder.getTotalharga();
    }
    public String getDetailPesanan(){
        String hasil="";
        for(ItemPesanan isi: daftaritem){
            hasil+=isi.produk.getNama()+" ";
        }

        return this.idPesanan+" "+hasil+" "+this.ekspedisi+" "+this.alamatkirim;
    }

    public void setStatus(StatePesanan newstate){
        this.currentState=newstate;
    }

    public void bayar(){
       if (this.metodePembayaran == null) {
            System.out.println("[SISTEM] Gagal Bayar: Silakan pilih metode pembayaran terlebih dahulu!");
            return;
        }

      this.currentState.bayar(this);
    }

    public void kirim(){
     this.currentState.kirim(this);
    }

    public void setMetodePembayaran(StrategiPembayaran metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
public void batal(){
        // Delegasikan keputusan pembatalan ke State aktif saat ini
        // Jika statusnya sudah 'Dikirim', otomatis State tersebut akan menolak pembatalan
        this.currentState.batal(this); 
    }
public int getIdPesanan() {
    return idPesanan;
}
public StatePesanan getCurrentState() {
    return currentState;
}
public Pelanggan getPembeli() {
    return pembeli;
}
public List<ItemPesanan> getDaftaritem() {
    return daftaritem;
}
public String getAlamatkirim() {
    return alamatkirim;
}
public String getEkspedisi() {
    return ekspedisi;
}
public StrategiPembayaran getMetodePembayaran() {
    return metodePembayaran;
}
public Double getTotalHarga() {
    return totalHarga;
}
    
}