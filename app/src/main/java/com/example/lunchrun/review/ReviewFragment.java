package com.example.lunchrun.review;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lunchrun.MetaInfo;
import com.example.lunchrun.R;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;


public class ReviewFragment extends Fragment {
    private View v;
    private MapView mapView;
    private MapReverseGeoCoder reverseGeoCoder;
    private MapReverseGeoCoder.ReverseGeoCodingResultListener reverseGeoCodingResultListener;
    private String curAdr;
    private MapPoint curPoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map_menu, container, false);

        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        curPoint = MapPoint.mapPointWithGeoCoord(37.0, 127.0);
        //addMarker(curPoint);
        return v;
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
