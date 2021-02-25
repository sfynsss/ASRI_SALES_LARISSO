package com.asa.asribares.Response;

import com.asa.asribares.Table.user;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    @Expose
    private user user;

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

}