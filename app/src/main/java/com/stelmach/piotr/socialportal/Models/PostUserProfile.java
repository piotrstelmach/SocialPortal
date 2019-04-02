package com.stelmach.piotr.socialportal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostUserProfile {

    @SerializedName("skills")
    @Expose
    private List<String> skills = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String user;
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
    @SerializedName("experience")
    @Expose
    private List<UserExperience> experience = null;
    @SerializedName("education")
    @Expose
    private List<UserEducation> education = null;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserExperience> getExperience() {
        return experience;
    }

    public void setExperience(List<UserExperience> experience) {
        this.experience = experience;
    }

    public List<UserEducation> getEducation() {
        return education;
    }

    public void setEducation(List<UserEducation> education) {
        this.education = education;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "skills=" + skills +
                ", id='" + id + '\'' +
                ", user=" + user +
                ", handle='" + handle + '\'' +
                ", company='" + company + '\'' +
                ", website='" + website + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", experience=" + experience +
                ", education=" + education +
                ", date='" + date + '\'' +
                ", v=" + v +
                '}';
    }
}
