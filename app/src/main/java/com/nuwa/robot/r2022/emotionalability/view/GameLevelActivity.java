package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.adapter.LevelAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityGameLevelBinding;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.viewModel.LevelViewModel;
import com.nuwa.robot.r2022.emotionalability.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameLevelActivity extends AppCompatActivity {

    private LevelViewModel levelViewModel;
    private ActivityGameLevelBinding binding;
    private LevelAdapter levelAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialization();
        getUnit();



    }

    private void initialization() {
        levelViewModel = new ViewModelProvider(this).get(LevelViewModel.class);
//        Log.d("TAG", "initialization: "+levels);

    }

    private void getUnit() {
        levelViewModel.getUnit().observe(this, listStateData -> {
            switch (listStateData.getStatus()) {
                case COMPLETE:
                    break;
                case ERROR:
                    break;

                case LOADING:
                    break;
                case SUCCESS:
                    successCase(listStateData.getData());
                    break;
            }
        });
    }

    private void successCase(List<Unit> data) {
       // Log.d("TAG", "successCase Unit: "+data);
        List<Level> levels = new ArrayList<>();

        for (Unit unit : data) {
            levels.addAll(unit.getLevels());
//            Log.d("TAG", "initialization 3: "+levels);

        }
        initAdapter(levels);



    }

    private void initAdapter(List<Level> levels) {
        levelAdapter = new LevelAdapter(this , levels);
        binding.levelRv.setAdapter(levelAdapter);
        binding.levelRv.setLayoutManager(new GridLayoutManager(this , 5));

    }
}