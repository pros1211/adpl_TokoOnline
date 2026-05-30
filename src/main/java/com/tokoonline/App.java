package com.tokoonline;

import com.tokoonline.config.DatabaseConnection;
import com.tokoonline.ui.MainTUI;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        DatabaseConnection singleton = DatabaseConnection.getInstance();
        System.out.println("alamat memori 1: " + singleton.hashCode());
        DatabaseConnection singleton2 = DatabaseConnection.getInstance();
        System.out.println("alamat memori 2: " + singleton.hashCode());
        if (singleton == singleton2) {
            System.out.println("✅ SUKSES: Kedua variabel memegang objek memori yang SAMA.");
            System.out.println("Singleton pattern Anda bekerja sempurna mengamankan koneksi XAMPP!");
        } else {
            System.out.println("❌ GAGAL: Terdeteksi ada lebih dari satu koneksi yang terbuka!");
        }
        MainTUI terminalUI = new MainTUI();
        terminalUI.run();
    }
}
