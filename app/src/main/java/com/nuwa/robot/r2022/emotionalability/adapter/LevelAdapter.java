package com.nuwa.robot.r2022.emotionalability.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.LevelItemBinding;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.view.GameActivity;
import com.nuwa.robot.r2022.emotionalability.view.StartLevelActivity;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder>{

    private Context context;
    List<Level> levels;

    public LevelAdapter(Context context, List<Level> levels) {
        this.context = context;
        this.levels = levels;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LevelItemBinding binding = LevelItemBinding
                .inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder viewHolder, int i) {
        Level level = levels.get(i);
        int count =0;
        for (Phase p :level.getPhases()) {
            if (p.isAnswered()){
                count ++;

            }
        }

        if (count ==level.getPhases().size()){
            Log.d("TAG", "onBindViewHolder: count ==level.getPhases().size()");
            viewHolder.binding.imagePlay.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);

        }else {
            viewHolder.binding.imageLevel.setImageResource(R.drawable.play_icon);

        }
        Log.d("TAG", "onBindViewHolder:count " +count +"level id :" +level.getIdLevel()
        +"phases count "+level.getPhases().size()
        );
        viewHolder.binding.txtLevelName.setText(level.getName());
        viewHolder.binding.imageLevel.setImageResource(R.drawable.level_image);


//        Glide.with(context).load(level.getImage()).into(viewHolder.binding.imageLevel);

        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context , GameActivity.class);
                intent.putExtra(Constants.LEVEL_ID_Key, level.getIdLevel());
                intent.putExtra(Constants.UNIT_ID_KEY, level.getUnitId());
                Log.d("TAG", "onClick getAnswerContent: " +level.getPhases().get(0).getAnswerContent().toString());

                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LevelItemBinding binding ;
        public ViewHolder(@NonNull LevelItemBinding itemView) {
            super(itemView.getRoot());
            this.binding =itemView;

        }
    }
}
