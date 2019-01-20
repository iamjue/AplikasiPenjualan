package com.iamjue.aplikasipenjualan.fragment;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.iamjue.aplikasipenjualan.Data.DataStok;
import com.iamjue.aplikasipenjualan.R;
import com.iamjue.aplikasipenjualan.adapter.AdapterStokView;
import com.iamjue.aplikasipenjualan.server.Server;
import com.iamjue.aplikasipenjualan.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentStokView extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    SwipeRefreshLayout swipe;
    List<DataStok> itemList = new ArrayList<DataStok>();
    AdapterStokView adapterStokView;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    String idBarang, namaBarang, stokBarang;
    SharedPreferences sharedPreferences;
    private static final String TAG = FragmentStokView.class.getSimpleName();
    private static String url_select = Server.URL + "select_tb_stok.php";

    public static final String TAG_ID_BARANG = "idBarang";
    public static final String TAG_NAMA_BARANG = "namaBarang";
    public static final String TAG_STOK_BARANG = "stokBarang";
    public static final String TAG_HARGA = "hargaBarang";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    public static FragmentStokView newInstance() {
        return new FragmentStokView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.fragment_stok_view, container, false );
        //menghubung variabel pada layout dan pada java
        swipe = (SwipeRefreshLayout) v.findViewById( R.id.swipe_refresh_layout );
        list = (ListView) v.findViewById( R.id.list_stok );

        // untuk mengisi data dari JSON ke dalam adapterStokView
        adapterStokView = new AdapterStokView( FragmentStokView.this, itemList );
        list.setAdapter( (ListAdapter) adapterStokView );

        //menampilkan widget refresh
        swipe.setOnRefreshListener( this );
        swipe.post( new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing( true );
                itemList.clear();
                adapterStokView.notifyDataSetChanged();
                selectTbStok();
            }
        } );


        return v;
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapterStokView.notifyDataSetChanged();
        selectTbStok();

    }

    // untuk menampilkan semua dataStok pada listview
    private void selectTbStok() {
        itemList.clear();
        adapterStokView.notifyDataSetChanged();
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

                        DataStok item = new DataStok();

                        item.setIdBarang( obj.getString( TAG_ID_BARANG ) );
                        item.setNamaBarang( obj.getString( TAG_NAMA_BARANG ) );
                        item.setStokBarang( obj.getString( TAG_STOK_BARANG ) );
                        item.setHarga( obj.getString( TAG_HARGA ) );

                        //menambahkan item ke array
                        itemList.add( item );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // notifikasi adanya perubahan data pada adapterStokView
                adapterStokView.notifyDataSetChanged();

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
}
