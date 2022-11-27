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
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitSpinnerAdapter extends ArrayAdapter<Unit> {

    public UnitSpinnerAdapter(Context context,
                              List<Unit> moduleArrayList) {
        super(context, 0, moduleArrayList);
//        moduleArrayList.add(0 ,new Unit(0 , 0,"" , null ));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text_view);

        Unit currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getUnitName());
            Log.d("TAG", "initView: " + currentItem.getUnitName());
        }
        return convertView;
    }
}
