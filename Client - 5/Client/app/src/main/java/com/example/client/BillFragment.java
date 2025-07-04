package com.example.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

public class BillFragment extends Fragment {
    TextView approvedCount, rejectedCount, pendingCount;
    String visitor_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_bill, container, false);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Visitors");





     return view;
    }
}