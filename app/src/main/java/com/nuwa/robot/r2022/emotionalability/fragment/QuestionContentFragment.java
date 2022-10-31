package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentQuestionContentBinding;


public class QuestionContentFragment extends Fragment {

    private FragmentQuestionContentBinding binding ;
   private String content ;
    public QuestionContentFragment(String content) {
        // Required empty public constructor
        this.content = content ;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestionContentBinding.inflate(inflater , container , false);
        Log.d("TAG", "createFragment:content "+content);

        if (content !=null){
            binding.txtQuestionTitle.setText(content);
        }
        return binding.getRoot();
    }
}