package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Response.UserResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.user;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_login extends AppCompatActivity {

    private EditText user, pass;
    private Button btn;
    private Toast ts;
    private static String URL, URL1;
    private ImageView imageView, lihat_password;
    private static String nama_toko, paket, mac_address;
    Boolean showPasswordClicked = false;

    Api api;
    Session session;
    Call<BaseResponse> cekRegis;
    Call<UserResponse> login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_login);

        session = new Session(act_login.this);
        api = RetrofitClient.createService(Api.class);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        btn = (Button) findViewById(R.id.login);
        imageView = (ImageView) findViewById(R.id.setting);
        lihat_password = (ImageView) findViewById(R.id.lihat_password);

        lihat_password.setOnClickListener(mToggleShowPasswordButton);
        nama_toko = session.getNamaToko();
        paket = session.getPaket();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.registered() == false) {
                    Toast.makeText(getApplicationContext(), "Maaf Device Anda belum di registrasi.", Toast.LENGTH_LONG).show();
                } else if (session.registered() == true) {
                    cekRegis = api.cekRegis(session.getNamaToko(), session.getPaket(), session.getImei(), session.getKunciProgram());
                    cekRegis.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.isSuccessful()){
                                login = api.login(user.getText().toString().toUpperCase(), pass.getText().toString());
                                login.enqueue(new Callback<UserResponse>() {
                                    @Override
                                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                        if (response.isSuccessful()) {
                                            session.setLoggedin(true, response.body().getUser().getId()+"", response.body().getUser().getName()+"", response.body().getUser().getOtoritas().toString()+"", response.body().getUser().getApiToken() + "");
                                            session.setPegawai(response.body().getUser().getName()+"", response.body().getUser().getKdPeg()+"");
                                            if (response.body().getUser().getOtoritas().toString().equals("1")) {
                                                startActivity(new Intent(act_login.this, act_home_admin.class));
                                                finish();
                                            } else if (response.body().getUser().getOtoritas().toString().equals("5")) {
                                                startActivity(new Intent(act_login.this, act_home_sales_order.class));
                                                finish();
                                            } else {
                                                SweetAlertDialog pDialog = new SweetAlertDialog(act_login.this, SweetAlertDialog.ERROR_TYPE);
                                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                                pDialog.setTitleText("ERROR");
                                                pDialog.setContentText("Maaf Anda tidak memiliki otoritas !!!");
                                                pDialog.show();
                                            }
                                        } else {
                                            SweetAlertDialog pDialog = new SweetAlertDialog(act_login.this, SweetAlertDialog.ERROR_TYPE);
                                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                            pDialog.setTitleText("ERROR");
                                            pDialog.setContentText("User Tidak Ditemukan !!!");
                                            pDialog.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserResponse> call, Throwable t) {
                                        Toasty.error(act_login.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                SweetAlertDialog pDialog = new SweetAlertDialog(act_login.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("ERROR");
                                pDialog.setContentText("Registrasi Server Bermasalah !!!");
                                pDialog.show();
                                startActivity(new Intent(getApplicationContext(), act_register.class));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Toasty.error(act_login.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                session.setUrl(false, "");
//                startActivity(new Intent(getApplicationContext(), act_setting_url.class));
//                finish();
//            }
//        });
    }

    View.OnClickListener mToggleShowPasswordButton = new View.OnClickListener(){

        @Override
        public void onClick(View v){
            // change your button background

            if(showPasswordClicked){
                lihat_password.setImageResource(R.drawable.ic_eye_closed);
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                lihat_password.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            showPasswordClicked = !showPasswordClicked; // reverse
        }

    };
}