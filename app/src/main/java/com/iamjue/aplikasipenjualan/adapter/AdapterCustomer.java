package com.iamjue.aplikasipenjualan.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iamjue.aplikasipenjualan.Data.DataCustomer;
import com.iamjue.aplikasipenjualan.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterCustomer extends BaseAdapter {
    private Fragment fragment;
    private LayoutInflater inflater;
    private List<DataCustomer> items;

    public AdapterCustomer(Fragment fragment, List<DataCustomer> items){
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
            convertView = inflater.inflate( R.layout.list_row_customer, null );
        TextView id =(TextView)convertView.findViewById( R.id.tv_id ) ;
        TextView noPenjualan =(TextView)convertView.findViewById( R.id.tv_no_penjualan );
        TextView namaBarang = (TextView)convertView.findViewById( R.id.tv_nama_barang );
        TextView namaPembeli = (TextView)convertView.findViewById( R.id.tv_nama_pembeli );


        DataCustomer dataCustomer = items.get( position );
        id.setText( dataCustomer.getId() );
        noPenjualan.setText( dataCustomer.getNoPenjualan() );
        namaBarang.setText( dataCustomer.getNamaBarang() );
        namaPembeli.setText( dataCustomer.getNamaPembeli() );

        return convertView;
    }
}
