package com.nuwa.robot.r2022.emotionalability.repository;

import android.content.Context;

import com.nuwa.robot.r2022.emotionalability.api.ClientApi;
import com.nuwa.robot.r2022.emotionalability.model.Details;
import com.nuwa.robot.r2022.emotionalability.model.Doctor;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.utils.StateLiveData;

import io.realm.Realm;
import io.realm.RealmModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInRepository {

    private ClientApi clientApi;
    Realm realm = Realm.getDefaultInstance();

    public SignInRepository(Context context) {
        clientApi = new ClientApi(context);

    }

    public StateLiveData<Doctor> doctorLogin(String userName, String password) {
        final StateLiveData<Doctor> data = new StateLiveData<>();
        clientApi.getApiInterface().userLogin(userName, password)
                .enqueue(new Callback<Doctor>() {
                    @Override
                    public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                        if (response.body() != null) {
                            data.postSuccess(response.body());

                        }
                    }

                    @Override
                    public void onFailure(Call<Doctor> call, Throwable t) {
                        data.postError(t);
                    }
                });


        return data;
    }
    public void  addSignedDoctor(Details details){
//        realm.insertOrUpdate((RealmModel) details);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(details);

            }
        });
    }
    public StateLiveData<Details>  getSignedDoctor(String doctorId){
        StateLiveData<Details> data = new StateLiveData<>();
        Details details = realm.where(Details.class).equalTo("id", doctorId) .findFirst();
                if(details!=null){
                    data.postSuccess(details);
                }else {
                    data.postError(null);
                }

               return data;

    }


}
