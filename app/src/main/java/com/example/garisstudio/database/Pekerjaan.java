package com.example.garisstudio.database;

public class Pekerjaan {
    String jabatan, gaji_pokok, tunjangan_kesehatan, tunjangan_pendidikan, tunjangan_transportasi, total_gaji;

    public Pekerjaan(String jabatan, String gaji_pokok, String tunjangan_kesehatan, String tunjangan_pendidikan, String tunjangan_transportasi, String total_gaji) {
        this.jabatan = jabatan;
        this.gaji_pokok = gaji_pokok;
        this.tunjangan_kesehatan = tunjangan_kesehatan;
        this.tunjangan_pendidikan = tunjangan_pendidikan;
        this.tunjangan_transportasi = tunjangan_transportasi;
        this.total_gaji = total_gaji;
    }

    public Pekerjaan() {
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getGaji_pokok() {
        return gaji_pokok;
    }

    public void setGaji_pokok(String gaji_pokok) {
        this.gaji_pokok = gaji_pokok;
    }

    public String getTunjangan_kesehatan() {
        return tunjangan_kesehatan;
    }

    public void setTunjangan_kesehatan(String tunjangan_kesehatan) {
        this.tunjangan_kesehatan = tunjangan_kesehatan;
    }

    public String getTunjangan_pendidikan() {
        return tunjangan_pendidikan;
    }

    public void setTunjangan_pendidikan(String tunjangan_pendidikan) {
        this.tunjangan_pendidikan = tunjangan_pendidikan;
    }

    public String getTunjangan_transportasi() {
        return tunjangan_transportasi;
    }

    public void setTunjangan_transportasi(String tunjangan_transportasi) {
        this.tunjangan_transportasi = tunjangan_transportasi;
    }

    public String getTotal_gaji() {
        return total_gaji;
    }

    public void setTotal_gaji(String total_gaji) {
        this.total_gaji = total_gaji;
    }
}
