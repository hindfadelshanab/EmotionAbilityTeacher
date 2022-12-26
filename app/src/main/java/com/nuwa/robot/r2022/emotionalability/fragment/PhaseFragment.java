package com.nuwa.robot.r2022.emotionalability.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.adapter.PhaseAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentPhaseBinding;
import com.nuwa.robot.r2022.emotionalability.model.MessageExpression;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.ResultInfo;
import com.nuwa.robot.r2022.emotionalability.model.StudentAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherSocketClient;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.view.GameLevelActivity;
import com.nuwa.robot.r2022.emotionalability.view.MainActivity;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class PhaseFragment extends Fragment implements TeacherSocketClient.IOnMessageListener , AnswerContentFragment.IButtonClicked
        {

    private final String TAG = "PhaseFragment";
    FragmentPhaseBinding binding;
    private PhaseAdapter phaseAdapter;
    private int phaseListPosition = 0;
    private GameViewModel gameViewModel;
    private List<Phase> phaseList = new ArrayList<>();

    int unitID;
    int levelID;
    FragmentActivity context;
    private Realm realm;
    RobotController robotController;
    Gson gson;
    private PreferenceManager preferenceManager;
    TeacherSocketClient teacherClient;
    private boolean isPhaseAnswered = false;

//     static ArrayList<OnStudentAnsweredListener> onStudentAnsweredListeners  = new ArrayList<>();

    public PhaseFragment(int levelID, int unitID, FragmentActivity context) {
        this.unitID = unitID;
        this.levelID = levelID;
        this.context = context;
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        gameViewModel = new ViewModelProvider(context).get(GameViewModel.class);

        if (menuVisible) {
            gameViewModel.setIsFirstItemInPhase(true);
            StudentAnsweredLiveData.get().postLoading();
            StudentAnsweredLiveData.get().postSuccess(0);

        } else {
            gameViewModel.setIsFirstItemInPhase(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhaseBinding.inflate(inflater, container, false);


        initialization();
        initConnection();
        gameViewModel.setPhaseFragmentLaunched(true);

        gameViewModel.getPhaseCompletedLiveData().postValue(0);
        getPhaseList();
        AnswerContentFragment.setIButtonClickedItem(this);
        return binding.getRoot();

    }

    public void initialization() {
        robotController = new RobotController(getContext());
        realm = Realm.getDefaultInstance();
        gson = new Gson();
        preferenceManager = new PreferenceManager(getContext());
        gameViewModel = new ViewModelProvider(context).get(GameViewModel.class);
        realm = Realm.getDefaultInstance();


        gameViewModel.getNextButtonClickedLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {

                    if (phaseAdapter != null) {
                        next();
                    }
                }
            }
        });
    }


    public void next() {
        int pagerAdapterPosition = binding.phaseViewPager.getCurrentItem();
        goNextPosition(binding.phaseViewPager, pagerAdapterPosition);

        if (pagerAdapterPosition == 2) {
            backToHome();
        } else {
            goNextPosition(binding.phaseViewPager, pagerAdapterPosition);

        }


    }

    private String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("en"));
        String strDate = sdf.format(cal.getTime());

        System.out.println("getCurrentDate Format: " + strDate);
        Log.d("TAG6", "getCurrentDate:  strDate : " + strDate);
        return strDate;

    }

    private String getCurrentDateANDTime() {
        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        //2022-12-13 10:30:33
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("en"));
        String strDate = sdf.format(cal.getTime());

        System.out.println("Current date in String Format: " + strDate);
        Log.d("TAG6", "getCurrentDate:  strDate : " + strDate);
        return strDate;

    }

    private void backToHome() {
        getCurrentDate();
        Phase phase2 = realm.where(Phase.class).equalTo("id", phaseList.get(phaseListPosition).getId())
                .equalTo("levelId", phaseList.get(phaseListPosition).getLevelId())
                .equalTo("unitId", phaseList.get(phaseListPosition).getUnitId())
                .findFirst();
        if (phase2.isAnswered()) {
            if (phaseListPosition == phaseList.size() - 1) {

                preferenceManager.putInt(Constants.MODULE_ENDED_ID_Key, phase2.getModuleId());

                preferenceManager.putInt(Constants.LEVEL_ENDED_ID_Key, phaseList.get(phaseListPosition).getLevelId());
                preferenceManager.putInt(Constants.UNIT_ENDED_ID_KEY, phase2.getUnitId());

                if (preferenceManager.getString(Constants.DOCTOR_ID) != null) {
                    sendAnsweredResult();
                }
                context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                this.getActivity().finish();


            } else {
                phaseListPosition++;
                lunchPhase(phaseList, phaseListPosition);
            }
        } else {
            lunchPhase(phaseList, phaseListPosition);
        }

    }

    private void sendAnsweredResult() {
        String doctorId = preferenceManager.getString(Constants.DOCTOR_ID);
        String patientId = preferenceManager.getString(Constants.PATIENT_ID);
        String termId = preferenceManager.getString(Constants.TERM_ID);
        String baseLineId = preferenceManager.getString(Constants.BASELINE_ID);
        String curriculumId = preferenceManager.getString(Constants.CURRICULUM_ID);
        String moduleId = preferenceManager.getString(Constants.MODULE_ID);
        String objectivesId = preferenceManager.getString(Constants.OBJECTIVES_ID);
        String specializationId = preferenceManager.getString(Constants.SPECIALIZATION_ID);
//
//
//        Log.d("TAG6", "sendAnsweredResult: doctorId " + doctorId);
//        Log.d("TAG6", "sendAnsweredResult: patientId " + patientId);
//        Log.d("TAG6", "sendAnsweredResult: termId " + termId);
//        Log.d("TAG6", "sendAnsweredResult: baseLineId " + baseLineId);
        if (patientId != null && termId != null && baseLineId != null && specializationId != null && curriculumId != null
                && moduleId != null && objectivesId!=null) {
            gameViewModel.sendAnswerResult(patientId, doctorId, curriculumId, moduleId, objectivesId, specializationId,
                    termId, getCurrentDate(), baseLineId, getCurrentDateANDTime()).observe(this, new Observer<StateData<ResultInfo>>() {
                @Override
                public void onChanged(StateData<ResultInfo> resultInfoStateData) {
                    Log.d("TAG6", "onChanged: sendAnswerResult success " + resultInfoStateData.getData().getMessage());
                    Log.d("TAG6", "onChanged: sendAnswerResult success " + resultInfoStateData.getData().getMessage());

                    Log.d("TAG6", "sendAnsweredResult: doctorId " + doctorId);
                    Log.d("TAG6", "sendAnsweredResult: patientId " + patientId);
                    Log.d("TAG6", "sendAnsweredResult: termId " + termId);
                    Log.d("TAG6", "sendAnsweredResult: curriculumId " + curriculumId);
                    Log.d("TAG6", "sendAnsweredResult: moduleId " + moduleId);
                    Log.d("TAG6", "sendAnsweredResult: objectivesId " + objectivesId);
                    Log.d("TAG6", "sendAnsweredResult: specializationId " + specializationId);
                    Log.d("TAG6", "sendAnsweredResult: baseLineId " + baseLineId);
                    Log.d("TAG6", "sendAnsweredResult: getCurrentDate " + getCurrentDate());
                    Log.d("TAG6", "sendAnsweredResult: getCurrentDate " + getCurrentDateANDTime());
                    if (resultInfoStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                        Log.d("TAG6", "onChanged: sendAnswerResult success " + resultInfoStateData.getData().getMessage());
                        Toast.makeText(context, "sendAnswerResult success", Toast.LENGTH_SHORT).show();
                    } else if (resultInfoStateData.getStatus() == StateData.DataStatus.ERROR) {
                        Log.d("TAG6", "onChanged: error " + resultInfoStateData.getError());
                    }
                }
            });
        }
    }


    public void getPhaseList() {

        gameViewModel.getPhaseList(levelID, unitID).observe(getActivity(), listStateData -> {
            switch (listStateData.getStatus()) {
                case COMPLETE:
                    break;
                case ERROR:
                    break;

                case LOADING:
                    break;
                case SUCCESS:
                    phaseList.addAll(listStateData.getData());
                    lunchPhase(phaseList, phaseListPosition);
                    break;
            }

        });


    }


    public void lunchPhase(List<Phase> phases, int phaseListPosition) {
        Log.d("TAG8", "lunchPhase: " + phases.get(phaseListPosition));
        phaseAdapter = new PhaseAdapter(phases.get(phaseListPosition), context);
        binding.phaseViewPager.setAdapter(phaseAdapter);


    }


    public static void goNextPosition(ViewPager2 viewPager, int position) {
        position = position + 1;
        int count = viewPager.getAdapter().getItemCount();
        if (position >= 0 && position < count) {
            viewPager.setCurrentItem(position, true);
        }
    }

    private void initConnection() {

        String serverIp = preferenceManager.getString(Constants.IPKEY);
        if (serverIp != null) {
            try {
                teacherClient = new TeacherSocketClient(new URI("ws://" + serverIp + ":8887"));
                teacherClient.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onMessage(String Message) {
        try {

            JSONObject jsonObjectMessage = new JSONObject(Message);
            if (jsonObjectMessage.has("phaseAnswered")) {
                String messageBody = jsonObjectMessage.getString("phaseAnswered");
                PhaseAnswered phaseAnswered = gson.fromJson(messageBody, PhaseAnswered.class);
                handlePhaseAnsweredMessage(phaseAnswered);
            }
            if (jsonObjectMessage.has("messageExpressionWithResponse")) {
                String messageBody = jsonObjectMessage.getString("messageExpressionWithResponse");
                MessageExpression messageExpression = gson.fromJson(messageBody, MessageExpression.class);
                handleMessageExpressionMessage(messageExpression);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void handleMessageExpressionMessage(MessageExpression messageExpression) {
        if (messageExpression != null) {
            if (messageExpression.getEmotionName() != null) {
                gameViewModel.updatePhase(messageExpression.getPhaseId(), messageExpression.getLevelId(), messageExpression.getUnitId(), messageExpression.getResponse());
                gameViewModel.updatePhase(messageExpression.getPhaseId(), messageExpression.getLevelId(), messageExpression.getUnitId(), true);
                gameViewModel.setIsRobotAnsweredEmotion(messageExpression.getResponse());
            }
        }
    }

    private void handlePhaseAnsweredMessage(PhaseAnswered phaseAnswered) {

        if (phaseAnswered != null) {
            Log.d("TAG8", "handlePhaseAnsweredMessage:  StudentAnsweredLiveData.get().postSuccess(phaseAnswered.isAnswered());" + phaseAnswered.toString());
            StudentAnsweredLiveData.get().postSuccess(phaseAnswered.isAnswered());
            int pagerAdapterPosition = binding.phaseViewPager.getCurrentItem();
            goNextPosition(binding.phaseViewPager, pagerAdapterPosition);
            Log.d("TAG8", "handlePhaseAnsweredMessage: " + phaseAnswered.isAnswered());
        }

        if (phaseAnswered.isAnswered() == Constants.PHASE_ANSWERED_TRUE) {

            gameViewModel.updatePhase(phaseAnswered.getPhaseId(),
                    phaseAnswered.getLevelId(), phaseAnswered.getUnitId(), true).observe(this, new Observer<StateData<Phase>>() {
                @Override
                public void onChanged(StateData<Phase> phaseStateData) {
                    Log.d("TAG AnswerContent", "onChanged: " + phaseStateData.getData().toString());
                }
            });

        } else {
            gameViewModel.updatePhase(phaseAnswered.getPhaseId(),
                    phaseAnswered.getLevelId(), phaseAnswered.getUnitId(), false);
        }

    }

    @Override
    public void onMessage(ByteBuffer message) {

    }

    @Override
    public void onError(Exception ex) {

    }

    @Override
    public void onResume() {
        super.onResume();
        TeacherSocketClient.addIOnMessageListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TeacherSocketClient.removeIOnMessageListener(this);

    }

    @Override
    public void onWrongClicked() {
        next();
    }

    @Override
    public void onTureClicked() {
        next();
    }
}