package com.nuwa.robot.r2022.emotionalability.view;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.adapter.LevelSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.PatientSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityMainBinding;
import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculumInfo;
import com.nuwa.robot.r2022.emotionalability.model.Patient;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.PatientInfo;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    private Realm realm;
    PreferenceManager preferenceManager;
    Context context;
    Resources resources;
    RobotController robotController;
    Gson gson;
    Patient patient;
    PatientSpinnerAdapter patientSpinnerAdapter;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialization();
        getModule();
        getCurrentModule();
        getPatients();
        getAutismCurriculum();
        checkSelectedLanguage();
        binding.btnSelectPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Patient patient = (Patient) binding.btnSelectPatient.getSelectedItem();

                if (patient != null) {

                    binding.txtPatientName.setText(patient.getUsername());
                    Glide.with(MainActivity.this).load(patient.getImage()).into(binding.imagePatient);
                    preferenceManager.putString(Constants.PATIENT_ID, patient.getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        chart();


    }

    private void getCurrentModule() {
        mainViewModel.getModuleById(1).observe(this, new Observer<StateData<Module>>() {
            @Override
            public void onChanged(StateData<Module> moduleStateData) {
                if (moduleStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                    module = moduleStateData.getData();
                    setModuleData(module);
                }
            }
        });
    }

    private void checkSelectedLanguage() {
        if (preferenceManager.getString(LANGUAGE) != null) {
            if (preferenceManager.getString(LANGUAGE).equals(Constants.ENGLISH)) {
                context = LocaleHelper.setLocale(this, "en");
                resources = context.getResources();
            } else if (preferenceManager.getString(LANGUAGE).equals(Constants.ARABIC)) {
                context = LocaleHelper.setLocale(this, "ar");
                resources = context.getResources();
                Drawable img = resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_left_24);
                binding.btnStart.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

            }

        }
    }

    private void getPatients() {
        if (preferenceManager.getString(Constants.DOCTOR_ID) != null) {

            mainViewModel.getPatientList(preferenceManager.getString(Constants.DOCTOR_ID)).observe(this, new Observer<StateData<PatientInfo>>() {
                @Override
                public void onChanged(StateData<PatientInfo> listStateData) {
                    if (listStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                        PatientInfo patientInfo = listStateData.getData();

                        List<Patient> patients = patientInfo.getDetails();
                        patient = patients.get(0);
                        Log.d("main", "onChanged: patient " + patients.toString());
                        patientSpinnerAdapter = new PatientSpinnerAdapter(MainActivity.this, patients);
                        binding.btnSelectPatient.setAdapter(patientSpinnerAdapter);
                    }

                }
            });
        }
    }

    private void getAutismCurriculum() {
        if (preferenceManager.getString(Constants.DOCTOR_ID) != null) {
            mainViewModel.getAutismCurriculum("en", "1").observe(this, new Observer<StateData<AutismCurriculumInfo>>() {
                @Override
                public void onChanged(StateData<AutismCurriculumInfo> autismCurriculumInfoStateData) {
                    if (autismCurriculumInfoStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                        AutismCurriculumInfo autismCurriculumInfo = autismCurriculumInfoStateData.getData();

                        Log.d("TAG5", "onChanged: autismCurriculumInfo" + autismCurriculumInfo.toString());
                        Log.d("TAG5", "onChanged: getDetails " + autismCurriculumInfo.getDetails().toString());
                    }
                }
            });
        }
    }

    private void initialization() {

        preferenceManager = new PreferenceManager(this);
        realm = Realm.getDefaultInstance();
        gson = new Gson();
        robotController = new RobotController(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferenceManager.getString(LANGUAGE) != null) {
                    if (preferenceManager.getString(LANGUAGE).equals(Constants.ENGLISH)) {
                        sendMessageToRobot(Constants.ENGLISH);
                    } else if (preferenceManager.getString(LANGUAGE).equals(Constants.ARABIC)) {
                        sendMessageToRobot(Constants.ARABIC);
                    }
                }
                Intent intent = new Intent(MainActivity.this, StartGameActivity.class);
                if (module != null) {
                    intent.putExtra(Constants.ModuleNameKEY, module.getModuleName());
                }else {
                    intent.putExtra(Constants.ModuleNameKEY,"Emotional Ability");

                }
                startActivity(intent);

                //  }

            }
        });


    }

    private void setModuleData(Module module) {
        if (module!=null) {
            binding.btnModuleNumber.setText(module.getModuleNumber());
            binding.btnModuleObjective.setText(module.getObjectives());
            binding.btnModuleCurriculum.setText(module.getCurriculum());
            binding.btnModuleArea.setText(module.getAreaOfSpecialization());
        }
    }

    private void chart() {


        BarChart barChart = (BarChart) findViewById(R.id.bar_chart_vertical);
        barChart.setBarMaxValue(100);

        BarChartModel barChartModel = new BarChartModel();
        barChartModel.setBarValue(100);
        barChartModel.setBarColor(Color.parseColor("#ff0000"));


        BarChartModel barChartModel2 = new BarChartModel();
        barChartModel2.setBarValue(50);
        barChartModel2.setBarColor(Color.parseColor("#42b0ff"));

//        barChartModel2.setBarColor(R.color.blueBar);


        BarChartModel barChartModel3 = new BarChartModel();
        barChartModel3.setBarValue(50);
        barChartModel3.setBarColor(Color.parseColor("#92d050"));


        BarChartModel barChartModel4 = new BarChartModel();
        barChartModel4.setBarValue(70);
        barChartModel4.setBarColor(Color.parseColor("#42b0ff"));

        BarChartModel barChartModel5 = new BarChartModel();
        barChartModel5.setBarValue(100);
        barChartModel5.setBarColor(Color.parseColor("#f09456"));

        List<BarChartModel> barChartModelList = new ArrayList<>();
        barChartModelList.add(barChartModel);
        barChartModelList.add(barChartModel2);
        barChartModelList.add(barChartModel3);
        barChartModelList.add(barChartModel4);
        barChartModelList.add(barChartModel5);

        barChart.addBar(barChartModelList);
    }

    private void getModule() {
        if (preferenceManager.getString(Constants.LANGUAGE).equals(Constants.ARABIC)) {
            mainViewModel.getModule_AR().observe(this, moduleStateData -> {
                switch (moduleStateData.getStatus()) {
                    case COMPLETE:
                        break;
                    case ERROR:
                        break;

                    case LOADING:
                        break;
                    case SUCCESS:
                        assert moduleStateData.getData() != null;
                        successCase(moduleStateData.getData());
                        Log.d("TAGH", "getModule: getModule_AR  " + moduleStateData.getData());

                        break;

                }
            });
        } else if (preferenceManager.getString(Constants.LANGUAGE).equals(Constants.ENGLISH)) {
            mainViewModel.getModule().observe(this, moduleStateData -> {
                switch (moduleStateData.getStatus()) {
                    case COMPLETE:
                        break;
                    case ERROR:
                        break;

                    case LOADING:
                        break;
                    case SUCCESS:
                        assert moduleStateData.getData() != null;
                        successCase(moduleStateData.getData());
                        Log.d("TAGH", "getModule: getModule  " + moduleStateData.getData());

                        break;

                }
            });
        }

    }

    private void successCase(List<Module> data) {
        Log.d("TAG5", "successCase:data " + data);
        for (Module m : data) {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(m);

                }
            });

        }

    }


    private void sendMessageToRobot(String content) {
        String messageJSon = "{\"lang\":" + content + "}";
        Log.d("TAG", "sendMessageToRobot:messageJSon " + messageJSon);
        robotController.sendMessageForRobot(messageJSon);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}