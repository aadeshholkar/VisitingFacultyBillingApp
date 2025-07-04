package com.example.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    TextView approvedCount, rejectedCount, pendingCount;
    String visitor_name;

    // Get a reference to your Firebase database
    //   VisitorDataRetrieve visitorDataRetrieve;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/");
    DatabaseReference myRef = database.getReference("Visitors");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        approvedCount = findViewById(R.id.approvedNo);
        rejectedCount = findViewById(R.id.rejectedNo);
        pendingCount = findViewById(R.id.pendingNo);

        String VemailFromDB = Constants.vis_email;//loginEmail.getText().toString();
        System.out.println("VemailFromDB:" + VemailFromDB);

        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int accpeted_count = 0;
                int rejected_count = 0;
                int pending_count = 0;

                for (DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {
                    String visitorName = nameSnapshot.getKey();
                    System.out.println("name-----" + visitorName);
                    System.out.println("name2-------" + visitor_name);
                    String vis_email = nameSnapshot.child("vis_email").getValue().toString();

                    if (Objects.equals(vis_email, Constants.vis_email))
                    {
                        // Retrieve the data for the visitor with the accepted text and the specified name
                        for (DataSnapshot calendarSnapshot : nameSnapshot.getChildren()) {


                                    for (DataSnapshot dateSnapshot : calendarSnapshot.getChildren()) {

                                        for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                                            // Retrieve the specific data for the accepted visitor

                                            String date2 = timeSnapshot.child("date2").getValue(String.class);

                                            String text = timeSnapshot.child("text").getValue(String.class);

                                            //              FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors").child("Calender1").child(year).child(month).child(visitorName).child(text);
                                            if (text != null && text.equals("Accepted")) {
                                                accpeted_count++;
                                                System.out.println("Count ---" + accpeted_count);
                                            }
                                            if (text != null && text.equals("Pending")) {
                                                pending_count++;
                                                System.out.println("Count ---" + pending_count);
                                            }
                                            if (text != null && text.equals("Rejected")) {
                                                rejected_count++;
                                                System.out.println("Count ---" + rejected_count);
                                            }
                                        }

                                    }
                                }

                    }
                }

                System.out.println("Number of accepted texts: " + accpeted_count);
                System.out.println("Number of pending texts: " + pending_count);
                System.out.println("Number of rejected texts: " + rejected_count);

                approvedCount.setText("" + accpeted_count);
                rejectedCount.setText("" + rejected_count);
                pendingCount.setText("" + pending_count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

}