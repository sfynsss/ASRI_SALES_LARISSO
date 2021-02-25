package com.asa.asribares.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asa.asribares.R;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterCustomer extends ArrayAdapter<String> {

    private ArrayList<String> kode_customer = new ArrayList<String>();
    private ArrayList<String> nama_customer = new ArrayList<String>();
    private ArrayList<String> alamat_customer = new ArrayList<String>();
    private ArrayList<String> kode_aktivasi_customer = new ArrayList<String>();
    private OnEditLocationListener generate_btn;
    private OnEditLocationListener tambah;
    private Activity context;

    public AdapterCustomer(Activity context, ArrayList<String> kode_customer, ArrayList<String> nama_customer, ArrayList<String> alamat_customer, ArrayList<String> kode_aktivasi_customer, OnEditLocationListener generate_btn, OnEditLocationListener tambah) {
        super(context, R.layout.adapter_customer, kode_customer);

        this.context = context;
        this.kode_customer = kode_customer;
        this.nama_customer = nama_customer;
        this.alamat_customer = alamat_customer;
        this.kode_aktivasi_customer = kode_aktivasi_customer;
        this.generate_btn = generate_btn;
        this.tambah = tambah;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = null;
        if (v == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            v = layoutInflater.inflate(R.layout.adapter_customer, null, true);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.kode_customer.setText(kode_customer.get(position));
        viewHolder.nama_customer.setText(nama_customer.get(position));
        viewHolder.alamat_customer.setText(alamat_customer.get(position));
        viewHolder.kode_aktivasi_customer.setText(kode_aktivasi_customer.get(position));

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (generate_btn != null) {
                    generate_btn.onClickAdapter(position);
                    String data = kode_aktivasi_customer.get(position);
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 200);
                    Bitmap qrBits = qrgEncoder.getBitmap();
                    finalViewHolder.qrImage.setImageBitmap(qrBits);
                }
            }
        });

        viewHolder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tambah != null) {
                    tambah.onClickAdapter(position);
                }
            }
        });

        return v;


    }

    class ViewHolder{
        TextView kode_customer;
        TextView nama_customer;
        TextView alamat_customer;
        TextView kode_aktivasi_customer;
        LinearLayout linearLayout;
        Button generateBtn;
        ImageView qrImage, tambah;

        ViewHolder(View view){
            kode_customer = (TextView) view.findViewById(R.id.kode_customer);
            nama_customer = (TextView) view.findViewById(R.id.nama_customer);
            alamat_customer = (TextView) view.findViewById(R.id.alamat_customer);
            kode_aktivasi_customer = (TextView) view.findViewById(R.id.kode_aktivasi_customer);
            linearLayout = (LinearLayout) view.findViewById(R.id.list_customer);

            qrImage = (ImageView) view.findViewById(R.id.qrImage);
            tambah = (ImageView) view.findViewById(R.id.tambah);
            generateBtn = (Button) view.findViewById(R.id.generateBtn);

        }
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    public interface OnEditLocationListener {
        void onClickAdapter(int position);
    }
}
