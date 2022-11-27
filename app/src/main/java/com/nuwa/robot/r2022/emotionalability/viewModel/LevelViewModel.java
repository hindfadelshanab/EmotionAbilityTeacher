package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
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

    public StateLiveData<List<Unit>> getUnit(int moduleID) {
        return levelRepository.getUnit(context , moduleID);
    }
    public StateLiveData<List<Level>> getLevels( int unitID) {
        return levelRepository.getLevels( unitID);
    }

    public StateLiveData<List<Module>> getModule() {
        return levelRepository.getModule(context);
    }


}
