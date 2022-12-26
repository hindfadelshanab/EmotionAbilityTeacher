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
import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculum;
import com.nuwa.robot.r2022.emotionalability.model.AutismSpecialization;

import java.util.List;

public class AutismSpecializationAdapter extends ArrayAdapter<AutismSpecialization> {

    public AutismSpecializationAdapter(Context context,
                                       List<AutismSpecialization> moduleArrayList)
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

        TextView textViewName = convertView.findViewById(R.id.text_view);
        AutismSpecialization currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getAutism_Specialization_Type());
            Log.d("TAG", "initView: " +currentItem.getAutism_Specialization_Type());
        }
        return convertView;
    }
}
