package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.api.ClientApi;
import com.nuwa.robot.r2022.emotionalability.database.GameDao;
import com.nuwa.robot.r2022.emotionalability.database.GameDatabase;
import com.nuwa.robot.r2022.emotionalability.model.BaselineResultInfo;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.ResultInfo;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GameRepository {
    Realm realm = Realm.getDefaultInstance();


    private ClientApi clientApi;

    public GameRepository(Context context) {
        clientApi = new ClientApi(context);

    }

    private final StateLiveData<Level> stateLiveData = new StateLiveData<Level>();
    private final StateLiveData<Phase> stateLiveDataPhase = new StateLiveData<Phase>();
    private final StateLiveData<Phase> getPhaseLiveData = new StateLiveData<Phase>();
    private final StateLiveData<List<Phase>> stateLiveDataListPhase = new StateLiveData<List<Phase>>();


    public StateLiveData<Phase> getPhase(int id, int levelId, int unitId) {
        Phase phase2 = realm.where(Phase.class).equalTo("id", id)
                .equalTo("levelId", levelId)
                .equalTo("unitId", unitId)
                .findFirst();
        getPhaseLiveData.postSuccess(phase2);
        return getPhaseLiveData;
    }

    public StateLiveData<Level> getNextLevel(Context context, int id, int unitId) {

        RealmResults<Unit> unitsR = realm.where(Unit.class).findAll();


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


    public StateLiveData<List<Phase>> getPhaseList(int levelId, int unitId) {

        List<Phase> phaseRealmResults = realm.where(Phase.class)
                .equalTo("levelId", levelId)
                .equalTo("unitId", unitId)
                .findAll();

        if (phaseRealmResults != null) {

            stateLiveDataListPhase.postSuccess(phaseRealmResults);
        }

        return stateLiveDataListPhase;
    }

    public StateLiveData<Phase> updatePhase(int phaseId, int levelId, int unitId, boolean isAnswered) {

        Realm realm = Realm.getDefaultInstance();

        Phase phase = realm.where(Phase.class).equalTo("id", phaseId)
                .equalTo("levelId", levelId)
                .equalTo("unitId", unitId)
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                phase.setAnswered(isAnswered);
                Phase phase1 = realm.copyToRealmOrUpdate(phase);
                Log.d("TAG", "updatePhase:GameRepository after update " + phase.toString());

                stateLiveDataPhase.postSuccess(phase1);
            }
        });

        return stateLiveDataPhase;
    }

    public StateLiveData<Phase> updatePhase(int phaseId, int levelId, int unitId, String response) {

        Realm realm = Realm.getDefaultInstance();

        Phase phase2 = realm.where(Phase.class).equalTo("id", phaseId)
                .equalTo("levelId", levelId)
                .equalTo("unitId", unitId)
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                phase2.setResponse(response);
                Phase phase1 = realm.copyToRealmOrUpdate(phase2);
                Log.d("TAG", "updatePhase Response:GameRepository after update " + phase2.toString());

                stateLiveDataPhase.postSuccess(phase1);
            }
        });

        return stateLiveDataPhase;
    }

    public StateLiveData<ResultInfo> sendAnswerResult(String Patient_Id,
                                                      String doctorId,
                                                      String Curriculum_Id,
                                                      String Module_Id,
                                                      String Objectives_Id,
                                                      String Specialization_Id,
                                                      String Term,
                                                      String Date,
                                                      String AET_Autism_Baseline_Id,
                                                      String Created
    ) {
        final StateLiveData<ResultInfo> data = new StateLiveData<>();

        clientApi.getApiInterface().sendAnswerResult(Patient_Id, doctorId, Curriculum_Id,
                        Module_Id, Objectives_Id, Specialization_Id, Term, Date, AET_Autism_Baseline_Id,
                        "1",
                        Created
                )
                .enqueue(new Callback<ResultInfo>() {
                    @Override
                    public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                        if (response.body() != null) {
                            data.postSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultInfo> call, Throwable t) {
                        data.postError(t);
                    }
                });


        return data;
    }

    public StateLiveData<BaselineResultInfo> getAutismBaseline(String language){
        StateLiveData<BaselineResultInfo> data = new StateLiveData<>();
        clientApi.getApiInterface().getAutismBaseline(language).enqueue(new Callback<BaselineResultInfo>() {
            @Override
            public void onResponse(Call<BaselineResultInfo> call, Response<BaselineResultInfo> response) {
            if (response.body()!=null)
                data.postSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BaselineResultInfo> call, Throwable t) {

                data.postError(t);
            }
        });

        return data;
    }

}
