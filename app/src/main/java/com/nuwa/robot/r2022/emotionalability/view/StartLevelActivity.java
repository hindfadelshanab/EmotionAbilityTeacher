package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nuwa.robot.r2022.emotionalability.databinding.ActivityStartLevelBinding;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;

import io.realm.Realm;


public class StartLevelActivity extends AppCompatActivity {


    ActivityStartLevelBinding binding;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        realm = Realm.getDefaultInstance();

        int levelId = getIntent().getIntExtra(Constants.LEVEL_ID_Key, 0);
        int unitId = getIntent().getIntExtra(Constants.UNIT_ID_KEY, 0);


        Level level = realm.where(Level.class).equalTo("idLevel", levelId).equalTo("unitId", unitId).findFirst();

        String levelName = level.getName();

        String name = levelName.substring(0, levelName.indexOf("?"));
        String levelNum = levelName.substring(levelName.lastIndexOf("?")+1);
        binding.txtLevelName.setText(name);
        binding.txtLevelNum.setText("("+levelNum +")");
        binding.txtLevelObjective.setText(level.getObjective());

        binding.btnNext.setOnClickListener(view -> {

            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(Constants.LEVEL_ID_Key, level.getIdLevel());
            intent.putExtra(Constants.UNIT_ID_KEY, level.getUnitId());

            startActivity(intent);

        });


    }

}