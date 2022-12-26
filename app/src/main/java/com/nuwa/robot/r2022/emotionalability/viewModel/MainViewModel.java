package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculumInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismModuleInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismObjectivesInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismSpecialization;
import com.nuwa.robot.r2022.emotionalability.model.AutismSpecializationInfo;
import com.nuwa.robot.r2022.emotionalability.model.BaselineResultInfo;
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


    public StateLiveData<AutismModuleInfo> getAutismModule(String language , String Curriculum_Id){
        return mainRepository.getAutismModule(language , Curriculum_Id);
    }

    public StateLiveData<AutismSpecializationInfo> getAutismSpecialization(String language , String module_Id){
        return mainRepository.getAutismSpecialization(language , module_Id);
    }
public StateLiveData<AutismObjectivesInfo> getAutismObjectives(String language , String Specialization_Id){
        return mainRepository.getAutismObjectives(language , Specialization_Id);
    }
    public StateLiveData<BaselineResultInfo> getAutismBaseline(String language){
        return mainRepository.getAutismBaseline(language);
    }
}
