package com.theekshana.codefestexamcustomer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;


public class TickeHome extends Fragment {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    ImageView imageProduct;
    EditText name,des,location,time;
    private StorageReference mStorageRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticke_home, container, false);
        return view;


//        name = view.findViewById(R.id.newsTitle);
//        des = view.findViewById(R.id.newsDis);
//        location = view.findViewById(R.id.newLoca);
//        imageProduct = view.findViewById(R.id.newTime);



    }
}