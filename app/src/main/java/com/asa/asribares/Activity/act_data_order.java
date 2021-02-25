package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterDataOrder;
import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.order;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_data_order extends AppCompatActivity {

    EditText dt_start, dt_end;
    Button btn_cari_data;
    ListView data_order;
    TextView total_order;
    ArrayList<String> no_faktur = new ArrayList<String>();
    ArrayList<String> tgl_trans = new ArrayList<String>();
    ArrayList<String> cust = new ArrayList<String>();
    ArrayList<String> total = new ArrayList<String>();

    Session session;
    Api api;
    Call<BaseResponse<order>> getDataOrder;
    private static String URL, URL_HAPUS_MASTER;

    AdapterDataOrder dataOrderListView;
    DateFormat dateFormat;
    Date date;
    String dt;
    final Calendar calendar = Calendar.getInstance();
    int yy = calendar.get(Calendar.YEAR);
    int mm = calendar.get(Calendar.MONTH);
    int dd = calendar.get(Calendar.DAY_OF_MONTH);
    double tot_ord = 0;
    NumberFormat formatRupiah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_data_order);

        dt_start = (EditText) findViewById(R.id.date_start1);
        dt_end = (EditText) findViewById(R.id.date_end1);
        btn_cari_data = (Button) findViewById(R.id.bt_search_order);
        data_order = (ListView) findViewById(R.id.data_order1);
        total_order = (TextView) findViewById(R.id.total_order);

        session = new Session(this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        System.out.println(session.getCustomer());

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        date = new Date();
        dt_start.setText(dateFormat.format(date));
        dt_end.setText(dateFormat.format(date));

        dt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_data_order.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                        dt_start.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });

        dt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_data_order.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                        dt_end.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });

        dt = dt_end.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        System.out.println(dt);

        System.out.println(dt_start.getText().toString()+" "+dt);
        getData();

        btn_cari_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        data_order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                SweetAlertDialog pDialog = new SweetAlertDialog(act_data_order.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("WARNING");
                pDialog.setContentText("Apakah Anda Yakin untuk Menghapus ? ");
                pDialog.setConfirmText("YES");
                pDialog.setCancelText("NO");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sweetAlertDialog) {

                    }
                });
                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                pDialog.show();
                return true;
            }
        });

        data_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent it = new Intent(act_data_order.this, DetailOrder.class);
//                it.putExtra("no_faktur", no_faktur.get(position));
//                it.putExtra("nm_cust", cust.get(position));
//                it.putExtra("tgl", tgl_trans.get(position));
//                it.putExtra("total", total.get(position));
//                it.putExtra("req_code", "1");
//                startActivity(it);
//                finish();
                System.out.println(no_faktur.get(position));
                System.out.println(cust.get(position));
                System.out.println(tgl_trans.get(position));
                System.out.println(total.get(position));
            }
        });
    }

    public void getData(){
        getDataOrder = api.getDataOrderJual(dt_start.getText().toString(), dt, session.getUsername());
        getDataOrder.enqueue(new Callback<BaseResponse<order>>() {
            @Override
            public void onResponse(Call<BaseResponse<order>> call, Response<BaseResponse<order>> response) {
                if (response.isSuccessful()) {
                    no_faktur.clear();
                    tgl_trans.clear();
                    cust.clear();
                    total.clear();
                    tot_ord = 0;
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        no_faktur.add(response.body().getData().get(i).getNOENT());
                        tgl_trans.add(response.body().getData().get(i).getTANGGAL());
                        cust.add(response.body().getData().get(i).getNMCUST());
                        total.add(response.body().getData().get(i).getTOTAL());
                        tot_ord += Double.parseDouble(response.body().getData().get(i).getTOTAL().replace(",", ""));
                    }

                    total_order.setText(formatRupiah.format(tot_ord));
                    dataOrderListView = new AdapterDataOrder(act_data_order.this, no_faktur, cust, tgl_trans, total);
                    dataOrderListView.notifyDataSetChanged();
                    data_order.setAdapter(dataOrderListView);
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(act_data_order.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("ERROR");
                    pDialog.setContentText("Data Tidak Ditemukan !!");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<order>> call, Throwable t) {
                Toasty.error(act_data_order.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}