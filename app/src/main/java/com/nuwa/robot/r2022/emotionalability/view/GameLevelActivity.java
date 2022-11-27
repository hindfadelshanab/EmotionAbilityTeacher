package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nuwa.robot.r2022.emotionalability.adapter.LevelAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.LevelSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.ModuleSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.UnitSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityGameLevelBinding;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.LevelViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class GameLevelActivity extends AppCompatActivity {

    private LevelViewModel levelViewModel;
    private ActivityGameLevelBinding binding;
    private LevelAdapter levelAdapter;

    private int selectedUnitId = 0;
    private int selectedLevelId = 0;
    private PreferenceManager preferenceManager;
    private UnitSpinnerAdapter unitSpinnerAdapter;
    LevelSpinnerAdapter levelSpinnerAdapter;
    int endedLevel;
    int endedUnit;

    private Realm realm;
    List<Level> levels  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(1, 0);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(2, 0);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(3, 1);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(4, 2);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(5, 0);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(6, 1);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(7, 0);
        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(8, 1);
//        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(9, 0);
//        Constants.LEVEL_ID_AND_POSITION_HASH_MAP.put(10, 1);

        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(0 ,0);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(1 ,1);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(2 ,2);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(3 ,3);
        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(4 ,4);
//        Constants.UNIT_ID_AND_POSITION_HASH_MAP.put(5 ,0);
        init();


        getModule();
        getUnit();


    }

    private void init() {
        Log.d("TAGg", "init: TAGg ");
        levelViewModel = new ViewModelProvider(this).get(LevelViewModel.class);
        realm = Realm.getDefaultInstance();
        preferenceManager = new PreferenceManager(this);
        endedLevel = preferenceManager.getInt(Constants.LEVEL_ENDED_ID_Key);
        endedUnit = preferenceManager.getInt(Constants.UNIT_ENDED_ID_KEY);
        Log.d("TAG2", "init:endedLevel "+endedLevel);
        Log.d("TAG2", "init:endedUnit "+endedUnit);

        binding.btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedUnitId > 0 && selectedLevelId > 0) {
                    Intent intent = new Intent(GameLevelActivity.this, StartLevelActivity.class);
                    intent.putExtra(Constants.LEVEL_ID_Key, selectedLevelId);
                    intent.putExtra(Constants.UNIT_ID_KEY, selectedUnitId);
                    startActivity(intent);
                } else {
                    Toast.makeText(GameLevelActivity.this, "Select Level Pleas!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        List<Unit> units = realm.where(Unit.class).findAll();
        if (units != null) {
            if (endedUnit > 0) {
                int endedUnitPotion = Constants.UNIT_ID_AND_POSITION_HASH_MAP.get(endedUnit);
                Log.d("TAG2", "init: units.size()" + units.size());
                int endedLevelPotion = Constants.LEVEL_ID_AND_POSITION_HASH_MAP.get(endedLevel);
                levels = realm.where(Level.class).equalTo("unitId", endedUnit).findAll();

                if (units.size() > endedUnit) {
                    Log.d("TAG2", "init: units.size() > (endedUnitPotion+1)" + (endedUnitPotion+1));


                    if (levels.size() > (endedLevelPotion+1)){
                        initLevelSpinner(endedUnit, (endedLevelPotion + 1));
                        initUnitSpinner(1, endedUnitPotion ,(endedLevelPotion + 1));
                        Log.d("TAG2",  "init:levels.size() > (endedLevelPotion+1)" + (endedLevelPotion+1));

                    }else if (levels.size() == (endedLevelPotion + 1)) {
                        initLevelSpinner((endedUnit+1), 0);
                        initUnitSpinner(1,(endedUnitPotion+1) ,0);
                        Log.d("TAG2", "levels.size() == (endedLevelPotion + 1)" + (endedLevelPotion+1));

                    }

                } else if (units.size() ==endedUnit  ) {

                    if (levels.size()==(endedLevelPotion+1)){
                        Log.d("TAG2", "init: The End ");
                        initLevelSpinner(1, 0);
                        initUnitSpinner(1, 1 ,0);
                    }else {
                        Log.d("TAG2", "init: not The End ");
                        initLevelSpinner(endedUnit, 0);
                        initUnitSpinner(1, endedUnit ,(endedLevelPotion + 1));
                    }


//
                }
            }else if (endedUnit == 0) {
                initUnitSpinner(1, 1 ,0);
//                levels = realm.where(Level.class).equalTo("unitId", 0).findAll();
                initLevelSpinner(1, 0);
                Log.d("TAG2"  ,"init: else endedUnit > 0 ");

            }
        }




    }


    private void getUnit() {
        levelViewModel.getUnit(1).observe(this, listStateData -> {

            switch (listStateData.getStatus()) {
                case COMPLETE:
                    break;
                case ERROR:
                    break;
                case LOADING:
                    break;
                case SUCCESS:
                    if (listStateData.getData() != null)
                        break;
            }
        });

    }

    private void getModule() {
        levelViewModel.getModule().observe(this, listStateData -> {
            switch (listStateData.getStatus()) {
                case COMPLETE:
                    break;
                case ERROR:
                    break;
                case LOADING:
                    break;
                case SUCCESS:
                    if (listStateData.getData() != null)
                        initModuleSpinner(listStateData.getData());
                    break;
            }
        });
    }

    private void initModuleSpinner(List<Module> data ) {

        ModuleSpinnerAdapter typeAdapter = new ModuleSpinnerAdapter(this, data);
        binding.moduleSpinner.setAdapter(typeAdapter);

        binding.moduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int moduleId;
                String moduleName = "";
//                if (i > 0) {

                Module module = (Module) binding.moduleSpinner.getItemAtPosition(i);
                moduleId = module.getId();
                moduleName = module.getModuleName();
//                } else {
//                    moduleId = 0;
//                }
                Log.d("TAG", "onItemSelected:moduleId " + moduleId);
                Log.d("TAG", "onItemSelected:moduleName " + moduleName);

                //TODO: this method initUnitSpinner(moduleId, 0); should be replaced in next build
//                if (moduleId > 0) {
//                    initUnitSpinner(moduleId, 0 ,0);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void initUnitSpinner(int moduleId, int unitPosition , int endedLevelPosition) {

        Log.d("TAG2", "initUnitSpinner:unitPosition "+unitPosition);
        Log.d("TAG2", "initUnitSpinner:endedLevelPosition "+endedLevelPosition);
        levelViewModel.getUnit(moduleId).observe(this
                , new Observer<StateData<List<Unit>>>() {
                    @Override
                    public void onChanged(StateData<List<Unit>> listStateData) {
                        if (listStateData.getData() != null) {
                            List<Unit> uu = new ArrayList<>();
                            List<Unit> units = listStateData.getData();

                            uu.addAll(units);
                            Unit unit = new Unit();
                            unit.setIdUnit(0);
                            uu.add(0 , unit);

                            unitSpinnerAdapter = new UnitSpinnerAdapter(GameLevelActivity.this, uu);
                            binding.unitSpinner.setAdapter(unitSpinnerAdapter);

                            binding.unitSpinner.setSelection(unitPosition, true);
//                              initLevelSpinner((unitPosition+1), endedLevelPosition);
                            Log.d("TAG", "UnitIndex=" + unitPosition);
                            unitSpinnerAdapter.notifyDataSetChanged();


                        }
                    }
                });
        binding.unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

         if (i==1){
             Unit selectedUnit = (Unit) binding.unitSpinner.getItemAtPosition(i);
//                 binding.unitSpinner.setSelection(i, true);
             preferenceManager.putInt(Constants.LEVEL_ENDED_ID_Key, 0);
             preferenceManager.putInt(Constants.UNIT_ENDED_ID_KEY, 0);
               initLevelSpinner(selectedUnit.getIdUnit(), 0);
     } else if (i > 0) {
                    Unit selectedUnit = (Unit) binding.unitSpinner.getItemAtPosition(i);
                    Log.d("TAGg", " 2onItemSelected:  " +i);
                    Log.d("TAGg", " 2 onItemSelected:  getIdUnit " +selectedUnit.getIdUnit());
                    initLevelSpinner(selectedUnit.getIdUnit(), endedLevelPosition);
             }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initLevelSpinner(int idUnit, int levelIndex) {

        Log.d("TAG2", "initLevelSpinner:idUnit "+idUnit);
        Log.d("TAG2", "initLevelSpinner:levelIndex "+levelIndex);
        if (idUnit > 0) {
            levelViewModel.getLevels(idUnit).observe(this, new Observer<StateData<List<Level>>>() {
                @Override
                public void onChanged(StateData<List<Level>> listStateData) {
                    if (listStateData.getData() != null) {

                        Log.d("TAG", "onChanged:initLevelSpinner "+listStateData.getData());
                        levelSpinnerAdapter = new LevelSpinnerAdapter(GameLevelActivity.this, listStateData.getData());
                        binding.levelSpinner.setAdapter(levelSpinnerAdapter);
                        Log.d("TAG", "UnitIndex=" + levelIndex);
                        Log.d("TAG3", "idUnit =" + idUnit);
                        Log.d("TAG3", "listStateData.getData().size =" + listStateData.getData().size());
                        Log.d("TAG3", "levelIndex = " + levelIndex);



                            binding.levelSpinner.setSelection(levelIndex, true);


                        levelSpinnerAdapter.notifyDataSetChanged();

                    }
                }
            });

        }


        binding.levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.levelSpinner.setSelection(i, true);
                Level selectedLevel = (Level) binding.levelSpinner.getItemAtPosition(i);
                selectedUnitId = selectedLevel.getUnitId();
                selectedLevelId = selectedLevel.getIdLevel();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}