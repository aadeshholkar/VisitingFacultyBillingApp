package com.example.client;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.client.CalenderOperation.CalenderActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VisInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    ImageSlider imageSlider ;
    ArrayList<SlideModel> slideModels = new ArrayList<>();

    TextView tx1, tx2;
    String TH_amount, PR_amount;
    int pr_remun, th_remun;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis_info);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        //setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);



        imageSlider = findViewById(R.id.imageSlider);
        slideModels.add(new SlideModel(R.drawable.welcome, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.welcome2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);





        tx1 = findViewById(R.id.th);
        tx2 = findViewById(R.id.pr);

        reference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Salary");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                TH_amount = snapshot1.child("theory").getValue(String.class);
                PR_amount = snapshot1.child("practical").getValue(String.class);
                System.out.println(TH_amount + "  " + "  " + PR_amount);

                tx1.setText("Theory's\n Amount: " + TH_amount);
                tx2.setText("Practical's Amount : " + PR_amount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_about:
                Intent i2 = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(i2);
                break;

            case R.id.nav_activity:
                Intent i1 = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(i1);
                break;

            case R.id.nav_calender:
                 i1 = new Intent(getApplicationContext(), CalenderActivity.class);
                startActivity(i1);
                break;

            case R.id.nav_profile:
                Intent i = new Intent(getApplicationContext(),VisAboutActivity.class);
              //  Toast.makeText(VisInfoActivity.this, "About Us page", Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;


            case R.id.nav_upload:
                Intent i10 = new Intent(getApplicationContext(),ShowPDFActivity.class);
                Toast.makeText(VisInfoActivity.this, "Show PDF", Toast.LENGTH_SHORT).show();

                startActivity(i10);
                break;

            case R.id.nav_logut:
                AlertDialog.Builder builder = new AlertDialog.Builder(VisInfoActivity.this);
                builder.setMessage("are you sure ?");
                builder.setCancelable(false);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),VisLoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(VisInfoActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                break;

            default:
                return true;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}