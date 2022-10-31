package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nuwa.robot.r2022.emotionalability.databinding.ActivityStartLevelBinding;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;


public class StartLevelActivity extends AppCompatActivity {


    ActivityStartLevelBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Level level = getIntent().getParcelableExtra(Constants.LevelKEY);

//        Log.d("TAG", "onCreate level: "+level.getPhases().get(0));
        int id = getIntent().getIntExtra(Constants.LEVEL_ID_Key, 0);
        binding.txtLevelName.setText(level.getName());

        binding.btnNext.setOnClickListener(view -> {

            Intent intent = new Intent(this , GameActivity.class);
            intent.putExtra(Constants.LEVEL_ID_Key ,level.getIdLevel());
            intent.putExtra(Constants.UNIT_ID_KEY ,level.getUnitId());
//            Log.d("TAG", "onCreate: startLevel" +level.getPhases().get(0).getQuestioncontent());
            startActivity(intent);

        });



    }

}