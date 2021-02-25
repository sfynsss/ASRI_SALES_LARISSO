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
    @SerializedName("ALM_CUST")
    @Expose
    private String aLMCUST;
    @SerializedName("activation_token")
    @Expose
    private String activationToken;

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


}
