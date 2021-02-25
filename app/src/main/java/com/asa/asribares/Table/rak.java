package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class rak {

    @SerializedName("KD_RAK")
    @Expose
    private String kDRAK;
    @SerializedName("NM_RAK")
    @Expose
    private String nMRAK;
    @SerializedName("KETERANGAN")
    @Expose
    private String kETERANGAN;

    public String getKDRAK() {
        return kDRAK;
    }

    public void setKDRAK(String kDRAK) {
        this.kDRAK = kDRAK;
    }

    public String getNMRAK() {
        return nMRAK;
    }

    public void setNMRAK(String nMRAK) {
        this.nMRAK = nMRAK;
    }

    public String getKETERANGAN() {
        return kETERANGAN;
    }

    public void setKETERANGAN(String kETERANGAN) {
        this.kETERANGAN = kETERANGAN;
    }


}
