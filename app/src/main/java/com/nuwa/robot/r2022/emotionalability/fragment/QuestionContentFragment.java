package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentQuestionContentBinding;
import com.nuwa.robot.r2022.emotionalability.model.Message;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;


public class QuestionContentFragment extends Fragment {

    private FragmentQuestionContentBinding binding;
    private String content;
    private int position;
    Gson gson;
    RobotController robotController ;
    GameViewModel gameViewModel;
    FragmentActivity fragmentActivity ;

    public QuestionContentFragment(String gameName, int position ,FragmentActivity fragmentActivity ) {
        // Required empty public constructor
        this.content = gameName;
        this.position = position;
        this.fragmentActivity = fragmentActivity ;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        gameViewModel = new ViewModelProvider(fragmentActivity).get(GameViewModel.class);
        if (menuVisible){
            sendMessageToRobot(position ,content);
            gameViewModel.setIsFirstItemInPhase(false);
            Log.d("Viewpager", " AnswerContentFragment fragment is  visible ");

        }else {
            Log.d("Viewpager", " AnswerContentFragment fragment is not visible ");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestionContentBinding.inflate(inflater, container, false);
        Log.d("TAG", "createFragment:content " + content);
        robotController = new RobotController(getContext() );
        gson = new Gson();

        if (content != null) {

            switch (position) {
                case 0:
                    handleGameName(content);
//                    sendMessageToRobot(content);
                    break;
                case 1:
                    handleGameDesc(content);
//                    sendMessageToRobot(content);

                    break;
                case 2:
                    handleLetISBegin();
//                    sendMessageToRobot(content);
                    break;
            }
        }
        return binding.getRoot();
    }

    private void sendMessageToRobot(int position ,String content) {
        Message message = new Message((position+1),Constants.MESSAGE_FOR_KEBHI_SPECK, content);

       String messageJSon = gson.toJson(message) ;
        messageJSon = "{\"message\":" + messageJSon + "}";

        Log.d("TAG", "sendMessageToRobot:messageJSon " +messageJSon);
        robotController.sendMessageForRobot(messageJSon);
    }

    private void handleLetISBegin() {
        binding.txtIntro.setText("Let’s");
        binding.txtGameDesc2.setVisibility(View.GONE);
        binding.txtGame.setVisibility(View.GONE);
        binding.txtQuestionTitle.setText("Begin !");
    }

    private void handleGameDesc(String content) {
        String gameIntro =content.substring(0 ,content.indexOf("?"));
        String gameDesc =content.substring(content.indexOf("?")+1 ,content.lastIndexOf("*"));
        String gameDesc2 =content.substring(content.indexOf("*") +1);
        binding.txtGame.setVisibility(View.GONE);
        binding.txtGameDesc2.setVisibility(View.VISIBLE);

        binding.txtIntro.setText(gameIntro);
        binding.txtQuestionTitle.setText(gameDesc);
        binding.txtGameDesc2.setText(gameDesc2);

    }

    private void handleGameName(String content) {
     String gameName =content.substring(content.indexOf("?")+1 ,content.lastIndexOf("?"));
        binding.txtQuestionTitle.setText(gameName);
        binding.txtGameDesc2.setVisibility(View.GONE);


        Log.d("TAG", "handleGameName content.indexOf(\"?\"): " +content.indexOf("?"));
        Log.d("TAG", "handleGameName content.indexOf(\"?\"): " +content.indexOf("?"));
        Log.d("TAG", "handleGameName content.indexOf(\"?\"): " +gameName);

    }
}