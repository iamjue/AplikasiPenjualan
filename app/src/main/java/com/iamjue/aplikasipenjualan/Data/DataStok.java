package com.iamjue.aplikasipenjualan.Data;

public class DataStok {
    private String idBarang, namaBarang, stokBarang, harga; //*sesuaikan dengan database table*

    public DataStok() {
    }

    public DataStok(String idBarang, String namaBarang, String stokBarang, String harga) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.stokBarang = stokBarang;
        this.harga = harga;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getStokBarang() {
        return stokBarang;
    }

    public void setStokBarang(String stokBarang) {
        this.stokBarang = stokBarang;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
