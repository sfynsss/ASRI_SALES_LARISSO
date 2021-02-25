package com.asa.asribares.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.asa.asribares.Api.Api;
import com.asa.asribares.Api.RetrofitClient;
import com.asa.asribares.R;
import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Session.Session;
import com.asa.asribares.Table.rak;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_stok_opname#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_stok_opname extends Fragment implements View.OnKeyListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_stok_opname() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_stok_opname.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_stok_opname newInstance(String param1, String param2) {
        frm_stok_opname fragment = new frm_stok_opname();
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

    Spinner rak;
    EditText nama_brg;
    ListView list_barang;
    Button btn_simpan;

    Session session;
    Api api;

    Call<BaseResponse<rak>> getRak;

    ArrayList<String> tmp_nm_rak = new ArrayList<>();
    ArrayList<String> tmp_kd_rak = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_stok_opname, container, false);

        rak = view.findViewById(R.id.rak);
        nama_brg = view.findViewById(R.id.nama_barang);
        list_barang = view.findViewById(R.id.list_barang);
        btn_simpan = view.findViewById(R.id.btn_simpan);

        session = new Session(getContext());
        api = RetrofitClient.createService(Api.class);

        getDataRak();
        nama_brg.setOnKeyListener(this);

        return view;
    }

    public void getDataRak(){
        getRak = api.getRak();
        getRak.enqueue(new Callback<BaseResponse<com.asa.asribares.Table.rak>>() {
            @Override
            public void onResponse(Call<BaseResponse<com.asa.asribares.Table.rak>> call, Response<BaseResponse<com.asa.asribares.Table.rak>> response) {
                if (response.isSuccessful()) {
                    tmp_nm_rak.clear();
                    tmp_kd_rak.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        tmp_nm_rak.add(response.body().getData().get(i).getNMRAK());
                        tmp_kd_rak.add(response.body().getData().get(i).getKDRAK());
                    }

                    //Ini buat ngisi Spinner
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_rak, tmp_nm_rak);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_rak);
                    rak.setAdapter(arrayAdapter);
                    //End isi spinner
                } else {
                    Toasty.error(getContext(), "Data Tidak Ditemukan !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<com.asa.asribares.Table.rak>> call, Throwable t) {
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)))
        {
            Toasty.success(getContext(), nama_brg.getText().toString()+"", Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }
}