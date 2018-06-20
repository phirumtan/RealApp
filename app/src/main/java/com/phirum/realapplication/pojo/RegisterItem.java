package com.phirum.realapplication.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterItem implements Serializable {

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    private final static long serialVersionUID = -3179285423858192568L;

    public RegisterItem withEmail(String email) {
        this.email = email;
        return this;
    }

    public RegisterItem withPassword(String password) {
        this.password = password;
        return this;
    }

}