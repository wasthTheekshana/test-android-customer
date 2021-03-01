package com.theekshana.codefestexamcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theekshana.codefestexamcustomer.Holder.ProductHolder;
import com.theekshana.codefestexamcustomer.Model.Products;

public class DeatilNewsMap extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView productlistvie;
    StorageReference storageRef;
    ;
    FirebaseStorage storage;
    private StorageReference mStorageRef;
    private FirestoreRecyclerAdapter<Products, ProductHolder> fsFirestoreRecyclerAdapter;



    public String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatil_news_map);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        Bundle bundle = getIntent().getExtras();
      String Name = bundle.getString("auth_name");
        Query query = db.collection("Products");
        FirestoreRecyclerOptions<Products> options = new FirestoreRecyclerOptions.Builder<Products>().setQuery(query,Products.class).build();
        //create adapter
        productlistvie = (RecyclerView)findViewById(R.id.newsRecycleViews);
        productlistvie.setLayoutManager(new LinearLayoutManager(this));
        fsFirestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Products, ProductHolder>(options) {

            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_feed,parent,false);
                productlistvie = view.findViewById(R.id.newsRecycleViews);
                return new ProductHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Products model) {
                holder.titles.setText(model.getProName());
                holder.diss.setText(model.getProDis());
                holder.prices.setText(model.getProPrice()+"");
                storageRef.child(model.getProImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(DeatilNewsMap.this).load(uri).into(holder.images);
                    }
                });
            }
        };

        //set adapter to reyclciew
        productlistvie.setAdapter(fsFirestoreRecyclerAdapter);
    }
    public String getName() {
        return Name;
    }
    @Override
    protected void onStart() {
        super.onStart();
        fsFirestoreRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fsFirestoreRecyclerAdapter.stopListening();
    }
}