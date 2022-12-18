package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculumInfo;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.PatientInfo;
import com.nuwa.robot.r2022.emotionalability.repository.MainRepository;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private MainRepository mainRepository;
    private Context context ;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context =application;

        mainRepository = new MainRepository(application);
    }

    public StateLiveData<Module> getModuleById(int id) {
        return mainRepository.getModuleById(id);
    }
    public StateLiveData<List<Module>> getModule() {
        return mainRepository.getModule(context);
    }
    public StateLiveData<List<Module>> getModule_AR() {
        return mainRepository.getModule_AR(context);
    }
    public StateLiveData<PatientInfo> getPatientList(String doctorID) {
        return mainRepository.getPatientList(doctorID);
    }

    public StateLiveData<AutismCurriculumInfo> getAutismCurriculum(String language , String displayType){
        return mainRepository.getAutismCurriculum(language , displayType);
    }

}
