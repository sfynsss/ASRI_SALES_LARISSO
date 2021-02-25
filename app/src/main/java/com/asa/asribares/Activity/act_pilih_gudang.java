package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.gudang;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_pilih_gudang extends AppCompatActivity {

    int no_gudang;
    Spinner gudang;
    Button btn;
    Toast ts;
    ArrayList<String> tmp_gudang = new ArrayList<>();
    private Session session;
    private static String URL;

    Api api;
    Call<BaseResponse<gudang>> getGudang;

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_pilih_gudang);

        session = new Session(this);
        btn = (Button) findViewById(R.id.lanjut);
        gudang = (Spinner) findViewById(R.id.gudang);

        api = RetrofitClient.createService(Api.class);
        getGudang = api.getGudang();
        getGudang.enqueue(new Callback<BaseResponse<com.asa.asribares.Table.gudang>>() {
            @Override
            public void onResponse(Call<BaseResponse<com.asa.asribares.Table.gudang>> call, Response<BaseResponse<com.asa.asribares.Table.gudang>> response) {
                if (response.isSuccessful()) {
                    tmp_gudang.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        tmp_gudang.add(response.body().getData().get(i).getGUDANG());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(act_pilih_gudang.this, android.R.layout.simple_spinner_item, tmp_gudang);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gudang.setAdapter(arrayAdapter);
                } else {
                    Toasty.error(act_pilih_gudang.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<com.asa.asribares.Table.gudang>> call, Throwable t) {
                Toasty.error(act_pilih_gudang.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.getUserStatus().equals("1")) {
                    session.setGudang(true, gudang.getSelectedItem().toString());
                    startActivity(new Intent(act_pilih_gudang.this, act_home_admin.class));
                    finish();
                } else if (session.getUserStatus().equals("5")) {
                    session.setGudang(true, gudang.getSelectedItem().toString());
                    startActivity(new Intent(act_pilih_gudang.this, act_home_sales_order.class));
                    finish();
                }
//                else if (session.getUserStatus().equals("3")) {
//                    session.setGudang(true, gudang.getSelectedItem().toString());
//                    startActivity(new Intent(act_pilih_gudang.this, act_home_stok_opname.class));
//                    finish();
//                } else if (session.getUserStatus().equals("4")) {
//                    session.setGudang(true, gudang.getSelectedItem().toString());
//                    startActivity(new Intent(act_pilih_gudang.this, act_home_barang_datang.class));
//                    finish();
//                }
            }
        });
    }

}