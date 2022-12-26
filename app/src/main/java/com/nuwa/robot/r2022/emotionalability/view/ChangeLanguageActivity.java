package com.nuwa.robot.r2022.emotionalability.view;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityChangeLanguageBinding;
import com.nuwa.robot.r2022.emotionalability.model.ResultInfo;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.ChangeLanguageViewModel;

public class ChangeLanguageActivity extends AppCompatActivity {

    ActivityChangeLanguageBinding binding ;
    String selectedLanguage;
    ChangeLanguageViewModel changeLanguageViewModel ;
    PreferenceManager preferenceManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangeLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changeLanguageViewModel = new ViewModelProvider(this).get(ChangeLanguageViewModel.class);
        preferenceManager = new PreferenceManager(this);
        binding.languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId==R.id.englishRadioButton){
                    updateLanguage("English");
                    selectedLanguage="English";
                }else if (checkedId==R.id.arabicRadioButton){
                    updateLanguage("Arabic");
                    selectedLanguage="Arabic";

                }
            }
        });
//        binding.btnSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             if (selectedLanguage!=null){
//                 updateLanguage(selectedLanguage);
//             }else {
//                 Toast.makeText(ChangeLanguageActivity.this, "select language", Toast.LENGTH_SHORT).show();
//             }
//            }
//        });
    }

    private void updateLanguage(String selectedLanguage) {

        if (preferenceManager.getString(Constants.DOCTOR_ID) != null) {
            changeLanguageViewModel.updateLanguage(selectedLanguage, preferenceManager.getString(Constants.DOCTOR_ID)).observe(this, new Observer<StateData<ResultInfo>>() {
                @Override
                public void onChanged(StateData<ResultInfo> updateLanguageInfoStateData) {
//                    Toast.makeText(ChangeLanguageActivity.this, "update langouage  :" + updateLanguageInfoStateData.getData().getMessage(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(ChangeLanguageActivity.this, "update langouage  :" + updateLanguageInfoStateData.getData().getSuccess(), Toast.LENGTH_SHORT).show();

                    if (selectedLanguage.equals("English")) {
                        LocaleHelper.setLocale(ChangeLanguageActivity.this, "en");
                        preferenceManager.putString(LANGUAGE, Constants.ENGLISH);
                        preferenceManager.putString(Constants.LANGUAGE_SIGN_IN,"en");
                        recreate();
                        finish();
                    } else if (selectedLanguage.equals("Arabic")) {
                        LocaleHelper.setLocale(ChangeLanguageActivity.this, "ar");
                        preferenceManager.putString(LANGUAGE, Constants.ARABIC);
                        preferenceManager.putString(Constants.LANGUAGE_SIGN_IN,"ar");
                        recreate();
                        finish();
                    }

                }
            });
        }
    }
}

