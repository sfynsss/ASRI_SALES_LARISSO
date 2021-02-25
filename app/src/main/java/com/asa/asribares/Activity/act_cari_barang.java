package com.asa.asribares.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterBarang;
import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.barang;
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

public class act_cari_barang extends AppCompatActivity {

    TextView btn_close;
    Button cari_brg;
    EditText nm_brg, qty;
    ListView data_brg;
    Session session;
    Api api;

    AdapterBarang barangListView1;
    String quantity;
    EditText masukan_qty;
    Button simpan_order;

    ArrayList<String> v_kode_brg = new ArrayList<String>();
    ArrayList<String> v_nama_brg = new ArrayList<String>();
    ArrayList<String> v_harga_brg = new ArrayList<String>();
    ArrayList<String> v_harga_brg2 = new ArrayList<String>();
    ArrayList<String> v_harga_brg3 = new ArrayList<String>();
    ArrayList<String> v_harga_brg4 = new ArrayList<String>();
    ArrayList<String> v_harga_brg5 = new ArrayList<String>();
    ArrayList<String> v_satuan = new ArrayList<String>();
    ArrayList<String> v_satuan2 = new ArrayList<String>();
    ArrayList<String> v_satuan3 = new ArrayList<String>();
    ArrayList<String> v_satuan4 = new ArrayList<String>();
    ArrayList<String> v_satur2 = new ArrayList<String>();
    ArrayList<String> v_satur3 = new ArrayList<String>();
    ArrayList<String> v_satur4 = new ArrayList<String>();
    ArrayList<String> v_kapasitas2 = new ArrayList<String>();
    ArrayList<String> v_kapasitas3 = new ArrayList<String>();
    ArrayList<String> v_kapasitas4 = new ArrayList<String>();
    ArrayList<String> qty_min1 = new ArrayList<String>();
    ArrayList<String> qty_min2 = new ArrayList<String>();
    ArrayList<String> qty_min3 = new ArrayList<String>();
    ArrayList<String> qty_min4 = new ArrayList<String>();
    ArrayList<String> qty_min5 = new ArrayList<String>();
    ArrayList<String> v_hpp = new ArrayList<String>();
    ArrayList<String> v_hpp_tampil = new ArrayList<String>();
    ArrayList<String> v_stok = new ArrayList<String>();
    ArrayList<String> v_spin_satuan = new ArrayList<String>();
    ArrayList<String> v_gambar_brg = new ArrayList<String>();

    boolean isImageFitToScreen;
    Call<BaseResponse<barang>> getBarang;

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
        setContentView(R.layout.activity_act_cari_barang);

        session = new Session(this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        cari_brg = (Button) findViewById(R.id.btn_cari_brg);
        btn_close = (TextView) findViewById(R.id.close_popup);
        nm_brg = (EditText) findViewById(R.id.nm_brg_tmp);
        data_brg = (ListView) findViewById(R.id.data_barang);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cari_brg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_brg = nm_brg.getText().toString().toUpperCase();
                getDataBarang(nama_brg);
            }
        });

        data_brg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String kd_brg = (String) parent.getItemAtPosition(position);
