package com.iamjue.aplikasipenjualan.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iamjue.aplikasipenjualan.Data.DataStok;
import com.iamjue.aplikasipenjualan.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterStokView extends BaseAdapter {
    Fragment fragment;
    private LayoutInflater inflater;
    private List<DataStok> items;

    public AdapterStokView(Fragment fragment, List<DataStok> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get( location );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity()
                    .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null)
            convertView = inflater.inflate( R.layout.list_row_stok, null );

        TextView idBarang = (TextView) convertView.findViewById( R.id.tv_id_barang );
        TextView namaBarang = (TextView) convertView.findViewById( R.id.tv_nama_barang );
        TextView stokBarang = (TextView) convertView.findViewById( R.id.tv_jumlah_stok );
        TextView harga = (TextView)convertView.findViewById( R.id.tv_harga ) ;

        DataStok dataStok = items.get( position );
        idBarang.setText( dataStok.getIdBarang() );
        namaBarang.setText( dataStok.getNamaBarang() );
        stokBarang.setText( dataStok.getStokBarang() );
        harga.setText( dataStok.getHarga() );
        return convertView;
    }
}
