package com.asa.asribares.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterKunjungan;
import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.kunjungan;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_data_kunjungan_customer extends AppCompatActivity {

    Session session;
    Api api;
    Call<BaseResponse<kunjungan>> getDataKunjungan;
    Call<BaseResponse> insertKunjungan;

    private static String URL, URL_DATA_KUNJUNGAN, URL_DATA_KUNJUNGAN1;
    Button btn_kunjungan, btn_search;
    EditText dt_start, dt_end;
    ListView listView;
    Intent it, ti;
    String contents, format, dt;
    Toast ts;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    DateFormat dateFormat;
    Date date;
    final Calendar calendar = Calendar.getInstance();
    int yy = calendar.get(Calendar.YEAR);
    int mm = calendar.get(Calendar.MONTH);
    int dd = calendar.get(Calendar.DAY_OF_MONTH);
    ArrayList<String> kd_cust = new ArrayList<String>();
    ArrayList<String> nm_cust = new ArrayList<String>();
    ArrayList<String> tgl_kunjungan = new ArrayList<String>();
    AdapterKunjungan kunjunganListView;

    int PERMISSION_ID = 44;
    String lat = "", lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_kunjungan);

        btn_kunjungan = (Button) findViewById(R.id.tambah_kunjungan);
        //btn_search = (Button) findViewById(R.id.bt_search_kunjungan);
        dt_start = (EditText) findViewById(R.id.date_start);
        dt_end = (EditText) findViewById(R.id.date_end);
        //listView = (ListView) findViewById(R.id.list_data_kunjungan);
        session = new Session(this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        date = new Date();
        dt_start.setText(dateFormat.format(date));
        dt_end.setText(dateFormat.format(date));

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        checkPermissions();
        isLocationEnabled();
        requestPermissions();

        btn_kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(act_data_kunjungan_customer.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("PILIHAN")
                        .setContentText("Pilih untuk cari customer ?")
                        .setCancelText("SCAN")
                        .setConfirmText("CARI NAMA")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                try {
                                    it = new Intent(ACTION_SCAN);
                                    it.putExtra("SCAN_CODE", "PRODUCT_MODE");
                                    startActivityForResult(it, 0);
                                } catch (ActivityNotFoundException anfe) {
                                    showDialog(act_data_kunjungan_customer.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                                }
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                it = new Intent(act_data_kunjungan_customer.this, act_data_customer.class);
                                it.putExtra("status", "kunjungan");
                                startActivityForResult(it, 1);
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        dt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_data_kunjungan_customer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(year)+"-"+String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth) ;
                        dt_start.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });

        dt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_data_kunjungan_customer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(year)+"-"+String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth) ;
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
        System.out.println(dt_start.getText().toString() + " | " + dt);

        getData();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(dt_start.getText().toString() + " | " + dt_end.getText().toString());
                getData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                System.out.println(nm_cust.get(position));
//                ti = new Intent(act_kunjungan.this, OrderBarangActivity.class);
//                ti.putExtra("nm_cust", nm_cust.get(position));
//                ti.putExtra("kd_cust", kd_cust.get(position));
//                startActivity(ti);
            }
        });
    }

    public void getData() {
        getDataKunjungan = api.getDataKunjungan(dt_start.getText().toString(), dt, session.getUserId());
        getDataKunjungan.enqueue(new Callback<BaseResponse<kunjungan>>() {
            @Override
            public void onResponse(Call<BaseResponse<kunjungan>> call, Response<BaseResponse<kunjungan>> response) {
                if (response.isSuccessful()) {
                    kd_cust.clear();
                    nm_cust.clear();
                    tgl_kunjungan.clear();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_cust.add(response.body().getData().get(i).getKDCUST());
                        nm_cust.add(response.body().getData().get(i).getNMCUST());
                        tgl_kunjungan.add(response.body().getData().get(i).getTGLKUNJUNGAN());
                    }

                    kunjunganListView = new AdapterKunjungan(act_data_kunjungan_customer.this, kd_cust, nm_cust, tgl_kunjungan);
                    listView.setAdapter(kunjunganListView);
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(act_data_kunjungan_customer.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("ERROR");
                    pDialog.setContentText("Data Tidak Ditemukan !!");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<kunjungan>> call, Throwable t) {
                Toasty.error(act_data_kunjungan_customer.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Do it all with location
                            Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                            lat = location.getLatitude() + "";
                            lang = location.getLongitude() + "";
                            // Display in Toast
//                            Toast.makeText(KunjunganActivity.this,
//                                    "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });

        dialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == -1) {
                contents = data.getStringExtra("SCAN_RESULT");
                format = data.getStringExtra("SCAN_RESULT_FORMAT");

                insertKunjungan = api.insertKunjungan(contents, session.getUserId(), dateFormat.format(date), lat, lang);
                insertKunjungan.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            getData();
                        } else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(act_data_kunjungan_customer.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("ERROR");
                            pDialog.setContentText("Data Tidak Ditemukan !!");
                            pDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_data_kunjungan_customer.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (requestCode == 1) {
            if (resultCode == 1) {
                contents = data.getStringExtra("kd_cust");
                session.setCustomer(contents + ". " + data.getStringExtra("nm_cust"));
                session.setAlamatCustomer(data.getStringExtra("alamat_cust"));

                System.out.println(contents);
                System.out.println(session.getUserId());
                System.out.println(dateFormat.format(date));

                insertKunjungan = api.insertKunjungan(contents, session.getUserId(), dateFormat.format(date), lat, lang);
                insertKunjungan.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Toasty.success(act_data_kunjungan_customer.this, response.message()+"", Toast.LENGTH_SHORT).show();
                            getData();
                        } else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(act_data_kunjungan_customer.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("ERROR");
                            pDialog.setContentText("Data Tidak Ditemukan !!");
                            pDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_data_kunjungan_customer.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}