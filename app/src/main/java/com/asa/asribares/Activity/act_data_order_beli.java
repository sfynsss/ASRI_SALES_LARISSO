package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterDataOrder;
import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.orderBeli;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_data_order_beli extends AppCompatActivity {

    Session session;
    Api api;
    Call<BaseResponse<orderBeli>> getOrderBeli;

    ArrayList<String> no_ent = new ArrayList<>();
    ArrayList<String> tanggal = new ArrayList<>();
    ArrayList<String> kd_suppl = new ArrayList<>();
    ArrayList<String> nm_suppl = new ArrayList<>();
    ArrayList<String> total = new ArrayList<>();

    EditText dt_start, dt_end;
    ListView listView;
    Button search;
    AdapterDataOrder adapterDataOrder;

    String dt;
    DateFormat dateFormat;
    Date date;
    final Calendar calendar = Calendar.getInstance();
    int yy = calendar.get(Calendar.YEAR);
    int mm = calendar.get(Calendar.MONTH);
    int dd = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_data_order_beli);

        session = new Session(act_data_order_beli.this);
        api = RetrofitClient.createService(Api.class);

        dt_start = findViewById(R.id.date_start);
        dt_end = findViewById(R.id.date_end);
        listView = findViewById(R.id.list_data);
        search = findViewById(R.id.bt_search);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = new Date();
        dt_start.setText(dateFormat.format(date));
        dt_end.setText(dateFormat.format(date));

        dt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_data_order_beli.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
                        dt_start.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });

        dt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_data_order_beli.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
                        dt_end.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });

        dt = dt_end.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        System.out.println(dt_start.getText().toString() + " | " + dt);

        getData();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent();
                it.putExtra("no_ent", no_ent.get(position));
                it.putExtra("nm_suppl", nm_suppl.get(position));
                it.putExtra("kd_suppl", kd_suppl.get(position));
                it.putExtra("tgl_order", tanggal.get(position));
                setResult(1, it);
                finish();
                return true;
            }
        });
    }

    public void getData(){
        getOrderBeli = api.getDataOrderBeli(dt_start.getText().toString(), dt);
        getOrderBeli.enqueue(new Callback<BaseResponse<orderBeli>>() {
            @Override
            public void onResponse(Call<BaseResponse<orderBeli>> call, Response<BaseResponse<orderBeli>> response) {
                if (response.isSuccessful()) {
                    no_ent.clear();
                    tanggal.clear();
                    kd_suppl.clear();
                    nm_suppl.clear();
                    total.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        no_ent.add(response.body().getData().get(i).getNOENT());
                        tanggal.add(response.body().getData().get(i).getTANGGAL());
                        kd_suppl.add(response.body().getData().get(i).getKDSUPPL());
                        nm_suppl.add(response.body().getData().get(i).getNMSUPPL());
                        total.add(response.body().getData().get(i).getTOTAL());
                    }

                    adapterDataOrder = new AdapterDataOrder(act_data_order_beli.this, no_ent, nm_suppl, tanggal, total);
                    adapterDataOrder.notifyDataSetChanged();
                    listView.setAdapter(adapterDataOrder);
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(act_data_order_beli.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("ERROR");
                    pDialog.setContentText("Data Tidak Ditemukan !!");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<orderBeli>> call, Throwable t) {
                Toasty.error(act_data_order_beli.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}