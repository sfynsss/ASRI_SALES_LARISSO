package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class order {

    @SerializedName("NO_ENT")
    @Expose
    private String nOENT;
    @SerializedName("TANGGAL")
    @Expose
    private String tANGGAL;
    @SerializedName("NM_CUST")
    @Expose
    private String nMCUST;
    @SerializedName("TOTAL")
    @Expose
    private String tOTAL;

    public String getNOENT() {
        return nOENT;
    }

    public void setNOENT(String nOENT) {
        this.nOENT = nOENT;
    }

    public String getTANGGAL() {
        return tANGGAL;
    }

    public void setTANGGAL(String tANGGAL) {
        this.tANGGAL = tANGGAL;
    }

    public String getNMCUST() {
        return nMCUST;
    }

    public void setNMCUST(String nMCUST) {
        this.nMCUST = nMCUST;
    }

    public String getTOTAL() {
        return tOTAL;
    }

    public void setTOTAL(String tOTAL) {
        this.tOTAL = tOTAL;
    }

}
