package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class orderBeli {

    @SerializedName("NO_ENT")
    @Expose
    private String nOENT;
    @SerializedName("TANGGAL")
    @Expose
    private String tANGGAL;
    @SerializedName("KD_SUPPL")
    @Expose
    private String kDSUPPL;
    @SerializedName("NM_SUPPL")
    @Expose
    private String nMSUPPL;
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

    public String getKDSUPPL() {
        return kDSUPPL;
    }

    public void setKDSUPPL(String kDSUPPL) {
        this.kDSUPPL = kDSUPPL;
    }

    public String getNMSUPPL() {
        return nMSUPPL;
    }

    public void setNMSUPPL(String nMSUPPL) {
        this.nMSUPPL = nMSUPPL;
    }

    public String getTOTAL() {
        return tOTAL;
    }

    public void setTOTAL(String tOTAL) {
        this.tOTAL = tOTAL;
    }

}
