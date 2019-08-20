package com.example.lunchrun.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.lunchrun.model.Restaurant;

import java.util.List;

public class RestaurantListViewAdapter extends BaseAdapter {
    private List<Restaurant> list;
    private LayoutInflater inflater;
    public RestaurantListViewAdapter(Context context, List<Restaurant> list){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list=list;
    }
    @Override
    public int getCount() {
        if(list!= null)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if(list!= null)
            return list.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
            return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private class ViewHoleder{

    }
}

