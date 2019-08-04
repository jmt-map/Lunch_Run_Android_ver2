package com.example.lunchrun.base;

import com.example.lunchrun.model.User;

public class UserInfo {
    private static User user;

    public static User getUser(){
        return user;
    }

    public static void setUser(User _user){
        user = _user;
    }
}
