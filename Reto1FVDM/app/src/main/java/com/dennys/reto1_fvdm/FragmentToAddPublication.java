package com.dennys.reto1_fvdm;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentToAddPublication extends Fragment implements FragmentToShowMap.OnMapsListener{

    private FragmentToShowMap fragmentToShowMap;

    private OnCreatePublicationListener listener = null;

    private Button btnLocation, btnAddPublication, btnInitDate, btnEndDate;

    private TextView txtNameEvent;
    private TextView txtAddressEvent;

    private ArrayList<Publication> posts;


    private Publication objPublication;

    public FragmentToAddPublication() {

    }

    public void setListener(OnCreatePublicationListener listener) {
        this.listener=listener;
    }

    public static FragmentToAddPublication newInstance() {
        FragmentToAddPublication fragment = new FragmentToAddPublication();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_add_publication, container, false);
        txtNameEvent = view.findViewById(R.id.nameEvent);
        txtAddressEvent = view.findViewById(R.id.textDir);
        btnInitDate = view.findViewById(R.id.inicioBtn);
        btnEndDate = view.findViewById(R.id.finalBtn);

        fragmentToShowMap = new FragmentToShowMap();
        fragmentToShowMap.setListener(this);

        btnLocation = view.findViewById(R.id.locationBtn);
        btnAddPublication = view.findViewById(R.id.addBtn);

        objPublication = new Publication();
        btnInitDate.setOnClickListener(this::defineStartDate);
        btnEndDate.setOnClickListener(this::defineEndDate);

        btnLocation.setOnClickListener(
                v -> {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.fragmentContainer,fragmentToShowMap);
                    transaction.hide(this);
                    transaction.commit();
                }
        );
        btnAddPublication.setOnClickListener( v -> {

            objPublication.setNamePublication(txtNameEvent.getText().toString());
            objPublication.setNameBusiness("");
            objPublication.setUriImageBusiness("");

            objPublication.setInitDatePublication(btnInitDate.getText().toString());
            objPublication.setEndDatePublication(btnEndDate.getText().toString());

            objPublication.setLocationPublication(txtAddressEvent.getText().toString());
            listener.onCreateBtn(objPublication);


        });
        return view;
    }

    @Override
    public void onMaps(String dir) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragmentToShowMap);
        transaction.show(this);
        transaction.commit();
        txtAddressEvent.setText(dir);
    }

    public interface OnCreatePublicationListener{
        void onCreateBtn(Publication objPublication);
    }
    private void defineStartDate(View view) {
        showDatePicker(date->{
            btnInitDate.setText(formatDate(date));
        });
    }

    private String formatDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(new Date(date));
    }

    private void defineEndDate(View view) {
        showDatePicker(date -> {
            btnEndDate.setText(formatDate(date));
        });
    }
    private void savePublications() {
        Gson gson = new Gson();
        String json = gson.toJson(posts);
        //Local storage
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MainActivity", getActivity().MODE_PRIVATE);
        sharedPreferences.edit()
                .putString("posts", json)
                .apply();

    }
    private void showDatePicker(DateDialogFragment.OnDateSelectedListener listener) {
        DateDialogFragment dialog = new DateDialogFragment();
        dialog.setListener(listener);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }
}