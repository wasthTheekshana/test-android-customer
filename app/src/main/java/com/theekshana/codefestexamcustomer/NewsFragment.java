package com.theekshana.codefestexamcustomer;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theekshana.codefestexamcustomer.Holder.newsHolder;
import com.theekshana.codefestexamcustomer.Model.NewsModel;


public class NewsFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter<NewsModel, newsHolder> fsFirestoreRecyclerAdapter;
    RecyclerView productlistvie;
    private StorageReference mStorageRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Query query = db.collection("Products");
        FirestoreRecyclerOptions<NewsModel> options = new FirestoreRecyclerOptions.Builder<NewsModel>().setQuery(query,NewsModel.class).build();
        //create adapter
        fsFirestoreRecyclerAdapter = new FirestoreRecyclerAdapter<NewsModel,newsHolder>(options) {

            @NonNull
            @Override
            public newsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfedd_list,parent,false);
               productlistvie = view.findViewById(R.id.newsRecycle);
                return new newsHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull newsHolder holder, int position, @NonNull NewsModel model) {
                holder.title.setText(model.getNewsTitle());
                holder.dis.setText(model.getNewsDescription());
                holder.title.setText(model.getNewslocation()+"");
                holder.time.setText(model.getNewstime()+"");
                mStorageRef.child(model.getNewsImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).load(uri).into(holder.image);
                    }
                });
            }
        };

        //set adapter to reyclciew
        productlistvie.setAdapter(fsFirestoreRecyclerAdapter);
        return view;
    }
}