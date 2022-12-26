package com.nuwa.robot.r2022.emotionalability.view;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.DOCTOR_ID;
import static com.nuwa.robot.r2022.emotionalability.utils.Constants.DOCTOR_NAME;
import static com.nuwa.robot.r2022.emotionalability.utils.Constants.LANGUAGE;
import static com.nuwa.robot.r2022.emotionalability.utils.Constants.TERM_ID;
import static com.nuwa.robot.r2022.emotionalability.utils.Constants.TERM_POSITION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.adapter.AutismModuleSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.AutismSpecializationAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.BaseLineSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.CurriculumSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.PatientSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.adapter.TermSpinnerAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityMainBinding;
import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculum;
import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculumInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismModule;
import com.nuwa.robot.r2022.emotionalability.model.AutismModuleInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismObjective;
import com.nuwa.robot.r2022.emotionalability.model.AutismObjectivesInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismSpecialization;
import com.nuwa.robot.r2022.emotionalability.model.AutismSpecializationInfo;
import com.nuwa.robot.r2022.emotionalability.model.Baseline;
import com.nuwa.robot.r2022.emotionalability.model.BaselineResultInfo;
import com.nuwa.robot.r2022.emotionalability.model.Details;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Patient;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.PatientInfo;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.MainViewModel;
import com.nuwa.robot.r2022.emotionalability.viewModel.SignInViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private MainViewModel mainViewModel;
    private SignInViewModel signInViewModel;
    private ActivityMainBinding binding;
    private Realm realm;
    private String curriculumIdSelected;
    PreferenceManager preferenceManager;
    Context context;
    Resources resources;
    RobotController robotController;
    Gson gson;
    Patient patient;
    PatientSpinnerAdapter patientSpinnerAdapter;
    TermSpinnerAdapter termSpinnerAdapter;
    CurriculumSpinnerAdapter curriculumSpinnerAdapter;
    AutismModuleSpinnerAdapter autismModuleSpinnerAdapter;
    AutismSpecializationAdapter autismSpecializationAdapter;
    BaseLineSpinnerAdapter baseLineSpinnerAdapter;
    Module module;
    String selectedObjective;
    int endedSpecialization;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    int endedModule;
    List<Level> levels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialization();
        getModule();
        getCurrentModule();
        if (preferenceManager.getString(DOCTOR_ID) != null) {
            getPatients();
        } else {
            binding.imagePatient.setImageResource(R.drawable.patient);
        }
        getAutismCurriculum();
