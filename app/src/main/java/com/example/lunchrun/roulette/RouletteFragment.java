package com.example.lunchrun.roulette;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lunchrun.R;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.Restaurant;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.RestaurantApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouletteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_roulette_menu, container, false);

    }
}
