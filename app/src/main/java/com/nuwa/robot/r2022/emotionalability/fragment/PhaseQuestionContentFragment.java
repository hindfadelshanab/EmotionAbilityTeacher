package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentPhaseQuestionContentBinding;
import com.nuwa.robot.r2022.emotionalability.model.Phase;

public class PhaseQuestionContentFragment extends Fragment {



    FragmentPhaseQuestionContentBinding binding ;
    private Phase phase ;
    public PhaseQuestionContentFragment(Phase phase ){
        this.phase =phase ;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentPhaseQuestionContentBinding.inflate(inflater , container , false) ;

        if (phase !=null){
            binding.txtQuestionTitle.setText(phase.getQuestioncontent().getTitle());

        }
        return binding .getRoot();
    }
}