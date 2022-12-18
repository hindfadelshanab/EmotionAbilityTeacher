package com.nuwa.robot.r2022.emotionalability.view;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityWelcomBinding;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;

import java.util.Locale;

public class WelcomActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    ActivityWelcomBinding binding ;
    String[] lang = { Constants.ENGLISH,Constants.ARABIC};
    PreferenceManager preferenceManager ;
    Context context;
    Resources resource ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =ActivityWelcomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this) ;
        findViewById(R.id.btnLogInAsStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomActivity.this,MakeConnectionWithRobotActivity.class ));
            }
        });
        binding.languageSpinner.setOnClickListener(view -> {

            showChangeLanguageDialog();
        });



//        binding.languageSpinner.setOnItemSelectedListener(this);
//
//
        if (preferenceManager.getString(LANGUAGE)!=null) {
            Log.d("TAG9", "onCreate: "+preferenceManager.getString(LANGUAGE));
            if (preferenceManager.getString(LANGUAGE).equals(Constants.ENGLISH)) {

                context = LocaleHelper.setLocale(this, "en");
                 resource = context.getResources();
                binding.btnLogInAsStudent.setText(resource.getString(R.string.next));
                binding.languageSpinner.setText(resource.getString(R.string.choose_langouge));


            } else if (preferenceManager.getString(LANGUAGE).equals(Constants.ARABIC)) {
                context = LocaleHelper.setLocale(this, "ar");
                resource = context.getResources();
                binding.btnLogInAsStudent.setText(resource.getString(R.string.next));
                binding.languageSpinner.setText(resource.getString(R.string.choose_langouge));

            }

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if (i==0) {
//            binding.btnLogInAsStudent.setText(resource.getString(R.string.next));
//        }else if (i==1){
//            binding.btnLogInAsStudent.setText(resource.getString(R.string.next));
//
//        }

        if (i>0) {
            preferenceManager.putString(LANGUAGE, lang[i]);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }





    private  void showChangeLanguageDialog(){

        AlertDialog.Builder  mBuilder  = new AlertDialog.Builder(this);

        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preferenceManager.putString(LANGUAGE, lang[i]);

                if (i==0){
                    context = LocaleHelper.setLocale(WelcomActivity.this, "en");
                    resource = context.getResources();
                    preferenceManager.putString(LANGUAGE, Constants.ENGLISH);

                    recreate();
                }else if (i==1){
                    context = LocaleHelper.setLocale(WelcomActivity.this, "ar");
                    resource = context.getResources();
                    recreate();
                    preferenceManager.putString(LANGUAGE, Constants.ARABIC);

                }

            }
        });
        mBuilder.create().show();
    }
}