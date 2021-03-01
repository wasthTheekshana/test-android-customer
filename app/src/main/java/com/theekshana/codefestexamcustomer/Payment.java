package com.theekshana.codefestexamcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theekshana.codefestexamcustomer.Model.PaymentsMOdel;

public class Payment extends AppCompatActivity {

    private String Pname;
    private String PEmail;

    TextView title, price;
    EditText cardnme,cardNum,cardCvv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button addbtn;

    CustomerHome deatilNewsMap;
   public Payment(){
        deatilNewsMap = new CustomerHome();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        title = findViewById(R.id.Ordername);
        price = findViewById(R.id.price);
        cardnme = findViewById(R.id.cdname);
        cardNum = findViewById(R.id.cdnum);
        cardCvv = findViewById(R.id.cdcvv);
        addbtn = findViewById(R.id.button5);

        String name = deatilNewsMap.PNAme;

        Bundle bundle = getIntent().getExtras();
        Pname = bundle.getString("title");
        PEmail = bundle.getString("price");

        title.setText(Pname);
        price.setText(PEmail);
        Toast.makeText(Payment.this, "Name"+ name, Toast.LENGTH_LONG).show();
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Payment.this, "PaymentsMOdel", Toast.LENGTH_SHORT).show();
                PaymentsMOdel paymentsMOdel = new PaymentsMOdel(title.getText().toString(),price.getText().toString(),name,cardnme.getText().toString(),cardnme.getText().toString(),cardCvv.getText().toString());
                db.collection("Payment").add(paymentsMOdel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String customerId=documentReference.getId();
                        Toast.makeText(Payment.this, "Payment Successfuly", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Payment.this, "Customer Not Registerd !", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



    }
}