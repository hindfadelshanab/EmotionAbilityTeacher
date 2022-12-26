package com.nuwa.robot.r2022.emotionalability.fragment;

import static com.nuwa.robot.r2022.emotionalability.utils.StateData.DataStatus.SUCCESS;

import android.annotation.SuppressLint;
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
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentResponsBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnStudentAnsweredListener;
import com.nuwa.robot.r2022.emotionalability.model.Message;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.model.StudentAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import io.realm.Realm;


public class PhaseResponseFragment extends Fragment {


    FragmentResponsBinding binding;
    FragmentActivity fragmentActivity;
    Phase phase;
    public String TAG = "PhaseResponseFragment";
    GameViewModel gameViewModel;
    Realm realm;
    Gson gson;
    RobotController robotController;
    int position;
    public static int isFragmentVisible = 0;
    public static int isStudentAnswered;
    public static boolean isTrue_FalseAnswered;
    public static boolean isRobotAnswered;
    private PreferenceManager preferenceManager;

    public PhaseResponseFragment(Phase phase, FragmentActivity fragmentActivity, int position) {
        this.phase = phase;
        this.fragmentActivity = fragmentActivity;
        this.position = position;

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (menuVisible) {
            Log.d("TAG2", " Phase6ResponseFragment fragment is  visible " + menuVisible);
        } else {
            Log.d("TAG2", " Phase6ResponseFragment fragment is not visible ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResponsBinding.inflate(inflater, container, false);
        Log.d("TAG2", "onCreateView: isFragmentVisible " + isFragmentVisible);
        preferenceManager = new PreferenceManager(fragmentActivity);

        initialization();

        return binding.getRoot();
    }


    private void initialization() {
        gameViewModel = new ViewModelProvider(fragmentActivity).get(GameViewModel.class);
        realm = Realm.getDefaultInstance();
        robotController = new RobotController(getContext());
        gson = new Gson();
        answerListener();


    }



    public void answerListener() {
        Log.d("TAG2", "answerListener: ");


        binding.btnSendResponseWellDone.setOnClickListener(view -> {

            Log.d("TAGkk", "answerListener: " + this.getResources().getString(R.string.well_done));
            sendMessageToRobot(15, "well Done");

        });
        binding.btnSendResponseYouAreAStar.setOnClickListener(view -> {

            sendMessageToRobot(8, "You are a star");

        });
        binding.btnSendResponseYoCanDoIt.setOnClickListener(view -> {

            sendMessageToRobot(11, "You can do it");

        });
        if (phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_SELECT_FROM_TABLE) {
            Log.d("TAG2", "answerListener== " + phase.getAnswerContent().getAnswerWay());
            gameViewModel.getIsRobotAnsweredEmotion().observe(fragmentActivity, stringStateData -> {
                if (stringStateData.getData() != null) {

                    binding.txtQuestionTitle.setText(stringStateData.getData());
                    sendMessageToRobot(10, stringStateData.getData());
                    showWellDone();
                } else {
                    isRobotAnswered =false;
                    handleWrongRespond();
                    sendMessageToRobot(10, Constants.LET_IS_TRY_AGAIN);

                    showYouCanDoIt();
                }
            });
        } else if (phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_TRU_FALSE) {
            PhaseAnsweredLiveData.get().observe((LifecycleOwner) fragmentActivity, phaseAnsweredStateData -> {
                if (phaseAnsweredStateData.getStatus()== SUCCESS) {
                    Log.d("TAG2", "answerListener:     PhaseAnsweredLiveData.get().get()");
                    Log.d("TAG9", "phaseAnsweredStateData:" + phaseAnsweredStateData.getData());
//                if (phaseAnsweredStateData!=null) {
                    PhaseAnswered phaseAnswered = phaseAnsweredStateData.getData();
                    if (phaseAnswered != null){

                        if ((phaseAnswered.getPhaseId() == phase.getId() && phaseAnswered.isAnswered() == Constants.PHASE_ANSWERED_TRUE)) {
                            isTrue_FalseAnswered = true;
                            handleRightRespond(phase.getResponse());
                            sendMessageToRobot(8, phase.getResponse());
                            showWellDone();

                        } else if ((phaseAnswered.getPhaseId() == phase.getId() && phaseAnswered.isAnswered() == Constants.PHASE_ANSWERED_FALSE)) {
                            isTrue_FalseAnswered = false;
                            sendMessageToRobot(8, Constants.LET_IS_TRY_AGAIN);
                            handleWrongRespond();
                            showYouCanDoIt();
                        }
                }
            }
            });



        } else if (phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_SELECT){
            Log.d("TAG2", "answerListener: btnSendResponseToRobot isClickable");

            StudentAnsweredLiveData.get().observe(fragmentActivity, new Observer<StateData<Integer>>() {
                @Override
                public void onChanged(StateData<Integer> integerStateData) {
                    Log.d("TAG2", "answerListener: btnSendResponseToRobot "+integerStateData.getData());
                    Log.d("TAG2", "answerListener: btnSendResponseToRobot "+integerStateData.getStatus());

                    if (integerStateData.getStatus() ==SUCCESS){
                        if (integerStateData.getData()==Constants.PHASE_ANSWERED_TRUE) {

                            handleRightRespond(phase.getResponse());
                            sendMessageToRobot(8, phase.getResponse());
                            showWellDone();
                        } else if (integerStateData.getData()==Constants.PHASE_ANSWERED_FALSE) {
                            handleWrongRespond();
                            sendMessageToRobot(8, Constants.LET_IS_TRY_AGAIN);
                            showYouCanDoIt();
                        }
                    }
                }
            });
//            StudentAnsweredLiveData.get().observe(fragmentActivity, new Observer<StateData<Integer>>() {
//                @Override
//                public void onChanged(StateData<Integer> integerStateData) {
//
//
//                    int isAnswered = integerStateData.getData();
//                    Log.d("TAG2", "answerListener:    StudentAnsweredLiveData.get()" + isAnswered);
//                    isStudentAnswered = isAnswered;
//                    if (integerStateData.getData()==Constants.PHASE_ANSWERED_TRUE) {
//
//                        handleRightRespond(phase.getResponse());
//                        sendMessageToRobot(8, phase.getResponse());
//                        showWellDone();
//                    } else if (integerStateData.getData()==Constants.PHASE_ANSWERED_FALSE) {
//                        handleWrongRespond();
//                        sendMessageToRobot(8, Constants.LET_IS_TRY_AGAIN);
//                        showYouCanDoIt();
//                    }
//                }
//            });


        }
    }

    private void showWellDone(){
        binding.btnSendResponseWellDone.setVisibility(View.VISIBLE);
        binding.btnSendResponseYoCanDoIt.setVisibility(View.GONE);
    }
    private void showYouCanDoIt(){
        binding.btnSendResponseWellDone.setVisibility(View.GONE);
        binding.btnSendResponseYoCanDoIt.setVisibility(View.VISIBLE);
    }
    @SuppressLint("SetTextI18n")
    private void handleWrongRespond() {

        if (preferenceManager.getString(Constants.LANGUAGE) != null) {
            if (preferenceManager.getString(Constants.LANGUAGE).equals(Constants.ENGLISH)) {
                binding.txtIntro.setText("Let’s");
                binding.txtQuestionTitle.setText("try again");
            } else if (preferenceManager.getString(Constants.LANGUAGE).equals(Constants.ARABIC)) {
                binding.txtIntro.setText("هيَا");
                binding.txtQuestionTitle.setText("حاول مرة أخرى");
            }
        } else {
            binding.txtIntro.setText("Let’s");
            binding.txtQuestionTitle.setText("try again");
        }


    }

    private void handleRightRespond(String phaseResponse) {
        try {
            String responseIntro = phaseResponse.substring(0, phaseResponse.indexOf("!"));
            String response = phaseResponse.substring(phaseResponse.indexOf("!") + 1);
            binding.txtIntro.setText(responseIntro);
            binding.txtQuestionTitle.setText(response);
        } catch (Exception e) {
            Log.d(TAG, "handleRightRespond: error" + e.getMessage());
        }

    }


    private void sendMessageToRobot(int id, String content) {
        Log.d("TAG2", "sendMessageToRobot:isFragmentVisible " + content);
        Message message = new Message(id, Constants.MESSAGE_FOR_KEBHI_SPECK, content);
        String messageJSon = gson.toJson(message);
        messageJSon = "{\"message\":" + messageJSon + "}";
        Log.d("TAG", "sendMessageToRobot:messageJSon " + messageJSon);
        robotController.sendMessageForRobot(messageJSon);
    }


}
//            if (phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_SELECT || phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_TRU_FALSE) {
//                binding.btnSendResponseToRobot.setVisibility(View.VISIBLE);
//            }
//
//            binding.btnSendResponseToRobot.setOnClickListener(view -> {
//                Log.d("TAG2", "onClick: isStudentAnswered" + isStudentAnswered);
//
//
//                if (phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_SELECT) {
//                    if (isStudentAnswered==Constants.PHASE_ANSWERED_TRUE) {
//                        sendMessageToRobot(8, phase.getResponse());
//
//                    } else {
//                        sendMessageToRobot(8, Constants.LET_IS_TRY_AGAIN);
//                    }
//                } else if (phase.getAnswerContent().getAnswerWay() == Constants.ANSWERED_WAY_TRU_FALSE) {
//
//                    if (isTrue_FalseAnswered) {
//                        sendMessageToRobot(8, phase.getResponse());
//                    } else {
//                        sendMessageToRobot(8, Constants.LET_IS_TRY_AGAIN);
//                    }
//                }
//
//            });