package com.stelmach.piotr.socialportal.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEducation {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("fieldofstudy")
    @Expose
    private String fieldOfStudy;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("current")
    @Expose
    private Boolean current;
    @SerializedName("description")
    @Expose
    private String description;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEducation(String school, String degree, String fieldOfStudy, String from, String to, Boolean current, String description) {
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.from = from;
        this.to = to;
        this.current = current;
        this.description = description;
    }

    public UserEducation(String id, String school, String degree, String fieldOfStudy, String from, String to, Boolean current, String description) {
        this.id = id;
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.from = from;
        this.to = to;
        this.current = current;
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserEducation{" +
                "id='" + id + '\'' +
                ", school='" + school + '\'' +
                ", degree='" + degree + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", current=" + current +
                ", description='" + description + '\'' +
                '}';
    }
}
