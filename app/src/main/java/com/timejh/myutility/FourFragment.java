package com.timejh.myutility;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends Fragment implements OnMapReadyCallback {

    private MainActivity mainActivity;
    private LocationManager manager;

    private GoogleMap map;
    private SupportMapFragment mapFragment;

    public FourFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
        manager = mainActivity.manager;
    }

    @Override
    public void onResume() {
        super.onResume();
        //리스너 등록
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                3000, // 통지사이의 최소 시간간격 (miliSecond) // 업데이트 간격
                10, // 통지사이의 최소 변경거리 (m)
                locationListener);
        // 정보제공자로 네트워크프로바이더 등록
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                3000, // 통지사이의 최소 시간간격 (miliSecond)
                10, // 통지사이의 최소 변경거리 (m)
                locationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        //리스너 해제
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        manager.removeUpdates(locationListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();  //위치제공자

            LatLng myPosition = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(myPosition).title("I'm here now"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 10));
        }

        // Provider(GPS Sensor, Network State)가 변경되었을떄
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider(GPS Sensor, Network State)가 사용 할 수 없다가 사용 가능 할 떄
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider(GPS Sensor, Network State)가 사용 불가능 해 졌을 때
        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
