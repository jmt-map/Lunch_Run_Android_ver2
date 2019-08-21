package com.example.lunchrun.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lunchrun.MetaInfo;
import com.example.lunchrun.R;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.Restaurant;
import com.example.lunchrun.model.RestaurantCategory;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.RestaurantApiService;
import com.example.lunchrun.retrofit.UserApiService;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment {
    private View v;
    private MapView mapView;
    private MapReverseGeoCoder reverseGeoCoder;
    private MapReverseGeoCoder.ReverseGeoCodingResultListener reverseGeoCodingResultListener;

    private String curAdr;
    private MapPoint curPoint;
    private RestaurantApiService apiService;

    private ListView listView;
    private RestaurantListViewAdapter listViewAdapter;
    private List<RestaurantCategory> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map_menu, container, false);
/*
        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        curPoint = MapPoint.mapPointWithGeoCoord(37.0, 127.0);
*/
//        getAddress(curPoint);
//        addMarker(curPoint);

        listView = v.findViewById(R.id.restaurant_list_view);

        apiService=  ApiClient.getClient().create(RestaurantApiService.class);

        Call<List<RestaurantCategory>> categoryCall = apiService.getRestaurantCategoryList(UserInfo.getToken());
        categoryCall.enqueue(new Callback<List<RestaurantCategory>>() {
            @Override
            public void onResponse(Call<List<RestaurantCategory>> call, Response<List<RestaurantCategory>> response) {
                Log.d("REST CATEGORY", "CODE"+response.code());
                categories = response.body();
                Log.d("REST CATEGORY", categories.toString());

            }

            @Override
            public void onFailure(Call<List<RestaurantCategory>> call, Throwable t) {

            }
        });

        Call<List<Restaurant>> call = apiService.getRestaurantList(UserInfo.getToken(),0, 0);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d("REST", "CODE +" +response.code());
                if(response.body()!=null){
                    List<Restaurant> restList = response.body();
                    Log.d("REST", "List +" +restList.toString());
                    setRestaurantListView(restList);
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });
        return v;
    }

    private void setRestaurantListView(List<Restaurant> list){
        listViewAdapter = new RestaurantListViewAdapter(v.getContext(),list, categories );
        listView.setAdapter(listViewAdapter);
    }

    private void getAddress(MapPoint mapPoint){
        reverseGeoCodingResultListener = new MapReverseGeoCoder.ReverseGeoCodingResultListener() {
            @Override
            public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
                curAdr = s;
                Log.d("ADR",s);
            }

            @Override
            public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
                Log.e("ADR", "ERRORS");
            }
        };
        reverseGeoCoder = new MapReverseGeoCoder(MetaInfo.getAdminKey(), mapPoint, reverseGeoCodingResultListener,getActivity());
        reverseGeoCoder.startFindingAddress();
    }

    private void addMarker(MapPoint mapPoint){
        if(mapView==null)
            return;

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);
    }


}
