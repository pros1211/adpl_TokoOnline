    package com.tokoonline.builder;

    import java.util.ArrayList;
    import java.util.List;

    import com.tokoonline.State.StatePesanan;
    import com.tokoonline.model.ItemPesanan;
    import com.tokoonline.model.Pelanggan;
    import com.tokoonline.model.Pesanan;

    public class PesananBuilder {
        // atribut id pesanan default agar dimulai dari 1
        private int id = 1;
        private Pelanggan pembeli;
        private StatePesanan currentState;
        // list pesanan dalam satu pesanan
        private List<ItemPesanan> daftarItem = new ArrayList<>();
        private String alamatKirim;
        private String ekspedisi;
        // total harga default 0
        private Double totalHarga = 0.0;

        // method untuk set pembeli
        public PesananBuilder setPelanggan(Pelanggan pembeli) {
            this.pembeli = pembeli;
            return this;
        }

        // method untuk memasukkan list item pesanan di keranjang
        public PesananBuilder tambahItem(ItemPesanan item) {
            this.daftarItem.add(item);
            this.totalHarga += item.getSubtotal();
            return this;
        }

        // method untuk set alamat pengiriman
        public PesananBuilder setAlamat(String alamat) {
            this.alamatKirim = alamat;
            return this;
        }

        // method untuk set kurir ekspedisi
        public PesananBuilder setEkspedisi(String kurir) {
            this.ekspedisi = kurir;
            return this;
        }

        // method untuk build pesanan
        public Pesanan build() {
            // jika arraylist daftarItem masih kosong maka return keranjang kosong
            if (this.daftarItem.isEmpty()) {
                throw new IllegalStateException("Keranjang tidak boleh kosong!");
            }
            if (this.pembeli == null || this.alamatKirim == null) {
                // jika belum mengisi alamat kirim atau login akun pembeli maka gagal
                throw new IllegalStateException("Data pembeli dan alamat harus lengkap!");
            }

            return new Pesanan(this);
        }

        // method untuk reset daftar item pesanan
        public void reset() {
            this.daftarItem = new ArrayList<>();
            this.totalHarga = 0.0;
            this.pembeli = null;
            this.alamatKirim = null;
            this.ekspedisi = null;
        }

        public int getId() {
            return id;
        }

        public Pelanggan getPembeli() {
            return pembeli;
        }

        public StatePesanan getCurrentState() {
            return currentState;
        }

        public List<ItemPesanan> getDaftarItem() {
            return daftarItem;
        }

        public String getAlamatKirim() {
            return alamatKirim;
        }

        public String getEkspedisi() {
            return ekspedisi;
        }

        public Double getTotalHarga() {
            return totalHarga;
        }

    }
