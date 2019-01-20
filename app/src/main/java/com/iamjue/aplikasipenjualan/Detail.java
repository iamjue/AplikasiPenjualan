package com.iamjue.aplikasipenjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iamjue.aplikasipenjualan.Data.DataCustomer;

public class Detail extends AppCompatActivity {
    TextView tvNama, tvNoPenjualan, tvNamaBarang, tvNoHp, tvAlamat, tvTgl, tvHarga;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        getSupportActionBar().setTitle( "Detail Customer" );
        tvNama = findViewById( R.id.tv_namaDetail );
        tvNoPenjualan = findViewById( R.id.tv_noPenjualanDetail );
        tvNamaBarang = findViewById( R.id.tv_namaBarangDetail );
        tvNoHp = findViewById( R.id.tv_noHpDetail );
        tvAlamat = findViewById( R.id.tv_AlamatDetail );
        tvTgl = findViewById( R.id.tv_tglPembelianDetail );
        tvHarga = findViewById( R.id.tv_HargaBarangDetail );
        btnBack = findViewById( R.id.btn_backDetail );

        DataCustomer dataCustomer = getIntent().getParcelableExtra( "data" );

        tvNama.setText( ": " + dataCustomer.getNamaPembeli() );
        tvNoPenjualan.setText( ": " + dataCustomer.getNoPenjualan() );
        tvNamaBarang.setText( ": " + dataCustomer.getNamaBarang() );
        tvNoHp.setText( ": " + dataCustomer.getNoHp() );
        tvAlamat.setText( ": " + dataCustomer.getAlamat() );
        tvTgl.setText( ": " + dataCustomer.getTglPembelian() );
        tvHarga.setText( ": " + dataCustomer.getHargaBarang() );
        btnBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                startActivity( intent );
            }
        } );

    }
}
