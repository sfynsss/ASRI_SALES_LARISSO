package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterCustomer;
import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.customer;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_data_customer extends AppCompatActivity {

    EditText nama_cust;
    Button btn_cari;
    ListView list_customer;

    Session session;
    Api api;
    Call<BaseResponse<customer>> getDataCustomer;

    private static String URL;
    AdapterCustomer customerListView;
    ArrayList<String> v_kode_cust = new ArrayList<String>();
    ArrayList<String> v_nama_cust = new ArrayList<String>();
    ArrayList<String> v_email_cust = new ArrayList<String>();
    ArrayList<String> v_alamat_cust = new ArrayList<String>();
    ArrayList<String> v_aktivasi_cust = new ArrayList<String>();
    ArrayList<String> v_otoritas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_data_customer);



//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        nama_cust = (EditText) findViewById(R.id.cari_nama_customer);
        btn_cari = (Button) findViewById(R.id.btn_cari_customer);
        list_customer = (ListView) findViewById(R.id.list_cari_customer);

        session = new Session(this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataCustomer = api.getDataCustomer(nama_cust.getText().toString().toUpperCase());
                getDataCustomer.enqueue(new Callback<BaseResponse<customer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<customer>> call, Response<BaseResponse<customer>> response) {
                        if (response.isSuccessful()) {
                            v_kode_cust.clear();
                            v_nama_cust.clear();
                            v_email_cust.clear();
                            v_alamat_cust.clear();
                            v_aktivasi_cust.clear();
                            v_otoritas.clear();
//                            kode_aktifasi.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                v_kode_cust.add(response.body().getData().get(i).getKDCUST());
                                v_nama_cust.add(response.body().getData().get(i).getNMCUST());
                                v_email_cust.add(response.body().getData().get(i).getEMAIL());
                                v_alamat_cust.add(response.body().getData().get(i).getALMCUST());
                                v_aktivasi_cust.add(response.body().getData().get(i).getActivationToken());
                                v_otoritas.add(response.body().getData().get(i).getOtoritas());
//                                kode_aktifasi.add(response.body().getData().get(i).getALMCUST());
                            }

                            customerListView = new AdapterCustomer(act_data_customer.this, v_kode_cust, v_nama_cust, v_alamat_cust, v_aktivasi_cust, v_email_cust, v_otoritas, new AdapterCustomer.OnEditLocationListener() {
                                @Override
                                public void onClickAdapter(int position) {

                                }
                            }, new AdapterCustomer.OnEditLocationListener() {
                                @Override
                                public void onClickAdapter(int position) {
                                    if (getIntent().getStringExtra("status").equals("kunjungan")) {
                                        Intent it = new Intent();
                                        it.putExtra("kd_cust", v_kode_cust.get(position));
                                        it.putExtra("nm_cust", v_nama_cust.get(position));
                                        it.putExtra("alamat_cust", v_alamat_cust.get(position));
                                        it.putExtra("aktivasi_cust", v_aktivasi_cust.get(position));
                                        setResult(1, it);
                                        onBackPressed();
                                        finish();
                                    } else if (getIntent().getStringExtra("status").equals("aktifasi")) {

                                    }
                                }
                            });
                            list_customer.setAdapter(customerListView);
                        } else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(act_data_customer.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("ERROR");
                            pDialog.setContentText("Data Tidak Ditemukan !!");
                            pDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<customer>> call, Throwable t) {
                        Toasty.error(act_data_customer.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}