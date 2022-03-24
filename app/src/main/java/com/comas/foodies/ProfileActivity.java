package com.comas.foodies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintInfo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

   TextView fullName,email,phone;
   FirebaseAuth fAuth;
   FirebaseFirestore fStore;
   String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        phone = findViewById(R.id.profilePhone);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("Ta","Error:"+e.getMessage());
                }else {
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));
                }
            }
            });


    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
    }
}