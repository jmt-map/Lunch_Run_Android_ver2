package com.example.lunchrun;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MetaInfo {
    private static String nativeKey = " a5;0fcc6893b4821a8da2de70579cb920";
    private static String adminKey = "27f10765ae7a54ecb11f0c397f81e236";
    private static String restKey ="0020cc55bc997c846f5414c238d655b2";
    private static String appaKey = "dLyxtvPJkmENwwmDjCLcrR7YjZc=";
    public static String getNativeKey() {
        return nativeKey;
    }

    public static  String getAdminKey() {
        return adminKey;
    }

    public static  String getRestKey() {
        return restKey;
    }

    private static String getAppKey(){
        return appaKey;
    }
    public static String getSigneture(Context context){
        PackageManager pm = context.getPackageManager();
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for(int i = 0; i < packageInfo.signatures.length; i++){
                Signature signature = packageInfo.signatures[i];
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }

        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
