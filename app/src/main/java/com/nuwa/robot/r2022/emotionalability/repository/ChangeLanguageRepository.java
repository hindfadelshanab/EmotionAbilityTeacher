package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;

import com.nuwa.robot.r2022.emotionalability.api.ClientApi;
import com.nuwa.robot.r2022.emotionalability.model.ResultInfo;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeLanguageRepository {

    private ClientApi clientApi;

    public ChangeLanguageRepository(Context context) {
        clientApi = new ClientApi(context);

    }

    public StateLiveData<ResultInfo> updateLanguage(String language, String doctorId) {
        final StateLiveData<ResultInfo> data = new StateLiveData<>();
        clientApi.getApiInterface().updateLanguage(language, doctorId)
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


}
