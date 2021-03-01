package com.theekshana.codefestexamcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;
import com.theekshana.codefestexamcustomer.Holder.newsHolder;
import com.theekshana.codefestexamcustomer.Model.NewsModel;

public class CustomerHome extends AppCompatActivity {
    String userId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String PNAme;
    public String PEmail;
    private String pAutId;

    String riderAppStatus ="";
    private double newPrice;
    private String uid;
    private String telephone;
    private String docId;
    private String UfcmID;

    ChipNavigationBar chipNavigationBar;
Button logout, tickect,inqury;
    RecyclerView productlistvie;
    StorageReference storageRef;
;
    FirebaseStorage storage;
    private FirestoreRecyclerAdapter<NewsModel, newsHolder> fsFirestoreRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        Bundle bundle =getIntent().getExtras();
        PNAme = bundle.getString("auth_name");
        PEmail = bundle.getString("auth_email");
        pAutId = bundle.getString("auth_id");
        docId = bundle.getString("userDocId");
        telephone = bundle.getString("auth_Telephone");
        uid = bundle.getString("auth_userId");
        UfcmID = bundle.getString("auth_fcmID");
        tickect = findViewById(R.id.button4);
        inqury = findViewById(R.id.inqury);
        logout = findViewById(R.id.logout);

        tickect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,DeatilNewsMap.class);
                intent.putExtra("auth_name",PNAme);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        inqury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this,TicketInqury.class);
                intent.putExtra("DocId",PNAme);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        productlistvie = (RecyclerView)findViewById(R.id.newsRecycleViews);
        productlistvie.setLayoutManager(new LinearLayoutManager(this));

        Query query = db.collection("News");
        FirestoreRecyclerOptions<NewsModel> options = new FirestoreRecyclerOptions.Builder<NewsModel>().setQuery(query,NewsModel.class).build();
        //create adapter
        fsFirestoreRecyclerAdapter = new FirestoreRecyclerAdapter<NewsModel, newsHolder>(options) {

            @NonNull
            @Override
            public newsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfedd_list,parent,false);

                return new newsHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull newsHolder holder, int position, @NonNull NewsModel model) {
                holder.title.setText(model.getNewsTitle());
                holder.dis.setText(model.getNewsDescription());
                holder.loc.setText(model.getNewslocation()+"");
                holder.time.setText(model.getNewstime()+"");

                storageRef.child(model.getNewsImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(CustomerHome.this).load(uri).into(holder.image);
                    }
                });
            }
        };

        //set adapter to reyclciew
        productlistvie.setAdapter(fsFirestoreRecyclerAdapter);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START auth_fui_signout]
                AuthUI.getInstance()
                        .signOut(CustomerHome.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(CustomerHome.this, "Log Out Succesfully!", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(CustomerHome.this,MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);
                                finish();
                            }
                        });
                // [END auth_fui_signout]
            }
        });
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