package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class user {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("tanggal_lahir")
    @Expose
    private String tanggalLahir;
    @SerializedName("no_telp")
    @Expose
    private String noTelp;
    @SerializedName("cabang")
    @Expose
    private String cabang;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("foto_ktp")
    @Expose
    private String fotoKtp;
    @SerializedName("otoritas")
    @Expose
    private String otoritas;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("email_activation")
    @Expose
    private String emailActivation;
    @SerializedName("activation_token")
    @Expose
    private String activationToken;
    @SerializedName("kd_peg")
    @Expose
    private String kdPeg;
    @SerializedName("kd_alamat")
    @Expose
    private String kdAlamat;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_penerima")
    @Expose
    private String namaPenerima;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("kd_provinsi")
    @Expose
    private String kdProvinsi;
    @SerializedName("kd_kota")
    @Expose
    private String kdKota;
    @SerializedName("kd_kecamatan")
    @Expose
    private String kdKecamatan;
    @SerializedName("alamat_lengkap")
    @Expose
    private String alamatLengkap;
    @SerializedName("no_telp_penerima")
    @Expose
    private String noTelpPenerima;
    @SerializedName("kode_pos")
    @Expose
    private String kodePos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoKtp() {
        return fotoKtp;
    }

    public void setFotoKtp(String fotoKtp) {
        this.fotoKtp = fotoKtp;
    }

    public String getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(String otoritas) {
        this.otoritas = otoritas;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getEmailActivation() {
        return emailActivation;
    }

    public void setEmailActivation(String emailActivation) {
        this.emailActivation = emailActivation;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getKdPeg() {
        return kdPeg;
    }

    public void setKdPeg(String kdPeg) {
        this.kdPeg = kdPeg;
    }

    public String getKdAlamat() {
        return kdAlamat;
    }

    public void setKdAlamat(String kdAlamat) {
        this.kdAlamat = kdAlamat;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKdProvinsi() {
        return kdProvinsi;
    }

    public void setKdProvinsi(String kdProvinsi) {
        this.kdProvinsi = kdProvinsi;
    }

    public String getKdKota() {
        return kdKota;
    }

    public void setKdKota(String kdKota) {
        this.kdKota = kdKota;
    }

    public String getKdKecamatan() {
        return kdKecamatan;
    }

    public void setKdKecamatan(String kdKecamatan) {
        this.kdKecamatan = kdKecamatan;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getNoTelpPenerima() {
        return noTelpPenerima;
    }

    public void setNoTelpPenerima(String noTelpPenerima) {
        this.noTelpPenerima = noTelpPenerima;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

}
