package com.asa.asribares.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asa.asribares.R;

import java.util.ArrayList;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterKunjungan extends ArrayAdapter<String> {

    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayList3;
    private Activity context;

    public AdapterKunjungan(Activity context, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3) {
        super(context, R.layout.adapter_kunjungan, arrayList);

        this.context = context;
        this.arrayList = arrayList;
        this.arrayList2 = arrayList2;
        this.arrayList3 = arrayList3;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_kunjungan, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.textView.setText(arrayList.get(position));
        viewHolder.textView2.setText(arrayList2.get(position));
        viewHolder.textView3.setText(arrayList3.get(position));
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
        TextView textView3;
        ViewHolder(View view){
            textView = (TextView) view.findViewById(R.id.kd_cust_tmp);
            textView2 = (TextView) view.findViewById(R.id.nm_cust_tmp);
            textView3 = (TextView) view.findViewById(R.id.tgl_kunjungan_tmp);
        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }
}
