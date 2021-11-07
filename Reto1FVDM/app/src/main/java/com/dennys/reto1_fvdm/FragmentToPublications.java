package com.dennys.reto1_fvdm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentToPublications extends Fragment implements FragmentToAddPublication.OnCreatePublicationListener {

    private FragmentToAddPublication fragmentToAddPublication;

    private OnAddPublicationListener listener = null;

    private Button btnAddNewPublications;

    private RecyclerView recyclerView;

    private PublicationAdapter adapter;

    private Button btnAddOthers;

    private ArrayList<Publication> publications;


    public FragmentToPublications() {
        // Required empty public constructor
        adapter = new PublicationAdapter();
        publications = new ArrayList<>();
    }

    public static FragmentToPublications newInstance() {
        FragmentToPublications fragment = new FragmentToPublications();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_publications, container, false);

        btnAddNewPublications = view.findViewById(R.id.btnAddPublication);
        recyclerView = view.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        btnAddOthers = view.findViewById(R.id.btnAddOther);
        btnAddOthers.setVisibility(View.INVISIBLE);

        fragmentToAddPublication = FragmentToAddPublication.newInstance();
        fragmentToAddPublication.setListener(this);

        btnAddNewPublications.setOnClickListener(v -> {
            listener.swapFragment(fragmentToAddPublication, 0);
        });
        btnAddOthers.setOnClickListener(v -> {
            listener.swapFragment(fragmentToAddPublication, 0);
        });

        if (adapter.getItemCount() > 0) {
            ScrollView scrollView2 = view.findViewById(R.id.scrollView2);
            scrollView2.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            btnAddOthers.setVisibility(View.VISIBLE);
            btnAddNewPublications.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onCreateBtn(Publication newPublication) {
        listener.onAdd(newPublication);
    }

    public interface OnAddPublicationListener {
        void onAdd(Publication publication);

        void swapFragment(Fragment f, int opt);
    }

    public void addPublication(Publication publication) {
        adapter.addPublication(publication);
        publications = adapter.getPublication();
    }

    public void setPublications(ArrayList<Publication> ps) {
        for(int i = 0; i<ps.size();i++){
            addPublication(ps.get(i));
        }
    }

    public void setListener(OnAddPublicationListener listener) {
        this.listener = listener;
    }

    public PublicationAdapter getAdapter() {
        return adapter;
    }

    public ArrayList<Publication> getPublications() {
        return publications;
    }

}