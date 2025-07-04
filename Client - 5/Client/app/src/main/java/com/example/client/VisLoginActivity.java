package com.example.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class VisLoginActivity extends AppCompatActivity {

    EditText loginEmail;
    Button login1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis_login);


        loginEmail = findViewById(R.id.login_Vemail);
        login1 = findViewById(R.id.login_button);

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail()) {

                } else {
                    checkUser();

                }
            }
        });
    }

    public Boolean validateEmail() {
        String val = loginEmail.getText().toString();
        if (val.isEmpty()) {
            loginEmail.setError("Email Address cannot be empty");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }


    public void checkUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(VisLoginActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        String userLoginEmail = loginEmail.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors");
        Query checkUserDatabase = reference.orderByChild("vis_email").equalTo(userLoginEmail);


        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    System.out.println("Snapshot in login:" + snapshot.toString());
                    String userLEmail = loginEmail.getText().toString().trim().toLowerCase();
                    loginEmail.setError(null);
                    String userFromDB = snapshot.child("vis_email").getValue(String.class);
                    String userFromDB1 = snapshot.child("Visitors").getValue(String.class);

                    System.out.println("userFromDB Login" + userFromDB);
                    System.out.println("userFromDB Login 1 :" + userFromDB1);
                    if (!Objects.equals(userFromDB, userLEmail))
                    // if(Objects.equals(userFromDB, userLoginEmail))
                    {
                        Constants.vis_email = userLEmail;
                        System.out.println("Workshere" + snapshot.child("vis_branch").getValue(String.class));
                        loginEmail.setError(null);
                        Intent i = new Intent(getApplicationContext(), VisInfoActivity.class);

                        dialog.dismiss();

                        startActivity(i);

                    } else {
                        loginEmail.setError("Invalid Credentials");
                        loginEmail.requestFocus();
                    }
                } else {
                    loginEmail.setError("Visitor's Email does not exist");
                    loginEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}