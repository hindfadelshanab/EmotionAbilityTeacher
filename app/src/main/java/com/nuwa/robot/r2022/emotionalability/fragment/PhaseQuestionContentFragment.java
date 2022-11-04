package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentPhaseQuestionContentBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnMessageReciveListener;
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

public class PhaseQuestionContentFragment extends Fragment{


    FragmentPhaseQuestionContentBinding binding;
    RobotController robotController;
    private Phase phase;




    public PhaseQuestionContentFragment(Phase phase) {
        this.phase = phase;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentPhaseQuestionContentBinding.inflate(inflater, container, false);

        initialization();

        return binding.getRoot();
    }

    private  void initialization(){
        robotController = new RobotController(getContext());

        if (phase != null) {
            String phaseTitle =phase.getQuestioncontent().getTitle();
            String title = phaseTitle.substring(phaseTitle.indexOf("?")+1 ,phaseTitle.lastIndexOf("?"));
            String titleIntro = phaseTitle.substring(0,phaseTitle.indexOf("?"));

            binding.txtQuestionTitle.setText(title);
            binding.txtIntro.setText(titleIntro);
            if (phase.getQuestioncontent().getImage() != null && phase.getQuestioncontent().getImage().equals("HappyFace"))
                robotController.sendMessageForRobot(Constants.MESSAGE_FOR_SHOW_IMAGE);
        }
    }


}