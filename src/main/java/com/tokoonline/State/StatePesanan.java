package com.tokoonline.State;

import com.tokoonline.model.Pesanan;

public interface StatePesanan {
  void bayar(Pesanan pesanan);

  void proses(Pesanan pesanan);

  void kirim(Pesanan pesanan);

  void batal(Pesanan pesanan);
}
