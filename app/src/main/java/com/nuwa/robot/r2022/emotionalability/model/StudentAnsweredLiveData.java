package com.nuwa.robot.r2022.emotionalability.model;

import androidx.annotation.MainThread;

import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

public class StudentAnsweredLiveData extends StateLiveData<Integer> {
    private  static StudentAnsweredLiveData studentAnsweredLiveData ;
    @MainThread
    public static StudentAnsweredLiveData get(){
        if (studentAnsweredLiveData == null)
            studentAnsweredLiveData = new StudentAnsweredLiveData();
        return studentAnsweredLiveData;
    }

    public static StudentAnsweredLiveData reset(){
        studentAnsweredLiveData = new StudentAnsweredLiveData();
        return studentAnsweredLiveData;
    }


}
