package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.asa.asribares.R;
import com.asa.asribares.Session.Encryption;
import com.asa.asribares.Session.Session;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import es.dmoral.toasty.Toasty;

public class act_splash extends AppCompatActivity {

    private ImageView iv, iv1, iv2;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);

//        iv = (ImageView) findViewById(R.id.image_splash);
//        iv1 = (ImageView) findViewById(R.id.image_splash2);
//        iv2 = (ImageView) findViewById(R.id.logo_splash);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
//        iv.startAnimation(animation);
//        iv1.startAnimation(animation);
//        iv2.startAnimation(animation);

        session = new Session(this);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (session.registered() == false) {
                        startActivity(new Intent(getApplicationContext(), act_register.class));
                        finish();
                    } else if (session.registered() == true  && session.loggedin() == false) {
                        startActivity(new Intent(getApplicationContext(), act_login.class));
                        finish();
                    } else if (session.registered() == true  && session.loggedin() == true && (session.getUserStatus().equals("ADMIN") || session.getUserStatus().equals("SUPER ADMIN"))) {
                        startActivity(new Intent(getApplicationContext(), act_home_admin.class));
                        finish();
                    } else if (session.registered() == true  && session.loggedin() == true && session.getUserStatus().equals("SALES")) {
                        startActivity(new Intent(getApplicationContext(), act_home_sales_order.class));
                        finish();
                    } else {
//                        SweetAlertDialog pDialog = new SweetAlertDialog(act_splash.this, SweetAlertDialog.ERROR_TYPE);
//                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                        pDialog.setTitleText("ERROR");
//                        pDialog.setContentText("Maaf Anda tidak memiliki otoritas !!!");
//                        pDialog.show();
//                        Toasty.error(act_splash.this, "Maaf Anda tidak memiliki otoritas !!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), act_login.class));
                        finish();
                    }
                }
            }
        };

        if (session.registered() == false) {
            Toast.makeText(getApplicationContext(), "Device Anda belum di registrasi", Toast.LENGTH_LONG).show();
            startActivity(new Intent(act_splash.this, act_register.class));
            finish();
        } else {
            timer.start();
        }

    }
}