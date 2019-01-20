package com.iamjue.aplikasipenjualan.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.iamjue.aplikasipenjualan.Data.DataCustomer;

import com.iamjue.aplikasipenjualan.Detail;
import com.iamjue.aplikasipenjualan.MainActivity;
import com.iamjue.aplikasipenjualan.R;
import com.iamjue.aplikasipenjualan.adapter.AdapterCustomer;

import com.iamjue.aplikasipenjualan.adapter.ViewPagerAdapter;
import com.iamjue.aplikasipenjualan.app.AppController;
import com.iamjue.aplikasipenjualan.server.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentDataCustomerView extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnClickListener {
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataCustomer> itemList = new ArrayList<DataCustomer>();
    AdapterCustomer adapterCustomer;
    FloatingActionButton fabCustomer;
    int success;
    Adapter adapter;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    String id, no_penjualan, nama_barang, nama_pembeli, no_hp, harga_barang, tgl_pembelian, alamat;
    SharedPreferences sharedPreferences;
    EditText etId, etNoPenjualan, etNamaBarang, etNamaPebeli, etNoHP, etHarga, etAlamat, etTglPembelian;
    private static final String TAG = FragmentDataCustomerView.class.getSimpleName();
    private static String url_select = Server.URL + "select_tb_customer.php";
    private static String url_insert = Server.URL + "insert_tb_customer.php";
    private static String url_update = Server.URL + "update_tb_customer.php";
    private static String url_edit = Server.URL + "edit_tb_customer.php";
    private static String url_delete = Server.URL + "delete_tb_customer.php";

    private static final String TAG_ID = "id";
    public static final String TAG_NO_PENJUALAN = "no_penjualan";
    public static final String TAG_NAMA_BARANG = "nama_barang";
    public static final String TAG_NAMA_PEMBELI = "nama_pembeli";
    public static final String TAG_NO_HP = "no_hp";
    public static final String TAG_HARGA_BARANG = "harga_barang";
    public static final String TAG_TGL_PEMBELIAN = "tgl_pembelian";
    public static final String TAG_ALAMAT = "alamat";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    public static FragmentDataCustomerView newInstance() {
        return new FragmentDataCustomerView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.fragment_data_customer_view, container, false );
        //menghubung variabel pada layout dan pada java
        swipe = (SwipeRefreshLayout) v.findViewById( R.id.swipe_refresh_layout_customer );
        list = (ListView) v.findViewById( R.id.list_customer );
        fabCustomer = (FloatingActionButton) v.findViewById( R.id.fab_create );
        fabCustomer.setOnClickListener( this );

        // untuk mengisi data dari JSON ke dalam adapterStokView
        adapterCustomer = new AdapterCustomer( FragmentDataCustomerView.this, itemList );
        list.setAdapter( (ListAdapter) adapterCustomer );

        //menampilkan widget refresh
        swipe.setOnRefreshListener( this );
        swipe.post( new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing( true );
                itemList.clear();
                adapterCustomer.notifyDataSetChanged();
                selectTbCustomer();
            }
        } );
        holdList();


        return v;
    }

    public void holdList() {

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get( position ).getId();

                final CharSequence[] dialogitem = {"Detail", "Edit", "Delete"};
                dialog = new AlertDialog.Builder( getActivity() );
                dialog.setCancelable( true );
                dialog.setItems( dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                DataCustomer dataCustomer = new DataCustomer( );
                                dataCustomer.setNamaPembeli( itemList.get(position).getNamaPembeli() );
                                dataCustomer.setAlamat( itemList.get( position ).getAlamat() );
                                dataCustomer.setTglPembelian( itemList.get( position ).getTglPembelian() );
                                dataCustomer.setHargaBarang( itemList.get(position).getHargaBarang() );
                                dataCustomer.setNoHp( itemList.get(position).getNoHp() );
                                dataCustomer.setNamaBarang( itemList.get( position ).getNamaBarang() );
                                dataCustomer.setNoPenjualan( itemList.get( position).getNoPenjualan() );

                                Intent detailIntent = new Intent( getActivity() , Detail.class );
                                detailIntent.putExtra( "data", dataCustomer );
                                startActivity( detailIntent );
                                break;
                            case 1:
                                edit( idx );
                                break;
                            case 2:
                                delete( idx );
                                break;
                        }
                    }
                } ).show();
                return false;
            }
        } );
    }

    private void edit(final String idx) {
        StringRequest strReq = new StringRequest( Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d( TAG, "Response: " + response.toString() );

                try {
                    JSONObject jObj = new JSONObject( response );
                    success = jObj.getInt( TAG_SUCCESS );

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d( "get edit data", jObj.toString() );
                        String idx = jObj.getString( TAG_ID );
                        String noPenjualanx = jObj.getString( TAG_NO_PENJUALAN );
                        String namaBarangx = jObj.getString( TAG_NAMA_BARANG );
                        String namaPembelix = jObj.getString( TAG_NAMA_PEMBELI );
                        String noHpx = jObj.getString( TAG_NO_HP );
                        String hargaBarangx = jObj.getString( TAG_HARGA_BARANG );
                        String tglPembelianx = jObj.getString( TAG_TGL_PEMBELIAN );
                        String alamatx = jObj.getString( TAG_ALAMAT );

                        DialogFormCustomer( idx, noPenjualanx, namaBarangx, namaPembelix, noHpx, hargaBarangx,tglPembelianx,
                                alamatx, "UPDATE" );

                        adapterCustomer.notifyDataSetChanged();

                    } else {
                        Toast.makeText( getActivity(), jObj.getString( TAG_MESSAGE ), Toast.LENGTH_LONG ).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( TAG, "Error: " + error.getMessage() );
                Toast.makeText( getActivity(), error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } ) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put( "id", idx );

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue( strReq, tag_json_obj );


    }

    private void delete(final String idx) {
        StringRequest strReq = new StringRequest( Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d( TAG, "Response: " + response.toString() );

                try {
                    JSONObject jObj = new JSONObject( response );
                    success = jObj.getInt( TAG_SUCCESS );

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d( "delete", jObj.toString() );

                        selectTbCustomer();

                        Toast.makeText( getActivity(), jObj.getString( TAG_MESSAGE ), Toast.LENGTH_LONG ).show();

                        adapterCustomer.notifyDataSetChanged();

                    } else {
                        Toast.makeText( getActivity(), jObj.getString( TAG_MESSAGE ), Toast.LENGTH_LONG ).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( TAG, "Error: " + error.getMessage() );
                Toast.makeText( getActivity(), error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } ) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put( "id", idx );

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue( strReq, tag_json_obj );
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapterCustomer.notifyDataSetChanged();
        selectTbCustomer();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_create) {
            DialogFormCustomer( "", "", "", "", "",
                    "", "", "", "SIMPAN" );
        }

    }

    private void selectTbCustomer() {
        itemList.clear();
        adapterCustomer.notifyDataSetChanged();
        swipe.setRefreshing( true );

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest( url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d( TAG, response.toString() );

                //parsing JSON
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject( i );

                        DataCustomer item = new DataCustomer();

                        item.setId( obj.getString( TAG_ID ) );
                        item.setNoPenjualan( obj.getString( TAG_NO_PENJUALAN ) );
                        item.setNamaBarang( obj.getString( TAG_NAMA_BARANG ) );
                        item.setNamaPembeli( obj.getString( TAG_NAMA_PEMBELI ) );
                        item.setNoHp( obj.getString( TAG_NO_HP ) );
                        item.setHargaBarang( obj.getString( TAG_HARGA_BARANG ) );
                        item.setTglPembelian( obj.getString( TAG_TGL_PEMBELIAN ) );
                        item.setAlamat( obj.getString( TAG_ALAMAT ) );

                        //menambahkan item ke array
                        itemList.add( item );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // notifikasi adanya perubahan data pada adapterCut
                adapterCustomer.notifyDataSetChanged();

                swipe.setRefreshing( false );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( TAG, "Error: " + error.getMessage() );
                swipe.setRefreshing( false );
            }
        } );
        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue( jArr );

    }

    // untuk menampilkan dialog form Customer
    private void DialogFormCustomer(String idx, String noPenjualanx, String namaBarangx, String namaPembelix, String noHpx, String hargaBarangx,
                                    String tglPembelianx, String alamatx, String button) {


        dialog = new AlertDialog.Builder( getActivity() );
        inflater = getLayoutInflater();
        dialogView = inflater.inflate( R.layout.form_customer, null );
        dialog.setView( dialogView );
        dialog.setCancelable( true );
        dialog.setIcon( R.mipmap.ic_launcher );
        dialog.setTitle( "Form Customer" );

        etId = (EditText) dialogView.findViewById( R.id.et_id );
        etNoPenjualan = (EditText) dialogView.findViewById( R.id.et_noPembelian );
        etNamaBarang = (EditText) dialogView.findViewById( R.id.et_namaBarang );
        etNamaPebeli = (EditText) dialogView.findViewById( R.id.et_namaPembeli );
        etNoHP = (EditText) dialogView.findViewById( R.id.et_noHp );
        etHarga = (EditText) dialogView.findViewById( R.id.et_hargaBarang );
        etTglPembelian = (EditText) dialogView.findViewById( R.id.et_tglPembelian );
        etAlamat = (EditText) dialogView.findViewById( R.id.et_alamat );
        if (!idx.isEmpty()) {
            etId.setText( idx );
            etNoPenjualan.setText( noPenjualanx );
            etNamaBarang.setText( namaBarangx );
            etNamaPebeli.setText( namaPembelix );
            etNoHP.setText( noHpx );
            etHarga.setText( hargaBarangx );
            etTglPembelian.setText( tglPembelianx );
            etAlamat.setText( alamatx );
        } else {
            kosong();
        }

        dialog.setPositiveButton( button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = etId.getText().toString();
                no_penjualan = etNoPenjualan.getText().toString();
                nama_barang = etNamaBarang.getText().toString();
                nama_pembeli = etNamaPebeli.getText().toString();
                no_hp = etNoHP.getText().toString();
                harga_barang = etHarga.getText().toString();
                tgl_pembelian = etTglPembelian.getText().toString();
                alamat = etAlamat.getText().toString();

                simpan_update();
                dialog.dismiss();

            }
        } );

        dialog.setNegativeButton( "BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        } );

        dialog.show();
    }
