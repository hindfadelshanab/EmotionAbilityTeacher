package com.nuwa.robot.r2022.emotionalability.fragment;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.PHASE_ANSWERED_FALSE;
import static com.nuwa.robot.r2022.emotionalability.utils.Constants.PHASE_ANSWERED_TRUE;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.adapter.ImageOptionAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentAnswerContentBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnImageOptionSelectedListener;
import com.nuwa.robot.r2022.emotionalability.model.AnswerContent;
import com.nuwa.robot.r2022.emotionalability.model.ImageOption;
import com.nuwa.robot.r2022.emotionalability.model.Message;
import com.nuwa.robot.r2022.emotionalability.model.MessageExpression;
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


public class AnswerContentFragment extends Fragment implements View.OnClickListener {

    private FragmentAnswerContentBinding binding;
    private Phase phase;
    RobotController robotController;
    Gson gson;
    private Realm realm;
    private FragmentActivity fragmentActivity;
    private ImageOptionAdapter imageOptionAdapter;

   public static  IButtonClicked iButtonClicked ;

    public AnswerContentFragment(Phase phase, FragmentActivity fragmentActivity) {
        this.phase = phase;
        this.fragmentActivity = fragmentActivity;

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
        binding = FragmentAnswerContentBinding.inflate(inflater, container, false);


        try {
            initialization();
        }catch (Exception e){
            Log.d("TAG8", "onCreateView:  Exception "+e.getMessage());
        }

        return binding.getRoot();
    }



    private void initialization() {
        robotController = new RobotController(getContext());
        realm = Realm.getDefaultInstance();
        gson = new Gson();
        if (phase != null) {
            showAnswerContent(phase.getAnswerContent());
        }
        PhaseAnsweredLiveData.get().postValue( PhaseAnsweredLiveData.get().getValue().success(new
                PhaseAnswered()));

//
//
        Log.d("TAG10", "initialization: anserw content" +  StudentAnsweredLiveData.get().getValue().getData());
    }


    private void showAnswerContent(AnswerContent answerContent) {


        switch (answerContent.getAnswerWay()) {
            case Constants.ANSWERED_WAY_TRU_FALSE:
                showTrueFalseAnswerContent();
                break;
            case Constants.ANSWERED_WAY_SELECT:
                showSelectContent(answerContent);
                break;
            case Constants.ANSWERED_WAY_DRAG:
                showDragContent(answerContent);
                break;
            case Constants.ANSWERED_WAY_SELECT_FROM_TABLE:
                showEmotionTable();
                break;
        }



        if (answerContent.isHaveImageForShow() ) {
            binding.imageForShow.setVisibility(View.VISIBLE);
            switch (answerContent.getImageForShow()){
                case "ScaredChild":
                    binding.imageForShow.setImageResource(R.drawable.scared_child);
                    break;
                case "rectangle":
                    binding.imageForShow.setImageResource(R.drawable.orange_rectangle);

                    break;
                case "circle":
                    binding.imageForShow.setImageResource(R.drawable.large_circle);

                    break;
            }

        }


    }

    private void showDragContent(AnswerContent answerContent) {
        String phaseJson = "";

        if (phase != null) {
            phase = realm.copyFromRealm(phase);
            phaseJson = gson.toJson(phase);
        }
        robotController.sendMessageForStudent(phaseJson);
    }

    private void showSelectContent(AnswerContent answerContent) {
        binding.layoutCorrectAnswer.setVisibility(View.GONE);
        binding.layoutWrongAnswer.setVisibility(View.GONE);
        binding.imageList.setVisibility(View.VISIBLE);
        String phaseJson = "";

        if (phase != null) {
            phase = realm.copyFromRealm(phase);
            phaseJson = gson.toJson(phase);
        }
        robotController.sendMessageForStudent(phaseJson);
        initAdapter(answerContent);

    }

    private void initAdapter(AnswerContent answerContent) {
        imageOptionAdapter = new ImageOptionAdapter(getActivity(), answerContent.getImageOptions(), answerContent.getAnswerWay(), null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.imageList.setLayoutManager(layoutManager);
        binding.imageList.setAdapter(imageOptionAdapter);
    }

    @SuppressLint("ResourceAsColor")
    private void showTrueFalseAnswerContent() {


        PhaseAnswered phaseAnswered = new PhaseAnswered();
        phaseAnswered.setPhaseId(phase.getId());
        phaseAnswered.setLevelId(phase.getLevelId());
        phaseAnswered.setUnitId(phase.getUnitId());
        binding.layoutCorrectAnswer.setVisibility(View.VISIBLE);
        binding.layoutWrongAnswer.setVisibility(View.VISIBLE);
        phaseAnswered.setAnswered(0);
        Log.d("TAG9", "PhaseAnswered:" +phaseAnswered);

//        PhaseAnsweredLiveData.get().postValue(phaseAnswered);
//        PhaseAnsweredLiveData.get().getValue();

//        PhaseAnsweredLiveData.get().postValue( PhaseAnsweredLiveData.get().getValue().success(phaseAnswered));


        Log.d("TAG9", "phaseAnsweredStateData:" +phaseAnswered.isAnswered());

        binding.txtCorrect.setOnClickListener(view -> {
            updatePhase( true);
            phaseAnswered.setAnswered(PHASE_ANSWERED_TRUE);
            PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
            if (iButtonClicked != null){
                iButtonClicked.onTureClicked();
            }

        });


        binding.txtWrong.setOnClickListener(view -> {
            phaseAnswered.setAnswered(PHASE_ANSWERED_FALSE);
            PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
            updatePhase( false);
            if (iButtonClicked != null){
                iButtonClicked.onWrongClicked();
            }
        });

    }

    private void showEmotionTable( ) {
        binding.layoutCorrectAnswer.setVisibility(View.GONE);
        binding.layoutWrongAnswer.setVisibility(View.GONE);
        binding.imageList.setVisibility(View.VISIBLE);
        imageOptionAdapter = new ImageOptionAdapter(getActivity(), phase.getAnswerContent().getImageOptions(), phase.getAnswerContent().getAnswerWay(), new OnImageOptionSelectedListener() {
            @Override
            public void OnImageOptionSelected(ImageOption imageOption) {
                sendMessageExpressionForRobot(imageOption);
            }
        });

        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        binding.imageList.setLayoutManager(layoutManager);
        binding.imageList.setAdapter(imageOptionAdapter);


    }

    private void sendMessageExpressionForRobot(ImageOption imageOption) {
        MessageExpression  messageExpression = new MessageExpression();
        messageExpression.setMessageId(9);
        messageExpression.setPhaseId(phase.getId());
        messageExpression.setLevelId(phase.getLevelId());
        messageExpression.setUnitId(phase.getUnitId());
        messageExpression.setEmotionName(imageOption.getDescription());
        String  messageJSon=  gson.toJson(messageExpression);
        messageJSon = "{\"messageExpression\":" + messageJSon + "}";

        robotController.sendMessageForRobot(messageJSon);
    }


    private void updatePhase( boolean isAnswered) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                phase.setAnswered(isAnswered);
                realm.copyToRealmOrUpdate(phase);
            }
        });


    }


    public interface IButtonClicked{
        void onWrongClicked();
        void onTureClicked();
    }


    @Override
    public void onClick(View view) {


    }
    public static void  setIButtonClickedItem(IButtonClicked i){
        iButtonClicked = i ;
    }
}