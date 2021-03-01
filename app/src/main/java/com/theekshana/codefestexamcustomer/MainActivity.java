package com.theekshana.codefestexamcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.theekshana.codefestexamcustomer.Model.Customer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    SignInButton singButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int RC_SIGN_IN = 100;
    String FCMToken = null;
    CollectionReference AdminCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singButton = findViewById(R.id.signIn);
        singButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createSignInIntent();

            }
        });
        initFCM();
    }

    private void initFCM() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        FCMToken = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.FCMToken, token);
//                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, FCMToken, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, "Sign In Ok", Toast.LENGTH_SHORT).show();

                String Uemail = user.getEmail();
                String Uname = user.getDisplayName();
                String UuthId = user.getUid();
                db.collection("Customer").whereEqualTo("email",Uemail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        List<Customer> customerList = queryDocumentSnapshots.toObjects(Customer.class);
                        if(customerList.size() != 0){
                            if(customerList.get(0).getStatus() == 1) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);


                                Customer customer = customerList.get(0);
                                updateFCMtokeb(documentSnapshot.getId());
                                String fcm = customer.getFcmId();
                                Intent intent = new Intent(MainActivity.this, CustomerHome.class);
                                intent.putExtra("auth_name", Uname + "");
                                intent.putExtra("auth_email", Uemail + "");
                                intent.putExtra("auth_Telephone", customer.getTelephone() + "");
                                intent.putExtra("auth_userId", customer.getGoogleId() + "");
                                intent.putExtra("auth_id", UuthId + "");
                                intent.putExtra("auth_fcmID", FCMToken+"");
                                intent.putExtra("userDocId", documentSnapshot.getId());

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Your not active user", LENGTH_LONG).show();
                            }
                        }else {
                            Intent intent = new Intent(MainActivity.this,RegisterForm.class);
                            intent.putExtra("auth_name", Uname+"");
                            intent.putExtra("auth_email", Uemail+"");
                            intent.putExtra("auth_id", UuthId+"");

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            } else {
                Toast.makeText(this, "Something Went Worng!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateFCMtokeb(String documentId) {

        db.collection("Customer").document(documentId).update("FcmId",FCMToken).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
    // [END auth_fui_result]


    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
//                        .setLogo(R.drawable.my_great_logo)      // Set logo drawable
//                        .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }
}