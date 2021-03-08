package com.asa.asribares.Api;

import com.asa.asribares.Response.BaseResponse;
import com.asa.asribares.Response.BaseResponse1;
import com.asa.asribares.Response.UserResponse;
import com.asa.asribares.Table.barang;
import com.asa.asribares.Table.customer;
import com.asa.asribares.Table.detOrderBeli;
import com.asa.asribares.Table.gudang;
import com.asa.asribares.Table.kunjungan;
import com.asa.asribares.Table.order;
import com.asa.asribares.Table.orderBeli;
import com.asa.asribares.Table.rak;
import com.asa.asribares.Table.user;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("registrasi/namaPerusahaan")
    Call<BaseResponse1> getNamaPerusahaan();

    @FormUrlEncoded
    @POST("registrasi")
    Call<BaseResponse> registrasi(
            @Field("nama_toko") String nama_toko,
            @Field("paket") String paket,
            @Field("mac_address") String mac_address,
            @Field("kunci") String kunci
    );

    @FormUrlEncoded
    @POST("cekRegis")
    Call<BaseResponse> cekRegis(
            @Field("nama_toko") String nama_toko,
            @Field("paket") String paket,
            @Field("mac_address") String mac_address,
            @Field("kunci") String kunci
    );

    @FormUrlEncoded
    @POST("loginSales")
    Call<UserResponse> login(
            @Field("email") String username,
            @Field("password") String password
    );

    @GET("gudang/getAll")
    Call<BaseResponse<gudang>> getGudang();

    @FormUrlEncoded
    @POST("getNoFaktur")
    Call<BaseResponse1> getNoFaktur(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("getBarangSales")
    Call<BaseResponse<barang>> getBarangSales(
            @Field("nm_brg") String nm_brg,
            @Field("gudang") String gudang
    );

    @FormUrlEncoded
    @POST("insertMasterOrderJual")
    Call<BaseResponse> insertMasterOrderJual(
            @Field("no_ent") String  no_ent,
            @Field("tanggal") String tanggal,
            @Field("kd_cust") String kd_cust,
            @Field("total") String total,
            @Field("kd_user") String kd_user,
            @Field("gudang") String gudang,
            @Field("kd_peg") String kd_peg
    );

    @FormUrlEncoded
    @POST("insertDetailOrderJual")
    Call<BaseResponse> insertDetailOrderJual(
            @Field("no_ent") String  no_ent,
            @Field("kd_brg") String kd_brg,
            @Field("nm_brg") String nm_brg,
            @Field("satuan") String satuan,
            @Field("jumlah") String jumlah,
            @Field("harga") String harga,
            @Field("sub_total") String sub_total,
            @Field("hpp") String hpp
    );

    @FormUrlEncoded
    @POST("dataKunjungan")
    Call<BaseResponse<kunjungan>> getDataKunjungan(
            @Field("tgl_start") String  tgl_start,
            @Field("tgl_end") String tgl_end,
            @Field("no_user") String no_user
    );

    @FormUrlEncoded
    @POST("getCustomer")
    Call<BaseResponse<customer>> getDataCustomer(
            @Field("nm_cust") String nm_cust
    );

    @FormUrlEncoded
    @POST("insertKunjungan")
    Call<BaseResponse> insertKunjungan(
            @Field("kd_cust") String kd_cust,
            @Field("no_user") String no_user,
            @Field("tgl") String tgl,
            @Field("lat") String lat,
            @Field("lang") String lang
    );

    @FormUrlEncoded
    @POST("getDataOrderJual")
    Call<BaseResponse<order>> getDataOrderJual(
            @Field("tgl_start") String tgl_start,
            @Field("tgl_end") String tgl_end,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("Order/getDataOrderBeli")
    Call<BaseResponse<orderBeli>> getDataOrderBeli(
            @Field("tgl_start") String tgl_start,
            @Field("tgl_end") String tgl_end
    );

    @FormUrlEncoded
    @POST("Order/getDetailOrderBeli")
    Call<BaseResponse<detOrderBeli>> getDetailOrderBeli(
            @Field("no_ent") String no_ent
    );

    @FormUrlEncoded
    @POST("Order/insertMasterBarangDatang")
    Call<BaseResponse> insertMasterBarangDatang(
            @Field("no_ent") String  no_ent,
            @Field("tanggal") String tanggal,
            @Field("no_ent_ord") String no_ent_ord,
            @Field("kd_suppl") String kd_suppl,
            @Field("kd_user") String kd_user,
            @Field("tgl_order") String tgl_order,
            @Field("nm_suppl") String nm_suppl
    );

    @FormUrlEncoded
    @POST("Order/insertDetailBarangDatang")
    Call<BaseResponse> insertDetailBarangDatang(
            @Field("no_ent") String  no_ent,
            @Field("tanggal") String tanggal,
            @Field("kd_brg") String kd_brg,
            @Field("nm_brg") String nm_brg,
            @Field("satuan") String satuan,
            @Field("harga") String harga,
            @Field("jml_order") String jml_order,
            @Field("jumlah") String jumlah_brg_datang,
            @Field("jml_tur") String jml_tur,
            @Field("no_ent_ord") String no_ent_ord,
            @Field("gudang") String gudang,
            @Field("sat_ke") String sat_ke
    );

    @FormUrlEncoded
    @POST("Order/getNoFakturBD")
    Call<BaseResponse1> getNoFakturBD(
            @Field("username") String username
    );

    @GET("Gudang/getRak")
    Call<BaseResponse<rak>> getRak();

    @FormUrlEncoded
    @POST("generateGrosirToken")
    Call<BaseResponse1> generateGrosirToken(
            @Field("email") String email
    );

}
