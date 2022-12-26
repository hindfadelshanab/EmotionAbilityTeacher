package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivitySignAsBinding;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;

public class SignAsActivity extends AppCompatActivity {

    ActivitySignAsBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignAsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);

        binding.btnLogInAsTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignAsActivity.this, SignInActivity.class));
            }
        });
        binding.btnLogInAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignAsActivity.this, BeginningActivity.class));
                preferenceManager.putString(Constants.DOCTOR_NAME ,"Guest");

                preferenceManager.putString(Constants.DOCTOR_ID ,null);
            }
        });

    }
}