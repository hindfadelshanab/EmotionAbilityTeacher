package com.nuwa.robot.r2022.emotionalability.viewModel;

import android.app.Application;
import android.content.Context;
import android.text.BoringLayout;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.repository.GameRepository;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import java.util.List;

public class GameViewModel extends AndroidViewModel {


    private GameRepository gameRepository;
    private Context context;
    private boolean isPhaseFragmentLaunched = false ;
    private boolean isStudentAnsweredPhase = false ;
    private final MutableLiveData<Integer> phaseCompletedLiveData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> nextButtonLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> previousButtonLiveData = new MutableLiveData<>();
    private final StateLiveData<Boolean> studentAnsweredLiveData = new StateLiveData<>();
    private final StateLiveData<String> robotAnsweredLiveData = new StateLiveData<>();
    private final StateLiveData<Boolean> isFirstItemLiveData = new StateLiveData<>();

    public MutableLiveData<Integer> getPhaseCompletedLiveData() {
        return phaseCompletedLiveData;
    }

    public GameViewModel(@NonNull Application application) {
        super(application);
        gameRepository = new GameRepository();
        this.context = application;
    }


    public  StateLiveData<List<Phase>> getPhaseList (int levelId , int unitId){
        return  gameRepository.getPhaseList(levelId , unitId);
    }

    public  StateLiveData<Phase> getPhase(int id , int levelId , int unitId){
        return  gameRepository.getPhase(id , levelId , unitId);
    }

    public void onNextButtonClicked() {
        nextButtonLiveData.postValue(true);

    }
    public void onPreviousButtonClicked() {
        previousButtonLiveData.postValue(true);
    }

    public MutableLiveData<Boolean> getNextButtonClickedLiveData() {
        return nextButtonLiveData;
    }
    public MutableLiveData<Boolean> getPreviousButtonClickedLiveData() {
        return previousButtonLiveData;
    }





    public StateLiveData<Phase> updatePhase(int phaseId ,int levelId ,int unitId ,boolean isAnswered){
        Log.d("TAG", "updatePhase:GameViewModel ");
        return gameRepository.updatePhase(phaseId , levelId , unitId ,isAnswered);
    }


    public StateLiveData<Phase> updatePhase(int phaseId ,int levelId ,int unitId ,String response){
        Log.d("TAG", "updatePhase:GameViewModel ");
        return gameRepository.updatePhase(phaseId , levelId , unitId ,response);
    }

    public StateLiveData<Level> getNextLevel(int currentLevelId, int currentUnitId) {
        return gameRepository.getNextLevel(context, currentLevelId, currentUnitId);
    }

    public boolean isPhaseFragmentLaunched() {
        return isPhaseFragmentLaunched;
    }

    public void setPhaseFragmentLaunched(boolean phaseFragmentLaunched) {
        isPhaseFragmentLaunched = phaseFragmentLaunched;
    }



    public StateLiveData<Boolean> getStudentAnsweredPhase() {
        return studentAnsweredLiveData;
    }

    public void setStudentAnsweredPhase(boolean studentAnsweredPhase) {
        studentAnsweredLiveData.postSuccess(studentAnsweredPhase);
    }
    public void setIsRobotAnsweredEmotion(String emotionName) {
        robotAnsweredLiveData.postSuccess(emotionName);
    }
    public StateLiveData<String> getIsRobotAnsweredEmotion() {
        return robotAnsweredLiveData;
    }



    public StateLiveData<Boolean> getISFirstItemInPhase() {
        return isFirstItemLiveData;
    }

    public void setIsFirstItemInPhase(boolean isFirstItemInPhase) {
        isFirstItemLiveData.postSuccess(isFirstItemInPhase);
    }

}

