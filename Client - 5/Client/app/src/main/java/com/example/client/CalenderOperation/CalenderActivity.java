package com.example.client.CalenderOperation;

import static com.example.client.R.id.calender_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.client.Constants;

public class CalenderActivity extends AppCompatActivity {


    public static Event event;
    @SuppressLint("StaticFieldLeak")
    public static EventAdapter adapter;
    private Spinner subject;
    String status;
    private Spinner class10;
    private Spinner time10;
    private static DatabaseReference databaseReference;
    private CalendarView calendarView;
    private Button newbutton;
    private static RecyclerView recyclerView;
    private String selectedDate;
    public static TextView selectedText;

    private TextView date10;

    private static ArrayList<Event> eventList;
    private EventAdapter eventAdapter;
    private Button submit, pending;

    private CardView recard;
    public static String selectedMonth;
    public static String selectedYear;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);


        calendarView = findViewById(calender_view);
        newbutton = findViewById(R.id.newbutton);
        recyclerView = findViewById(R.id.events_list);
        selectedText = findViewById(R.id.selecteddate);
        recard = findViewById(R.id.reCard);

        pending = findViewById(R.id.pending);


        subject = findViewById(R.id.subject2);
        class10 = findViewById(R.id.class2);
        date10 = findViewById(R.id.date2);
        time10 = findViewById(R.id.time2);


        eventList = new ArrayList<Event>();
        adapter = new EventAdapter(eventList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        newbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalenderActivity.this, NewEventActivity.class);
                startActivity(intent);
            }
        });

        List<Event> pendingCards = new ArrayList<>();
        for (Event card1 : eventList) {
            if (card1.isPending()) {
                pendingCards.add(card1);
            }
        }

        DatabaseReference zonesRef = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors");

        zonesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Count: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String vis_email = snapshot.child("vis_email").getValue(String.class);
                    String vis_name = snapshot.child("vis_name").getValue(String.class);
                    System.out.println("vis_email   " + vis_email);

                    if (Objects.equals(vis_email, Constants.vis_email)) {
                        System.out.println(" Finally: " + vis_email);

                        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                                //  Date selectedDate = new Date(year-1900,month,dayOfMonth);

                                eventList.clear();
                                recyclerView.setVisibility(View.INVISIBLE);

                                selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;

                                selectedMonth = String.valueOf(month + 1);
                                selectedYear = String.valueOf(year);


                                selectedText.setText(selectedDate);

                                //   String pathValue = (String) Constants.vis_email;
                                databaseReference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors").child(vis_name).child("Calender");

                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            //      displayEvents(selectedDate);


                                            eventList.clear();
                                            System.out.println("displayEvents: " + selectedDate);
                                            String pathvalue = Constants.vis_email;

                                            databaseReference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors").child(vis_name).child("Calender");

                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                        String date = dataSnapshot.getKey();
                                                        if (date.equals(selectedDate)) {

                                                            System.out.println("Hellooo");
                                                            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                                                                String subject2 = eventSnapshot.child("subject").getValue(String.class);
                                                                String class2 = eventSnapshot.child("class2").getValue(String.class);
                                                                String date2 = eventSnapshot.child("date2").getValue(String.class);
                                                                String time2 = eventSnapshot.child("time2").getValue(String.class);
                                                                String type2 = eventSnapshot.child("calender_t").getValue(String.class);
                                                                String Student2 = eventSnapshot.child("calender_S").getValue(String.class);
                                                                String content2 = eventSnapshot.child("cont").getValue(String.class);
                                                                status = eventSnapshot.child("text").getValue(String.class);
                                                                System.out.println("Status........................." + status);

                                                                System.out.println(subject2 + "--" + class2 + "--" + date2 + "--" + time2 + "\n");
                                                                event = new Event(subject2, class2, date2, time2, type2, Student2, content2);
                                                                eventList.add(event);
//
                                                            }
                                                            if (status.equals("Accepted") || status.equals("Rejected")) {
                                                                recyclerView.setVisibility(View.INVISIBLE);
                                                                recyclerView.setVisibility(View.GONE);
                                                                adapter = new EventAdapter(eventList);
                                                                recyclerView.setAdapter(adapter);
                                                            } else {
                                                                recyclerView.setVisibility(View.VISIBLE);
                                                                adapter = new EventAdapter(eventList);
                                                                recyclerView.setAdapter(adapter);
                                                            }

                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(CalenderActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(CalenderActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        });

                        recyclerView = findViewById(R.id.events_list);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(CalenderActivity.this, 1);
                        recyclerView.setLayoutManager(gridLayoutManager);

                        eventList = new ArrayList<>();
                        eventAdapter = new EventAdapter(eventList);
                        recyclerView.setAdapter(eventAdapter);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}