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
import android.widget.Toast;

import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Response.BaseResponse1;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.customer;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sfyn on 04/02/2018.
 */

public class AdapterCustomer extends ArrayAdapter<String> {

    private ArrayList<String> kode_customer = new ArrayList<String>();
    private ArrayList<String> nama_customer = new ArrayList<String>();
    private ArrayList<String> email_customer = new ArrayList<String>();
    private ArrayList<String> alamat_customer = new ArrayList<String>();
    private ArrayList<String> kode_aktivasi_customer = new ArrayList<String>();
    private ArrayList<String> otoritas = new ArrayList<String>();
    private OnEditLocationListener generate_btn;
    private OnEditLocationListener tambah;
    private Activity context;

    Session session;
    Api api;
    Call<BaseResponse1> generateGrosirToken;

    public AdapterCustomer(Activity context, ArrayList<String> kode_customer, ArrayList<String> nama_customer, ArrayList<String> alamat_customer, ArrayList<String> kode_aktivasi_customer, ArrayList<String> email_customer, ArrayList<String> otoritas, OnEditLocationListener generate_btn, OnEditLocationListener tambah) {
        super(context, R.layout.adapter_customer, kode_customer);

        this.context = context;
        this.kode_customer = kode_customer;
        this.nama_customer = nama_customer;
        this.email_customer = email_customer;
        this.alamat_customer = alamat_customer;
        this.kode_aktivasi_customer = kode_aktivasi_customer;
        this.otoritas = otoritas;
        this.generate_btn = generate_btn;
        this.tambah = tambah;

        session = new Session(context);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

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
        viewHolder.otoritas.setText(otoritas.get(position));

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (generate_btn != null) {
                    generate_btn.onClickAdapter(position);
//                    String data = kode_aktivasi_customer.get(position);
                    if (otoritas.get(position).equals("GROSIR")) {
                        Toasty.success(context, "User Grosir", Toast.LENGTH_SHORT).show();
                    } else {
                        generateGrosirToken = api.generateGrosirToken(email_customer.get(position));
                        generateGrosirToken.enqueue(new Callback<BaseResponse1>() {
                            @Override
                            public void onResponse(Call<BaseResponse1> call, Response<BaseResponse1> response) {
                                if (response.isSuccessful()) {
                                    String data = response.body().getData();
                                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 200);
                                    Bitmap qrBits = qrgEncoder.getBitmap();
                                    finalViewHolder.qrImage.setImageBitmap(qrBits);
                                } else {
                                    Toasty.error(context, "Generate Token Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse1> call, Throwable t) {
                                Toasty.error(context, "Generate Token Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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
        TextView otoritas;
        TextView kode_aktivasi_customer;
        LinearLayout linearLayout;
        Button generateBtn;
        ImageView qrImage, tambah;

        ViewHolder(View view){
            kode_customer = (TextView) view.findViewById(R.id.kode_customer);
            nama_customer = (TextView) view.findViewById(R.id.nama_customer);
            alamat_customer = (TextView) view.findViewById(R.id.alamat_customer);
            kode_aktivasi_customer = (TextView) view.findViewById(R.id.kode_aktivasi_customer);
            otoritas = (TextView) view.findViewById(R.id.otoritas);
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
