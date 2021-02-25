package com.asa.asribares.Adapter;

import android.annotation.SuppressLint;
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
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterBarang extends ArrayAdapter<String> {

    private ArrayList<String> text = new ArrayList<String>();
    private ArrayList<String> text2 = new ArrayList<String>();
    private ArrayList<String> harga1 = new ArrayList<String>();
    private ArrayList<String> harga2 = new ArrayList<String>();
    private ArrayList<String> harga3 = new ArrayList<String>();
    private ArrayList<String> harga4 = new ArrayList<String>();
    private ArrayList<String> harga5 = new ArrayList<String>();
    private ArrayList<String> hpp = new ArrayList<String>();
    private ArrayList<String> stok = new ArrayList<String>();
    private Activity context;

    public AdapterBarang(Activity context, ArrayList<String> text, ArrayList<String> text2, ArrayList<String> harga1, ArrayList<String> harga2,
                         ArrayList<String> harga3, ArrayList<String> harga4, ArrayList<String> harga5, ArrayList<String> hpp, ArrayList<String> stok) {
        super(context, R.layout.adapter_barang, text);

        this.context = context;
        this.text = text;
        this.text2 = text2;
        this.harga1 = harga1;
        this.harga2 = harga2;
        this.harga3 = harga3;
        this.harga4 = harga4;
        this.harga5 = harga5;
        this.hpp = hpp;
        this.stok = stok;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_barang, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.textView.setText(text.get(position));
        viewHolder.textView2.setText(text2.get(position));
        viewHolder.harga_brg1.setText("H1 : "+harga1.get(position));
        if (harga2.get(position).equals("0")) {
//            viewHolder.harga_brg2.setText("");
            viewHolder.harga_brg2.setVisibility(View.GONE);
        } else {
            viewHolder.harga_brg2.setText("H2 : "+harga2.get(position));
        }

        if (harga3.get(position).equals("0")) {
//            viewHolder.harga_brg3.setText("");
            viewHolder.harga_brg3.setVisibility(View.GONE);
        } else {
            viewHolder.harga_brg3.setText("H3 : "+harga3.get(position));
        }

        if (harga4.get(position).equals("0")) {
//            viewHolder.harga_brg4.setText("");
            viewHolder.harga_brg4.setVisibility(View.GONE);
        } else {
            viewHolder.harga_brg4.setText("H4 : "+harga4.get(position));
        }

        if (harga5.get(position).equals("0")) {
//            viewHolder.harga_brg5.setText("");
            viewHolder.harga_brg5.setVisibility(View.GONE);
        } else {
            viewHolder.harga_brg5.setText("H5 : "+harga5.get(position));
        }

        viewHolder.stok.setText(stok.get(position));
//        Toast.makeText(getContext(), text[position], Toast.LENGTH_SHORT).show();
//        if (text[position].equals("SELESAI")) {
//            viewHolder.textView.setBackgroundColor(Color.rgb(0, 191, 22));
//        } else if (text[position].equals("BELUM")) {
//            viewHolder.textView.setBackgroundColor(Color.rgb(207, 60, 60));
//        }

        return v;
    }

    class ViewHolder{
        TextView textView;
        TextView textView2;
        TextView harga_brg1;
        TextView harga_brg2;
        TextView harga_brg3;
        TextView harga_brg4;
        TextView harga_brg5;
        TextView hpp;
        TextView stok;
        RelativeLayout relativeLayout;
        ViewHolder(View view){
            textView = (TextView) view.findViewById(R.id.kode_barang1);
            textView2 = (TextView) view.findViewById(R.id.nama_barang1);
            harga_brg1 = (TextView) view.findViewById(R.id.harga_barang1);
            harga_brg2 = (TextView) view.findViewById(R.id.harga_barang2);
            harga_brg3 = (TextView) view.findViewById(R.id.harga_barang3);
            harga_brg4 = (TextView) view.findViewById(R.id.harga_barang4);
            harga_brg5 = (TextView) view.findViewById(R.id.harga_barang5);
            hpp = (TextView) view.findViewById(R.id.harga_pokok);
            stok = (TextView) view.findViewById(R.id.stok_tersedia);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.list_barang);
        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }
}
