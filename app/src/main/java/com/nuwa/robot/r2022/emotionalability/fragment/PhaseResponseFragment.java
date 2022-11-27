package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentResponsBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnStudentAnsweredListener;
import com.nuwa.robot.r2022.emotionalability.model.Message;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.model.StudentAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import io.realm.Realm;


public class PhaseResponseFragment extends Fragment   {


    FragmentResponsBinding binding;
    FragmentActivity fragmentActivity;
    Phase phase;
    public String TAG = "PhaseResponseFragment";
    GameViewModel gameViewModel;
    Realm realm;
    Gson gson;
    RobotController robotController ;
    int position ;
   public static int  isFragmentVisible =0 ;
   public static  boolean isStudentAnswered ;
    public PhaseResponseFragment(Phase phase, FragmentActivity fragmentActivity , int position) {
        this.phase = phase;
        this.fragmentActivity = fragmentActivity;
        this.position = position;

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (menuVisible){
            Log.d("TAG2", " Phase6ResponseFragment fragment is  visible " +menuVisible);
        }else {
            Log.d("TAG2", " Phase6ResponseFragment fragment is not visible ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResponsBinding.inflate(inflater, container, false);
        Log.d("TAG2", "onCreateView: isFragmentVisible "+isFragmentVisible);
        initialization();

        return binding.getRoot();
    }



    private void initialization(){
        gameViewModel = new ViewModelProvider(fragmentActivity).get(GameViewModel.class);
        realm = Realm.getDefaultInstance();
        robotController = new RobotController(getContext() );
        gson = new Gson();
        answerListener();


    }
    public  void answerListener(){
        Log.d("TAG2", "answerListener: ");

        if (phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_SELECT_FROM_TABLE) {
            Log.d("TAG2", "answerListener== " + phase.getAnswerContent().getAnswerWay());
            gameViewModel.getIsRobotAnsweredEmotion().observe(fragmentActivity, stringStateData -> {
                if (stringStateData.getData() != null) {

                    binding.txtQuestionTitle.setText(stringStateData.getData());
                    sendMessageToRobot(stringStateData.getData());
                } else {
                    handleWrongRespond();
                    sendMessageToRobot(Constants.LET_IS_TRY_AGAIN);

                }
            });
        }
        if (phase != null) {
            PhaseAnsweredLiveData.get().observe((LifecycleOwner) fragmentActivity, phaseAnsweredStateData -> {
                Log.d("TAG2", "answerListener:     PhaseAnsweredLiveData.get().get()" );

                PhaseAnswered phaseAnswered = phaseAnsweredStateData.getData();
                if ((phaseAnswered.getPhaseId() == phase.getId() && phaseAnswered.isAnswered())) {
                    handleRightRespond(phase.getResponse());
                    sendMessageToRobot(phase.getResponse());

                } else if ((phaseAnswered.getPhaseId() == phase.getId() && !phaseAnswered.isAnswered())) {
                    handleWrongRespond();
                    sendMessageToRobot(Constants.LET_IS_TRY_AGAIN);

                }
            });


                Log.d("TAG2", "answerListener: btnSendResponseToRobot isClickable");
                    StudentAnsweredLiveData.get().observe(fragmentActivity, booleanStateData -> {
                        binding.btnSendResponseToRobot.setVisibility(View.VISIBLE);

                        boolean isAnswered = booleanStateData.getData();
                        Log.d("TAG2", "answerListener:    StudentAnsweredLiveData.get()" +isAnswered);
                        isStudentAnswered =isAnswered ;
                        if (booleanStateData.getData()) {

                            handleRightRespond(phase.getResponse());
                        } else if (!isAnswered) {
                            handleWrongRespond();
                        }
                    });

          binding.btnSendResponseToRobot.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Log.d("TAG2", "onClick: isStudentAnswered"+isStudentAnswered);
                  if (isStudentAnswered){
                      sendMessageToRobot(phase.getResponse());

                  }else {
                      sendMessageToRobot(Constants.LET_IS_TRY_AGAIN);

                  }
              }
          });


        }
    }





    private void handleWrongRespond() {
        binding.txtIntro.setText("Let’s");
        binding.txtQuestionTitle.setText("try again.");

    }

    private void handleRightRespond(String phaseResponse){
        String responseIntro = phaseResponse.substring(0, phaseResponse.indexOf("!"));
        String response = phaseResponse.substring(phaseResponse.indexOf("!") + 1);
        binding.txtIntro.setText(responseIntro);
        binding.txtQuestionTitle.setText(response);
    }


    private void sendMessageToRobot(String content) {
        Log.d("TAG2", "sendMessageToRobot:isFragmentVisible " +content);
        Message message = new Message(8,Constants.MESSAGE_FOR_KEBHI_SPECK, content);
        String messageJSon = gson.toJson(message) ;
        messageJSon = "{\"message\":" + messageJSon + "}";
        Log.d("TAG", "sendMessageToRobot:messageJSon " +messageJSon);
        robotController.sendMessageForRobot(messageJSon);
    }

//    @Override
//    public void OnStudentAnswered(boolean answered) {
//        Log.d("TAG2", "OnStudentAnswered: answered "+answered);
//        if (answered) {
//            handleRightRespond(phase.getResponse());
//        } else {
//            handleWrongRespond();
//        }
//    }
}