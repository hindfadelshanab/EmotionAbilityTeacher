package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nuwa.robot.r2022.emotionalability.databinding.ActivityStartLevelBinding;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;

import io.realm.Realm;


public class StartLevelActivity extends AppCompatActivity {


    ActivityStartLevelBinding binding;
    Realm realm;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);
        realm = Realm.getDefaultInstance();
        Constants.addToHash();

        preferenceManager.getString(Constants.CURRICULUM_ID);
        preferenceManager.getString(Constants.MODULE_ID);
        preferenceManager.getString(Constants.SPECIALIZATION_ID);
        preferenceManager.getString(Constants.OBJECTIVES_ID);

        int unitId = Constants.MODULE_UNIT_MAPPING.get(preferenceManager.getString(Constants.MODULE_ID));
        int levelId = Constants.SPECIALIZATION_LEVEL_MAPPING.get(preferenceManager.getString(Constants.SPECIALIZATION_ID));

//        Log.d("TAGNN", "onCreate: moduleIDd "+unitId);
//        Log.d("TAGNN", "onCreate: SPECIALIZATION_ID "+levelId);

//        int levelId = getIntent().getIntExtra(Constants.LEVEL_ID_Key, 0);
//        int unitId = getIntent().getIntExtra(Constants.UNIT_ID_KEY, 0);
//        int moduleId = getIntent().getIntExtra(Constants.MODULE_ID_KEY, 0);
        String objective = getIntent().getStringExtra(Constants.SELECTED_OBJECTIVES);


        Level level = realm.where(Level.class).equalTo("idLevel", levelId).equalTo("unitId", unitId).findFirst();

        String levelName = level.getName();

        String name = levelName.substring(0, levelName.indexOf("?"));
        String levelNum = levelName.substring(levelName.lastIndexOf("?") + 1);
        binding.txtLevelName.setText(name);
        binding.txtLevelNum.setText("(" + levelNum + ")");
        binding.txtLevelObjective.setText(objective);

        binding.btnNext.setOnClickListener(view -> {

            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(Constants.LEVEL_ID_Key, level.getIdLevel());
            intent.putExtra(Constants.UNIT_ID_KEY, level.getUnitId());

            startActivity(intent);
            finish();

        });

        binding.btnPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}