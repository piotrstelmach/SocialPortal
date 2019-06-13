package com.stelmach.piotr.socialportal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostToSend {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user")
    @Expose
    private CurrentUser user;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }
}
