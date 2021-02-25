package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class kunjungan {

    @SerializedName("KD_CUST")
    @Expose
    private String kDCUST;
    @SerializedName("NM_CUST")
    @Expose
    private String nMCUST;
    @SerializedName("TGL_KUNJUNGAN")
    @Expose
    private String tGLKUNJUNGAN;

    public String getKDCUST() {
        return kDCUST;
    }

    public void setKDCUST(String kDCUST) {
        this.kDCUST = kDCUST;
    }

    public String getNMCUST() {
        return nMCUST;
    }

    public void setNMCUST(String nMCUST) {
        this.nMCUST = nMCUST;
    }

    public String getTGLKUNJUNGAN() {
        return tGLKUNJUNGAN;
    }

    public void setTGLKUNJUNGAN(String tGLKUNJUNGAN) {
        this.tGLKUNJUNGAN = tGLKUNJUNGAN;
    }

}
