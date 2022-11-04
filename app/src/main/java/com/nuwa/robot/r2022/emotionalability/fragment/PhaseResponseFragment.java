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
    public String TAG ="PhaseResponseFragment";

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

            Log.d("PhaseResponseFragment", "onCreateView: phase" +phase.toString());
            if ( phase.isAnswered()) {
                handleRightRespond(phase.getResponse());
            } else if (!phase.isAnswered()) {
                Log.d(TAG, "onCreateView: " +phase.isAnswered());
                handleWrongRespond();

            }
                PhaseAnsweredLiveData.get().observe((LifecycleOwner) fragmentActivity, new Observer<StateData<PhaseAnswered>>() {
                    @Override
                    public void onChanged(StateData<PhaseAnswered> phaseAnsweredStateData) {

                        PhaseAnswered phaseAnswered = phaseAnsweredStateData.getData();
                        if ((phaseAnswered.getPhaseId() == phase.getId() && phaseAnswered.isAnswered()) ) {
                            handleRightRespond(phase.getResponse());


                        } else if ((phaseAnswered.getPhaseId() == phase.getId() && !phaseAnswered.isAnswered()) ) {
                            handleWrongRespond();


                        }
                    }
                });

        }

        return binding.getRoot();
    }

    private void handleWrongRespond() {

        binding.txtIntro.setText("Let’s");
        binding.txtQuestionTitle.setText("try again.");
    }

    private void handleRightRespond(String phaseResponse) {

        String responseIntro = phaseResponse.substring(0 , phaseResponse.indexOf("!"));
        String response = phaseResponse.substring(phaseResponse.indexOf("!")+1 );

        binding.txtIntro.setText(responseIntro);
        binding.txtQuestionTitle.setText(response);

    }
}