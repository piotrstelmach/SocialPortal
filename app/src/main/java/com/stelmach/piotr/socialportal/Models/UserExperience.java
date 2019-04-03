package com.stelmach.piotr.socialportal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserExperience {

    @SerializedName("current")
    @Expose
    private Boolean current;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("description")
    @Expose
    private String description;

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserExperience(Boolean current, String id, String title, String company, String location, String from, String to, String description) {
        this.current = current;
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public UserExperience(Boolean current, String title, String company, String location, String from, String to, String description) {
        this.current = current;
        this.title = title;
        this.company = company;
        this.location = location;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserExperience{" +
                "current=" + current +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
