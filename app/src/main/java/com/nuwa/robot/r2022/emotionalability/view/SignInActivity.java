package com.nuwa.robot.r2022.emotionalability.view;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivitySignInBinding;
import com.nuwa.robot.r2022.emotionalability.model.Doctor;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.LevelViewModel;
import com.nuwa.robot.r2022.emotionalability.viewModel.SignInViewModel;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignInBinding binding;
    SignInViewModel signInViewModel;
    PreferenceManager preferenceManager ;
    Context context;
    Resources resource ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        binding.btnSigIn.setOnClickListener(this);
        preferenceManager = new PreferenceManager(this);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSigIn:
                SignIn();
                break;

        }
    }

    private void SignIn() {
        if (!binding.inputEmail.getText().toString().isEmpty() &&
                !binding.inputPassword.getText().toString().isEmpty()) {

            Log.d("sign", "inputEmail:" +binding.inputEmail.getText().toString());
            Log.d("sign", "inputPassword:" + binding.inputPassword.getText().toString());

            signInViewModel.doctorLogin("t-doctor@gmail.com",
                 "12345678").observe(this, new Observer<StateData<Doctor>>() {
                @Override
                public void onChanged(StateData<Doctor> doctorStateData) {
                    if (doctorStateData.getStatus()== StateData.DataStatus.SUCCESS){
                        Doctor doctor = doctorStateData.getData();
                        if (doctor.getSuccess().equals("1")){
                            signInViewModel.addSignedDoctor(doctor.getDetails());
                            preferenceManager.putString(Constants.DOCTOR_ID , doctor.getDetails().getId());
                            preferenceManager.putString(Constants.LANGUAGE_SIGN_IN , doctor.getDetails().getLanguage());
                            Toast.makeText(SignInActivity.this, " langg :::"+ preferenceManager.getString(Constants.LANGUAGE_SIGN_IN), Toast.LENGTH_SHORT).show();
                            changeLanguage(doctor.getDetails().getLanguage());
                            startActivity(new Intent(SignInActivity.this, BeginningActivity.class ));
                        }
                        Log.d("sign", "onChanged: " +doctorStateData.getData());
                        Log.d("sign", "onChanged: doctor " +doctor.toString());
                    }
                }
            });
        } else {
            Toast.makeText(this, "Pleas fill field", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void changeLanguage(String language){
        if (language.equals("en")){
            context = LocaleHelper.setLocale(SignInActivity.this, "en");
            resource = context.getResources();
            preferenceManager.putString(LANGUAGE, Constants.ENGLISH);
            recreate();
        }else if (language.equals("ar")){
            context = LocaleHelper.setLocale(SignInActivity.this, "ar");
            resource = context.getResources();
            recreate();
            preferenceManager.putString(LANGUAGE, Constants.ARABIC);
            recreate();
        }
    }
}