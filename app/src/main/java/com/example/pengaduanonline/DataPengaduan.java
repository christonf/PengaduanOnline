package com.example.pengaduanonline;

public class DataPengaduan {
    private String username;
    private String judul;
    private String tanggal;
    private String lokasi;
    private String isi;
    private String key;

    public DataPengaduan() {}
    public DataPengaduan(String username, String judul, String tanggal, String lokasi, String isi) {
        this.username = username;
        this.judul = judul;
        this.tanggal = tanggal;
        this.lokasi = lokasi;
        this.isi = isi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
