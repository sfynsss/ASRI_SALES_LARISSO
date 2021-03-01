package com.asa.asribares.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class customer {

    @SerializedName("KD_CUST")
    @Expose
    private String kDCUST;
    @SerializedName("NM_CUST")
    @Expose
    private String nMCUST;
    @SerializedName("E_MAIL")
    @Expose
    private String eMAIL;
    @SerializedName("ALM_CUST")
    @Expose
    private String aLMCUST;
    @SerializedName("activation_token")
    @Expose
    private String activationToken;
    @SerializedName("otoritas")
    @Expose
    private String otoritas;

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

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getALMCUST() {
        return aLMCUST;
    }

    public void setALMCUST(String aLMCUST) {
        this.aLMCUST = aLMCUST;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(String otoritas) {
        this.otoritas = otoritas;
    }
}
