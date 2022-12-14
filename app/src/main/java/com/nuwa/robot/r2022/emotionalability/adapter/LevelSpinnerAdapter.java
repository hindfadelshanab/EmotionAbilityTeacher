package com.nuwa.robot.r2022.emotionalability.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Unit;

import java.util.List;

public class LevelSpinnerAdapter extends ArrayAdapter<Level> {

    public LevelSpinnerAdapter(Context context,
                               List<Level> moduleArrayList)
    {
        super(context, 0, moduleArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner, parent, false);
        }


        Log.d("TAG", "initView adapter: " +position);
        TextView textViewName = convertView.findViewById(R.id.text_view);
        Level currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            String s= currentItem.getName().replace("?" ,"");
            textViewName.setText(s);
            Log.d("TAG", "initView: " +currentItem.getName());
        }
        return convertView;
    }
}
