package com.nuwa.robot.r2022.emotionalability.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.database.GameDatabase;
import com.nuwa.robot.r2022.emotionalability.fragment.AnswerContentFragment;
import com.nuwa.robot.r2022.emotionalability.fragment.PhaseQuestionContentFragment;
import com.nuwa.robot.r2022.emotionalability.fragment.PhaseResponseFragment;
import com.nuwa.robot.r2022.emotionalability.listener.OnImageOptionSelectedListener;
import com.nuwa.robot.r2022.emotionalability.listener.OnPhaseContentListener;
import com.nuwa.robot.r2022.emotionalability.model.AnswerContent;
import com.nuwa.robot.r2022.emotionalability.model.ImageOption;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.view.GameActivity;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import java.lang.reflect.Type;
import java.util.HashMap;

import io.realm.Realm;

public class PhaseAdapter extends FragmentStateAdapter {
//    LayoutInflater mLayoutInflater;

    private final Phase phase;
    private final FragmentActivity fragmentActivity;

    Gson gson;
    Phase phase2;

    RobotController robotController;


    //    PreferenceManager preferenceManager;
    Realm realm = Realm.getDefaultInstance();

    public PhaseAdapter(Phase phase, @NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.phase = phase;
        this.fragmentActivity = fragmentActivity;
    }


//
//    public PhaseAdapter(Phase phase, fragmentActivity fragmentActivity  ) {
//        this.phase = phase;
//        this.fragmentActivity = fragmentActivity;
////        preferenceManager = new PreferenceManager(fragmentActivity);
//        gson = new Gson();
//
////        robotController = new RobotController(fragmentActivity);
//        phase2 = realm.where(Phase.class).equalTo("id", phase.getId())
//                .equalTo("levelId", phase.getLevelId())
//                .equalTo("unitId", phase.getUnitId())
//                .findFirst();
//
//    }

    private void showAnswerContent(AnswerContent answerContent, View view) {

        LinearLayout linearLayout = view.findViewById(R.id.layoutCorrectAnswer);
        LinearLayout linearLayout2 = view.findViewById(R.id.layoutWrongAnswer);
        Button textView = view.findViewById(R.id.txtCorrect);
        Button textViewWrong = view.findViewById(R.id.txtWrong);
        RecyclerView listView = view.findViewById(R.id.imageList);
        ImageView imageForShow = view.findViewById(R.id.imageForShow);


        if (answerContent.isHaveImageForShow() && answerContent.getImageForShow().equals("ScaredChild")) {
            imageForShow.setVisibility(View.VISIBLE);
            imageForShow.setImageResource(R.drawable.scared_child);
        }


        if (answerContent.getAnswerWay() == 1) {

            PhaseAnswered phaseAnswered = new PhaseAnswered();
            phaseAnswered.setPhaseId(phase.getId());
            phaseAnswered.setLevelId(phase.getLevelId());
            phaseAnswered.setUnitId(phase.getUnitId());
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(fragmentActivity, "Correct clicked", Toast.LENGTH_SHORT).show();
                    updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), true);

