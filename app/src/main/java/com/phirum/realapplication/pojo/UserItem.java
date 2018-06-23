package com.phirum.realapplication.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.LinkedList;

public class UserItem implements Serializable {

    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("per_page")
    @Expose
    public int perPage;
    @SerializedName("total")
    @Expose
    public int total;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("data")
    @Expose
    public LinkedList<Datum> data = null;
    private final static long serialVersionUID = -9171962494747942208L;

    public UserItem withPage(int page) {
        this.page = page;
        return this;
    }

    public UserItem withPerPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public UserItem withTotal(int total) {
        this.total = total;
        return this;
    }

    public UserItem withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public UserItem withData(LinkedList<Datum> data) {
        this.data = data;
        return this;
    }

}