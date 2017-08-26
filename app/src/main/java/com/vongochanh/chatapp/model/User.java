package com.vongochanh.chatapp.model;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("emailUser")
    private String emailuser;
    @SerializedName("userIconLink")
    private String userIconLink;

    public User(String emailuser, String userIconLink) {
        this.emailuser = emailuser;
        this.userIconLink = userIconLink;
    }

    public String getEmailUser() {
        return emailuser;
    }

    public String getUserIconLink() {
        return userIconLink;
    }

    public void setEmailUser(String emailuser) {
        this.emailuser = emailuser;
    }

    public void setUserIconLink(String userIconLink) {
        this.userIconLink = userIconLink;
    }
}
