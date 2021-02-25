package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Response.BaseResponse1;
import com.asa.asribares.Session.Encryption;
import com.asa.asribares.Session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_register extends AppCompatActivity {

    private ImageView imageView;
    private Button btn, btn2, rahasia, rahasia1, rahasia2, rahasia3;
    private EditText et1, et2, et3;
    private static String URL, URL1;
    private static String nama_toko = "", paket = "1", info;
    private RadioGroup radioGroup;
    private RadioButton paket1, paket2, paket3;
    private String kode, enk1, enk2, enk3, enk4, enk5, sKunci, sKunci2, sKunci3, enk_p1, encrypted;
    Encryption encryption;

    Api api;
    Session session;
    Call<BaseResponse1> getNamaPerusahaan;
    Call<BaseResponse> registrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_register);

        String device_unique_id = Settings.Secure.getString(act_register.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        System.out.println("id unique"+device_unique_id);

        encryption = Encryption.getDefault("Asa", "Mutiara", new byte[16]);
        encrypted = encryption.encryptOrNull(device_unique_id);
        System.out.println("enkrip id"+encrypted);

        session = new Session(this);
        api = RetrofitClient.createService(Api.class);

        imageView = (ImageView) findViewById(R.id.setting);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setUrl(false, "");
                startActivity(new Intent(getApplicationContext(), act_setting_url.class));
                finish();
            }
        });

        btn = (Button) findViewById(R.id.registrasi);
//        btn2 = (Button) findViewById(R.id.cek);
        et1 = (EditText) findViewById(R.id.nama_toko);
        et2 = (EditText) findViewById(R.id.kode_pembuka);
        et3 = (EditText) findViewById(R.id.kode_access);
        rahasia = (Button) findViewById(R.id.rahasia);
        rahasia1 = (Button) findViewById(R.id.rahasia1);
        rahasia2 = (Button) findViewById(R.id.rahasia2);
        rahasia3 = (Button) findViewById(R.id.rahasia3);

        enk1 = encrypted.substring(0, 9);
        enk2 = enk1.substring(0, 3);
        enk3 = enk1.substring(3, 6);
        enk4 = enk1.substring(6, 9);
        enk5 = enk2 + " - " + enk3 + " - " + enk4;
//        System.out.println(mac_address);
//        System.out.println(encrypted);
//        System.out.println(enk1+" | "+enk2+" | "+enk3+" | "+enk4+" | "+enk5);

        et2.setText(enk5);

        radioGroup = (RadioGroup) findViewById(R.id.rad1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.paket1) {
                    paket = "1";
                } else if (checkedId == R.id.paket2) {
                    paket = "2";
                } else if (checkedId == R.id.paket3) {
                    paket = "3";
                }
                rahasia.setEnabled(true);

                sKunci = encryption.encryptOrNull(nama_toko);
                enk_p1 = encryption.encryptOrNull(paket);
                if (nama_toko.equals("") && paket.equals("")) {
                    System.out.println("error bos");
                } else {
                    sKunci2 = encryption.encryptOrNull(sKunci.substring(0, 3) + "" + enk_p1.substring(0, 3) + "" + enk1);
                    sKunci3 = sKunci2.substring(0, 12);
                    System.out.println("kunci 2"+sKunci2);
                    System.out.println("kunci 3"+sKunci3);
                }
            }
        });

        nama_toko = "Larisso";
        et1.setText(nama_toko);
        et1.setFocusable(true);

        sKunci = encryption.encryptOrNull(nama_toko);
        enk_p1 = encryption.encryptOrNull(paket);
        if (nama_toko == "" && paket == "") {

        } else {
            sKunci2 = encryption.encryptOrNull(sKunci.substring(0, 3) + "" + enk_p1.substring(0, 3) + "" + enk1);
            sKunci3 = sKunci2.substring(0, 12);
            System.out.println("kunci 2"+sKunci2);
            System.out.println("kunci 3"+sKunci3);
        }

        rahasia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rahasia1.setEnabled(true);
                System.out.println("1");
            }
        });

        rahasia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rahasia2.setEnabled(true);
                System.out.println("2");
            }
        });

        rahasia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rahasia3.setEnabled(true);
                System.out.println("2");
            }
        });

        rahasia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et3.setText(sKunci3);
                System.out.println(et3.getText());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kode = et3.getText().toString();
                nama_toko = et1.getText().toString().toUpperCase();
//                System.out.println(kode+" | "+sKunci3);
//                System.out.println(nama_toko + " | " + paket + " | " + mac_address + " | " + session.kode());
                if (kode.equals(sKunci3)) {
                    registrasi = api.registrasi(nama_toko+"", paket+"", enk1+"", sKunci3+"");
                    registrasi.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.isSuccessful()) {
                                Toasty.success(act_register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                session.setActivation(true, nama_toko, paket, enk1, sKunci3);
                                startActivity(new Intent(act_register.this, act_login.class));
                                finish();
                            } else {
                                Toasty.success(act_register.this, "Registrasi Gagal !!! ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Toasty.error(act_register.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else  if (kode != sKunci3) {
                    Toast.makeText(getApplicationContext(), "Maaf Kunci Program yg Anda masukkan salah !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}