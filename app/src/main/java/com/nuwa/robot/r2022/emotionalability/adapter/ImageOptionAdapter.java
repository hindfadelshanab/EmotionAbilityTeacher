package com.nuwa.robot.r2022.emotionalability.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ImageOptionItemBinding;
import com.nuwa.robot.r2022.emotionalability.databinding.LevelItemBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnImageOptionSelectedListener;
import com.nuwa.robot.r2022.emotionalability.model.ImageOption;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.view.StartLevelActivity;

import java.util.List;

public class ImageOptionAdapter extends RecyclerView.Adapter<ImageOptionAdapter.ViewHolder>{

    private Context context;
    List<ImageOption> imageOptions;

    private OnImageOptionSelectedListener onImageOptionSelectedListener;

    public ImageOptionAdapter(Context context, List<ImageOption> imageOptions ,OnImageOptionSelectedListener onImageOptionSelectedListener) {
        this.context = context;
        this.imageOptions = imageOptions;
        this.onImageOptionSelectedListener =onImageOptionSelectedListener ;
    }

    @NonNull
    @Override
    public ImageOptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageOptionItemBinding binding = ImageOptionItemBinding
                .inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageOptionAdapter.ViewHolder viewHolder, int i) {
       ImageOption imageOption = imageOptions.get(i);


       if (imageOption.getDescription()!=null){
           viewHolder.binding.txtImageOptionDesc.setText(imageOption.getDescription());
       }
       if (imageOption.getImage().equals("AngryFace")) {
           viewHolder.binding.imageOption.setImageResource(R.drawable.angry_emotion);
           viewHolder.binding.imageOption.setBackgroundResource(R.drawable.level_background_green);
       }else  if (imageOption.getImage().equals("SadFace")){
            viewHolder.binding.imageOption.setImageResource(R.drawable.sad_emotion);
           viewHolder.binding.imageOption.setBackgroundResource(R.drawable.level_background_red);

        }else if(imageOption.getImage().equals("HappyBoy")){
           viewHolder.binding.imageOption.setImageResource(R.drawable.happy_boy);
           viewHolder.binding.imageOptionLayout.setBackgroundResource(R.drawable.image_option_rectangle_background);
       }else if (imageOption.getImage().equals("SadBoy")){
           viewHolder.binding.imageOption.setImageResource(R.drawable.sad_boy);
           viewHolder.binding.imageOptionLayout.setBackgroundResource(R.drawable.image_option_rectangle_background);

       }else if (imageOption.getImage().equals("AngryBoy")){
           viewHolder.binding.imageOption.setImageResource(R.drawable.angry_boy);
           viewHolder.binding.imageOptionLayout.setBackgroundResource(R.drawable.image_option_rectangle_background);

       }else if (imageOption.getImage().equals("ScaredBoy")){
           viewHolder.binding.imageOption.setImageResource(R.drawable.sacared_boy);
           viewHolder.binding.imageOptionLayout.setBackgroundResource(R.drawable.image_option_rectangle_background);

       }

       viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               onImageOptionSelectedListener.OnImageOptionSelected(imageOption);
           }
       });

//        Level level = levels.get(i);
//
//        viewHolder.binding.txtLevelName.setText(level.getName());
//        viewHolder.binding.imageLevel.setImageResource(R.drawable.level_image);
//
////        Glide.with(context).load(level.getImage()).into(viewHolder.binding.imageLevel);
//
//        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(context , StartLevelActivity.class);
//                intent.putExtra(Constants.LevelKEY , level);
//
//
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return imageOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageOptionItemBinding binding ;
        public ViewHolder(@NonNull ImageOptionItemBinding itemView) {
            super(itemView.getRoot());
            this.binding =itemView;

        }
    }
}