//    private void DialogDetail(){
//        dialog = new AlertDialog.Builder( getActivity() );
//        inflater = getLayoutInflater();
//        dialogView = inflater.inflate( R.layout.dialog_detail, null );
//        dialog.setView( dialogView );
//        dialog.setCancelable( true );
//        dialog.setTitle( "Detail Customer" );
//
//        TextView tv = (TextView)dialogView.findViewById( R.id.tv_detail );
//
//        DataCustomer dataCustomer = new DataCustomer(  );
//
//        dialog.setNegativeButton( "Kembali", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        } );
//        dialog.show();
//    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id.isEmpty()) {
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest( Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d( TAG, "Response: " + response.toString() );

                try {
                    JSONObject jObj = new JSONObject( response );
                    success = jObj.getInt( TAG_SUCCESS );

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d( "Add/update", jObj.toString() );

                        selectTbCustomer();
                        kosong();

                        Toast.makeText( getActivity(), jObj.getString( TAG_MESSAGE ), Toast.LENGTH_LONG ).show();
                        adapterCustomer.notifyDataSetChanged();

                    } else {
                        Toast.makeText( getActivity(), jObj.getString( TAG_MESSAGE ), Toast.LENGTH_LONG ).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( TAG, "Error: " + error.getMessage() );
                Toast.makeText( getActivity(), error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } ) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()) {
                    params.put( "no_penjualan", no_penjualan );
                    params.put( "nama_barang", nama_barang );
                    params.put( "nama_pembeli", nama_pembeli );
                    params.put( "no_hp", no_hp );
                    params.put( "harga_barang", harga_barang );
                    params.put( "tgl_pembelian", tgl_pembelian );
                    params.put( "alamat", alamat );

                } else {
                    params.put( "id", id );
                    params.put( "no_penjualan", no_penjualan );
                    params.put( "nama_barang", nama_barang );
                    params.put( "nama_pembeli", nama_pembeli );
                    params.put( "no_hp", no_hp );
                    params.put( "harga_barang", harga_barang );
                    params.put( "tgl_pembelian", tgl_pembelian );
                    params.put( "alamat", alamat );

                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue( strReq, tag_json_obj );
    }


    private void kosong() {
        etId.setText( null );
        etNoPenjualan.setText( null );
        etNamaBarang.setText( null );
        etNamaPebeli.setText( null );
        etNoHP.setText( null );
        etHarga.setText( null );
        etTglPembelian.setText( null );
        etAlamat.setText( null );
    }

}
