package com.asa.asribares.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asa.asribares.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_home_sales_order#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_home_sales_order extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_home_sales_order() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_home_sales_order.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_home_sales_order newInstance(String param1, String param2) {
        frm_home_sales_order fragment = new frm_home_sales_order();
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

    LinearLayout kunjungan, data_order, customer, tagihan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_home_sales_order, container, false);

        kunjungan = view.findViewById(R.id.kunjungan);
        data_order = view.findViewById(R.id.data_order);
        customer = view.findViewById(R.id.customer);
        tagihan = view.findViewById(R.id.tagihan);

        kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), act_kunjungan.class));
            }
        });

        data_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), act_data_order.class));
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), act_data_customer.class);
                it.putExtra("status", "aktifasi");
                startActivityForResult(it, 1);
            }
        });

        return view;
    }
}