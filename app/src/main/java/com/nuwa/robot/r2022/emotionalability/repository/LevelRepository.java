package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class LevelRepository {


    private StateLiveData<List<Unit>> stateLiveData = new StateLiveData<List<Unit>>();
    private StateLiveData<List<Module>> moduleListLiveData = new StateLiveData<List<Module>>();
    private StateLiveData<List<Level>> levelListLiveData = new StateLiveData<List<Level>>();
    private Realm realm = Realm.getDefaultInstance();

    public StateLiveData<List<Unit>> getUnit(Context context, int moduleId) {

        stateLiveData.postSuccess(loadJSONFromAsset(context, moduleId));

        return stateLiveData;

    }

    public StateLiveData<List<Level>> getLevels(  int unitId) {

        List<Level> levelList = realm.where(Level.class)
            .equalTo("unitId", unitId)
                .findAll();
        Log.d("TAG" , "getLevels: levelList"+levelList.toString());
        levelListLiveData.postSuccess(levelList);

        return levelListLiveData;

    }

    public StateLiveData<List<Module>> getModule(Context context) {

        List<Module> moduleList = realm.where(Module.class)
                .findAll();
        moduleListLiveData.postSuccess(moduleList);

        return moduleListLiveData;

    }

    public List<Unit> loadJSONFromAsset(Context context, int moduleId) {

        RealmResults<Unit> units = realm.where(Unit.class).equalTo("moduleId", moduleId).findAll();

//        if (units!=null){
//
//        }
//
//        String jsonFileString = Utils.getJsonFromAssets(context, "moduled.json");
////        Log.i("data", jsonFileString);
//
//        Gson gson = new Gson();
//        Type listUserType = new TypeToken<Module>() {}.getType();
//
//
//
//        Module module = gson.fromJson(jsonFileString, listUserType) ;
//
//
//        if (module != null) {
//            return module.getUnits();
//        }


        return units;
    }


}
