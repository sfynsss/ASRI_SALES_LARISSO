package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class barang {

    @SerializedName("kd_brg")
    @Expose
    private String kdBrg;
    @SerializedName("kd_kat_android")
    @Expose
    private String kdKatAndroid;
    @SerializedName("jns_brg")
    @Expose
    private String jnsBrg;
    @SerializedName("nm_brg")
    @Expose
    private String nmBrg;
    @SerializedName("stok")
    @Expose
    private Integer stok;
    @SerializedName("satuan1")
    @Expose
    private String satuan1;
    @SerializedName("satuan2")
    @Expose
    private String satuan2;
    @SerializedName("satuan3")
    @Expose
    private String satuan3;
    @SerializedName("satuan4")
    @Expose
    private String satuan4;
    @SerializedName("sat_tur2")
    @Expose
    private String satTur2;
    @SerializedName("sat_tur3")
    @Expose
    private String satTur3;
    @SerializedName("sat_tur4")
    @Expose
    private String satTur4;
    @SerializedName("kapasitas2")
    @Expose
    private Integer kapasitas2;
    @SerializedName("kapasitas3")
    @Expose
    private Integer kapasitas3;
    @SerializedName("kapasitas4")
    @Expose
    private Integer kapasitas4;
    @SerializedName("harga_bl")
    @Expose
    private Double hargaBl;
    @SerializedName("harga_jl")
    @Expose
    private Integer hargaJl;
    @SerializedName("harga_jl2")
    @Expose
    private Integer hargaJl2;
    @SerializedName("harga_jl3")
    @Expose
    private Integer hargaJl3;
    @SerializedName("harga_jl4")
    @Expose
    private Integer hargaJl4;
    @SerializedName("qty_min2")
    @Expose
    private Integer qtyMin2;
    @SerializedName("qty_min3")
    @Expose
    private Integer qtyMin3;
    @SerializedName("qty_min4")
    @Expose
    private Integer qtyMin4;
    @SerializedName("gambar")
    @Expose
    private String gambar;

    public String getKdBrg() {
        return kdBrg;
    }

    public void setKdBrg(String kdBrg) {
        this.kdBrg = kdBrg;
    }

    public String getKdKatAndroid() {
        return kdKatAndroid;
    }

    public void setKdKatAndroid(String kdKatAndroid) {
        this.kdKatAndroid = kdKatAndroid;
    }

    public String getJnsBrg() {
        return jnsBrg;
    }

    public void setJnsBrg(String jnsBrg) {
        this.jnsBrg = jnsBrg;
    }

    public String getNmBrg() {
        return nmBrg;
    }

    public void setNmBrg(String nmBrg) {
        this.nmBrg = nmBrg;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getSatuan1() {
        return satuan1;
    }

    public void setSatuan1(String satuan1) {
        this.satuan1 = satuan1;
    }

    public String getSatuan2() {
        return satuan2;
    }

    public void setSatuan2(String satuan2) {
        this.satuan2 = satuan2;
    }

    public String getSatuan3() {
        return satuan3;
    }

    public void setSatuan3(String satuan3) {
        this.satuan3 = satuan3;
    }

    public String getSatuan4() {
        return satuan4;
    }

    public void setSatuan4(String satuan4) {
        this.satuan4 = satuan4;
    }

    public String getSatTur2() {
        return satTur2;
    }

    public void setSatTur2(String satTur2) {
        this.satTur2 = satTur2;
    }

    public String getSatTur3() {
        return satTur3;
    }

    public void setSatTur3(String satTur3) {
        this.satTur3 = satTur3;
    }

    public String getSatTur4() {
        return satTur4;
    }

    public void setSatTur4(String satTur4) {
        this.satTur4 = satTur4;
    }

    public Integer getKapasitas2() {
        return kapasitas2;
    }

    public void setKapasitas2(Integer kapasitas2) {
        this.kapasitas2 = kapasitas2;
    }

    public Integer getKapasitas3() {
        return kapasitas3;
    }

    public void setKapasitas3(Integer kapasitas3) {
        this.kapasitas3 = kapasitas3;
    }

    public Integer getKapasitas4() {
        return kapasitas4;
    }

    public void setKapasitas4(Integer kapasitas4) {
        this.kapasitas4 = kapasitas4;
    }

    public Double getHargaBl() {
        return hargaBl;
    }

    public void setHargaBl(Double hargaBl) {
        this.hargaBl = hargaBl;
    }

    public Integer getHargaJl() {
        return hargaJl;
    }

    public void setHargaJl(Integer hargaJl) {
        this.hargaJl = hargaJl;
    }

    public Integer getHargaJl2() {
        return hargaJl2;
    }

    public void setHargaJl2(Integer hargaJl2) {
        this.hargaJl2 = hargaJl2;
    }

    public Integer getHargaJl3() {
        return hargaJl3;
    }

    public void setHargaJl3(Integer hargaJl3) {
        this.hargaJl3 = hargaJl3;
    }

    public Integer getHargaJl4() {
        return hargaJl4;
    }

    public void setHargaJl4(Integer hargaJl4) {
        this.hargaJl4 = hargaJl4;
    }

    public Integer getQtyMin2() {
        return qtyMin2;
    }

    public void setQtyMin2(Integer qtyMin2) {
        this.qtyMin2 = qtyMin2;
    }

    public Integer getQtyMin3() {
        return qtyMin3;
    }

    public void setQtyMin3(Integer qtyMin3) {
        this.qtyMin3 = qtyMin3;
    }

    public Integer getQtyMin4() {
        return qtyMin4;
    }

    public void setQtyMin4(Integer qtyMin4) {
        this.qtyMin4 = qtyMin4;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


}
