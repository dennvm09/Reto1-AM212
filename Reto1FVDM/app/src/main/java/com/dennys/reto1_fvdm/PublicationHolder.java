package com.dennys.reto1_fvdm;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PublicationHolder extends RecyclerView.ViewHolder {

    private TextView publicationBusiness;
    private TextView publicationlocation;
    private TextView publicationEndDate;
    private TextView publicationInitDate;
    private TextView publicationName;
    private ImageView publicationImage;

    public PublicationHolder(View itemView) {
        super(itemView);
        publicationBusiness = itemView.findViewById(R.id.txtBusinessName);
        publicationlocation = itemView.findViewById(R.id.txtUbication);
        publicationInitDate = itemView.findViewById(R.id.txtPublicationInitDate);
        publicationEndDate = itemView.findViewById(R.id.postend);
        publicationName = itemView.findViewById(R.id.txtPublicationName);
        publicationImage = itemView.findViewById(R.id.imgPublication);
    }

    public TextView getPublicationBusiness() {
        return publicationBusiness;
    }

    public TextView getPublicationName() {
        return publicationName;
    }

    public TextView getPublicationlocation() {
        return publicationlocation;
    }

    public TextView getPublicationInitDate() {
        return publicationInitDate;
    }

    public TextView getPublicationEndDate() {
        return publicationEndDate;
    }

    public ImageView getPublicationImage() {
        return publicationImage;
    }
}
