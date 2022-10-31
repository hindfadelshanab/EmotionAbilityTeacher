package com.nuwa.robot.r2022.emotionalability.model;

import androidx.annotation.MainThread;

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


}
