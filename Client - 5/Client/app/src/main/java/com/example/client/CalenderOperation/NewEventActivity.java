package com.example.client.CalenderOperation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.client.Constants;

import java.util.Objects;
import com.example.client.CalenderOperation.CalenderActivity.*;

public class NewEventActivity extends AppCompatActivity {


    private String month,year;
    private TextView date1;
    private Button save;
    private String date10, subject2, class2,type2,Student2,Content2;
    public static String time4;
    private Spinner subject;
    private Spinner class1;
    private Spinner time1;
    private  Spinner Type;
    private EditText No_Student,Content1;

    private String subject5[], class5[], time5[],Type5[];

    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;

    //   private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    // private  String selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        subject = findViewById(R.id.Subject);
        class1 = findViewById(R.id.Class);
        date1 = findViewById(R.id.date1);
        time1 = findViewById(R.id.time1);
        save = findViewById(R.id.save);
        Type = findViewById(R.id.TH_PR_type);
        No_Student = findViewById(R.id.No_Student);
        Content1 = findViewById(R.id.Contents);

        month = CalenderActivity.selectedMonth;
        year = CalenderActivity.selectedYear;


        subject5 = new String[]{"ST-6S505", "CSCL-6P501", "DS-6S207", "AJP-6S504","ADBMS-6P502","CN-6P403","OS-6S404","Java-6S403"};
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subject5);
        subject.setAdapter(adapter2);

        class5 = new String[]{"1st Year", "2nd Year", "3rd Year"};
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, class5);
        class1.setAdapter(adapter3);

        time5 = new String[]{"10:30-11:30", "11:30-12:30", "12:30-01:30", "02:00-03:00", "03:00-04:00", "04:00-05:00", "05:00-06:00"};
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, time5);
        time1.setAdapter(adapter1);


        Type5 = new String[]{"TH","PR"};
        adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Type5);
        Type.setAdapter(adapter4);

        date1.setText(CalenderActivity.selectedText.getText());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subject2 = subject.getSelectedItem().toString();
                class2 = class1.getSelectedItem().toString();
                date10 = date1.getText().toString();
                time4 = time1.getSelectedItem().toString();
                type2 = Type.getSelectedItem().toString();
                Student2 = No_Student.getText().toString();
                Content2 = Content1.getText().toString();

                String status5 = "Pending";



                    DatabaseReference zonesRef = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors");

                    zonesRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println("Count: " + dataSnapshot.getChildrenCount());
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                String vis_email = snapshot.child("vis_email").getValue(String.class);

                                String vis_name = snapshot.child("vis_name").getValue(String.class);

                                if (Objects.equals(vis_email, Constants.vis_email)) {

                                    databaseReference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors").child(vis_name).child("Calender").child(date10).child(time4);

                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {

                                                //   Toast.makeText(NewEventActivity.this, "Event already created", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Event event = new Event(subject2, class2, date10, time4, status5,type2,Student2,Content2);
                                                FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors").child(vis_name).child("Calender").child(date10).child(time4).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(NewEventActivity.this, subject2 + class2 + date10 + time4+type2+Student2+Content2, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                finish();

            }
        });
    }


}