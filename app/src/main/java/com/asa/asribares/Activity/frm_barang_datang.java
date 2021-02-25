package com.asa.asribares.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterDetOrderBeli;
import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Response.BaseResponse1;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.detOrderBeli;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_barang_datang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_barang_datang extends Fragment implements View.OnKeyListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_barang_datang() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_order_beli.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_barang_datang newInstance(String param1, String param2) {
        frm_barang_datang fragment = new frm_barang_datang();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Session session;
    Api api;
    Call<BaseResponse1> getNoFakturBD;
    Call<BaseResponse<detOrderBeli>> getDetailOrderBeli;
    Call<BaseResponse> simpanMasterBarangDatang;
    Call<BaseResponse> simpanDetailBarangDatang;

    TextView v_no_faktur, v_user_aktif, v_tgl_aktif, no_ent_order;
    Button btn_tambah, btn_simpan;
    EditText cari_brg;
    ListView list_order;

    DateFormat dateFormat, dateFormat1;
    Date date;
    NumberFormat formatRupiah;
    AdapterDetOrderBeli adapterDetOrderBeli;

    String tmp_no_ent = "", tmp_kd_suppl = "", tmp_nm_suppl = "", tmp_tgl_order = "", tmp_tgl_aktif = "";
    ArrayList<String> v_kode_brg = new ArrayList<String>();
    ArrayList<String> v_nama_brg = new ArrayList<String>();
    ArrayList<String> v_jml_order = new ArrayList<String>();
    ArrayList<String> v_harga_brg = new ArrayList<String>();
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
    ArrayList<String> v_spin_satuan = new ArrayList<String>();
    ArrayList<String> v_qty = new ArrayList<String>();

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_barang_datang, container, false);

        session = new Session(getContext());
        api = RetrofitClient.createService(Api.class);

        v_no_faktur = (TextView) view.findViewById(R.id.no_faktur_tmp);
        v_user_aktif = (TextView) view.findViewById(R.id.user_aktif_tmp);
        v_tgl_aktif = (TextView) view.findViewById(R.id.tgl_aktif_tmp);
        no_ent_order = (TextView) view.findViewById(R.id.no_ent_order);
        btn_tambah = view.findViewById(R.id.btn_tambah);
        cari_brg = view.findViewById(R.id.cari_brg);
        list_order = view.findViewById(R.id.list_order);
        btn_simpan = view.findViewById(R.id.btn_simpan);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat1 = new SimpleDateFormat("yyMM");
        date = new Date();
        tmp_tgl_aktif = dateFormat.format(date);
        v_user_aktif.setText("User : " +session.getUsername());
        v_tgl_aktif.setText("Tgl : " +dateFormat.format(date));
        getNomerFaktur();

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), act_data_order_beli.class), 0);
            }
        });

        cari_brg.requestFocus();
        cari_brg.setOnKeyListener(this);
        list_order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showInputBox(position);
                return true;
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanMaster(v_no_faktur.getText().toString(), tmp_tgl_aktif, tmp_no_ent, tmp_kd_suppl, session.getUsername(), tmp_tgl_order, tmp_nm_suppl);
            }
        });

        return view;
    }

    public void simpanMaster(String no_ent, String tgl, String no_ent_ob, String kode_suppl, String kode_user, String tanggal_order, String nama_suppl){
        final String det_kd_brg = TextUtils.join(",", v_kode_brg);
        final String det_nm_brg = TextUtils.join(",", v_nama_brg);
        final String det_harga = TextUtils.join(",", v_harga_brg);
        final String det_jumlah = TextUtils.join(",", v_jml_order);
        final String det_jumlah_bd = TextUtils.join(",", v_qty);
        final String det_jumlah_tur = TextUtils.join(",", v_qty);
        final String det_satuan = TextUtils.join(",", v_satuan);

        System.out.println(det_kd_brg);
        System.out.println(det_nm_brg);
        System.out.println(det_harga);
        System.out.println(det_jumlah);
        System.out.println(det_jumlah_bd);
        System.out.println(det_jumlah_tur);
        System.out.println(det_satuan);
        simpanMasterBarangDatang = api.insertMasterBarangDatang(no_ent, tgl, no_ent_ob, kode_suppl, kode_user, tanggal_order, nama_suppl);
        simpanMasterBarangDatang.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    getNomerFaktur();
                    simpanDetail(v_no_faktur.getText().toString(), tmp_tgl_aktif, det_kd_brg, det_nm_brg, det_satuan, det_harga, det_jumlah, det_jumlah_bd, det_jumlah_tur, tmp_no_ent, session.getGudang(), "1");
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("ERROR");
                    pDialog.setContentText("Data Master Gagal Disimpan !!!");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(getContext(), t.getMessage()+"master", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void simpanDetail(String no_ent_bd, String tgl, String kode_brg, String nama_brg, String satuan, String harga, String jumlah_order, String jumlah_barang_datang, String jml_tur,
                             String no_ent_ob, String gudang, String sat_ke){
        simpanDetailBarangDatang = api.insertDetailBarangDatang(no_ent_bd, tgl, kode_brg, nama_brg, satuan, harga, jumlah_order, jumlah_barang_datang,
                jml_tur, no_ent_ob, gudang, sat_ke);
        simpanDetailBarangDatang.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("SUCCESS");
                    pDialog.setContentText("Data Berhasil Ditambahkan !!");
                    pDialog.show();
                    no_ent_order.setText("");
                    tmp_kd_suppl = "";
                    tmp_nm_suppl = "";
                    tmp_no_ent = "";
                    tmp_tgl_order = "";
                    v_kode_brg.clear();
                    v_nama_brg.clear();
                    v_jml_order.clear();
                    v_harga_brg.clear();
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
                    v_spin_satuan.clear();
                    v_qty.clear();
                    adapterDetOrderBeli.notifyDataSetChanged();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("ERROR");
                    pDialog.setContentText("Data Detail Gagal Disimpan !!!");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toasty.error(getContext(), t.getMessage()+"detail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNomerFaktur(){
        getNoFakturBD = api.getNoFakturBD(session.getUsername());
        getNoFakturBD.enqueue(new Callback<BaseResponse1>() {
            @Override
            public void onResponse(Call<BaseResponse1> call, Response<BaseResponse1> response) {
                if (response.isSuccessful()) {
                    int fktr = Integer.parseInt(response.body().getData()) + 1;
                    v_no_faktur.setText("D" + dateFormat1.format(date) + "/" + String.format("%03d", Integer.parseInt(session.getUserId())) + "/" + String.format("%07d", fktr));
                } else {
                    v_no_faktur.setText("D" + dateFormat1.format(date) + "/" + String.format("%03d", Integer.parseInt(session.getUserId())) + "/" + String.format("%07d", 1));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse1> call, Throwable t) {
                v_no_faktur.setText("D" + dateFormat1.format(date) + "/" + String.format("%03d", Integer.parseInt(session.getUserId())) + "/" + String.format("%07d", 1));
            }
        });
    }

    public void getDetail(String no_ent_ord){
        getDetailOrderBeli = api.getDetailOrderBeli(no_ent_ord);
        getDetailOrderBeli.enqueue(new Callback<BaseResponse<detOrderBeli>>() {
            @Override
            public void onResponse(Call<BaseResponse<detOrderBeli>> call, Response<BaseResponse<detOrderBeli>> response) {
                if (response.isSuccessful()) {
                    v_kode_brg.clear();
                    v_nama_brg.clear();
                    v_harga_brg.clear();
                    v_jml_order.clear();
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
                    v_qty.clear();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        v_kode_brg.add(response.body().getData().get(i).getKDBRG());
                        v_nama_brg.add(response.body().getData().get(i).getNMBRG());
                        v_harga_brg.add(response.body().getData().get(i).getHARGA().replace(",", ""));
                        v_jml_order.add(response.body().getData().get(i).getJUMLAH());
                        v_satuan.add(response.body().getData().get(i).getSATUAN());
                        v_satuan2.add(response.body().getData().get(i).getSATUAN2());
                        v_satuan3.add(response.body().getData().get(i).getSATUAN3());
                        v_satuan4.add(response.body().getData().get(i).getSATUAN4());
                        v_satur2.add(response.body().getData().get(i).getSATUR2());
                        v_satur3.add(response.body().getData().get(i).getSATUR3());
                        v_satur4.add(response.body().getData().get(i).getSATUR4());
                        v_kapasitas2.add(response.body().getData().get(i).getKAPASITAS2().replace(",",""));
                        v_kapasitas3.add(response.body().getData().get(i).getKAPASITAS3().replace(",",""));
                        v_kapasitas4.add(response.body().getData().get(i).getKAPASITAS4().replace(",",""));
                        v_qty.add("0");
                    }

                    adapterDetOrderBeli = new AdapterDetOrderBeli(getActivity(), v_kode_brg, v_nama_brg, v_jml_order, v_satuan, v_qty);
                    adapterDetOrderBeli.notifyDataSetChanged();
                    list_order.setAdapter(adapterDetOrderBeli);
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("ERROR");
                    pDialog.setContentText("Data Tidak Ditemukan !!!");
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<detOrderBeli>> call, Throwable t) {
                Toasty.error(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            if (resultCode == 1) {
                tmp_no_ent = data.getStringExtra("no_ent");
                tmp_nm_suppl = data.getStringExtra("nm_suppl");
                tmp_kd_suppl = data.getStringExtra("kd_suppl");
                tmp_tgl_order = data.getStringExtra("tgl_order");

                no_ent_order.setText(tmp_no_ent+"");
                getDetail(tmp_no_ent);
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)))
        {
            int position = -1;
            position = v_kode_brg.indexOf(cari_brg.getText().toString());

            cari_brg.setText("");
            if (position == -1) {
                Toasty.error(getContext(), "Barang Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            } else {
                showInputBox(position);
            }

            return true;
        }
        return false;
    }

    public void showInputBox(final int position) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle("Update User");
        View v = getLayoutInflater().inflate(R.layout.adapter_input_qty, null);
        dialog.setContentView(v);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
//        System.out.println(oldItem);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final Spinner spinner = (Spinner) v.findViewById(R.id.pilih_satuan);
        final EditText qty_tmp = (EditText) v.findViewById(R.id.masukkan_qty);
        TextView nm_brg = v.findViewById(R.id.nm_brg);
        nm_brg.setText(v_nama_brg.get(position));
        nm_brg.setVisibility(View.VISIBLE);
        qty_tmp.setFocusable(true);
        Button simpan_tmp = (Button) v.findViewById(R.id.simpan_order);
        v_spin_satuan.clear();
        if (v_satuan2.get(position).equals("")) {
            v_spin_satuan.add(v_satuan.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, v_spin_satuan);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter1);
        } else if (v_satuan3.get(position).equals("")) {
            v_spin_satuan.add(v_satuan.get(position));
            v_spin_satuan.add(v_satuan2.get(position) + " : " + v_kapasitas2.get(position) + " " + v_satur2.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, v_spin_satuan);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter1);
        } else if (v_satuan4.get(position).equals("")) {
            v_spin_satuan.add(v_satuan.get(position));
            v_spin_satuan.add(v_satuan2.get(position) + " : " + v_kapasitas2.get(position) + " " + v_satur2.get(position));
            v_spin_satuan.add(v_satuan3.get(position) + " : " + v_kapasitas3.get(position) + " " + v_satur3.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, v_spin_satuan);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter1);
        } else {
            v_spin_satuan.add(v_satuan.get(position));
            v_spin_satuan.add(v_satuan2.get(position) + " : " + v_kapasitas2.get(position) + " " + v_satur2.get(position));
            v_spin_satuan.add(v_satuan3.get(position) + " : " + v_kapasitas3.get(position) + " " + v_satur3.get(position));
            v_spin_satuan.add(v_satuan4.get(position) + " : " + v_kapasitas4.get(position) + " " + v_satur4.get(position));
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, v_spin_satuan);
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
//                Intent it = new Intent();
//                it.putExtra("kode_brg", v_kode_brg.get(position));
//                it.putExtra("nama_brg", v_nama_brg.get(position));
//                it.putExtra("satuan", v_satuan.get(position));
//                it.putExtra("hpp", v_hpp.get(position));
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

                Toasty.success(getContext(), "Qty Barang Datang Berhasil Ditambahkan !!!", Toast.LENGTH_SHORT).show();
                v_qty.set(position, (int) tmp_qty + "");
                adapterDetOrderBeli.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}