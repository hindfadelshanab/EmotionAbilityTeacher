package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nuwa.robot.r2022.emotionalability.model.Details;
import com.nuwa.robot.r2022.emotionalability.model.Doctor;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.repository.LevelRepository;
import com.nuwa.robot.r2022.emotionalability.repository.SignInRepository;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import java.util.List;

public class SignInViewModel extends AndroidViewModel {


    private SignInRepository signInRepository;
    private Context context ;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        signInRepository = new SignInRepository(application);
        this.context =application;
    }



    public StateLiveData<Doctor> doctorLogin(String userName , String password) {
        return signInRepository.doctorLogin(userName , password);
    }
   public StateLiveData<Details> getSignedDoctor(String doctorId ) {
        return signInRepository.getSignedDoctor(doctorId );
    }  public void addSignedDoctor(Details details) {
         signInRepository.addSignedDoctor(details );
    }

}
