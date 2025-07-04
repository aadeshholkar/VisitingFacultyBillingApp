package com.example.main.AdminOperation;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.VisitorOperation.VisitorActivity;

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
import com.example.main.EventOperation.VisitorEventActivity;
import com.example.main.R;
import com.example.main.UploadPDF_Operation.UploadPDFActivity;
import com.example.main.VisitorOperation.DetailActivity;
import com.example.main.VisitorOperation.VisitorActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommanActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    ImageSlider imageSlider;
    TextView tx1, tx2;
    String TH_amount, PR_amount;
    int pr_remun, th_remun;
    DatabaseReference reference;
    ArrayList<SlideModel> slideModels = new ArrayList<>();

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comman);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        AdminHelperClass adminHelperClass = new AdminHelperClass();
        imageSlider = findViewById(R.id.imageSlider);
        slideModels.add(new SlideModel(R.drawable.welcome, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.welcome2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        tx1 = findViewById(R.id.th);
        tx2 = findViewById(R.id.pr);

        reference = FirebaseDatabase.getInstance("https://mainproject-e095f-default-rtdb.firebaseio.com/").getReference("Salary");
        reference.addValueEventListener(new ValueEventListener() {
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {

            case R.id.nav_salary:
                Toast.makeText(this, "Salary page", Toast.LENGTH_SHORT).show();
                i = new Intent(CommanActivity.this, SalaryActivity.class);
                startActivity(i);
                break;


            case R.id.nav_upload:
                Toast.makeText(this, "Upload PDF", Toast.LENGTH_SHORT).show();
                i = new Intent(CommanActivity.this, UploadPDFActivity.class);
                startActivity(i);
                break;

            case R.id.nav_show_visitor:
                Toast.makeText(this, "Show Visitor Page", Toast.LENGTH_SHORT).show();
                i = new Intent(CommanActivity.this, DetailActivity.class);
                startActivity(i);
                break;



            case R.id.nav_add_visitor:
                Toast.makeText(this, "Add Visitor Page", Toast.LENGTH_SHORT).show();
                i = new Intent(getApplicationContext(), VisitorActivity.class);
                startActivity(i);
                return true;

            case R.id.nav_event:
                Toast.makeText(this, "Event", Toast.LENGTH_SHORT).show();
                i = new Intent(CommanActivity.this, VisitorEventActivity.class);
                startActivity(i);
                break;


            default:
                return true;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_corner, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch ((item.getItemId())) {
            case R.id.right_item1:
                Toast.makeText(this, "Item1 selected", Toast.LENGTH_SHORT);
                i = new Intent(getApplicationContext(),About_Admin_Activity.class);
                startActivity(i);
                break;

            case R.id.right_item4:
                Toast.makeText(this,"Item selected 4",Toast.LENGTH_SHORT);
                i = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(i);
                break;

            case R.id.right_item2:
                Toast.makeText(this, "Item2 selected", Toast.LENGTH_SHORT);
                i = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(i);
                break;

            case R.id.right_item3:

                AlertDialog.Builder builder = new AlertDialog.Builder(CommanActivity.this);
                builder.setTitle("VisitorFunds");
                builder.setIcon(R.drawable.baseline_exit_to_app_24);
                builder.setMessage("are you sure to Logout ?");
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
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(CommanActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CommanActivity.this);
        builder.setTitle("VisitorFunds");
        builder.setIcon(R.drawable.baseline_exit_to_app_24);
        builder.setMessage("are you sure to Logout ?");
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
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(CommanActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }
}

