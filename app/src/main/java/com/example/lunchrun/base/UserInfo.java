package com.example.lunchrun.base;

import com.example.lunchrun.model.User;

public class UserInfo {
    private static User user;
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserInfo.token = token;
    }


    public static User getUser(){
        return user;
    }

    public static void setUser(User _user){
        user = _user;
    }
}
