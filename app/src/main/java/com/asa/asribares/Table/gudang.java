package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class gudang {

    @SerializedName("GUDANG")
    @Expose
    private String gUDANG;

    public String getGUDANG() {
        return gUDANG;
    }

    public void setGUDANG(String gUDANG) {
        this.gUDANG = gUDANG;
    }

}
