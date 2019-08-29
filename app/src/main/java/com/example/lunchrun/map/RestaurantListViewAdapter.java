package com.example.lunchrun.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lunchrun.R;
import com.example.lunchrun.base.CategoryTagSharedPreference;
import com.example.lunchrun.model.Restaurant;
import com.example.lunchrun.model.RestaurantCategory;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListViewAdapter extends BaseAdapter {
    private List<Restaurant> list;
    private LayoutInflater inflater;
    private  ArrayList<String> categories;

    public RestaurantListViewAdapter(Context context, List<Restaurant> list){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list=list;
        this.categories = CategoryTagSharedPreference.getCategoryList();
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
        ViewHolder holder = null;
        if(convertView ==null){
            convertView = inflater.inflate(R.layout.item_restaurant,parent,false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.name);
            holder.category = convertView.findViewById(R.id.category);
            holder.rate = convertView.findViewById(R.id.rate);

            convertView.setTag(holder);
        }

        else{
            holder= (ViewHolder)convertView.getTag();
        }

        Restaurant rest = list.get(position);
        holder.name.setText(rest.getName());

        String c = categories.get(rest.getCategory_id());
        holder.category.setText(c);

        String r = "평점 4.2";
        holder.rate.setText(r);
        return convertView;
    }

    private class ViewHolder{
        TextView name;
        TextView category;
        TextView rate;
    }
}

