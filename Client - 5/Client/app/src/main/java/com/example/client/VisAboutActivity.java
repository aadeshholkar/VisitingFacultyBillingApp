package com.example.client;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class VisAboutActivity extends AppCompatActivity {
//personal's Info
    TextView profileName, profileEmail, profilePhoneNumber, profileQualification, profileSubject,profileYear,profileBranch;
    //Bank's Info
    TextView profileAcc,profileBank,profileBBranch,profileIFSC;
    String visname;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis_about);



        profileName = findViewById(R.id.detailname);
        profileEmail = findViewById(R.id.detailemail);
        profilePhoneNumber = findViewById(R.id.detailnumber);
        profileQualification = findViewById(R.id.detailqualification);
        profileSubject = findViewById(R.id.detailsubject);

        profileYear = findViewById(R.id.detailyear);
        profileBranch = findViewById(R.id.Visitor_branch_name);
        profileAcc = findViewById(R.id.detail_acc_no);
        profileBank = findViewById(R.id.detail_bank_name);
        profileBBranch = findViewById(R.id.detail_bank_branch);
        profileIFSC = findViewById(R.id.detail_ifsc);

        AlertDialog.Builder builder = new AlertDialog.Builder(VisAboutActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();


        String VemailFromDB = Constants.vis_email;//loginEmail.getText().toString();
        System.out.println("VemailFromDB:" + VemailFromDB);
        DatabaseReference zonesRef = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors");
        dialog.show();
        zonesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Count: "+ dataSnapshot.getChildrenCount());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String vis_email = snapshot.child("vis_email").getValue().toString();
                    String vis_qualification = snapshot.child("vis_qualification").getValue().toString();
                    String vis_name = snapshot.child("vis_name").getValue().toString();
                    String vis_number = snapshot.child("vis_pnumber").getValue().toString();
                    String vis_subject = snapshot.child("vis_subject").getValue().toString();
                    String vis_year = snapshot.child("vis_year").getValue().toString();
                    String vis_branch = snapshot.child("vis_Subject_branch").getValue().toString();
                    String vis_acc = snapshot.child("vis_acc_no").getValue().toString();
                    String vis_bank = snapshot.child("vis_bank_name").getValue().toString();
                    String vis_Bbrach = snapshot.child("vis_branch").getValue().toString();
                    String vis_ifsc = snapshot.child("vis_ifsc").getValue().toString();

                    System.out.println("vis_email"+vis_email+"\n");
                    if (Objects.equals(vis_email, Constants.vis_email)) {
                        System.out.println(" Finally: " + vis_email);
                        profileQualification.setText(vis_qualification);
                        profileName.setText(vis_name);
                        visname = vis_name;
                        profileEmail.setText(vis_email);
                        profilePhoneNumber.setText(vis_number);
                        profileSubject.setText(vis_subject);
                        profileYear.setText(vis_year);
                        profileBranch.setText(vis_branch);
                        profileAcc.setText(vis_acc);
                        profileBank.setText(vis_bank);
                        profileBBranch.setText(vis_Bbrach);
                        profileIFSC.setText(vis_ifsc);

                        dialog.dismiss();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VisAboutActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });

    }
}