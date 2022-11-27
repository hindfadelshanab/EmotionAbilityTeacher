package com.nuwa.robot.r2022.emotionalability.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.fragment.PhaseFragment;
import com.nuwa.robot.r2022.emotionalability.fragment.QuestionContentFragment;
import com.nuwa.robot.r2022.emotionalability.listener.OnQuestionContentListener;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;

public class QuestionAdapter extends FragmentStateAdapter {
    LayoutInflater mLayoutInflater;

    private Level level;
    private Context context;
    private PhaseAdapter phaseAdapter;
    private RobotController robotController;
    private FragmentActivity fragmentActivity ;
    public QuestionAdapter(@NonNull FragmentActivity fragmentActivity , Level level) {
        super(fragmentActivity);
        this.level = level;
        this.fragmentActivity = fragmentActivity;
    }


//    public QuestionAdapter(Level level, Context context ) {
//        this.level = level;
//        this.context = context;
//        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        robotController=new RobotController(context);
//
//    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:

                return new QuestionContentFragment(level.getGameName() ,0 , fragmentActivity);
            case 1:
                return new QuestionContentFragment(level.getGameDescription() ,1,fragmentActivity);

            case 2:
                return new QuestionContentFragment(Constants.LET_IS_BEGIN_VALUE,2,fragmentActivity);

            case 3:
                return new PhaseFragment(level.getIdLevel(), level.getUnitId() , fragmentActivity);


        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

//    @NonNull
//    @Override
//    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.question_content_item, parent, false);
//        return new QuestionAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        switch (position) {
//            case 0:
//                holder. frameLayout.setVisibility(View.GONE);
//                holder.txtLevelName.setVisibility(View.VISIBLE);
//                holder.txtLevelObjective.setVisibility(View.VISIBLE);
//                holder.txtLevelName.setText(level.getName());
//                holder.txtLevelObjective.setText(level.getObjective());
//
//                break;
//            case 1:
//                holder.textView.setText(level.getGameName());
////                robotController.sendMessageToRobot(level.getGameName());
//                break;
//            case 2:
//                holder.  textView.setText(level.getGameDescription());
////                robotController.sendMessageToRobot(level.getGameDescription());
//                break;
//            case 3:
//                holder. textView.setText("Let’s Begin!");
////                robotController.sendMessageToRobot(Constants.LET_IS_BEGIN_VALUE);
//                break;
//
//        }
//
//    }
//
//
//
//    @Override
//    public int getItemCount() {
//        return 4;
//    }
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        FrameLayout frameLayout  ;
//        TextView textView ;
//        TextView txtLevelName  ;
//        TextView txtLevelObjective ;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            frameLayout  = itemView.findViewById(R.id.questionTitleLayout);
//            textView = itemView.findViewById(R.id.txtQuestionTitle);
//            txtLevelName  = itemView.findViewById(R.id.txtLevelName);
//            txtLevelObjective  = itemView.findViewById(R.id.txtLevelObjective);
//        }
//    }
}
