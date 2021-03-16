package com.asa.asribares.Session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sfyn on 01/02/2018.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ASRI", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setActivation(Boolean registered, String nama_toko, String paket, String imei, String kunci_program) {
        editor.putBoolean("registered", registered);
        editor.putString("nama_toko", nama_toko);
        editor.putString("paket", paket);
        editor.putString("imei", imei);
        editor.putString("kunci_program", kunci_program);
        editor.commit();
    }

    public void setLoggedin(boolean loggedin, String id, String username, String status, String token){
        editor.putBoolean("loggedInmode", loggedin);
        editor.putString("kodeUser", id);
        editor.putString("username", username);
        editor.putString("status", status);
        editor.putString("token", token);
        editor.commit();
    }

    public void setBluetooth(String bluetooth_name, String bluetooth_address, boolean sts_bt) {
        editor.putString("bt_name", bluetooth_name);
        editor.putString("bt_address", bluetooth_address);
        editor.putBoolean("bt_sts", sts_bt);
        editor.commit();
    }

    public void setPegawai(String nm_peg, String kd_peg) {
        editor.putString("Nama_Pegawai", nm_peg);
        editor.putString("Kode_Pegawai", kd_peg);
        editor.commit();
    }

    public void setGudang(boolean gudangset, String idGudang){
        editor.putBoolean("gudangSet", gudangset);
        editor.putString("idGudang", idGudang);
        editor.commit();
    }

    public void setUrl(boolean isUrl, String url){
        editor.putBoolean("isUrl", isUrl);
        editor.putString("URL", url);
        editor.commit();
    }

    public void setCustomer(String customer){
        editor.putString("customer_aktif", customer);
        editor.commit();
    }

    public void setAlamatCustomer(String alamat_cust) {
        editor.putString("alamat_cust", alamat_cust);
        editor.commit();
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail(){ return sharedPreferences.getString("email", ""); }

    public String getNamaBt() {
        return sharedPreferences.getString("bt_name", "");
    }

    public String getAddressBt(){
        return sharedPreferences.getString("bt_address", "");
    }

    public boolean getStatusBt() {
        return sharedPreferences.getBoolean("bt_sts", false);
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public String getCustomer() {
        return sharedPreferences.getString("customer_aktif", "");
    }

    public String getAlamatCustomer() {
        return sharedPreferences.getString("alamat_cust", "");
    }

    public String getNamaPegawai() {
        return sharedPreferences.getString("Nama_Pegawai", "");
    }

    public String getKodePegawai() {
        return sharedPreferences.getString("Kode_Pegawai", "");
    }

    public boolean registered() { return sharedPreferences.getBoolean("registered", false); }

    public String getNamaToko() { return sharedPreferences.getString("nama_toko", ""); }

    public String getPaket() { return sharedPreferences.getString("paket", ""); }

    public String getImei() { return sharedPreferences.getString("imei", ""); }

    public String getKunciProgram() { return sharedPreferences.getString("kunci_program", ""); }

    public boolean loggedin() {
        return sharedPreferences.getBoolean("loggedInmode", false);
    }

    public boolean gudangset(){
        return sharedPreferences.getBoolean("gudangSet", false);
    }

    public String getGudang(){
        return sharedPreferences.getString("idGudang","01-TOKO");
    }

    public String getUsername(){
        return sharedPreferences.getString("username", "");
    }

    public String getUserId(){
        return sharedPreferences.getString("kodeUser", "");
    }

    public String getUserStatus(){
        return sharedPreferences.getString("status", "");
    }

    public boolean url() {
        return sharedPreferences.getBoolean("isUrl", false);
    }

    public String BaseUrl(){
//        return "http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/";
        return "http://192.168.1.3/KUE/index.php/";
    }

    public String BaseUrl1(){
        return "http://"+sharedPreferences.getString("URL", "")+"/KUE/";
    }

    public String getUrlNamaPerusahaan(){
        return "http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/registrasi/namaPerusahaan";
    }

    public String getUrlRegistrasi(){
        return "http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/registrasi/registrasi";
    }

    public String getCekRegistrasi(){
        return "http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/registrasi/cekRegis";
    }

    public String getUrlLogin(){
        return "http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/login/login";
    }

    public String getUrlBarang(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Barang/getBarang";
    }

    public String getUrlBarang1(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Barang/getBarang1";
    }

    public String getUrlInsertBarang(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Barang/insertBarang";
    }

    public String getUrlUpdateBarang(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Barang/updateBarang";
    }

    public String getUrlStokOpnameAll(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/StokOpname/getAll";
    }

    public String getUrlUpdateStokOpname(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/StokOpname/UpdateStokOpname";
    }

    public String getUrlDeleteStokOpname(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/StokOpname/deleteStokOpname";
    }

    public String getUrlUser(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Login/getAll";
    }

    public String getUrlUser1(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Login/getAll1";
    }

    public String getUrlUpdateUser(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Login/updateUser";
    }

    public String getUrlInsertKunjungan(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Kunjungan/insertKunjungan";
    }

    public String getUrlDataKunjungan(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Kunjungan/dataKunjungan";
    }

    public String getUrlDataKunjungan1(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Kunjungan/dataKunjungan1";
    }

    public String getUrlDataKunjunganAll(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Kunjungan/dataKunjunganAll";
    }

    public String getUrlDataPegawai(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Kunjungan/getDataPegawai";
    }

    public String getUrlDataCustomer(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/getDataCustomer";
    }

    public String getUrlNoFaktur(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/getNoFaktur";
    }

    public String getUrlSimpanMaster(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/insertMasterOrderJual";
    }

    public String getUrlHapusMaster(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/deleteMasterOrderJual";
    }

    public String getUrlSimpanDetail(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/insertDetailOrderJual";
    }

    public String getUrlBarangTerlaris() {
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Barang/getBarangTerlaris";
    }

    public String getUrlBarangKritis() {
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Barang/getBarangKritis";
    }

    public String getUrlDataOrder() {
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/getDataOrder";
    }

    public String getUrlDetailOrder() {
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/getDetailOrder";
    }

    public String getUrlCariDataCustomer() {
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Kunjungan/dataCustomer";
    }

    public String getUrlUpdateMaster(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/updateMasterOrderJual";
    }

    public String getUrlHapusDetail(){
        return "Http://"+sharedPreferences.getString("URL", "")+"/KUE/index.php/Order/deleteDetailOrderJual";
    }

    //tagihan
    public String getUrlTagihan(String id){
        return "Http://"+sharedPreferences.getString("URL","")+"/KUE/index.php/DetailTagihan/getData/"+id;
    }

    public String getUrlSimpanTagihan(){
        return "Http://"+sharedPreferences.getString("URL","")+"/KUE/index.php/Tagihan/insertTagihan";
    }

}
