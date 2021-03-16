package com.asa.asribares.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.asribares.Adapter.AdapterUser;
import com.asa.asribares.R;
import com.asa.asribares.Session.Session;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_user extends Fragment implements PrintingCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_user.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_user newInstance(String param1, String param2) {
        frm_user fragment = new frm_user();
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

    TextView nama_pengguna, email_pengguna, telp_pengguna;
    Session session;
    String idgudang;
    String[] text={"Gudang", "Setting", "Logout"};
    String[] text2;
    Integer[] img = {R.drawable.ic_gudang, R.drawable.ic_setting, R.drawable.ic_logout};
    Printing printing;
    AdapterUser adapterUser;
    RelativeLayout bt_setting, bt_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_user, container, false);

        nama_pengguna = (TextView) view.findViewById(R.id.nama_pengguna);
        email_pengguna = (TextView) view.findViewById(R.id.email_pengguna);
        telp_pengguna = (TextView) view.findViewById(R.id.telp_pengguna);
        bt_setting = (RelativeLayout) view.findViewById(R.id.bt_setting);
        bt_logout = (RelativeLayout) view.findViewById(R.id.bt_logout);
        session = new Session(getContext());

        idgudang = session.getGudang()+"";

        if (session.getStatusBt() == true) {
            text2= new String[]{"Gudang " + idgudang + " Terpilih", session.getNamaBt(), "Keluar Akun"};
        } else {
            text2= new String[]{"Gudang " + idgudang + " Terpilih", "Pilih Device", "Keluar Akun"};
        }

        if (printing != null){
            printing.setPrintingCallback(this);
        }

        adapterUser = new AdapterUser(getActivity(), text, text2, img);
        nama_pengguna.setText(session.getNamaPegawai());
        email_pengguna.setText(session.getKodePegawai());
        //telp_pengguna.setText(session.getTelpUser());

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Printooth.INSTANCE.hasPairedPrinter()){
                    Printooth.INSTANCE.removeCurrentPrinter();
                } else {
                    startActivityForResult(new Intent(getActivity(),ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
                    changePairAndUnpair();
                }
            }
        });

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), act_login.class);
                session.setLoggedin(false, "", "", "","");
                session.setGudang(false, "GUDANG");
                session.setPegawai("", "");
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void changePairAndUnpair(){
        if (Printooth.INSTANCE.hasPairedPrinter()){
            session.setBluetooth(Printooth.INSTANCE.getPairedPrinter().getName(), Printooth.INSTANCE.getPairedPrinter().getAddress(), true);
            text2= new String[]{"Gudang " + idgudang + " Terpilih", Printooth.INSTANCE.getPairedPrinter().getName(), "Keluar Akun"};
        } else {
            session.setBluetooth("", "", false);
            text2= new String[]{"Gudang " + idgudang + " Terpilih", "Pilih Device", "Keluar Akun"};
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK){
            initPrinting();
            changePairAndUnpair();
        }
    }

    private void initPrinting() {
        if (!Printooth.INSTANCE.hasPairedPrinter()){
            printing = Printooth.INSTANCE.printer();
        }
        if (printing != null){
            printing.setPrintingCallback(this);
        }
    }

    @Override
    public void connectingWithPrinter() {
        Toast.makeText(getContext(), "Connected to Printer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectionFailed(String s) {
        Toast.makeText(getContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void printingOrderSentSuccessfully() {
        Toast.makeText(getContext(), "Order Sent To Print", Toast.LENGTH_SHORT).show();
    }
}