                    phaseAnswered.setAnswered(true);
                    PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);

                }
            });
            textViewWrong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(fragmentActivity, "Wrong clicked", Toast.LENGTH_SHORT).show();

                    phaseAnswered.setAnswered(false);
                    PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                    updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), false);

                }
            });

        } else if (answerContent.getAnswerWay() == 2) {
            linearLayout.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            String phaseJson = "";

            if (phase2 != null) {
                phase2 = realm.copyFromRealm(phase2); //detach from Realm, copy values to fields
                phaseJson = gson.toJson(phase2);
            }

            Log.d("TAG", "showAnswerContent ==phase: " + phase.toString());
            Log.d("TAG", "showAnswerContent ==phaseJson: " + phaseJson);

//            robotController.sendMessageForStudent(phaseJson);
            Log.d("TAG", "showAnswerContent message Send: ");


            ImageOptionAdapter adapter = new ImageOptionAdapter(fragmentActivity, answerContent.getImageOptions(), new OnImageOptionSelectedListener() {
                @Override
                public void OnImageOptionSelected(ImageOption imageOption) {

                    if (imageOption.getIsCorrect() == 1) {
//                        phaseAnswered.setAnswered(true);
//                        PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                        updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), true);

                        Toast.makeText(fragmentActivity, "Selected emotion" + imageOption.getIsCorrect(), Toast.LENGTH_SHORT).show();

                    } else if (imageOption.getIsCorrect() == 0) {
//                        phaseAnswered.setAnswered(false);
//                        PhaseAnsweredLiveData.get().postSuccess(phaseAnswered);
                        updatePhase(phase.getId(), phase.getLevelId(), phase.getUnitId(), false);

                    }
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentActivity);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            listView.setLayoutManager(layoutManager);
            listView.setAdapter(adapter);
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

    @NonNull
    @Override
    public Fragment createFragment(int position) {


        switch (position) {
            case 0:

                return new PhaseQuestionContentFragment(phase );
            case 1:
                return new AnswerContentFragment(phase , fragmentActivity);
            case 2:

                return new PhaseResponseFragment(phase, fragmentActivity);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

//    @NonNull
//    @Override
//    public PhaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(fragmentActivity).inflate(R.layout.question_content_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PhaseAdapter.ViewHolder holder, int position) {
//        switch (position) {
//            case 0:
////                updatePhase(phase.getId() ,phase.getLevelId() , phase.getUnitId() ,false);
////                robotController.sendMessageToStudent("Message for Student from teacher");
//
//                Log.d("TAG", "instantiateItem: pos 0 ");
//                holder.textView.setText(String.valueOf(phase.getQuestioncontent().getTitle()));
////                robotController.sendMessageForRobot(Constants.MESSAGE_FOR_SHOW_IMAGE);
//
////                Log.d("TAG", "instantiateItem: " + phase.getQuestioncontent().getTitle());
////                robotController.sendMessageToRobot(phase.getQuestioncontent().getTitle());
//                if (phase.getQuestioncontent().getImage() != null && phase.getQuestioncontent().getImage().equals("HappyFace")) {
//
//                }
//
//                break;
//            case 1:
//                Log.d("TAG", "instantiateItem: pos 1 ");
//
//                showAnswerContent(phase.getAnswerContent(), holder.itemView);
//                holder.frameLayout.setVisibility(View.GONE);
//
//                break;
//            case 2:
//                Log.d("TAG", "instantiateItem: pos 2 ");
//
//
//                Log.d("TAG", "instantiateItem phase2: " + phase2.toString());
//
//                PhaseAnsweredLiveData.get().observe((LifecycleOwner) fragmentActivity, new Observer<StateData<PhaseAnswered>>() {
//                    @Override
//                    public void onChanged(StateData<PhaseAnswered> phaseAnsweredStateData) {
//
//                        PhaseAnswered phaseAnswered = phaseAnsweredStateData.getData();
//                        Log.d("TAG", "onChanged observe: " + phaseAnswered.isAnswered());
//                        Log.d("TAG", "onChanged observe: " + phaseAnswered.getPhaseId());
//                        if ((phaseAnswered.getPhaseId() == phase.getId() && phaseAnswered.isAnswered()) || phase.isAnswered()) {
//
//
//                            Log.d("TAG", "onChanged: " +phase.isAnswered());
//                            Log.d("TAG", "onChanged: " +phase.getResponse());
//                           holder. frameLayout.setVisibility(View.VISIBLE);
//                            holder.textView.setText(phase.getResponse());
////                            robotController.sendMessageToRobot(phase.getResponse());
////                    updatePhase(phase.getId() ,phase.getLevelId() , phase.getUnitId() ,true);
//
////                    phase.setAnswered(false);
//                        } else if ((phaseAnswered.getPhaseId() == phase.getId() && !phaseAnswered.isAnswered()) || !phase.isAnswered()) {
//                            holder. frameLayout.setVisibility(View.VISIBLE);
//                            holder. textView.setText(Constants.LET_IS_BEGIN_VALUE);
////                            robotController.sendMessageToRobot(Constants.LET_IS_BEGIN_VALUE);
////                    updatePhase(phase.getId() ,phase.getLevelId() , phase.getUnitId() ,false);
//
//                        }
//                    }
//                });
//
//                break;
//
//
//        }
//    }

//    @Override
//    public int getItemCount() {
//        return 3;
//    }
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textView ;
//        FrameLayout frameLayout ;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(R.id.txtQuestionTitle);
//            frameLayout = itemView.findViewById(R.id.questionTitleLayout);
////            images = itemView.findViewById(R.id.images);
//        }
//    }
}
