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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import com.theekshana.codefestexamcustomer.Holder.ticketHolder;

import com.theekshana.codefestexamcustomer.Model.Tickect;

public class TicketInqury extends AppCompatActivity {

    Spinner field;
    EditText titles,disc;
    Button ticket;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference mStorageRef;
    FirebaseStorage storage;
    RecyclerView productlistvie;
    StorageReference storageRef;
    private FirestoreRecyclerAdapter<Tickect, ticketHolder> fsFirestoreRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_inqury);

        titles = findViewById(R.id.proName);
        disc = findViewById(R.id.proDisc);
        ticket = findViewById(R.id.proAdd);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        String docId = getIntent().getStringExtra("DocId");

        Query query = db.collection("Tickets");
        FirestoreRecyclerOptions<Tickect> options = new FirestoreRecyclerOptions.Builder<Tickect>().setQuery(query,Tickect.class).build();

        productlistvie = (RecyclerView)findViewById(R.id.ticketRecyview);
        productlistvie.setLayoutManager(new LinearLayoutManager(this));
        fsFirestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Tickect, ticketHolder>(options) {

            @NonNull
            @Override
            public ticketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_feed,parent,false);
                productlistvie = view.findViewById(R.id.newsRecycleViews);
                return new ticketHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull ticketHolder holder, int position, @NonNull Tickect model) {
                holder.titles.setText(model.getNAme());
                holder.dis.setText(model.getBody());
                holder.complain.setText(model.getComplain()+"");

            }
        };

        //set adapter to reyclciew
        productlistvie.setAdapter(fsFirestoreRecyclerAdapter);


   field = findViewById(R.id.spinner2);
        String[] foptions = {"Information Request", "Complain", "Compliment"};
        ArrayAdapter<String> option_adapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, foptions);
        option_adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        field.setAdapter(option_adapt);

       String newfiled =  field.getSelectedItem().toString();

        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsName = titles.getText().toString();
                String newdes = disc.getText().toString();


                Tickect tickect = new Tickect(newsName,newdes,newfiled,docId);
                CollectionReference PCollectionReference = db.collection("Tickets");
                PCollectionReference.add(tickect).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"News Add Succefuuly",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error :"+e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

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