package com.example.main.VisitorOperation;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.AdminOperation.CommanActivity;
import com.example.main.AdminOperation.LoginActivity;
import com.example.main.VisitorOperation.Info2Fragment;

import com.example.main.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Year;
import java.util.StringTokenizer;

import com.example.main.VisitorOperation.MyAdapter;

public class Bill2Fragment extends Fragment {

    Button createPDF;

    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    String Visname;
boolean flag=false;

    DatabaseReference reference;

    private Spinner monthSp, yearSp;
    private String monthA[], yearA[];
    public String name4;

    private String name, month, year, month_year;
    public static String TH_amount, PR_amount;


    public static int THCount, PRCount;
    int th_cont, pr_cont, th_remun, pr_remun, total_remun, professional_tax, total;
    private TextView name1;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill2, container, false);
        Context context = getContext();

        Detail4Activity detail4Activity = new Detail4Activity();
        Bill2Fragment Bill = new Bill2Fragment();

        name1 = view.findViewById(R.id.detailname);
        Visname = MyAdapter.visitorName;
        monthSp = view.findViewById(R.id.month);
        yearSp = view.findViewById(R.id.year);

        monthA = new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, monthA);
        monthSp.setAdapter(adapter1);

        yearA = new String[]{"2020", "2021", "2022", "2023"};
        adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, yearA);
        yearSp.setAdapter(adapter2);


        createPDF = view.findViewById(R.id.createPDF);


        String targetType1 = "TH"; // or "PR" for counting PR
        String targetType2 = "PR";

        reference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors");

        reference.child(Visname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int THCount = 0;
                int PRCount = 0;
                for (DataSnapshot calendarSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dateSnapshot : calendarSnapshot.getChildren()) {
                        String date = dateSnapshot.getKey();
                        System.out.println(date + ".....date");
                        String limiter = "-";
                        StringTokenizer stringTokenizer = new StringTokenizer(date, limiter);
                        String day = stringTokenizer.nextToken();
                        String month1 = stringTokenizer.nextToken();
                        String year1 = stringTokenizer.nextToken();
                        System.out.println(month1 + "....month");
                        System.out.println(month + "..........month");
                        System.out.println(year + "..........year");
                        System.out.println(year1 + "....year");
                        int month2 = Integer.parseInt(month1);
                        month1 = show(month2);

//                        if(month1.equals(month)&& year1.equals(year)){
                        for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                            String type = timeSnapshot.child("calender_t").getValue(String.class);
                            if (type != null && type.equals(targetType1)) {
                                THCount++;
                            }
                            if (type != null && type.equals(targetType2)) {
                                PRCount++;
                            }

                        }
                        Log.d("Type Count", targetType1 + ": " + THCount);
                        Log.d("Type Count", targetType2 + ": " + PRCount);
                        th_cont = THCount;
                        pr_cont = PRCount;


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur
            }
        });




        requestPermissions(
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_GRANTED);


        createPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfDocument myPdfDocument = new PdfDocument();
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(210, 297, 1).create();
                PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
                Context context = getContext();

                name = Visname;
                month = monthSp.getSelectedItem().toString();
                year = yearSp.getSelectedItem().toString();
                System.out.println(month);
                System.out.println(year);
                month_year = month + year;


                System.out.println("2345678765432");



                reference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Visitors");

                reference.child(Visname).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int THCount = 0;
                        int PRCount = 0;
                        for (DataSnapshot calendarSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot dateSnapshot : calendarSnapshot.getChildren()) {
                                String date = dateSnapshot.getKey();
                                System.out.println(date + ".....date");
                                String limiter = "-";
                                StringTokenizer stringTokenizer = new StringTokenizer(date, limiter);
                                String day = stringTokenizer.nextToken();
                                String month1 = stringTokenizer.nextToken();
                                String year1 = stringTokenizer.nextToken();
                                System.out.println(month1 + "....month");
                                System.out.println(month + "..........month");
                                System.out.println(year + "..........year");
                                System.out.println(year1 + "....year");
                                int month2 = Integer.parseInt(month1);
                                month1 = show(month2);
                                System.out.println("mont2......"+month1);

                       if(month1.equals(month)&& year1.equals(year)){
                           System.out.println("if condtion.............");
                                for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                                    String type = timeSnapshot.child("calender_t").getValue(String.class);
                                    if (type != null && type.equals(targetType1)) {
                                        THCount++;
                                    }
                                    if (type != null && type.equals(targetType2)) {
                                        PRCount++;
                                    }

                                }
                                Log.d("Type Count", targetType1 + ": " + THCount);
                                Log.d("Type Count", targetType2 + ": " + PRCount);
                                th_cont = THCount;
                                pr_cont = PRCount;

                            }
                       else {
                           THCount = 0;
                           PRCount = 0;
                           System.out.println("else condtion/.........");
                           flag = true;
                           Toast.makeText(context,"No lecture taken in year or month",Toast.LENGTH_SHORT).show();
                       }
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that occur
                    }
                });



                reference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Salary");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        TH_amount = snapshot.child("salary_pr").getValue(String.class);
                        PR_amount = snapshot.child("salary_th").getValue(String.class);
                        System.out.println(TH_amount + ".......456789");
                        System.out.println(PR_amount + "......9876");
                        th_remun = Integer.parseInt(TH_amount);
                        pr_remun = Integer.parseInt(PR_amount);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                total_remun = (th_cont * th_remun) + (pr_remun * pr_cont);
                professional_tax = 0;
                total = total_remun + professional_tax;


                if(flag==false) {


                    Paint paint = new Paint();
                    Paint mypaint = new Paint();
                    Canvas canvas = myPage.getCanvas();
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(10);
                    canvas.drawText("Government Polytechnic Aurangabad", 25, 20, paint);
                    paint.setTextSize(6);
                    canvas.drawText("(An autonomous institute of Govt. of Maharashtra)", 50, 30, paint);
                    mypaint.setStrokeWidth(1);
                    canvas.drawLine(0, 50, 230, 50, mypaint);
                    paint.setColor(Color.rgb(0, 0, 0));
                    canvas.drawText("Visiting Faculty Bill", 75, 60, paint);
                    paint.setTextSize(5);
                    canvas.drawText("Year :" + year, 92, 65, paint);
                    paint.setTextSize(3);
                    canvas.drawLine(0, 70, 230, 70, mypaint);
                    paint.setColor(Color.rgb(0, 0, 0));
                    canvas.drawText("PROGRAMME : Diploma in computer engineering", 20, 75, paint);
                    paint.setTextSize(3);

                    Paint linePaint = new Paint();
                    linePaint.setColor(Color.BLACK);
                    linePaint.setStyle(Paint.Style.STROKE);
                    linePaint.setStrokeWidth(2);

                    Paint textPaint = new Paint();
                    textPaint.setColor(Color.BLACK);
                    textPaint.setTextSize(30);

                    Paint line = new Paint();
                    line.setStrokeWidth((float) 0.2);
                    canvas.drawLine(20, 80, 208, 80, line);

                    //canvas.drawLine(20,85,200,85,line);
                    canvas.drawLine(20, 90, 208, 90, line);
                    canvas.drawLine(20, 95, 208, 95, line);
                    canvas.drawLine(20, 100, 208, 100, line);

                    //verticals lines

                    canvas.drawLine(20, 80, 20, 100, line);
                    canvas.drawLine(30, 80, 30, 100, line);
                    canvas.drawLine(55, 80, 55, 100, line);
                    canvas.drawLine(70, 80, 70, 100, line);
                    canvas.drawLine(90, 80, 90, 100, line);
                    canvas.drawLine(113, 80, 113, 100, line);
                    canvas.drawLine(135, 80, 135, 100, line);
                    canvas.drawLine(155, 80, 155, 100, line);
                    canvas.drawLine(170, 80, 170, 100, line);
                    canvas.drawLine(190, 80, 190, 100, line);
                    canvas.drawLine(208, 80, 208, 100, line);

                    //   canvas.drawText(name4,30,95,paint);

                    canvas.drawText("Sn.", 20, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("1", 23, 94, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Visiting Faculty ", 30, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("name ", 30, 88, paint);
                    paint.setTextSize(3);
                    canvas.drawText(name, 31, 94, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Month", 55, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText(month_year, 56, 94, paint);

                    paint.setTextSize(3);
                    canvas.drawText(" TH ", 70, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("Conducted", 70, 88, paint);
                    paint.setTextSize(3);
                    canvas.drawText(String.valueOf(th_cont), 71, 94, paint);
                    paint.setTextSize(3);


                    canvas.drawText(" PR ", 90, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("Conducted", 90, 88, paint);
                    paint.setTextSize(3);
                    canvas.drawText(String.valueOf(pr_cont), 92, 94, paint);

                    paint.setTextSize(3);
                    canvas.drawText(" TH ", 113, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("Remuneration", 113, 88, paint);
                    paint.setTextSize(3);
                    canvas.drawText(String.valueOf(th_remun), 115, 94, paint);
                    paint.setTextSize(3);


                    canvas.drawText(" PR ", 135, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("Remuneration", 135, 88, paint);
                    paint.setTextSize(3);
                    canvas.drawText(String.valueOf(pr_remun), 137, 94, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Total", 158, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("", 158, 88, paint);
                    paint.setTextSize(3);
                    canvas.drawText(String.valueOf(total_remun), 161, 94, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Professional", 170, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText("Tax", 173, 88, paint);
                    paint.setTextSize(3);
                    canvas.drawText(String.valueOf(professional_tax), 174, 94, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Total", 195, 85, paint);
                    paint.setTextSize(3);
                    canvas.drawText(String.valueOf(total), 198, 94, paint);
                    paint.setTextSize(3);

                    canvas.drawText("It is certified that the above visiting faulties working a department of Diploma in Computer Engineering  ", 35, 110, paint);
                    paint.setTextSize(3);
                    canvas.drawText("have conducted Theory and Practical workload as given above month of " + month_year + " It is recommended to proceed ", 20, 114, paint);
                    paint.setTextSize(3);
                    canvas.drawText("to pass the honoranum Amount RS " + total, 20, 118, paint);
                    paint.setTextSize(3);


                    canvas.drawText("Head of the Department", 20, 125, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Verified, the honoranum Amount of Rs." + total + " is approved and shall be passed under PP && SS grant for", 35, 130, paint);
                    paint.setTextSize(3);
                    canvas.drawText(" financial year 2022 -2023 ", 20, 134, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Registrar/AO", 150, 145, paint);
                    paint.setTextSize(3);

                    canvas.drawText("Principal", 180, 145, paint);
                    paint.setTextSize(3);
                    myPdfDocument.finishPage(myPage);
                    // Save the document
                    String directoryPath = Environment.getExternalStorageDirectory().getPath() + "/PDFs/";
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    String filePath = directoryPath + name + "_" + month_year + ".pdf";
                    File file = new File(filePath);

                    try {
                        myPdfDocument.writeTo(new FileOutputStream(file));
                        Toast.makeText(getContext(), "PDF created successfully", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error creating PDF", Toast.LENGTH_SHORT).show();
                    }
//                    String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/Demo.pdf";
//                    File myFile = new File(myFilePath);
//                    System.out.println("PDF : " + myFilePath);
//
//
//                    try {
//                        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            System.out.println("H>.....");
//                            myPdfDocument.writeTo(Files.newOutputStream(myFile.toPath()));
//                            System.out.println("PDF cretead successfulyy........ ");
//                            Toast.makeText(getActivity(), "PDF created successfully !!!", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            System.out.println("E....");
//                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                            builder.setTitle("VisitorFunds");
//                            builder.setIcon(R.drawable.baseline_warning_24);
//                            builder.setMessage("There is no lecture or practical taken in this month or year..");
//                            builder.setCancelable(false);
//                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//
//                            });
//                            builder.create().show();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
////                    myEditText.setText("ERROR");
//                        Toast.makeText(getActivity(), "Error Generated", Toast.LENGTH_SHORT).show();
//
//                    }
                  myPdfDocument.close();
                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("VisitorFunds");
                    builder.setIcon(R.drawable.baseline_warning_24);
                    builder.setMessage("There is no lecture or practical taken in this month or year..");
                    builder.setCancelable(false);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }

                    });
                    builder.create().show();
                }

            }
        });


        return view;
    }


    public String show(int month) {
        String monthName = null;
        switch (month) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
        }

        return monthName;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_GRANTED)
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                // Permission is granted
                // Handle the permission granted case
            } else {
                // Permission is denied
                // Handle the permission denied case
            }
    }

}

