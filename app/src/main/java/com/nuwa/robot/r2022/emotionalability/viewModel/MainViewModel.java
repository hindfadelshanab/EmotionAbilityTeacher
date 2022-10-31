package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.repository.MainRepository;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import kotlinx.coroutines.Dispatchers;

public class MainViewModel extends AndroidViewModel {


    private MainRepository mainRepository;
    private Context context ;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context =application;

        mainRepository = new MainRepository();
    }

    public StateLiveData<Module> getModule() {
        return mainRepository.getModule(context);
    }


}
