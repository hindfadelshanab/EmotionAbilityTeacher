package com.nuwa.robot.r2022.emotionalability.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nuwa.robot.r2022.emotionalability.adapter.PhaseAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentPhaseBinding;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.view.GameActivity;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

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
    private Realm  realm ;

    public PhaseFragment(int levelID, int unitID, FragmentActivity context) {
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


        realm =Realm.getDefaultInstance();
        gameViewModel.getNextButtonClickedLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
//                    Log.d(TAG, "onChanged: observeOnNextButtonClick");
                    if (phaseAdapter == null) {
//                        Log.d(TAG, "onChanged getCount:phaseAdapter null ");
                    } else {
//                        Log.d(TAG, "onChanged getCount:phaseAdapter not null ");
                    }
                    if (phaseAdapter != null) {
//                        binding.phaseViewPager.setCurrentItem(1, false);
                        next();
                    }
                }
            }
        });


        // set default position to 1 instead of default 0

    }


    public void next() {
        int pagerAdapterPosition = binding.phaseViewPager.getCurrentItem();
        Log.d(TAG, "next: getCurrentItem=" + pagerAdapterPosition);


        goNextPosition(binding.phaseViewPager, pagerAdapterPosition);

        if (pagerAdapterPosition == 2) {
            backToHome();
        }else{
            goNextPosition(binding.phaseViewPager, pagerAdapterPosition);

        }








    }

    private void backToHome() {
        Phase phase2 = realm.where(Phase.class).equalTo("id",phaseList.get(phaseListPosition).getId())
                .equalTo("levelId" ,phaseList.get(phaseListPosition).getLevelId())
                .equalTo("unitId" ,phaseList.get(phaseListPosition).getUnitId())
                .findFirst();
        if (phase2.isAnswered()) {
            if (phaseListPosition == phaseList.size() - 1) {
                context.startActivity(new Intent(context , GameActivity.class));
                Log.d(TAG, "onChanged Phase:  back to home" );
            } else {
                Log.d(TAG, "onChanged Phase:  lunch new phase " );

                phaseListPosition++;
                lunchPhase(phaseList, phaseListPosition);

            }
        } else {

            Log.d(TAG, "onChanged Phase:  return  phase " );
            Log.d(TAG, "onChanged Phase if not answered: "+phase2.isAnswered());
            lunchPhase(phaseList, phaseListPosition);

        }


//        gameViewModel.getPhase(phaseList.get(phaseListPosition).getId(), phaseList.get(phaseListPosition).getLevelId(),
//                phaseList.get(phaseListPosition).getUnitId()).observe(requireActivity(), new Observer<StateData<Phase>>() {
//            @Override
//            public void onChanged(StateData<Phase> phaseStateData) {
//                Phase phase = phaseStateData.getData();
//                Log.d(TAG, "onChanged Phase: "+phase.isAnswered());
//                Log.d(TAG, "onChanged Phase id: "+phase.getId());
//                Log.d(TAG, "onChanged Phase:phaseListPosition "+phaseListPosition);
//
//
//                if (phase.isAnswered()) {
//                    if (phaseListPosition == phaseList.size() - 1) {
//                        context.startActivity(new Intent(context , GameActivity.class));
//                        Log.d(TAG, "onChanged Phase:  back to home" );
//                    } else {
//                        Log.d(TAG, "onChanged Phase:  lunch new phase " );
//
//                        phaseListPosition++;
//                        lunchPhase(phaseList, phaseListPosition);
//
//                    }
//                } else {
//
//                    Log.d(TAG, "onChanged Phase:  return  phase " );
//                    Log.d(TAG, "onChanged Phase if not answered: "+phase.isAnswered());
//                    lunchPhase(phaseList, phaseListPosition);
//
//                }
//            }
//        });
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
//        Log.d(TAG, "getPhaseList: levelID" +levelID);
//        Log.d(TAG, "getPhaseList: unitID" +unitID);
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
                    lunchPhase(phaseList, phaseListPosition);
                    break;
            }

        });



//        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Log.d(TAG, "onTabSelected getId: "+tab.getId());
//                Log.d(TAG, "onTabSelected getPosition: "+tab.getPosition());
//                if (tab.getPosition() == 2) {
//                    checkForLaunchNewPhase();
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }


    public void lunchPhase(List<Phase> phases, int phaseListPosition) {
        Log.d(TAG, "lunchPhase:phaseListPosition "+phaseListPosition);
        phaseAdapter = new PhaseAdapter(phases.get(phaseListPosition),context);
        binding.phaseViewPager.setAdapter(phaseAdapter);
//        new TabLayoutMediator(binding.tabLayout, binding.phaseViewPager, (tab, position) -> {
//        }).attach();

    }


    public static void goNextPosition(ViewPager2 viewPager, int position) {
        position = position + 1;
        Log.d("TAG", "goNextPosition: " + position);
        int count = viewPager.getAdapter().getItemCount();
        if (position >= 0 && position < count) {
            viewPager.setCurrentItem(position, true);
        }
    }


}