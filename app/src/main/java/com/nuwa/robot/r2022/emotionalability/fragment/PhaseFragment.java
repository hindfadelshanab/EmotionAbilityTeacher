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

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.adapter.PhaseAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentPhaseBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnStudentAnsweredListener;
import com.nuwa.robot.r2022.emotionalability.model.MessageExpression;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.model.StudentAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherSocketClient;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.view.GameLevelActivity;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class PhaseFragment extends Fragment implements TeacherSocketClient.IOnMessageListener {

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

    private void backToHome() {
        Phase phase2 = realm.where(Phase.class).equalTo("id", phaseList.get(phaseListPosition).getId())
                .equalTo("levelId", phaseList.get(phaseListPosition).getLevelId())
                .equalTo("unitId", phaseList.get(phaseListPosition).getUnitId())
                .findFirst();
        if (phase2.isAnswered()) {
            if (phaseListPosition == phaseList.size() - 1) {
                context.startActivity(new Intent(context, GameLevelActivity.class));
                preferenceManager.putInt(Constants.LEVEL_ENDED_ID_Key, phaseList.get(phaseListPosition).getLevelId());
                preferenceManager.putInt(Constants.UNIT_ENDED_ID_KEY, phase2.getUnitId());
            } else {
                phaseListPosition++;
                lunchPhase(phaseList, phaseListPosition);
            }
        } else {
            lunchPhase(phaseList, phaseListPosition);

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
            Log.d(TAG, "handlePhaseAnsweredMessage:  StudentAnsweredLiveData.get().postSuccess(phaseAnswered.isAnswered());" +phaseAnswered.toString());
//            gameViewModel.setStudentAnsweredPhase(phaseAnswered.isAnswered());
            StudentAnsweredLiveData.get().postSuccess(phaseAnswered.isAnswered());
            Log.d("TAG2", "handlePhaseAnsweredMessage: " +phaseAnswered.isAnswered());
//            onStudentAnsweredListeners.forEach( i -> i.OnStudentAnswered(phaseAnswered.isAnswered()));
        }

        if (phaseAnswered.isAnswered()) {

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
}