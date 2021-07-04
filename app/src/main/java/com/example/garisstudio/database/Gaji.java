package com.example.garisstudio.database;

public class Gaji {
    String No_Gaji, tanggal_gaji, nik;

    public Gaji(String no_Gaji, String tanggal_gaji, String nik) {
        No_Gaji = no_Gaji;
        this.tanggal_gaji = tanggal_gaji;
        this.nik = nik;
    }

    public Gaji() {
    }

    public String getNo_Gaji() {
        return No_Gaji;
    }

    public void setNo_Gaji(String no_Gaji) {
        No_Gaji = no_Gaji;
    }

    public String getTanggal_gaji() {
        return tanggal_gaji;
    }

    public void setTanggal_gaji(String tanggal_gaji) {
        this.tanggal_gaji = tanggal_gaji;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }
}
