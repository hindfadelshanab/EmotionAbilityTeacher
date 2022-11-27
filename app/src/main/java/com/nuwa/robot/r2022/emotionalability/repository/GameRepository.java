package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.database.GameDao;
import com.nuwa.robot.r2022.emotionalability.database.GameDatabase;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.model.relation.FilterPhase;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class GameRepository {
    Realm realm =Realm.getDefaultInstance();



    private final StateLiveData<Level> stateLiveData = new StateLiveData<Level>();
    private final StateLiveData<Phase> stateLiveDataPhase = new StateLiveData<Phase>();
    private final StateLiveData<Phase> getPhaseLiveData = new StateLiveData<Phase>();
    private final StateLiveData<List<Phase>> stateLiveDataListPhase = new StateLiveData<List<Phase>>();


    public  StateLiveData<Phase> getPhase(int id , int levelId , int unitId){
        Phase phase2 = realm.where(Phase.class).equalTo("id",id)
                .equalTo("levelId" ,levelId)
                .equalTo("unitId" ,unitId)
                .findFirst();
        getPhaseLiveData.postSuccess(phase2);
        return  getPhaseLiveData;
    }

    public StateLiveData<Level> getNextLevel(Context context, int id, int unitId) {

        RealmResults<Unit> unitsR=realm.where(Unit.class).findAll();



        for (int i = 0; i < unitsR.size(); i++) {
            if (unitsR.get(i).getIdUnit() == unitId) {
                List<Level> levels = unitsR.get(i).getLevels();
                for (int j = 0; j < levels.size(); j++) {
                    if (levels.get(j).getIdLevel() == id) {
                        if (j + 1 < levels.size()) {
                            Level nextLevel = levels.get(j + 1);
                            if (nextLevel != null) {
                                stateLiveData.postSuccess(nextLevel);

                                Log.d("TAG", "loadJSONFromAsset level jjj: " + nextLevel.getName());
                                Log.d("TAG", "loadJSONFromAsset level jjj: " + nextLevel.getIdLevel());

                            }

                        } else {
                            if (i + 1 < unitsR.size()) {
                                Level levelFromNextUnit = unitsR.get(i + 1).getLevels().get(0);
                                if (levelFromNextUnit != null) {
                                    stateLiveData.postSuccess(levelFromNextUnit);

                                    Log.d("TAG", "loadJSONFromAsset:lll " + levelFromNextUnit.getName());
                                    Log.d("TAG", "loadJSONFromAsset:lll " + levelFromNextUnit.getIdLevel());

                                }
                            } else {
                                stateLiveData.postComplete();

                            }
                        }

                    }

                }

                Log.d("TAG", "loadJSONFromAsset level id: " + id);
                Log.d("TAG", "loadJSONFromAsset unit id: " + unitId);



            }

        }
        return stateLiveData;
    }


    public StateLiveData<List<Phase>> getPhaseList(int levelId , int unitId ) {

        List<Phase> phaseRealmResults = realm.where(Phase.class)
                .equalTo("levelId", levelId)
                .equalTo("unitId", unitId)
                .findAll();

        if (phaseRealmResults!=null){

            stateLiveDataListPhase.postSuccess(phaseRealmResults);
        }

        return stateLiveDataListPhase ;
    }

    public StateLiveData<Phase> updatePhase(int phaseId , int levelId , int unitId  , boolean isAnswered){

        Realm realm =Realm.getDefaultInstance();

        Phase phase = realm.where(Phase.class).equalTo("id", phaseId)
                .equalTo("levelId" ,levelId)
                .equalTo("unitId" ,unitId)
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                phase.setAnswered(isAnswered);
               Phase phase1=realm.copyToRealmOrUpdate(phase);
                Log.d("TAG", "updatePhase:GameRepository after update " +phase.toString());

                stateLiveDataPhase.postSuccess(phase1);
            }
        });

        return stateLiveDataPhase;
    }
public StateLiveData<Phase> updatePhase(int phaseId , int levelId , int unitId  , String  response){

        Realm realm =Realm.getDefaultInstance();

        Phase phase2 = realm.where(Phase.class).equalTo("id", phaseId)
                .equalTo("levelId" ,levelId)
                .equalTo("unitId" ,unitId)
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                phase2.setResponse(response);
               Phase phase1=realm.copyToRealmOrUpdate(phase2);
                Log.d("TAG", "updatePhase Response:GameRepository after update " +phase2.toString());

                stateLiveDataPhase.postSuccess(phase1);
            }
        });

        return stateLiveDataPhase;
    }






}
