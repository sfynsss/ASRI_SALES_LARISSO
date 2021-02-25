package com.asa.asribares.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asa.asribares.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 24/04/2018.
 */

public class AdapterOrder extends ArrayAdapter<String> {

    private ArrayList<String> text;
    private ArrayList<String> text2;
    private ArrayList<String> text3;
    private ArrayList<String> text4;
    private ArrayList<String> text5;
    private Activity context;

    public AdapterOrder(Activity context, ArrayList<String> text, ArrayList<String> text2, ArrayList<String> text3, ArrayList<String> text4, ArrayList<String> text5) {
        super(context, R.layout.adapter_order, text);

        this.context = context;
        this.text = text;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.text5 = text5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_order, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.textView.setText(text.get(position));
        viewHolder.textView2.setText(text2.get(position));
        viewHolder.textView3.setText(text3.get(position));
        viewHolder.textView4.setText(text4.get(position));
        viewHolder.textView5.setText(text5.get(position));

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
            textView3 = (TextView) view.findViewById(R.id.harga_barang);
            textView4 = (TextView) view.findViewById(R.id.qty);
            textView5 = (TextView) view.findViewById(R.id.total);
        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }
}
