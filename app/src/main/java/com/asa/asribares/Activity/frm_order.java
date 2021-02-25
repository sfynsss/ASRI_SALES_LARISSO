package com.asa.asribares.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterOrder;
import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Response.BaseResponse1;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Session.StringAlignUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.ImagePrintable;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_order#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_order extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_order() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_order.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_order newInstance(String param1, String param2) {
        frm_order fragment = new frm_order();
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

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    static String after(String value, String a) {
        // Returns a substring containing all characters after a string.
        int posA = value.lastIndexOf(a);
        if (posA == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= value.length()) {
            return "";
        }
        return value.substring(adjustedPosA);
    }

    TextView v_no_faktur, v_user_aktif, v_tgl_aktif, v_cust_aktif, v_harga_tot;
    Button btn_tambah_order, btn_simpan_order, btn_brg_prioritas;
    ListView listView_Order;
    Session session;
    Api api;

    DateFormat dateFormat, dateFormat1;
    Date date;
    AdapterOrder orderBarangListView;

    ArrayList<String> customer = new ArrayList<String>();
    ArrayList<String> kode_brg = new ArrayList<String>();
    ArrayList<String> nama_brg = new ArrayList<String>();
    ArrayList<String> harga_brg = new ArrayList<String>();
    ArrayList<String> harga_tampil = new ArrayList<String>();
    ArrayList<String> satuan = new ArrayList<String>();
    ArrayList<String> qty = new ArrayList<String>();
    ArrayList<String> hpp = new ArrayList<String>();
    ArrayList<String> qty_tampil = new ArrayList<String>();
    ArrayList<String> total_tampil = new ArrayList<String>();
    ArrayList<String> subtot = new ArrayList<String>();

    String no_ent, tanggal, kd_cust, kd_user, gudang, kd_peg;
    String det_kd_brg, det_nm_brg, det_satuan, det_jumlah, det_harga, det_sub_total, det_hpp;
    String kode_brg1 = "", nama_brg1 = "", harga_brg1 = "", harga_tampil1 = "", satuan1 = "", hpp1 = "";

    double qty_jum, harga_jum, jum, total;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    NumberFormat formatRupiah;

    Call<BaseResponse1> getNoFaktur;
    Call<BaseResponse> insertMaster;
    Call<BaseResponse> insertDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_order, container, false);

        v_no_faktur = (TextView) view.findViewById(R.id.no_faktur_tmp);
        v_user_aktif = (TextView) view.findViewById(R.id.user_aktif_tmp);
        v_tgl_aktif = (TextView) view.findViewById(R.id.tgl_aktif_tmp);
        v_cust_aktif = (TextView) view.findViewById(R.id.customer_aktif);
        v_harga_tot = (TextView) view.findViewById(R.id.harga_total);
        btn_tambah_order = (Button) view.findViewById(R.id.tambah_order_tmp);
        btn_simpan_order = (Button) view.findViewById(R.id.simpan_order_tmp);
        listView_Order = (ListView) view.findViewById(R.id.list_order);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
