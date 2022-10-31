package com.nuwa.robot.r2022.emotionalability.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuwa.robot.r2022.emotionalability.adapter.PhaseAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentPhaseBinding;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import java.util.ArrayList;
import java.util.List;

public class PhaseFragment extends Fragment {

    private final String TAG = "PhaseFragment";
    FragmentPhaseBinding binding;
    private PhaseAdapter phaseAdapter;
    private int phaseListPosition = 0;
    private GameViewModel gameViewModel;
    private List<Phase> phaseList = new ArrayList<>();

    int unitID;
    int levelID;
    FragmentActivity context;

    public PhaseFragment(int unitID, int levelID, FragmentActivity context) {
        this.unitID = unitID;
        this.levelID = levelID;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhaseBinding.inflate(inflater, container, false);


        initialization();
        gameViewModel.setPhaseFragmentLaunched(true);
        gameViewModel.getPhaseCompletedLiveData().postValue(0);
        getPhaseList();
        return binding.getRoot();

    }

    public void initialization() {
        gameViewModel = new ViewModelProvider(context).get(GameViewModel.class);

        gameViewModel.getNextButtonClickedLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    Log.d(TAG, "onChanged: observeOnNextButtonClick");
                    if (phaseAdapter == null) {
                        Log.d(TAG, "onChanged getCount:phaseAdapter null ");
                    } else {
                        Log.d(TAG, "onChanged getCount:phaseAdapter not null ");
                    }
                    if (phaseAdapter != null) {
                        next();
                    }
                }
            }
        });


    }


    public void next() {
        int pagerAdapterPosition = binding.phaseViewPager.getCurrentItem();
        Log.d(TAG, "next: getCurrentItem=" + pagerAdapterPosition);
        Fragment myFragment = getActivity().getSupportFragmentManager().findFragmentByTag("f" + binding.phaseViewPager.getCurrentItem());

        Log.d(TAG, "next:getSupportFragmentManager " +myFragment.getTag());
        Log.d(TAG, "next:getSupportFragmentManager " +myFragment.getId());
        Log.d(TAG, "next:getSupportFragmentManager " +myFragment.toString());


        if (pagerAdapterPosition == 2) {
            checkForLaunchNewPhase();
        } else {
            goNextPosition(binding.phaseViewPager, pagerAdapterPosition);
        }
        Log.d(TAG, "next: getCurrentItem= 2 ---" + pagerAdapterPosition);

    }

    private void checkForLaunchNewPhase() {
        gameViewModel.getPhase(phaseList.get(phaseListPosition).getId(), phaseList.get(phaseListPosition).getLevelId(),
                phaseList.get(phaseListPosition).getUnitId()).observe(requireActivity(), new Observer<StateData<Phase>>() {
            @Override
            public void onChanged(StateData<Phase> phaseStateData) {
                Phase phase = phaseStateData.getData();
                if (phase.isAnswered()) {
                    Log.d(TAG, "onChanged: isAnswered" + phase.isAnswered());
                    if (phaseListPosition == phaseList.size() - 1) {
                        Log.d(TAG, "onChanged: launchNewLevel");
                        gameViewModel.setPhaseFragmentLaunched(false);
                        gameViewModel.getPhaseCompletedLiveData().postValue(1);
                        phaseListPosition = 0;
                    } else {
                        phaseListPosition++;
                        lunchPhase(phaseList, phaseListPosition);
                        Log.d(TAG, "onChanged: lunch new phase");
                        Log.d(TAG, "onChanged: phaseListPosition " + phaseListPosition);
                    }
                } else {

                    lunchPhase(phaseList, phaseListPosition);
                    Log.d(TAG, "onChanged:  not isAnswered" + phase.isAnswered());
                    Log.d(TAG, "onChanged:  not isAnswered phaseList" + phaseList);
                    Log.d(TAG, "onChanged:  not isAnswered phaseListPosition" + phaseListPosition);

                }
            }
        });
    }

    public void getPhaseList() {
        gameViewModel.getPhaseList(levelID, unitID).observe(getActivity(), listStateData -> {
            switch (listStateData.getStatus()) {
                case COMPLETE:
                    break;
                case ERROR:
                    break;

                case LOADING:
                    break;
                case SUCCESS:
                    phaseList.addAll(listStateData.getData());
                    lunchPhase(phaseList, 0);
                    break;
            }

        });

    }


    public void lunchPhase(List<Phase> phases, int phaseListPosition) {
        phaseAdapter = new PhaseAdapter(phases.get(phaseListPosition), getActivity());
        binding.phaseViewPager.setAdapter(phaseAdapter);
    }


    public static void goNextPosition(ViewPager2 viewPager, int position) {
        position = position + 1;
        int count = viewPager.getAdapter().getItemCount();
        if (position >= 0 && position < count) {
            viewPager.setCurrentItem(position, true);
        }
    }

}