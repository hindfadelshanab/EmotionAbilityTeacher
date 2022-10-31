package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.repository.LevelRepository;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import java.util.List;

public class LevelViewModel extends AndroidViewModel {


    private LevelRepository levelRepository;
    private Context context ;

    public LevelViewModel(@NonNull Application application) {
        super(application);
        levelRepository = new LevelRepository();
        this.context =application;
    }

    public StateLiveData<List<Unit>> getUnit() {
        return levelRepository.getUnit(context);
    }

//    public List createGame(Level level){
//        return levelRepository.createGame(level);
//    }


}
