package com.bov.vitali.training.data.net.response;

import com.bov.vitali.training.data.model.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("data")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}