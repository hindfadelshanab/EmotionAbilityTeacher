package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.adapter.ImageOptionAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentAnswerContentBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnImageOptionSelectedListener;
import com.nuwa.robot.r2022.emotionalability.listener.OnMessageReciveListener;
import com.nuwa.robot.r2022.emotionalability.model.AnswerContent;
import com.nuwa.robot.r2022.emotionalability.model.ImageOption;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherClient;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import io.realm.Realm;


public class AnswerContentFragment extends Fragment implements OnMessageReciveListener {

    private FragmentAnswerContentBinding binding ;
    private Phase phase ;
    RobotController robotController ;
    Gson gson;
    private Realm realm ;
    private PreferenceManager preferenceManager;
    TeacherClient teacherClient;
    private GameViewModel gameViewModel;
    private FragmentActivity fragmentActivity ;
    public AnswerContentFragment(Phase phase , FragmentActivity fragmentActivity){
        this.phase = phase;
        this.fragmentActivity = fragmentActivity ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentAnswerContentBinding.inflate(inflater , container , false);




        initialization();

        initConnection();
        return binding.getRoot();
    }

    private void initialization(){
        robotController = new RobotController(getContext());
        realm = Realm.getDefaultInstance();
        gson = new Gson();
        preferenceManager= new PreferenceManager(getContext());
        gameViewModel = new ViewModelProvider(fragmentActivity).get(GameViewModel.class);
        if (phase!=null){
            Log.d("TAG", "AnswerContentFragment: " +phase);
            showAnswerContent(phase.getAnswerContent());
        }
    }


    private void showAnswerContent(AnswerContent answerContent) {

             PhaseAnswered     phaseAnswered = new PhaseAnswered();


        if (answerContent.isHaveImageForShow() && answerContent.getImageForShow().equals("ScaredChild")) {
           binding. imageForShow.setVisibility(View.VISIBLE);
            binding.imageForShow.setImageResource(R.drawable.scared_child);
        }


        if (answerContent.getAnswerWay() ==Constants.ANSWERED_WAY_TRU_FALSE) {


            phaseAnswered.setPhaseId(phase.getId());
            phaseAnswered.setLevelId(phase.getLevelId());
            phaseAnswered.setUnitId(phase.getUnitId());
            binding.layoutCorrectAnswer.setVisibility(View.VISIBLE);
            binding.layoutWrongAnswer.setVisibility(View.VISIBLE);


            binding.txtCorrect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Correct clicked", Toast.LENGTH_SHORT).show();
                    updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), true);

                    phaseAnswered.setAnswered(true);
                    PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);

                }
            });
           binding .txtWrong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Wrong clicked", Toast.LENGTH_SHORT).show();

                    phaseAnswered.setAnswered(false);
                    PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                    updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), false);

                }
            });

        } else if (answerContent.getAnswerWay() == Constants.ANSWERED_WAY_SELECT) {
            binding.layoutCorrectAnswer.setVisibility(View.GONE);
            binding.layoutWrongAnswer.setVisibility(View.GONE);
            binding.imageList.setVisibility(View.VISIBLE);
            String phaseJson = "";

            if (phase != null) {
                phase = realm.copyFromRealm(phase); //detach from Realm, copy values to fields
                phaseJson = gson.toJson(phase);
            }

            Log.d("TAG AnswerContent", "showAnswerContent ==phase: " + phase.toString());
            Log.d("TAG AnswerContent", "showAnswerContent ==phaseJson: " + phaseJson);

            robotController.sendMessageForStudent(phaseJson);
            Log.d("TAG AnswerContent", "showAnswerContent message Send: ");


            ImageOptionAdapter adapter = new ImageOptionAdapter(getActivity(), answerContent.getImageOptions(), new OnImageOptionSelectedListener() {
                @Override
                public void OnImageOptionSelected(ImageOption imageOption) {

                    if (imageOption.getIsCorrect() == 1) {
                        phaseAnswered.setAnswered(true);
                        PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                        updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), true);

                        Toast.makeText(getActivity(), "Selected emotion" + imageOption.getIsCorrect(), Toast.LENGTH_SHORT).show();

                    } else if (imageOption.getIsCorrect() == 0) {
                        phaseAnswered.setAnswered(false);
                        PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                        updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), false);

                    }
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            binding.imageList.setLayoutManager(layoutManager);
            binding.imageList.setAdapter(adapter);
        }
    }


    private void updatePhase(int phaseId, int levelId, int unitId, boolean isAnswered) {
        Realm realm = Realm.getDefaultInstance();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                phase.setAnswered(isAnswered);
                realm.copyToRealmOrUpdate(phase);
            }
        });


    }


    private void initConnection() {

        String serverIp = preferenceManager.getString(Constants.IPKEY);
        if (serverIp != null) {
            try {
                Log.d("TAG", "RobotController: " + serverIp);
                teacherClient = new TeacherClient(new URI("ws://" + serverIp + ":8887"), this);


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                teacherClient.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void OnMessageRecive(String Message) {
        try {
            JSONObject jObj = new JSONObject(Message);
            String action = jObj.getString(Constants.MESSAGE_FOR_ANSWERED_KEY);

            PhaseAnswered phaseAnswered = gson.fromJson(action, PhaseAnswered.class);

            phaseAnswered.isAnswered();

            if(phaseAnswered.isAnswered()) {
                Log.d("TAG AnswerContent", "OnMessageRecive: phaseAnswered.isAnswered() "+phaseAnswered.isAnswered());
                Log.d("TAG AnswerContent", "OnMessageRecive: phaseAnswered.isAnswered() "+phaseAnswered);


                gameViewModel.updatePhase(phaseAnswered.getPhaseId(),
                        phaseAnswered.getLevelId(), phaseAnswered.getUnitId(), true).observe(this, new Observer<StateData<Phase>>() {
                    @Override
                    public void onChanged(StateData<Phase> phaseStateData) {
                        Log.d("TAG AnswerContent", "onChanged: "+phaseStateData.getData().toString());
                    }
                });
                PhaseAnsweredLiveData.reset().postLoading();

                PhaseAnsweredLiveData.reset().postSuccess(phaseAnswered);


            } else {
                gameViewModel.updatePhase(phaseAnswered.getPhaseId(),
                        phaseAnswered.getLevelId(), phaseAnswered.getUnitId(), false);
                PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);

            }
            Log.d("TAG AnswerContent", "OnMessageRecive phaseAnswered: " + phaseAnswered.isAnswered());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}