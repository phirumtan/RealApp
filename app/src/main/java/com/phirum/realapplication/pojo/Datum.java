package com.phirum.realapplication.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("avatar")
    @Expose
    public String avatar;

    @SerializedName("password")
    @Expose
    public String password;

    private final static long serialVersionUID = 8516407839004241957L;

    public Datum withId(int id) {
        this.id = id;
        return this;
    }

    public Datum withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Datum withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Datum withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public Datum withPassword(String password) {
        this.password = password;
        return this;
    }


}