package com.nuwa.robot.r2022.emotionalability.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityBeginningBinding;
import com.nuwa.robot.r2022.emotionalability.model.Details;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.SignInViewModel;

public class BeginningActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityBeginningBinding binding ;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    PreferenceManager preferenceManager ;
    SignInViewModel signInViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityBeginningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.myDrawerLayout, R.string.nav_open, R.string.nav_close);

        if (preferenceManager.getString(Constants.DOCTOR_ID)!=null) {
            binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
            binding.navView.setNavigationItemSelectedListener(this);
            actionBarDrawerToggle.syncState();
            showPatientData();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.navView.findViewById(R.id.btnLogut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BeginningActivity.this , SignAsActivity.class));
            }
        });

        binding.tabletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(BeginningActivity.this, MainActivity.class));
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.changeLanguageAction){
            startActivity(new Intent(this , ChangeLanguageActivity.class));
            Toast.makeText(this, "changeLanguageAction", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId()==R.id.homeAction){
            startActivity(new Intent(this , BeginningActivity.class));

        }
        return false;
    }

    private void showPatientData(){
     View headerView =   binding.navView.getHeaderView(0);
     ImageView patientImage =headerView.findViewById(R.id.patientImage);
     TextView patientName =headerView.findViewById(R.id.doctorName);

     signInViewModel.getSignedDoctor(preferenceManager.getString(Constants.DOCTOR_ID)).observe(this, new Observer<StateData<Details>>() {
         @Override
         public void onChanged(StateData<Details> detailsStateData) {
             if (detailsStateData.getStatus()== StateData.DataStatus.SUCCESS) {
                 patientName.setText(detailsStateData.getData().getDoctor_Name());
                 Log.d("TAG sign", "onChanged: detailsStateData"+detailsStateData.getData().getDoctor_Name());

                 Glide.with(BeginningActivity.this).load(detailsStateData.getData().getImage()).into(patientImage);
             }
             Log.d("TAG sign", "onChanged: detailsStateData"+detailsStateData.getData().toString());
             Log.d("TAG sign", "onChanged: detailsStateData"+detailsStateData.getData().getUsername());
         }
     });
    }
}