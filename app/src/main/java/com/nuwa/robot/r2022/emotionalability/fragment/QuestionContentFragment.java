package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuwa.robot.r2022.emotionalability.databinding.FragmentQuestionContentBinding;


public class QuestionContentFragment extends Fragment {

    private FragmentQuestionContentBinding binding;
    private String content;
    private int position;

    public QuestionContentFragment(String gameName, int position) {
        // Required empty public constructor
        this.content = gameName;
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestionContentBinding.inflate(inflater, container, false);
        Log.d("TAG", "createFragment:content " + content);

        if (content != null) {

            switch (position) {
                case 0:
                    handleGameName(content);
                    break;
                case 1:
                    handleGameDesc(content);
                    break;
                case 2:
                    handleLetISBegin();
                    break;
            }
        }
        return binding.getRoot();
    }

    private void handleLetISBegin() {
        binding.txtIntro.setText("Let’s");
        binding.txtGameDesc2.setVisibility(View.GONE);
        binding.txtGame.setVisibility(View.GONE);
        binding.txtQuestionTitle.setText("Begin !");
    }

    private void handleGameDesc(String content) {
        String gameIntro =content.substring(0 ,content.indexOf("?"));
        String gameDesc =content.substring(content.indexOf("?")+1 ,content.lastIndexOf("?"));
        String gameDesc2 =content.substring(content.lastIndexOf("?") +1);
        binding.txtGame.setVisibility(View.GONE);

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