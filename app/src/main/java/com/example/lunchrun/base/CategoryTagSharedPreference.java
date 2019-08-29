package com.example.lunchrun.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CategoryTagSharedPreference {
    private static Context context;
    private static final String PREF_CATEGORY = "category";
    private static final String PREF_TAG = "tag";

    public static void setContext(Context _context){
        context = _context;
    }

    public static Context getContext(){
        return context;
    }

    public static void setCategoryList(ArrayList<String> list){
        if( context==null) return;

        Log.d("CATEGORY SP SAVED", list.toString());

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        JSONArray arr = new JSONArray();
        arr.put("카테고리 선택");
        for(int i=0; i<list.size(); i++){
            arr.put(list.get(i));
        }
        if( !list.isEmpty()){
            editor.putString(PREF_CATEGORY, arr.toString());
        }else{
            editor.putString(PREF_CATEGORY, null);
        }

        editor.apply();
    }

    public static ArrayList<String> getCategoryList(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String res = prefs.getString(PREF_CATEGORY, null);
        ArrayList<String> list = new ArrayList<String>();
        if (res != null) {
            try {
                JSONArray arr = new JSONArray(res);
                for (int i = 0; i < arr.length(); i++) {
                    String url = arr.optString(i);
                    list.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("CATEGORY SP GET", list.toString());

        return list;
    }



    public static void setTagList(ArrayList<String> list){
        if( context==null) return;

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        Log.d("TAG SP SAVED", list.toString());

        JSONArray arr = new JSONArray();
        arr.put("태그 선택");
        for(int i=0; i<list.size(); i++){
            arr.put(list.get(i));
        }
        if( !list.isEmpty()){
            editor.putString(PREF_TAG, arr.toString());
        }else{
            editor.putString(PREF_TAG, null);
        }

        editor.apply();
    }

    public static ArrayList<String> getTagList(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String res = prefs.getString(PREF_TAG, null);
        ArrayList<String> list = new ArrayList<String>();
        if (res != null) {
            try {
                JSONArray arr = new JSONArray(res);
                for (int i = 0; i < arr.length(); i++) {
                    String url = arr.optString(i);
                    list.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("TAG SP GET", list.toString());

        return list;
    }
}
