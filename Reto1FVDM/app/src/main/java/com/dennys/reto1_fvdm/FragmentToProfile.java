package com.dennys.reto1_fvdm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentToProfile extends Fragment implements FragmentToUpdateProfile.OnEditProfileListener {


    private Profile profile;

    private ImageView image;
    private TextView nameBusiness, descriptionBusiness;
    private ImageButton btnUpdate;

    private FragmentToUpdateProfile fragmentToUpdateProfile;

    private OnEditButtonListener listener = null;

    private FragmentTransaction ft;

    public FragmentToProfile() {

        profile = new Profile("Nombre del negocio", "Descripción del negocio", null);

    }

    public static FragmentToProfile newInstance() {
        FragmentToProfile fragment = new FragmentToProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnEditButtonListener listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_to_profile, container, false);

        image = view.findViewById(R.id.imgPhotoBusiness);
        nameBusiness = view.findViewById(R.id.lblNameBusiness);
        descriptionBusiness = view.findViewById(R.id.txtDescriptionBusiness);
        btnUpdate = view.findViewById(R.id.btnUpdateBusiness);
        fragmentToUpdateProfile = FragmentToUpdateProfile.newInstance();
        fragmentToUpdateProfile.setProfile(profile);
        fragmentToUpdateProfile.setListener(this);


        nameBusiness.setText(profile.getNameBusiness());
        descriptionBusiness.setText(profile.getDescriptionBusiness());
        if (profile.getUriImageBusiness() != null){
            String uri = profile.getUriImageBusiness();
            Bitmap bitmap = BitmapFactory.decodeFile(uri);
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4,bitmap.getHeight()/4,true);
            image.setImageBitmap(thumbnail);
        }

        ft = getActivity().getSupportFragmentManager().beginTransaction();

        btnUpdate.setOnClickListener(v->{
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.hide(this);
            ft.commit();
            listener.swapFragment(fragmentToUpdateProfile, 1);
        });

        return view;
    }

    @Override
    public void onEdit(Profile profile) {
        this.profile = profile;
        nameBusiness.setText(profile.getNameBusiness());
        descriptionBusiness.setText(profile.getDescriptionBusiness());
        if(profile.getUriImageBusiness()!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(profile.getUriImageBusiness());
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4,bitmap.getHeight()/4,true);
            image.setImageBitmap(thumbnail);
        }
        listener.onEditProfile(profile);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.show(this);
        ft.commit();
    }

    public interface OnEditButtonListener{
        void swapFragment(Fragment f, int opt);
        void onEditProfile(Profile profile);
    }

    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}