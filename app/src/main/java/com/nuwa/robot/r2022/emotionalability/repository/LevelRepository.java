package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.utils.ReaderGame;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;

public class LevelRepository {


    private StateLiveData<List<Unit>> stateLiveData = new StateLiveData<List<Unit>>();

    public StateLiveData<List<Unit>> getUnit(Context context) {

        stateLiveData.postSuccess(loadJSONFromAsset(context));

        return stateLiveData;

    }

    public List<Unit> loadJSONFromAsset(Context context) {



        String jsonFileString = Utils.getJsonFromAssets(context, "moduled.json");
//        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<Module>() {}.getType();



        Module module = gson.fromJson(jsonFileString, listUserType) ;


        if (module != null) {
            return module.getUnits();
        }


        return null;
    }


}