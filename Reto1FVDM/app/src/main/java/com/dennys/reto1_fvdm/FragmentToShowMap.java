package com.dennys.reto1_fvdm;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FragmentToShowMap extends Fragment {

    private OnMapsListener listener;

    private GoogleMap mMap;

    private Marker marker;

    private String dir;

    private Button btnConf;

    private GoogleMap.OnMapLongClickListener listenerClick = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(@NonNull LatLng latLng) {
            if(marker==null){
                marker = mMap.addMarker(new MarkerOptions().position(latLng));
            }
            else{
                marker.setPosition(latLng);
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
            Geocoder g = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> ads = g.getFromLocation(latLng.latitude, latLng.longitude, 1);
                dir = ads.get(0).getAddressLine(0);
                marker.setTitle(dir);

                Log.e(">>>>>",dir);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {


            mMap = googleMap;

            mMap.setOnMapLongClickListener(listenerClick);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_show_map, container, false);

        btnConf = view.findViewById(R.id.btnAprove);

        btnConf.setOnClickListener(v->{
            listener.onMaps(dir);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(this);
            transaction.commit();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public interface OnMapsListener{
        void onMaps(String dir);
    }

    public void setListener(OnMapsListener listener){
        this.listener = listener;
    }
}