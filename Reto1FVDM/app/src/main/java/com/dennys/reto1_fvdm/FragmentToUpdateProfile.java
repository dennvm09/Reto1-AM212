package com.dennys.reto1_fvdm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

public class FragmentToUpdateProfile extends Fragment implements ImageOption.OnChoiceListener {


    private OnEditProfileListener listener = null;

    private EditText nameBusiness, descriptionBusiness;
    private ImageView photoBusiness;
    private Button saveBtn;

    private File file;

    private Profile newProfile;

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    public FragmentToUpdateProfile() {
        // Required empty public constructor
    }

    public static FragmentToUpdateProfile newInstance() {
        FragmentToUpdateProfile fragment = new FragmentToUpdateProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_update_profile, container, false);



        nameBusiness = view.findViewById(R.id.lblName);
        descriptionBusiness = view.findViewById(R.id.txtDescription);
        photoBusiness = view.findViewById(R.id.imgBussines);
        saveBtn = view.findViewById(R.id.btnCreateBusiness);

        nameBusiness.setText(newProfile.getNameBusiness());
        descriptionBusiness.setText(newProfile.getDescriptionBusiness());

        String uri = newProfile.getUriImageBusiness();
        if(uri != null){
            Bitmap bitmap = BitmapFactory.decodeFile(newProfile.getUriImageBusiness());
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4,bitmap.getHeight()/4,true);
            photoBusiness.setImageBitmap(thumbnail);
        }
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onCameraResult);
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onGalleryResult);


        saveBtn.setOnClickListener(v->{
            String name = nameBusiness.getText().toString();
            String description = descriptionBusiness.getText().toString();
            newProfile.setNameBusiness(name);
            newProfile.setDescriptionBusiness(description);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(this);
            ft.commit();
            listener.onEdit(newProfile);
            saveProfile(newProfile);

        });

        photoBusiness.setOnClickListener(this::openChoice);


        return view;
    }

    private void saveProfile(Profile p) {
        Gson gson = new Gson();
        String json = gson.toJson(p);
        //Local storage
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MainActivity",getActivity().MODE_PRIVATE);
        sharedPreferences.edit()
                .putString("profile",json)
                .apply();
    }


    public void onGalleryResult(ActivityResult result){
        if(result.getResultCode()==getActivity().RESULT_OK){
            Uri uri = result.getData().getData();
            photoBusiness.setImageURI(uri);
            newProfile.setUriImageBusiness(UtilDomi.getPath(getContext(),uri));
        }
    }

    public void onCameraResult(ActivityResult result){
        if(result.getResultCode()==getActivity().RESULT_OK){

            //Foto completa
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4,bitmap.getHeight()/4,true);
            photoBusiness.setImageBitmap(thumbnail);
            Uri uri = FileProvider.getUriForFile(getContext(),getContext().getPackageName(),file);
            newProfile.setUriImageBusiness(file.getPath());

        }else if(result.getResultCode()==getActivity().RESULT_CANCELED){
            Toast.makeText(getContext(),"Operación cancelada", Toast.LENGTH_LONG).show();
        }
    }

    public void openCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(getContext().getExternalFilesDir(null)+"/photo.png");
        Uri uri = FileProvider.getUriForFile(getContext(),getContext().getPackageName(),file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        cameraLauncher.launch(intent);
    }

    public void setListener(OnEditProfileListener listener) {
        this.listener=listener;
    }

    public void openChoice(View view){
        ImageOption dialog = ImageOption.newInstance();
        dialog.setListener(this);
        dialog.show(getActivity().getSupportFragmentManager(),"dialog");
    }

    @Override
    public void onChoice(int choice) {
        if(choice == 0){
            openCamera(getView());
        }else if(choice==1){
            openGallery(getView());
        }
    }

    private void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    public interface OnEditProfileListener{
        void onEdit(Profile profile);
    }

    public void setProfile(Profile profile) {
        this.newProfile = profile;
    }
}