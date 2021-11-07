package com.dennys.reto1_fvdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentToProfile.OnEditButtonListener, FragmentToPublications.OnAddPublicationListener, FragmentToMainMap.OnMainMapListener {

    private FragmentToProfile fragmentToProfile;
    private FragmentToPublications fragmentToPublications;
    private FragmentToMainMap fragmentToMainMap;

    private BottomNavigationView navigator;

    private FragmentToAddPublication fragmentToAddPublication;

    private FragmentToUpdateProfile fragmentToUpdateProfile;

    private Profile profile;
    private ArrayList<Publication> publications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        },1);

        fragmentToProfile = FragmentToProfile.newInstance();
        fragmentToProfile.setListener(this);

        fragmentToPublications = FragmentToPublications.newInstance();

        fragmentToAddPublication = FragmentToAddPublication.newInstance();
        fragmentToPublications.setListener(this);

        fragmentToUpdateProfile = FragmentToUpdateProfile.newInstance();

        fragmentToMainMap = FragmentToMainMap.newInstance();
        fragmentToMainMap.setListener(this);

        navigator = findViewById(R.id.navigator);

        loadProfile();
        loadPublications();



        showFragment(fragmentToProfile);

        navigator.setOnItemSelectedListener(menuItem->{

            if(menuItem.getItemId()==R.id.menuProfile){
                swapFragment(fragmentToProfile,0);
            }else if(menuItem.getItemId()==R.id.menuPubli){
                swapFragment(fragmentToPublications,0);
            }else if(menuItem.getItemId()==R.id.menuMap){
                swapFragment(fragmentToMainMap,0);
            }
            return true;
        });

    }

    private void loadProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("MainActivity",MODE_PRIVATE);
        String json = sharedPreferences.getString("profile","NO_OBJ");
        if(!json.equals("NO_OBJ")){
            Gson gson = new Gson();
            Profile p = gson.fromJson(json,Profile.class);
            profile = p;
            fragmentToProfile.setProfile(p);
        }
    }

    private void loadPublications(){
        SharedPreferences sharedPreferences = getSharedPreferences("MainActivity",MODE_PRIVATE);
        String json = sharedPreferences.getString("posts","NO_OBJ");
        if(!json.equals("NO_OBJ")){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Publication>>(){}.getType();
            ArrayList<Publication> ps = gson.fromJson(json,type);
            publications = ps;
            fragmentToPublications.setPublications(ps);

            for(int i = 0;i<publications.size();i++){
                Log.e(">>>>>>>",publications.get(i).getLocationPublication());
            }
            fragmentToMainMap.setPublications(publications);
        }
    }

    public void showFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer,f);
        transaction.commit();
    }
    public void addFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragmentContainer,f);
        transaction.commit();
    }

    @Override
    public void swapFragment(Fragment f, int opt) {
        if(opt == 0){
            showFragment(f);
        }
        else{
            addFragment(f);
        }
    }

    @Override
    public void onEditProfile(Profile profile) {
        this.profile = profile;
        fragmentToUpdateProfile.setProfile(profile);
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public void onAdd(Publication publication) {
        publication.setNameBusiness(profile.getNameBusiness());
        publication.setUriImageBusiness(profile.getUriImageBusiness());
        fragmentToPublications.addPublication(publication);
        savePublications();
        swapFragment(fragmentToPublications,0);
    }
    private void savePublications() {
        publications = fragmentToPublications.getPublications();
        fragmentToMainMap.setPublications(publications);
        Gson gson = new Gson();
        String json = gson.toJson(publications);

        SharedPreferences sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
        sharedPreferences.edit()
                .putString("posts", json)
                .apply();
    }

    @Override
    public void onGeneralMap() {
        swapFragment(fragmentToMainMap,0);
    }
}