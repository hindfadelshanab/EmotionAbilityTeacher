package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nuwa.robot.r2022.emotionalability.model.ResultInfo;
import com.nuwa.robot.r2022.emotionalability.repository.ChangeLanguageRepository;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

public class ChangeLanguageViewModel extends AndroidViewModel {


    private ChangeLanguageRepository changeLanguageRepository;
    private Context context ;

    public ChangeLanguageViewModel(@NonNull Application application) {
        super(application);
        changeLanguageRepository = new ChangeLanguageRepository(application);
        this.context =application;
    }



    public StateLiveData<ResultInfo> updateLanguage(String language , String doctorId) {
        return changeLanguageRepository.updateLanguage(language , doctorId);
    }

}