//        getAutismModule();

        checkSelectedLanguage();
        setTermList();
        getAutismBaseline();
        chart();
      String doctorName=  preferenceManager.getString(DOCTOR_NAME);
      binding.txtDoctorName.setText(doctorName);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.myDrawerLayout, R.string.nav_open, R.string.nav_close);

        if (preferenceManager.getString(Constants.DOCTOR_ID)!=null) {
            binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
            binding.navView.setNavigationItemSelectedListener(this);
            actionBarDrawerToggle.syncState();
            showDoctorData();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.navView.findViewById(R.id.btnLogut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , SignAsActivity.class));
            }
        });
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

                        binding.btnSelectPatient.setSelection(preferenceManager.getInt(Constants.PATIENT_POSITION) , true);
                        patientSpinnerAdapter.notifyDataSetChanged();
                    }

                }
            });
        }
        binding.btnSelectPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Patient patient = (Patient) binding.btnSelectPatient.getSelectedItem();

                if (patient != null) {

                    binding.txtPatientName.setText(patient.getUsername());
                    Glide.with(MainActivity.this).load(patient.getImage()).into(binding.imagePatient);
                    preferenceManager.putString(Constants.PATIENT_ID, patient.getId());
                    preferenceManager.putInt(Constants.PATIENT_POSITION, i);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setTermList() {
        String[] data = {"First Term", "Second Term", "Third Term"};
        termSpinnerAdapter = new TermSpinnerAdapter(MainActivity.this, Arrays.asList(data));
        binding.termSpinner.setAdapter(termSpinnerAdapter);
        binding.termSpinner.setSelection(preferenceManager.getInt(TERM_POSITION), true);

        termSpinnerAdapter.notifyDataSetChanged();
        binding.termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                preferenceManager.putString(Constants.TERM_ID, String.valueOf((i + 1)));
                preferenceManager.putInt(TERM_POSITION, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAutismCurriculum() {
        if (preferenceManager.getString(Constants.LANGUAGE_SIGN_IN) != null) {
            mainViewModel.getAutismCurriculum(preferenceManager.getString(Constants.LANGUAGE_SIGN_IN), "1").observe(this, new Observer<StateData<AutismCurriculumInfo>>() {
                @Override
                public void onChanged(StateData<AutismCurriculumInfo> autismCurriculumInfoStateData) {
                    if (autismCurriculumInfoStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                        AutismCurriculumInfo autismCurriculumInfo = autismCurriculumInfoStateData.getData();

                        List<AutismCurriculum> autismCurriculumList = autismCurriculumInfo.getDetails();

                        curriculumSpinnerAdapter = new CurriculumSpinnerAdapter(MainActivity.this, autismCurriculumList);
                        binding.spinnerCurriculum.setAdapter(curriculumSpinnerAdapter);


                        Log.d("TAG5", "onChanged: autismCurriculumInfo" + autismCurriculumInfo.toString());
                        Log.d("TAG5", "onChanged: getDetails " + autismCurriculumInfo.getDetails().toString());
                    }
                }
            });

            binding.spinnerCurriculum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    AutismCurriculum autismCurriculum = (AutismCurriculum) binding.spinnerCurriculum.getSelectedItem();
                    curriculumIdSelected = autismCurriculum.getCurriculum_Id();

                    preferenceManager.putString(Constants.CURRICULUM_ID, autismCurriculum.getCurriculum_Id());
                    Log.d("TAG5", "onItemSelected: curriculumIdSelected " + autismCurriculum.toString());
                    getAutismModule(autismCurriculum.getCurriculum_Id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }


    private void getAutismModule(String curriculumId) {
        Log.d("TAG5", "onChanged: getAutismModule" + preferenceManager.getString(Constants.LANGUAGE_SIGN_IN));
        Log.d("TAG5", "onChanged: getAutismModule curriculumIdSelected" + curriculumIdSelected);

        if (preferenceManager.getString(Constants.LANGUAGE_SIGN_IN) != null && curriculumId != null) {
            mainViewModel.getAutismModule(preferenceManager.getString(Constants.LANGUAGE_SIGN_IN), curriculumId).observe(this, new Observer<StateData<AutismModuleInfo>>() {
                @Override
                public void onChanged(StateData<AutismModuleInfo> autismModuleInfoStateData) {
                    if (autismModuleInfoStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                        AutismModuleInfo autismModuleInfo = autismModuleInfoStateData.getData();

                        List<AutismModule> autismCurriculumList = autismModuleInfo.getDetails();

                        autismModuleSpinnerAdapter = new AutismModuleSpinnerAdapter(MainActivity.this, autismCurriculumList);
                        binding.spinnerModule.setAdapter(autismModuleSpinnerAdapter);


                        Log.d("TAG5", "onChanged: getAutismModule" + autismModuleInfo.toString());
                        Log.d("TAG5", "onChanged: getAutismModule" + autismModuleInfoStateData.getData().toString());
                        Log.d("TAG5", "onChanged: getAutismModule" + autismModuleInfoStateData.getStatus().toString());
//                        Log.d("TAG5", "onChanged: getDetails " + autismModuleInfo.getDetails().toString());

                    }
                }
            });

            binding.spinnerModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    AutismModule autismModule = (AutismModule) binding.spinnerModule.getSelectedItem();
                    preferenceManager.putString(Constants.MODULE_ID, autismModule.getModule_Id());

                    getAutismSpecialization(autismModule.getModule_Id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }


    private void getAutismSpecialization(String moduleId) {
        Log.d("TAG5", "onChanged: getAutismSpecialization" + preferenceManager.getString(Constants.LANGUAGE_SIGN_IN));
        Log.d("TAG5", "onChanged: getAutismSpecialization moduleId" + moduleId);

        if (preferenceManager.getString(Constants.LANGUAGE_SIGN_IN) != null && moduleId != null) {
            mainViewModel.getAutismSpecialization(preferenceManager.getString(Constants.LANGUAGE_SIGN_IN), moduleId).observe(this, new Observer<StateData<AutismSpecializationInfo>>() {
                @Override
                public void onChanged(StateData<AutismSpecializationInfo> autismModuleInfoStateData) {
                    if (autismModuleInfoStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                        AutismSpecializationInfo autismModuleInfo = autismModuleInfoStateData.getData();

                        List<AutismSpecialization> autismCurriculumList = autismModuleInfo.getDetails();

                        autismSpecializationAdapter = new AutismSpecializationAdapter(MainActivity.this, autismCurriculumList);
                        binding.spinnerSpecialization.setAdapter(autismSpecializationAdapter);


                        Log.d("TAG5", "onChanged: getAutismSpecialization" + autismModuleInfo.toString());
                        Log.d("TAG5", "onChanged: getAutismSpecialization" + autismModuleInfoStateData.getData().toString());
                        Log.d("TAG5", "onChanged: getAutismSpecialization" + autismModuleInfoStateData.getStatus().toString());
//                        Log.d("TAG5", "onChanged: getDetails " + autismModuleInfo.getDetails().toString());

                    }
                }
            });
            binding.spinnerSpecialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    AutismSpecialization autismSpecialization = (AutismSpecialization) binding.spinnerSpecialization.getSelectedItem();
                    preferenceManager.putString(Constants.SPECIALIZATION_ID, autismSpecialization.getSpecialization_Id());

                    getAutismObjectives(autismSpecialization.getSpecialization_Id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }


    private void getAutismObjectives(String specialization_Id) {

        if (preferenceManager.getString(Constants.LANGUAGE_SIGN_IN) != null && specialization_Id != null) {
            mainViewModel.getAutismObjectives(preferenceManager.getString(Constants.LANGUAGE_SIGN_IN), specialization_Id).observe(this, new Observer<StateData<AutismObjectivesInfo>>() {
                @Override
                public void onChanged(StateData<AutismObjectivesInfo> autismModuleInfoStateData) {
                    if (autismModuleInfoStateData.getStatus() == StateData.DataStatus.SUCCESS) {

                        AutismObjectivesInfo autismModuleInfo = autismModuleInfoStateData.getData();
                        if (autismModuleInfo.getSuccess().equals("1")) {

                            List<AutismObjective> autismCurriculumList = autismModuleInfo.getDetails();
                            preferenceManager.putString(Constants.OBJECTIVES_ID, autismCurriculumList.get(0).getObjectives_Id());
                            selectedObjective = autismCurriculumList.get(0).getAutism_Objectives_Type();
                            binding.txtModuleObjective.setText(autismCurriculumList.get(0).getAutism_Objectives_Type());
                        }
                        Log.d("TAG5", "onChanged: getAutismSpecialization" + autismModuleInfo.toString());
                        Log.d("TAG5", "onChanged: getAutismSpecialization" + autismModuleInfoStateData.getData().toString());
                        Log.d("TAG5", "onChanged: getAutismSpecialization" + autismModuleInfoStateData.getStatus().toString());
//                        Log.d("TAG5", "onChanged: getDetails " + autismModuleInfo.getDetails().toString());

                    }
                }
            });


        }
    }


    private void getAutismBaseline() {
        if (preferenceManager.getString(Constants.LANGUAGE_SIGN_IN) != null) {
            mainViewModel.getAutismBaseline(preferenceManager.getString(Constants.LANGUAGE_SIGN_IN)).observe(this, new Observer<StateData<BaselineResultInfo>>() {
                @Override
                public void onChanged(StateData<BaselineResultInfo> baselineResultInfoStateData) {
                    if (baselineResultInfoStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                        BaselineResultInfo baselineResultInfo = baselineResultInfoStateData.getData();

                        List<Baseline> baselines = baselineResultInfo.getDetails();
//                       patient = patients.get(0);
//                       Log.d("main", "onChanged: patient " + patients.toString());
                        baseLineSpinnerAdapter = new BaseLineSpinnerAdapter(MainActivity.this, baselines);
                        binding.spinnerBaseLine.setAdapter(baseLineSpinnerAdapter);

                        binding.spinnerBaseLine.setSelection(preferenceManager.getInt(Constants.BASELINE_POSITION), true);
                        baseLineSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
        binding.spinnerBaseLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Baseline baseline = (Baseline) binding.spinnerBaseLine.getSelectedItem();
//                Log.d("TAG6", "onItemSelected: baseline "+baseline.toString());

                preferenceManager.putString(Constants.BASELINE_ID, baseline.getId());
                preferenceManager.putInt(Constants.BASELINE_POSITION, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initialization() {

        preferenceManager = new PreferenceManager(this);
        realm = Realm.getDefaultInstance();
        gson = new Gson();
        robotController = new RobotController(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
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
                Intent intent = new Intent(MainActivity.this, StartLevelActivity.class);
                if (selectedObjective != null) {
                    intent.putExtra(Constants.SELECTED_OBJECTIVES, selectedObjective);
                    startActivity(intent);
                }


                //  }

            }
        });


    }

    private void setModuleData(Module module) {
        if (module != null) {
//            binding.btnModuleNumber.setText(module.getModuleNumber());
            binding.txtModuleObjective.setText(module.getObjectives());
//            binding.btnModuleCurriculum.setText(module.getCurriculum());
//            binding.btnModuleArea.setText(module.getAreaOfSpecialization());
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.changeLanguageAction){
            startActivity(new Intent(this , ChangeLanguageActivity.class));
            Toast.makeText(this, "changeLanguageAction", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId()==R.id.homeAction){
            startActivity(new Intent(this , BeginningActivity.class));

        }
        return false;
    }

    private void showDoctorData(){
        View headerView =   binding.navView.getHeaderView(0);
        ImageView patientImage =headerView.findViewById(R.id.patientImage);
        TextView patientName =headerView.findViewById(R.id.doctorName);

        signInViewModel.getSignedDoctor(preferenceManager.getString(Constants.DOCTOR_ID)).observe(this, new Observer<StateData<Details>>() {
            @Override
            public void onChanged(StateData<Details> detailsStateData) {
                if (detailsStateData.getStatus()== StateData.DataStatus.SUCCESS) {
                    patientName.setText(detailsStateData.getData().getDoctor_Name());
                    Log.d("TAG sign", "onChanged: detailsStateData"+detailsStateData.getData().getDoctor_Name());

                    Glide.with(MainActivity.this).load(detailsStateData.getData().getImage()).into(patientImage);
                }
                Log.d("TAG sign", "onChanged: detailsStateData"+detailsStateData.getData().toString());
                Log.d("TAG sign", "onChanged: detailsStateData"+detailsStateData.getData().getUsername());
            }
        });
    }
//
//
//    private void SetNextModule() {
//
//
//        Constants.addToLEVEL_ID_AND_POSITION_HASH_MAP();
//        Constants.addToUNIT_ID_AND_POSITION_HASH_MAP();
//        endedSpecialization = preferenceManager.getInt(Constants.LEVEL_ENDED_ID_Key);
//        endedModule = preferenceManager.getInt(Constants.UNIT_ENDED_ID_KEY);
//        List<Unit> units = Collections.singletonList(realm.where(Unit.class).findAll());
//
//
//        if (units != null) {
//            if (endedModule > 0) {
//                int endedModulePotion = Constants.UNIT_ID_AND_POSITION_HASH_MAP.get(endedModule);
//                int endedSpecializationPotion = Constants.LEVEL_ID_AND_POSITION_HASH_MAP.get(endedSpecialization);
//
//
//                Log.d("TAG7", "SetNextModule:endedModulePotion " + endedModulePotion);
//                Log.d("TAG7", "SetNextModule:endedSpecializationPotion " + endedSpecializationPotion);
////                levels = realm.where(Level.class).equalTo("unitId", endedModulePotion).findAll();
//
//                if (units.size() > endedModule) {
//                    if (levels.size() > (endedSpecializationPotion + 1)) {
//                        binding.spinnerSpecialization.setSelection((endedSpecializationPotion + 1), true);
//
//
//                    } else if (levels.size() == (endedSpecializationPotion + 1)) {
//                        binding.spinnerModule.setSelection((endedModulePotion + 1), true);
//                        binding.spinnerSpecialization.setSelection(0, true);
//
//
//                    }
//                } else if (units.size() == endedModule) {
//
//                    if (levels.size() == (endedSpecializationPotion + 1)) {
//                        Log.d("TAG2", "init: The End ");
//                        binding.spinnerModule.setSelection(0, true);
//                        binding.spinnerSpecialization.setSelection(0, true);
////                        initLevelSpinner(1, 0);
////                        initUnitSpinner(1, 1, 0);
//                    } else {
//                        Log.d("TAG2", "init: not The End ");
//
////                        initLevelSpinner(endedUnit, 0);
////                        initUnitSpinner(1, endedUnit, (endedLevelPotion + 1));
//                    }
//                }
//
//
//            } else if (endedModule == 0) {
//
//            }
//        }
//
//
//    }
}