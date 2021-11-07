package com.dennys.reto1_fvdm;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentToMainMap extends Fragment {

    private OnMainMapListener listener;

    private GoogleMap mMap;

    private Marker marker;

    private ArrayList<Publication> publications;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            loadMap();
        }
    };

    public void loadMap(){
        Geocoder coder = new Geocoder(getView().getContext());
        List<Address> addresses;
        LatLng p1 = null;
        try{
            for(int i = 0;i<publications.size();i++){
                addresses = coder.getFromLocationName(publications.get(i).getLocationPublication(), 5);
                if(addresses!=null){
                    Address location = addresses.get(0);

                    p1 = new LatLng(location.getLatitude(), location.getLongitude());
                    marker = mMap.addMarker(new MarkerOptions().position(p1));
                    marker.setTitle(publications.get(i).getNameBusiness());
                    marker.setSnippet(publications.get(i).getNamePublication());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FragmentToMainMap newInstance() {
        FragmentToMainMap fragment = new FragmentToMainMap();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_main_map, container, false);


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

    public void setPublications(ArrayList<Publication> publications) {
        this.publications = publications;
    }

    public interface OnMainMapListener{
        void onGeneralMap();
    }

    public void setListener(OnMainMapListener listener){
        this.listener = listener;
    }
}