package com.dennys.reto1_fvdm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PublicationAdapter extends RecyclerView.Adapter<PublicationHolder> {

    private ArrayList<Publication> publications;

    public PublicationAdapter(){
        publications = new ArrayList<>();
    }

    @NonNull
    @Override
    public PublicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_publication_holder,parent,false);

        PublicationHolder holder = new PublicationHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationHolder holder, int position) {
        Publication p = publications.get(position);
        holder.getPublicationName().setText(p.getNamePublication());
        holder.getPublicationlocation().setText(p.getLocationPublication());
        holder.getPublicationInitDate().setText(p.getInitDatePublication());
        holder.getPublicationEndDate().setText(p.getEndDatePublication());
        holder.getPublicationBusiness().setText(p.getNameBusiness());

        if(p.getUriImageBusiness()!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(p.getUriImageBusiness());
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4,bitmap.getHeight()/4,true);
            holder.getPublicationImage().setImageBitmap(thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

    public ArrayList<Publication> getPublication() {
        return publications;
    }

    public void addPublication(Publication publication){
        publications.add(publication);
    }

    public void setPublications(ArrayList<Publication> publications) {
        this.publications = publications;
    }

}
