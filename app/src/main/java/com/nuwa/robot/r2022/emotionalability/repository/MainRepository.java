package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.api.ClientApi;
import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculum;
import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculumInfo;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.PatientInfo;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;
import com.nuwa.robot.r2022.emotionalability.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private ClientApi clientApi;
    Realm realm = Realm.getDefaultInstance();

    public MainRepository(Context context) {
        clientApi = new ClientApi(context);

    }

    private final StateLiveData<List<Module>> stateLiveData = new StateLiveData<List<Module>>();
    private final StateLiveData<Module> stateLiveDataModule = new StateLiveData<Module>();
    private final StateLiveData<AutismCurriculumInfo> stateLiveDataAutismCurriculum = new StateLiveData<AutismCurriculumInfo>();

    public StateLiveData<Module> getModuleById(int id) {

        Module module = realm.where(Module.class).equalTo("id", id).findFirst();

        stateLiveDataModule.postSuccess(module);


        return stateLiveDataModule;

    }

    public StateLiveData<List<Module>> getModule(Context context) {
        stateLiveData.postSuccess(loadJSONFromAsset(context));
        return stateLiveData;
    }

    public StateLiveData<List<Module>> getModule_AR(Context context) {
        stateLiveData.postSuccess(loadJSONFromAsset_AR(context));
        return stateLiveData;
    }

    public List<Module> loadJSONFromAsset(Context context) {
        String jsonFileString = Utils.getJsonFromAssets(context, "moduled.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<Module>>() {
        }.getType();
        List<Module> module = gson.fromJson(jsonFileString, listUserType);

        return module;
    }

    public List<Module> loadJSONFromAsset_AR(Context context) {
        String jsonFileString = Utils.getJsonFromAssets(context, "moduled_ar.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<Module>>() {
        }.getType();
        List<Module> module = gson.fromJson(jsonFileString, listUserType);

        return module;
    }


    public StateLiveData<AutismCurriculumInfo> getAutismCurriculum(String language, String displayType) {

        final StateLiveData<AutismCurriculumInfo> data = new StateLiveData<>();
        clientApi.getApiInterface().getAutismCurriculum(language, displayType).enqueue(new Callback<AutismCurriculumInfo>() {
            @Override
            public void onResponse(Call<AutismCurriculumInfo> call, Response<AutismCurriculumInfo> response) {
                if (response.body() != null)
                    data.postSuccess(response.body());
            }
            @Override
            public void onFailure(Call<AutismCurriculumInfo> call, Throwable t) {
                data.postError(t);
            }
        });
        return stateLiveDataAutismCurriculum;
    }

    public StateLiveData<PatientInfo> getPatientList(String doctorID) {
        final StateLiveData<PatientInfo> data = new StateLiveData<>();

        clientApi.getApiInterface().getPatientList(doctorID).enqueue(new Callback<PatientInfo>() {
            @Override
            public void onResponse(Call<PatientInfo> call, Response<PatientInfo> response) {
                if (response.body() != null)
                    data.postSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PatientInfo> call, Throwable t) {

                data.postError(t);
            }
        });
        return data;
    }


}
