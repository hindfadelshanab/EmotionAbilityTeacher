package com.nuwa.robot.r2022.emotionalability.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ImageOptionItemBinding;
import com.nuwa.robot.r2022.emotionalability.databinding.ItemEmotionBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnImageOptionSelectedListener;
import com.nuwa.robot.r2022.emotionalability.model.ImageOption;

import java.util.List;

public class ImageOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<ImageOption> imageOptions;
    int viewAnsweredType;

    private final static int IMAGE_OPTION_ITEM_VIEW = 0;
    private final static int EMOTION_ITEM_VIEW = 1;
    private OnImageOptionSelectedListener onImageOptionSelectedListener;

    public ImageOptionAdapter(Context context, List<ImageOption> imageOptions, int viewAnsweredType, OnImageOptionSelectedListener onImageOptionSelectedListener) {
        this.context = context;
        this.imageOptions = imageOptions;
        this.onImageOptionSelectedListener = onImageOptionSelectedListener;
        this.viewAnsweredType = viewAnsweredType;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case IMAGE_OPTION_ITEM_VIEW:
                ImageOptionItemBinding binding = ImageOptionItemBinding
                        .inflate(LayoutInflater.from(context), parent, false);
                return new ImageOptionViewHolder(binding);
            case EMOTION_ITEM_VIEW:
                ItemEmotionBinding itemEmotionBinding = ItemEmotionBinding
                        .inflate(LayoutInflater.from(context), parent, false);
                return new EmotionViewHolder(itemEmotionBinding);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ImageOption imageOption = imageOptions.get(i);
        switch (viewHolder.getItemViewType()) {
            case IMAGE_OPTION_ITEM_VIEW:
                ImageOptionViewHolder imageOptionViewHolder = (ImageOptionViewHolder) viewHolder;
                imageOptionViewHolder.setImageOptionsData(imageOption);
                break;

            case EMOTION_ITEM_VIEW:
                EmotionViewHolder emotionViewHolder = (EmotionViewHolder) viewHolder;

                emotionViewHolder.setEmotionData(imageOption);
                break;
        }


    }


    @Override
    public int getItemCount() {
        return imageOptions.size();
    }

    public class ImageOptionViewHolder extends RecyclerView.ViewHolder {

        ImageOptionItemBinding binding;

        public ImageOptionViewHolder(@NonNull ImageOptionItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }

        public void setImageOptionsData(ImageOption imageOption) {
            if (imageOption.getDescription() != null) {

                binding.txtImageOptionDesc.setText(imageOption.getDescription());
            }
            if (imageOption.getImage().equals("AngryFace")) {

                binding.imageOption.setImageResource(R.drawable.angry_emotion);
            } else if (imageOption.getImage().equals("SadFace")) {
                binding.imageOption.setImageResource(R.drawable.sad_emotion);

            } else if (imageOption.getImage().equals("HappyBoy")) {
                binding.imageOption.setImageResource(R.drawable.happy_boy);
            } else if (imageOption.getImage().equals("SadBoy")) {
                binding.imageOption.setImageResource(R.drawable.sad_boy);

            } else if (imageOption.getImage().equals("AngryBoy")) {
                binding.imageOption.setImageResource(R.drawable.angry_boy);

            } else if (imageOption.getImage().equals("ScaredBoy")) {
                binding.imageOption.setImageResource(R.drawable.sacared_boy);

            }else if (imageOption.getImage().equals("largeCircle")){
                binding.imageOption.setImageResource(R.drawable.large_circle);

            }else if (imageOption.getImage().equals("mediumCircle")){
                binding.imageOption.setImageResource(R.drawable.medium_circle);

            }else if (imageOption.getImage().equals("smallCircle")){
              binding.imageOption.setImageResource(R.drawable.small_circle);

            }else if (imageOption.getImage().equals("pinkRectangle")) {
                binding.imageOption.setImageResource(R.drawable.pink_rectangel);

            } else if (imageOption.getImage().equals("orangeRectangle")) {
               binding.imageOption.setImageResource(R.drawable.orange_rectangle);

            } else if (imageOption.getImage().equals("lightBlueRectangle")) {
                binding.imageOption.setImageResource(R.drawable.light_blue_rectangle);

            } else if (imageOption.getImage().equals("darkBlueRectangle")) {
               binding.imageOption.setImageResource(R.drawable.dark_blue_rectangle);

            }


        }
    }

    public class EmotionViewHolder extends RecyclerView.ViewHolder {

        ItemEmotionBinding itemEmotionBinding;

        public EmotionViewHolder(@NonNull ItemEmotionBinding itemView) {
            super(itemView.getRoot());
            this.itemEmotionBinding = itemView;
        }

        public void setEmotionData(ImageOption imageOption) {

            itemEmotionBinding.emotionName.setText(imageOption.getDescription());
            Log.d("TAG8", "setEmotionData: imageOption.getImage() "+imageOption.getImage());
            Log.d("TAG8", imageOption.getImage());

            switch (imageOption.getDescription()) {
                case "happy":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.happy);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_purple);
                    break;
                case "proud":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.proud);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_orange);

                    break;
                case "sad":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.sad);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_green);
                    break;
                case "confused":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.confused);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_blue);
                    break;
                case "angry":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.angry);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_dark_purple);

                    break;
                case "worried":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.worried);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_pink);

                    break;
                case "scared":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.scared);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_light_green);

                    break;
                case "surprised":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.surprised);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_purple);

                    break;
                case "shy":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.shy);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_dark_purple);

                    break;
                case "tired":
                    itemEmotionBinding.emotionImage.setImageResource(R.drawable.tired);
                    itemEmotionBinding.mainLayout.setBackgroundResource(R.drawable.button_green);

                    break;


            }


            itemEmotionBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("itemEmotionBinding", "onClick: itemEmotionBinding");
                    onImageOptionSelectedListener.OnImageOptionSelected(imageOption);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {

        switch (viewAnsweredType) {
            case 2:
                return IMAGE_OPTION_ITEM_VIEW;
            case 5:
                return EMOTION_ITEM_VIEW;
            default:
                return -1;
        }
    }

}