//                quantity = qty.getText().toString();
                showInputBox(position);
            }
        });
    }

    public void getDataBarang(String nama_barang){
        getBarang = api.getBarangSales(nama_barang, session.getGudang());
        getBarang.enqueue(new Callback<BaseResponse<barang>>() {
            @Override
            public void onResponse(Call<BaseResponse<barang>> call, Response<BaseResponse<barang>> response) {
                if (response.isSuccessful()) {
                    v_kode_brg.clear();
                    v_nama_brg.clear();
                    v_harga_brg.clear();
                    v_harga_brg2.clear();
                    v_harga_brg3.clear();
                    v_harga_brg4.clear();
                    v_harga_brg5.clear();
                    v_satuan.clear();
                    v_satuan2.clear();
                    v_satuan3.clear();
                    v_satuan4.clear();
                    v_satur2.clear();
                    v_satur3.clear();
                    v_satur4.clear();
                    v_kapasitas2.clear();
                    v_kapasitas3.clear();
                    v_kapasitas4.clear();
                    qty_min1.clear();
                    qty_min2.clear();
                    qty_min3.clear();
                    qty_min4.clear();
                    qty_min5.clear();
                    v_hpp.clear();
                    v_stok.clear();
                    v_gambar_brg.clear();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        v_kode_brg.add(response.body().getData().get(i).getKdBrg());
                        v_nama_brg.add(response.body().getData().get(i).getNmBrg());
                        v_harga_brg.add(response.body().getData().get(i).getHargaJl().toString());
                        v_harga_brg2.add(response.body().getData().get(i).getHargaJl2().toString());
                        v_harga_brg3.add(response.body().getData().get(i).getHargaJl3().toString());
                        v_harga_brg4.add(response.body().getData().get(i).getHargaJl4().toString());
                        v_harga_brg5.add("0");
                        v_satuan.add(response.body().getData().get(i).getSatuan1());
                        v_satuan2.add(response.body().getData().get(i).getSatuan2());
                        v_satuan3.add(response.body().getData().get(i).getSatuan3());
                        v_satuan4.add(response.body().getData().get(i).getSatuan4());
                        v_satur2.add(response.body().getData().get(i).getSatTur2());
                        v_satur3.add(response.body().getData().get(i).getSatTur3());
                        v_satur4.add(response.body().getData().get(i).getSatTur4());
                        v_kapasitas2.add(response.body().getData().get(i).getKapasitas2().toString().replace(",",""));
                        v_kapasitas3.add(response.body().getData().get(i).getKapasitas3().toString().replace(",",""));
                        v_kapasitas4.add(response.body().getData().get(i).getKapasitas4().toString().replace(",",""));
                        qty_min1.add("1");
                        qty_min2.add(response.body().getData().get(i).getQtyMin2().toString());
                        qty_min3.add(response.body().getData().get(i).getQtyMin3().toString());
                        qty_min4.add(response.body().getData().get(i).getQtyMin4().toString());
                        qty_min5.add("0");
                        v_stok.add(response.body().getData().get(i).getStok().toString());
                        v_hpp.add(response.body().getData().get(i).getHargaJl().toString());
                    }
                    barangListView1 = new AdapterBarang(act_cari_barang.this, v_kode_brg, v_nama_brg, v_harga_brg, v_harga_brg2, v_harga_brg3, v_harga_brg4, v_harga_brg5, v_hpp_tampil, v_stok);
                    data_brg.setAdapter(barangListView1);
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(act_cari_barang.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("ERROR");
                    pDialog.setContentText("Data Tidak Ditemukan !!!");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<barang>> call, Throwable t) {
                Toasty.error(act_cari_barang.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showInputBox(final int position) {
        final Dialog dialog = new Dialog(act_cari_barang.this);
        dialog.setTitle("Update User");
        View v = getLayoutInflater().inflate(R.layout.adapter_input_qty, null);
        dialog.setContentView(v);
//        System.out.println(oldItem);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        final Spinner spinner = (Spinner) v.findViewById(R.id.pilih_satuan);
        final EditText qty_tmp = (EditText) v.findViewById(R.id.masukkan_qty);
        qty_tmp.setFocusable(true);
        Button simpan_tmp = (Button) v.findViewById(R.id.simpan_order);
        v_spin_satuan.clear();
        if (v_satuan2.get(position).equals("")) {
            v_spin_satuan.add(v_satuan.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(act_cari_barang.this, android.R.layout.simple_spinner_item, v_spin_satuan);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter1);
        } else if (v_satuan3.get(position).equals("")) {
            v_spin_satuan.add(v_satuan.get(position));
            v_spin_satuan.add(v_satuan2.get(position) + " : " + v_kapasitas2.get(position) + " " + v_satur2.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(act_cari_barang.this, android.R.layout.simple_spinner_item, v_spin_satuan);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter1);
        } else if (v_satuan4.get(position).equals("")) {
            v_spin_satuan.add(v_satuan.get(position));
            v_spin_satuan.add(v_satuan2.get(position) + " : " + v_kapasitas2.get(position) + " " + v_satur2.get(position));
            v_spin_satuan.add(v_satuan3.get(position) + " : " + v_kapasitas3.get(position) + " " + v_satur3.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(act_cari_barang.this, android.R.layout.simple_spinner_item, v_spin_satuan);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter1);
        } else {
            v_spin_satuan.add(v_satuan.get(position));
            v_spin_satuan.add(v_satuan2.get(position) + " : " + v_kapasitas2.get(position) + " " + v_satur2.get(position));
            v_spin_satuan.add(v_satuan3.get(position) + " : " + v_kapasitas3.get(position) + " " + v_satur3.get(position));
            v_spin_satuan.add(v_satuan4.get(position) + " : " + v_kapasitas4.get(position) + " " + v_satur4.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(act_cari_barang.this, android.R.layout.simple_spinner_item, v_spin_satuan);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter1);
        }
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        getWindow().setLayout((int) (width * .5), (int) (height * .8));
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.x = 0;
//        params.y = 0;
//
//        getWindow().setAttributes(params);

        simpan_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.putExtra("kode_brg", v_kode_brg.get(position));
                it.putExtra("nama_brg", v_nama_brg.get(position));
                it.putExtra("satuan", v_satuan.get(position));
                it.putExtra("hpp", v_hpp.get(position));
                double tmp_qty = 0;
                if (spinner.getSelectedItem().equals(v_satuan.get(position))) {
                    System.out.println("1");
                    tmp_qty = Double.parseDouble(qty_tmp.getText().toString());
//                    it.putExtra("qty", tmp_qty + "");
                } else if (before(spinner.getSelectedItem().toString(), " :").equals(v_satuan2.get(position))) {
                    if (v_satur2.get(position).equals(v_satuan.get(position))) {
                        tmp_qty = Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas2.get(position));
                        System.out.println("2");
                        System.out.println(tmp_qty + "");
//                        it.putExtra("qty", before(tmp_qty + "", ".") + "");
                    } else {
                        tmp_qty = Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas2.get(position));
                        System.out.println("3");
                        System.out.println(tmp_qty + "");
//                        it.putExtra("qty", before(tmp_qty + "", ".") + "");
                    }
                } else if (before(spinner.getSelectedItem().toString(), " :").equals(v_satuan3.get(position))) {
                    if (v_satur3.get(position).equals(v_satuan.get(position))) {
                        tmp_qty = Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas3.get(position));
                        System.out.println("4");
                        System.out.println(tmp_qty + "");
//                        it.putExtra("qty", before(tmp_qty + "", ".") + "");
                    } else if (v_satur3.get(position).equals(v_satuan2.get(position))) {
                        if (v_satur2.get(position).equals(v_satuan.get(position))) {
                            tmp_qty = (Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas3.get(position))) * Double.parseDouble(v_kapasitas2.get(position));
                            System.out.println("5");
                            System.out.println(tmp_qty + "");
//                            it.putExtra("qty", before(tmp_qty + "", ".") + "");
                        }
                    }
                } else if (before(spinner.getSelectedItem().toString(), " :").equals(v_satuan4.get(position))) {
                    if (v_satur4.get(position).equals(v_satuan.get(position))) {
                        tmp_qty = Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas4.get(position));
                        System.out.println("6");
                        System.out.println(tmp_qty + "");
//                        it.putExtra("qty", before(tmp_qty + "", ".") + "");
                    } else if (v_satur4.get(position).equals(v_satuan3.get(position))) {
                        if (v_satur3.get(position).equals(v_satuan.get(position))) {
                            tmp_qty = (Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas4.get(position))) * Double.parseDouble(v_kapasitas3.get(position));
                            System.out.println("7");
                            System.out.println(tmp_qty + "");
//                            it.putExtra("qty", before(tmp_qty + "", ".") + "");
                        } else if (v_satur3.get(position).equals(v_satuan2.get(position))) {
                            if (v_satur2.get(position).equals(v_satuan.get(position))) {
                                tmp_qty = ((Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas4.get(position))) * Double.parseDouble(v_kapasitas3.get(position))) * Double.parseDouble(v_kapasitas2.get(position));
                                System.out.println("8");
                                System.out.println(tmp_qty + "");
//                                it.putExtra("qty", before(tmp_qty + "", ".") + "");
                            }
                        }
                    } else if (v_satur4.get(position).equals(v_satuan2.get(position))) {
                        if (v_satur2.get(position).equals(v_satuan.get(position))) {
                            tmp_qty = (Double.parseDouble(qty_tmp.getText().toString()) * Double.parseDouble(v_kapasitas4.get(position))) * Double.parseDouble(v_kapasitas2.get(position));
                            System.out.println("5");
                            System.out.println(tmp_qty + "");
//                            it.putExtra("qty", before(tmp_qty + "", ".") + "");
                        }
                    }
                }
                it.putExtra("qty", before(tmp_qty + "", ".") + "");

                if (tmp_qty >= Double.parseDouble(qty_min5.get(position)) && Double.parseDouble(qty_min5.get(position)) > 0) {
                    it.putExtra("harga_brg", v_harga_brg5.get(position));
                } else if (tmp_qty >= Double.parseDouble(qty_min4.get(position)) && Double.parseDouble(qty_min4.get(position)) > 0) {
                    it.putExtra("harga_brg", v_harga_brg4.get(position));
                } else if (tmp_qty >= Double.parseDouble(qty_min3.get(position)) && Double.parseDouble(qty_min3.get(position)) > 0) {
                    it.putExtra("harga_brg", v_harga_brg3.get(position));
                } else if (tmp_qty >= Double.parseDouble(qty_min2.get(position)) && Double.parseDouble(qty_min2.get(position)) > 0) {
                    it.putExtra("harga_brg", v_harga_brg2.get(position));
                } else {
                    it.putExtra("harga_brg", v_harga_brg.get(position));
                }

                setResult(1, it);
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}