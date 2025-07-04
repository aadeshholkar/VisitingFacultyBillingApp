package com.example.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeFragment extends Fragment {

    TextView profileName, profileEmail, profilePhoneNumber, profileQualification, profileSubject,profileYear,profileBranch;
    //Bank's Info
    TextView profileAcc,profileBank,profileBBranch,profileIFSC;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        profileName = view.findViewById(R.id.detailname);
        profileEmail = view.findViewById(R.id.detailemail);
        profilePhoneNumber = view.findViewById(R.id.detailnumber);
        profileQualification = view.findViewById(R.id.detailqualification);
        profileSubject = view.findViewById(R.id.detailsubject);

        profileYear = view.findViewById(R.id.detailyear);
        profileBranch = view.findViewById(R.id.Visitor_branch_name);
        profileAcc = view.findViewById(R.id.detail_acc_no);
        profileBank = view.findViewById(R.id.detail_bank_name);
        profileBBranch = view.findViewById(R.id.detail_bank_branch);
        profileIFSC = view.findViewById(R.id.detail_ifsc);


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
               // Toast.makeText(VisAboutActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });



        return  view;
    }
}