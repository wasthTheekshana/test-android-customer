package com.theekshana.codefestexamcustomer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theekshana.codefestexamcustomer.Model.Customer;

public class RegisterForm extends AppCompatActivity {


    RadioGroup radioGroup;
    RadioButton male,female,other;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;

    EditText name,nic,addres,email,telepone,dlicNumber,v_category;
    TextView nameView;
    Button saveImage,SaveCustomer;
    ImageView d_photo;
    private int CODE_DRIVER =1001;

    Uri driver_Photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        storageRef= FirebaseStorage.getInstance().getReference();
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.others);

        name = findViewById(R.id.cusNAme);
        email = findViewById(R.id.CusEmail);
        telepone = findViewById(R.id.CusMobile);
        nic = findViewById(R.id.CusNic);
        nameView = findViewById(R.id.cusHeadline);
        d_photo = findViewById(R.id.imageView2);
        saveImage = findViewById(R.id.selectImage);
        SaveCustomer = findViewById(R.id.register);

        Bundle bundle = getIntent().getExtras();
        String PNAme = bundle.getString("auth_name");
        String PEmail = bundle.getString("auth_email");
        String PauithId = bundle.getString("auth_id");

        name.setText(PNAme);
        email.setText(PEmail);


        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filec=new Intent();
                filec.setAction(Intent.ACTION_GET_CONTENT);
                filec.setType("image/*");
                startActivityForResult(Intent.createChooser(filec,"Select Driver Photo..."),CODE_DRIVER);

            }
        });

        SaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname =name.getText().toString();
                String fEmail =email.getText().toString();
                String ftelepohne =email.getText().toString();
                String fnic = nic.getText().toString();

                String DriverImagePath="CustomerImage"+fnic+".png";
                storageRef.child("CustomerImage/"+DriverImagePath).putFile(driver_Photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(RegisterForm.this, "Driver Image Uplaod", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterForm.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                Customer customer = new Customer(fname,fEmail,ftelepohne,fnic,PauithId,null,1);

                db.collection("Customer").add(customer).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(RegisterForm.this, "Rider Registerd Successfully", Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(RegisterForm.this,MainActivity.class);

                        i.putExtra("riderName", fname + "");
                        i.putExtra("riderEmail", fEmail+ "");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(RegisterForm.this, "PLease Try Again Somthing Wrong!!", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CODE_DRIVER){

            if(resultCode==RESULT_OK){

                driver_Photo=data.getData();
                Picasso.with(RegisterForm.this).load(driver_Photo).into(d_photo);

            }
        }
    }
}