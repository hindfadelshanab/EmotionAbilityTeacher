package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nuwa.robot.r2022.emotionalability.databinding.ActivitySignInBinding;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityStartGameBinding;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;

public class StartGameActivity extends AppCompatActivity {

    ActivityStartGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String moduleName = getIntent().getStringExtra(Constants.ModuleNameKEY);

        if (moduleName != null)
            binding.txtModuleName.setText(moduleName);

        binding.btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(StartGameActivity.this, GameLevelActivity.class);
            startActivity(intent);

        });



    }
}