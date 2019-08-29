package com.example.lunchrun.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lunchrun.MetaInfo;
import com.example.lunchrun.R;
import com.example.lunchrun.base.CategoryTagSharedPreference;
import com.example.lunchrun.base.UserInfo;
import com.example.lunchrun.model.Restaurant;
import com.example.lunchrun.model.RestaurantCategory;
import com.example.lunchrun.model.RestaurantRequestBody;
import com.example.lunchrun.model.Tag;
import com.example.lunchrun.retrofit.ApiClient;
import com.example.lunchrun.retrofit.RestaurantApiService;
import com.example.lunchrun.retrofit.UserApiService;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements AbsListView.OnScrollListener {
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
    private List<String> categoriesStr;
    private List<String> tags;
    private List<Restaurant> restaurants;
    private List<Integer> pageList;
    private ProgressBar progressBar;

    private Spinner categorySpinner;
    private Spinner tagSpinner;


    private boolean lastItemVisibleFlag = false;
    private boolean mLockListView = false;
    private int page=0;
    private int curCategory =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_review_menu, container, false);

        progressBar = v.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        listView = v.findViewById(R.id.restaurant_list_view);
        listView.setOnScrollListener(this);

        categorySpinner = v.findViewById(R.id.category_spinner);
        tagSpinner = v.findViewById(R.id.tag_spinner);

        apiService = ApiClient.getClient().create(RestaurantApiService.class);

        Log.d("REVIEW - TOKEN", UserInfo.getToken());

        // 식당 리스트
        pageList = new ArrayList<>();
        restaurants = new ArrayList<>();

        // splash 할 때, 미리 category & tag 받아와서 SP에 저장해두고 !!!!!!!!!
        if(CategoryTagSharedPreference.getCategoryList()!=null){
            ArrayAdapter categorySpinnerAdapter = new ArrayAdapter(v.getContext(),R.layout.support_simple_spinner_dropdown_item, CategoryTagSharedPreference.getCategoryList());
            categorySpinner.setAdapter(categorySpinnerAdapter);

            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pageList.clear();
                    restaurants.clear();
                    curCategory = position;
                    requestRestaurantList(0);
                    Log.d("CATEGORY CLICKED", String.valueOf(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        if(CategoryTagSharedPreference.getTagList()!=null){
            ArrayAdapter tagSpinnerAdapter = new ArrayAdapter(v.getContext(), R.layout.support_simple_spinner_dropdown_item, CategoryTagSharedPreference.getTagList());
            tagSpinner.setAdapter(tagSpinnerAdapter);
            tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("TAG CLICKED", String.valueOf(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        requestRestaurantList(0);
        return v;
    }

    private void requestRestaurantList(int pageNum) {
        mLockListView = true;
        if( pageList.contains(pageNum))
            return;

        Log.d("CATEGORY ID = ", String.valueOf(curCategory));
        pageList.add(pageNum);

        Integer ct = curCategory;
        if(curCategory==0)
            ct=null;

        Call<List<Restaurant>> call = apiService.getRestaurantList(UserInfo.getToken(),ct, pageNum);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d("REST", "CODE +" +response.code());
                if(response.body()!=null){
                    restaurants.addAll( response.body());
                    Log.d("REST", "List +" +response.body().toString());

                    if( listViewAdapter!=null)
                        listViewAdapter.notifyDataSetChanged();

                    else{
                        initRestaurantListView(restaurants);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });
        progressBar.setVisibility(View.GONE);
        mLockListView = false;
    }


    private void initRestaurantListView(List<Restaurant> list){
        listViewAdapter = new RestaurantListViewAdapter(v.getContext(),list);
        listView.setAdapter(listViewAdapter);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            // 화면이 바닦에 닿을때 처리
            // 로딩중을 알리는 프로그레스바를 보인다.
            progressBar.setVisibility(View.VISIBLE);
            Log.d("SCROLL", "BOTTOM");
            // 다음 데이터를 불러온다.
            page++;
            requestRestaurantList(page);
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);

        if(firstVisibleItem ==0 && view.getChildAt(0) !=null && view.getChildAt(0).getTop() ==0){
            Log.d("SCROLL", "TOP");
//            initRestaurantListView(restaurants);
        }
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
