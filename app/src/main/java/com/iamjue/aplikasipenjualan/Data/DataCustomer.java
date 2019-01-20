package com.iamjue.aplikasipenjualan.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class DataCustomer implements Parcelable {
    private String id, noPenjualan, namaBarang, namaPembeli, noHp, hargaBarang,tglPembelian, alamat;

    public DataCustomer(){

    }
    public DataCustomer(String id,String noPenjualan,String namaBarang,String namaPembeli,String noHp,String hargaBarang,
                        String tglPembelian,String alamat){
        this.id = id;
        this.noPenjualan = noPenjualan;
        this.namaBarang = namaBarang;
        this.namaPembeli = namaPembeli;
        this.noHp = noHp;
        this.hargaBarang = hargaBarang;
        this.tglPembelian = tglPembelian;
        this.alamat = alamat;
    }

    protected DataCustomer(Parcel in) {
        id = in.readString();
        noPenjualan = in.readString();
        namaBarang = in.readString();
        namaPembeli = in.readString();
        noHp = in.readString();
        hargaBarang = in.readString();
        tglPembelian = in.readString();
        alamat = in.readString();
    }

    public static final Creator<DataCustomer> CREATOR = new Creator<DataCustomer>() {
        @Override
        public DataCustomer createFromParcel(Parcel in) {
            return new DataCustomer( in );
        }

        @Override
        public DataCustomer[] newArray(int size) {
            return new DataCustomer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoPenjualan() {
        return noPenjualan;
    }

    public void setNoPenjualan(String noPenjualan) {
        this.noPenjualan = noPenjualan;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getTglPembelian() {
        return tglPembelian;
    }

    public void setTglPembelian(String tglPembelian) {
        this.tglPembelian = tglPembelian;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString( id );
        parcel.writeString( noPenjualan );
        parcel.writeString( namaBarang );
        parcel.writeString( namaPembeli );
        parcel.writeString( noHp );
        parcel.writeString( hargaBarang );
        parcel.writeString( tglPembelian );
        parcel.writeString( alamat );
    }
}
