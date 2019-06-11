package com.stelmach.piotr.socialportal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateProfile {
    @SerializedName("skills")
    @Expose
    private String skills;
    @SerializedName("handle")
    @Expose
    private String handle;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("status")
    @Expose
    private String status;

    public String getSkills() {
        return skills;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
