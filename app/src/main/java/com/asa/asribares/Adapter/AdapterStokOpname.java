package com.asa.asribares.Adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asribares.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 24/04/2018.
 */

public class AdapterStokOpname extends ArrayAdapter<String> {

    private ArrayList<String> kd_brg;
    private ArrayList<String> nm_brg;
    private ArrayList<String> stok;
    private ArrayList<String> tmp_qty;
    private Activity context;

    public AdapterStokOpname(Activity context, ArrayList<String> kd_brg, ArrayList<String> nm_brg, ArrayList<String> stok, ArrayList<String> tmp_qty) {
        super(context, R.layout.adapter_stok_opname, kd_brg);

        this.context = context;
        this.kd_brg = kd_brg;
        this.nm_brg = nm_brg;
        this.stok = stok;
        this.tmp_qty = tmp_qty;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_stok_opname, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.kode_barang.setText(kd_brg.get(position));
        viewHolder.nama_barang.setText(nm_brg.get(position));
        viewHolder.stok.setText(stok.get(position));
        viewHolder.qty.setText(tmp_qty.get(position));

        viewHolder.qty.setTag(position);
        viewHolder.qty.setText(tmp_qty.get(position).toString());
        int tag_position = (Integer) viewHolder.qty.getTag();
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                final int position2 = finalViewHolder.qty.getId();
                final EditText qty = (EditText) finalViewHolder.qty;
                if(qty.getText().toString().length()>0){
                    tmp_qty.set(position2,qty.getText().toString());
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    class ViewHolder{
        TextView kode_barang;
        TextView nama_barang;
        TextView stok;
        EditText qty;
        ViewHolder(View view){
            kode_barang = (TextView) view.findViewById(R.id.kode_barang);
            nama_barang = (TextView) view.findViewById(R.id.nama_barang);
            stok = (TextView) view.findViewById(R.id.stok);
            qty = view.findViewById(R.id.qty);
        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }
}
