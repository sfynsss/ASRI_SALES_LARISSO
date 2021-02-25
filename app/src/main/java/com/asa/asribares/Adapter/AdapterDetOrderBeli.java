package com.asa.asribares.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asa.asribares.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 24/04/2018.
 */

public class AdapterDetOrderBeli extends ArrayAdapter<String> {

    private ArrayList<String> kd_brg;
    private ArrayList<String> nm_brg;
    private ArrayList<String> jml_order;
    private ArrayList<String> satuan;
    private ArrayList<String> qty;
    private Activity context;

    public AdapterDetOrderBeli(Activity context, ArrayList<String> kd_brg, ArrayList<String> nm_brg, ArrayList<String> jml_order, ArrayList<String> satuan, ArrayList<String> qty) {
        super(context, R.layout.adapter_det_order_beli, kd_brg);

        this.context = context;
        this.kd_brg = kd_brg;
        this.nm_brg = nm_brg;
        this.jml_order = jml_order;
        this.satuan = satuan;
        this.qty = qty;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_det_order_beli, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.textView.setText(kd_brg.get(position));
        viewHolder.textView2.setText(nm_brg.get(position));
        viewHolder.textView3.setText(jml_order.get(position));
        viewHolder.textView4.setText(satuan.get(position));
        viewHolder.textView5.setText(qty.get(position));

        return v;
    }

    class ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        ViewHolder(View view){
            textView = (TextView) view.findViewById(R.id.kode_barang);
            textView2 = (TextView) view.findViewById(R.id.nama_barang);
            textView3 = (TextView) view.findViewById(R.id.order);
            textView4 = (TextView) view.findViewById(R.id.satuan);
            textView5 = (TextView) view.findViewById(R.id.qty);
        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }
}