//        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        session = new Session(getContext());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat1 = new SimpleDateFormat("yyMM");
//        URL_CUST = session.getUrlDataCustomer();
//        URL_FAKTUR = session.getUrlNoFaktur();
//        URL_SIMPAN_MASTER = session.getUrlSimpanMaster();
//        URL_SIMPAN_DETAIL = session.getUrlSimpanDetail();
        date = new Date();
        v_user_aktif.setText("User : " +session.getUsername());
        v_tgl_aktif.setText("Tgl : " +dateFormat.format(date));
        if (session.getCustomer().equals("")) {
            v_cust_aktif.setText("");
        } else {
            v_cust_aktif.setText(session.getCustomer()+" / "+session.getAlamatCustomer());
        }
        v_harga_tot.setText(formatRupiah.format(total));

        getNomerFaktur();

        btn_tambah_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResult(new Intent(getContext(), PopUpMasukanQty.class), 0);
                if (kode_brg.size() < 15) {
                    startActivityForResult(new Intent(getContext(), act_cari_barang.class), 0);
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("WARNING");
                    pDialog.setContentText("Maksimum 15 Item !!!");
                    pDialog.show();
                }
            }
        });

        btn_simpan_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_ent = v_no_faktur.getText().toString();
                tanggal = dateFormat.format(date);
                kd_user = session.getUsername();
                kd_cust = before(v_cust_aktif.getText().toString(), ".");
                gudang = session.getGudang();
                kd_peg = session.getKodePegawai();

                det_kd_brg = TextUtils.join(",", kode_brg);
                det_nm_brg = TextUtils.join(",", nama_brg);
                det_harga = TextUtils.join(",", harga_brg);
                det_jumlah = TextUtils.join(",", qty);
                det_sub_total = TextUtils.join(",", subtot);
                det_satuan = TextUtils.join(",", satuan);
                det_hpp = TextUtils.join(",", hpp);

                System.out.println(det_kd_brg);
                System.out.println(det_nm_brg);
                System.out.println(det_harga);
                System.out.println(det_jumlah);
                System.out.println(det_sub_total);
                System.out.println(det_hpp);
                if (kode_brg.size() > 0) {
                    if  (TextUtils.isEmpty(v_cust_aktif.getText()) || v_cust_aktif.getText().toString().equals("")) {
                        Toasty.error(getContext(), "Customer masih kosong !!!", Toast.LENGTH_SHORT, true).show();
                    } else {
                        insertMaster = api.insertMasterOrderJual(no_ent, tanggal, kd_cust, total+"", kd_user, gudang, kd_peg);
                        insertMaster.enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response.isSuccessful()) {
                                    getNomerFaktur();
                                    insertDetail = api.insertDetailOrderJual(no_ent, det_kd_brg, det_nm_brg, det_satuan, det_jumlah, det_harga, det_sub_total, det_hpp);
                                    insertDetail.enqueue(new Callback<BaseResponse>() {
                                        @Override
                                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                            if (response.isSuccessful()) {
                                                SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                                pDialog.setTitleText("SUCCESS");
                                                pDialog.setContentText("Data Berhasil Ditambahkan !!");
                                                pDialog.show();
                                                if (session.getStatusBt() == true) {
                                                    printText();
                                                }
                                                kode_brg.clear();
                                                nama_brg.clear();
                                                harga_brg.clear();
                                                harga_tampil.clear();
                                                satuan.clear();
                                                qty.clear();
                                                qty_tampil.clear();
                                                total_tampil.clear();
                                                hpp.clear();
                                                total = 0;
                                                System.out.println(total);
                                                v_harga_tot.setText(formatRupiah.format(total));
                                                orderBarangListView = new AdapterOrder(getActivity(), kode_brg, nama_brg, harga_tampil, qty_tampil, total_tampil);
                                                orderBarangListView.notifyDataSetChanged();
                                                listView_Order.setAdapter(orderBarangListView);
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
                                            Toasty.error(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
                                Toasty.error(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toasty.error(getContext(), "Tambahkan Order Terlebih dahulu !!!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        listView_Order.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                builder = new AlertDialog.Builder(getContext());
                final double n = Double.parseDouble(subtot.get(position));
                builder.setMessage("Apakah Anda yakin untuk menghapus ? ");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kode_brg.remove(position);
                        nama_brg.remove(position);
                        harga_brg.remove(position);
                        harga_tampil.remove(position);
                        satuan.remove(position);
                        qty.remove(position);
                        hpp.remove(position);
                        qty_tampil.remove(position);
                        total_tampil.remove(position);
                        subtot.remove(position);
                        total -= n;
                        System.out.println(total);
                        v_harga_tot.setText(formatRupiah.format(total));
                        orderBarangListView = new AdapterOrder(getActivity(), kode_brg, nama_brg, harga_tampil, qty_tampil, total_tampil);
                        orderBarangListView.notifyDataSetChanged();
                        listView_Order.setAdapter(orderBarangListView);
                    }
                });
                System.out.println(total + "");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

        return view;
    }

    public void getNomerFaktur(){
        System.out.println(session.getUsername());
        getNoFaktur = api.getNoFaktur(session.getUsername());
        getNoFaktur.enqueue(new Callback<BaseResponse1>() {
            @Override
            public void onResponse(Call<BaseResponse1> call, Response<BaseResponse1> response) {
                if (response.isSuccessful()) {
                    int fktr = Integer.parseInt(response.body().getMessage()) + 1;
                    v_no_faktur.setText("OJ" + dateFormat1.format(date) + "/" + String.format("%03d", Integer.parseInt(session.getUserId())) + "/" + String.format("%07d", fktr));
                } else {
//                    System.out.println("disini1");
                    v_no_faktur.setText("OJ" + dateFormat1.format(date) + "/" + String.format("%03d", Integer.parseInt(session.getUserId())) + "/" + String.format("%07d", 1));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse1> call, Throwable t) {
//                System.out.println("disini2");
                v_no_faktur.setText("OJ" + dateFormat1.format(date) + "/" + String.format("%03d", Integer.parseInt(session.getUserId())) + "/" + String.format("%07d", 1));
//                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            if (resultCode == 1) {
                kode_brg.add(data.getStringExtra("kode_brg"));
                nama_brg.add(data.getStringExtra("nama_brg"));
                harga_brg.add(data.getStringExtra("harga_brg").replace(",",""));
                harga_tampil.add(formatRupiah.format(Long.parseLong(data.getStringExtra("harga_brg").replace(",","")))+"");
                satuan.add(data.getStringExtra("satuan"));
                qty.add(data.getStringExtra("qty"));
                hpp.add(data.getStringExtra("hpp").replace(",",""));
                if (after(data.getStringExtra("qty"), ".").equals("0")) {
                    qty_tampil.add(before(data.getStringExtra("qty"), ".")+" "+data.getStringExtra("satuan"));
                } else {
                    qty_tampil.add(data.getStringExtra("qty")+" "+data.getStringExtra("satuan"));
                }
                qty_jum = Double.parseDouble(data.getStringExtra("qty"));
                harga_jum = Double.parseDouble(data.getStringExtra("harga_brg").replaceAll("[\\D]",""));
                jum = harga_jum*qty_jum;
                total_tampil.add(formatRupiah.format(jum));
                subtot.add(before(jum+"", "."));
                System.out.println(before(jum+"", "."));
                total += jum;
                System.out.println(total);
                v_harga_tot.setText(formatRupiah.format(total));

                orderBarangListView = new AdapterOrder(getActivity(), kode_brg, nama_brg, harga_tampil, qty_tampil, total_tampil);
                orderBarangListView.notifyDataSetChanged();
                listView_Order.setAdapter(orderBarangListView);
            }
        }
    }

    private void printText() {
        ArrayList<Printable> printables = new ArrayList<>();

        printables.add(new TextPrintable.Builder()
                .setText("Larisso")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("Jl. Watu Ulo No.21 Ambulu")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("HP : 082234078999")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("------------------------------------------")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        dateFormat1 = new SimpleDateFormat("dd MMM yyyy HH:mm");
        StringBuffer buffer1 = new StringBuffer();
        buffer1.append("User      : "+session.getUsername()+"\n");
        buffer1.append("Waktu     : "+dateFormat1.format(date)+"\n");
        buffer1.append("No Ent    : "+no_ent);

        printables.add(new TextPrintable.Builder()
                .setText(buffer1.toString())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("------------------------------------------")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("#### SALES ORDER ####")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("------------------------------------------")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

//        QRGEncoder qrgEncoder = new QRGEncoder(no_ent, null, QRGContents.Type.TEXT, 100);
//        Bitmap qrBits = qrgEncoder.getBitmap();
//        printables.add(new ImagePrintable.Builder(qrBits)
//                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
//                .setNewLinesAfter(1)
//                .build());
//
//        printables.add(new TextPrintable.Builder()
//                .setText("------------------------------------------")
//                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
//                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
//                .setNewLinesAfter(1).build()
//        );

        StringBuffer buffer = new StringBuffer();
        StringAlignUtils utils = new StringAlignUtils(20, StringAlignUtils.Alignment.LEFT);
        StringAlignUtils utils1 = new StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT);
        for (int i = 0; i < kode_brg.size(); i++) {
            buffer.append(nama_brg.get(i)+"\n");
//            buffer.append("    "+hrg_brg.get(i).replace(",00","")+" x "+qty.get(i));
            buffer.append(utils.format("   "+harga_tampil.get(i).replace(",00","").replace("Rp","")+" x "+qty.get(i)));
            buffer.append(utils1.format(total_tampil.get(i).replace(",00","").replace("Rp",""))+"\n");
        }

        printables.add(new TextPrintable.Builder()
                .setText(buffer.toString())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("------------------------------------------")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        StringBuffer buffer2 = new StringBuffer();
        buffer2.append(utils.format("   Subtotal"));
        buffer2.append(utils1.format(v_harga_tot.getText().toString().replace(",00","").replace("Rp","")));

        printables.add(new TextPrintable.Builder()
                .setText(buffer2.toString()+"\n\n")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new TextPrintable.Builder()
                .setText("Simpan struk ini sebagai bukti order\n\n")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1).build()
        );

        printables.add(new RawPrintable.Builder(new byte[]{27, 100, 2}).build());
        Printooth.INSTANCE.printer().print(printables);
    }
}