package com.nuwa.robot.r2022.emotionalability.model;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

public class PhaseAnsweredLiveData extends StateLiveData<PhaseAnswered> {
    private  static  PhaseAnsweredLiveData phaseAnsweredLiveData ;
    @MainThread
    public static PhaseAnsweredLiveData get(){
        if (phaseAnsweredLiveData == null)
            phaseAnsweredLiveData = new PhaseAnsweredLiveData();
        return phaseAnsweredLiveData;
    }

    public static PhaseAnsweredLiveData reset(){
            phaseAnsweredLiveData = new PhaseAnsweredLiveData();
        return phaseAnsweredLiveData;
    }
    public static void clear(){
        phaseAnsweredLiveData.postSuccess(null);
    }


  
}
