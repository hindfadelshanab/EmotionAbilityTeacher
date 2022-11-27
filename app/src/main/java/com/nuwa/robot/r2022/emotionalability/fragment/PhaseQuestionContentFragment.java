package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentPhaseQuestionContentBinding;
import com.nuwa.robot.r2022.emotionalability.model.Message;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;

public class PhaseQuestionContentFragment extends Fragment {


    FragmentPhaseQuestionContentBinding binding;
    RobotController robotController;
    private Phase phase;
    Gson gson;
    FragmentActivity fragmentActivity;

    public PhaseQuestionContentFragment(Phase phase, FragmentActivity fragmentActivity) {
        this.phase = phase;
        this.fragmentActivity = fragmentActivity;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhaseQuestionContentBinding.inflate(inflater, container, false);
        gson = new Gson();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();

    }

    private void initialization() {
        robotController = new RobotController(getContext());

        if (phase != null) {
            handlePhaseTitle();

            if (phase.getQuestioncontent().getImage() != null && phase.getQuestioncontent().getImage().equals("HappyFace")) {
                sendShowImageMessageForRobot();
            }
            if (!phase.getQuestioncontent().getFaceExpression().equals("")) {
                sendFaceExpressionForRobot();
            }
            if (!phase.getQuestioncontent().getBodyMotion().equals("")) {
                sendBodyMotionForRobot();
            }
        }

    }

    private void sendBodyMotionForRobot() {
        Message message = new Message(6 ,Constants.MESSAGE_FOR_BODY_MOTION_KEY, phase.getQuestioncontent().getBodyMotion());

        String messageJson = gson.toJson(message);
        messageJson = "{\"message\":" + messageJson + "}";

        robotController.sendMessageForRobot(messageJson);
    }

    private void sendFaceExpressionForRobot() {
        Message message = new Message(5,Constants.MESSAGE_FOR_FACE_EXPRESSION_KEY, phase.getQuestioncontent().getFaceExpression());

        String messageJson = gson.toJson(message);
        messageJson = "{\"message\":" + messageJson + "}";
        robotController.sendMessageForRobot(messageJson);
    }

    private void sendShowImageMessageForRobot() {
        Message message = new Message(7, Constants.MESSAGE_FOR_SHOW_IMAGE_KEY, Constants.MESSAGE_FOR_SHOW_IMAGE);
        String messageJSon = gson.toJson(message);
        messageJSon = "{\"message\":" + messageJSon + "}";
        robotController.sendMessageForRobot(messageJSon);
    }

    private void handlePhaseTitle() {

        String phaseTitle = phase.getQuestioncontent().getTitle();
        String title = phaseTitle.substring(phaseTitle.indexOf("?") + 1, phaseTitle.lastIndexOf("?"));
        String titleIntro = phaseTitle.substring(0, phaseTitle.indexOf("?"));
        binding.txtQuestionTitle.setText(title);
        binding.txtIntro.setText(titleIntro);

        sendMessageForRobot(4 ,phaseTitle);

    }

    private void sendMessageForRobot(int id ,String title) {
        Message messageTitle = new Message(id ,Constants.MESSAGE_FOR_KEBHI_SPECK, title);
        String messageJSonTitle = gson.toJson(messageTitle);
        messageJSonTitle = "{\"message\":" + messageJSonTitle + "}";
        robotController.sendMessageForRobot(messageJSonTitle);
    }


}