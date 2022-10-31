package com.nuwa.robot.r2022.emotionalability.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.adapter.ImageOptionAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.FragmentAnswerContentBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnImageOptionSelectedListener;
import com.nuwa.robot.r2022.emotionalability.model.AnswerContent;
import com.nuwa.robot.r2022.emotionalability.model.ImageOption;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;

import io.realm.Realm;


public class AnswerContentFragment extends Fragment {

    private FragmentAnswerContentBinding binding ;
    private Phase phase ;
    private     PhaseAnswered phaseAnswered ;
    public AnswerContentFragment(Phase phase){
        this.phase = phase;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentAnswerContentBinding.inflate(inflater , container , false);
        if (phase!=null){
            Log.d("TAG", "AnswerContentFragment: " +phase);
            showAnswerContent(phase.getAnswerContent());
        }
        return binding.getRoot();
    }

    private void showAnswerContent(AnswerContent answerContent) {

//        LinearLayout linearLayout = view.findViewById(R.id.layoutCorrectAnswer);
//        LinearLayout linearLayout2 = view.findViewById(R.id.layoutWrongAnswer);
//        Button textView = view.findViewById(R.id.txtCorrect);
//        Button textViewWrong = view.findViewById(R.id.txtWrong);
//        RecyclerView listView = view.findViewById(R.id.imageList);
//        ImageView imageForShow = view.findViewById(R.id.imageForShow);


        if (answerContent.isHaveImageForShow() && answerContent.getImageForShow().equals("ScaredChild")) {
           binding. imageForShow.setVisibility(View.VISIBLE);
            binding.imageForShow.setImageResource(R.drawable.scared_child);
        }


        if (answerContent.getAnswerWay() == 1) {

             phaseAnswered = new PhaseAnswered();
            phaseAnswered.setPhaseId(phase.getId());
            phaseAnswered.setLevelId(phase.getLevelId());
            phaseAnswered.setUnitId(phase.getUnitId());
            binding.layoutCorrectAnswer.setVisibility(View.VISIBLE);
            binding.layoutWrongAnswer.setVisibility(View.VISIBLE);


            binding.txtCorrect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Correct clicked", Toast.LENGTH_SHORT).show();
                    updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), true);

                    phaseAnswered.setAnswered(true);
                    PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);

                }
            });
           binding .txtWrong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Wrong clicked", Toast.LENGTH_SHORT).show();

                    phaseAnswered.setAnswered(false);
                    PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                    updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), false);

                }
            });

        } else if (answerContent.getAnswerWay() == 2) {
            binding.layoutCorrectAnswer.setVisibility(View.GONE);
            binding.layoutWrongAnswer.setVisibility(View.GONE);
            binding.imageList.setVisibility(View.VISIBLE);
            String phaseJson = "";

//            if (phase2 != null) {
//                phase2 = realm.copyFromRealm(phase2); //detach from Realm, copy values to fields
//                phaseJson = gson.toJson(phase2);
//            }

            Log.d("TAG", "showAnswerContent ==phase: " + phase.toString());
            Log.d("TAG", "showAnswerContent ==phaseJson: " + phaseJson);

//            robotController.sendMessageForStudent(phaseJson);
            Log.d("TAG", "showAnswerContent message Send: ");


            ImageOptionAdapter adapter = new ImageOptionAdapter(getActivity(), answerContent.getImageOptions(), new OnImageOptionSelectedListener() {
                @Override
                public void OnImageOptionSelected(ImageOption imageOption) {

                    if (imageOption.getIsCorrect() == 1) {
                        phaseAnswered.setAnswered(true);
                        PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                        updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), true);

                        Toast.makeText(getActivity(), "Selected emotion" + imageOption.getIsCorrect(), Toast.LENGTH_SHORT).show();

                    } else if (imageOption.getIsCorrect() == 0) {
                        phaseAnswered.setAnswered(false);
                        PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                        updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), false);

                    }
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            binding.imageList.setLayoutManager(layoutManager);
            binding.imageList.setAdapter(adapter);
        }
    }


    private void updatePhase(int phaseId, int levelId, int unitId, boolean isAnswered) {
        Realm realm = Realm.getDefaultInstance();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                phase.setAnswered(isAnswered);
                realm.copyToRealmOrUpdate(phase);
            }
        });


    }

}