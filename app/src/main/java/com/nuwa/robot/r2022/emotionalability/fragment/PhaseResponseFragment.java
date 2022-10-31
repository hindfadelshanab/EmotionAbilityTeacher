package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentResponsBinding;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;


public class PhaseResponseFragment extends Fragment {


    FragmentResponsBinding binding;
    FragmentActivity fragmentActivity ;
    Phase phase;

    public PhaseResponseFragment(Phase phase , FragmentActivity fragmentActivity) {

        this.phase = phase;
        this.fragmentActivity = fragmentActivity ;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResponsBinding.inflate(inflater, container, false);

        if (phase !=null){
                PhaseAnsweredLiveData.get().observe((LifecycleOwner) fragmentActivity, new Observer<StateData<PhaseAnswered>>() {
                    @Override
                    public void onChanged(StateData<PhaseAnswered> phaseAnsweredStateData) {

                        PhaseAnswered phaseAnswered = phaseAnsweredStateData.getData();
                        Log.d("TAG", "onChanged observe: " + phaseAnswered.isAnswered());
                        Log.d("TAG", "onChanged observe: " + phaseAnswered.getPhaseId());
                        if ((phaseAnswered.getPhaseId() == phase.getId() && phaseAnswered.isAnswered()) || phase.isAnswered()) {


                            Log.d("TAG", "onChanged: " +phase.isAnswered());
                            Log.d("TAG", "onChanged: " +phase.getResponse());
                            binding.txtQuestionTitle.setText(phase.getResponse());
//                            robotController.sendMessageToRobot(phase.getResponse());
//                    updatePhase(phase.getId() ,phase.getLevelId() , phase.getUnitId() ,true);

//                    phase.setAnswered(false);
                        } else if ((phaseAnswered.getPhaseId() == phase.getId() && !phaseAnswered.isAnswered()) || !phase.isAnswered()) {
                            binding.txtQuestionTitle.setText(Constants.LET_IS_TRY_AGAIN_VALUE);
//                            robotController.sendMessageToRobot(Constants.LET_IS_BEGIN_VALUE);
//                    updatePhase(phase.getId() ,phase.getLevelId() , phase.getUnitId() ,false);

                        }
                    }
                });

        }

        return binding.getRoot();
    }
}