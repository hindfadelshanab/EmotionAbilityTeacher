package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.database.GameDao;
import com.nuwa.robot.r2022.emotionalability.database.GameDatabase;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;

public class MainRepository {

    private StateLiveData<Module> stateLiveData = new StateLiveData<Module>();

    public StateLiveData<Module> getModule(Context context) {
        stateLiveData.postSuccess(loadJSONFromAsset(context));
        return stateLiveData;
    }

    public Module loadJSONFromAsset(Context context) {
        String jsonFileString = Utils.getJsonFromAssets(context, "moduled.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<Module>() {}.getType();
        Module module =gson.fromJson(jsonFileString ,listUserType);

        return module;
    }

}
