package com.nuwa.robot.r2022.emotionalability.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityMainBinding;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.RobotController;
import com.nuwa.robot.r2022.emotionalability.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmResults;
import me.ithebk.barchart.BarChart;
import me.ithebk.barchart.BarChartModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    private Module module ;
    private Realm realm;
    PreferenceManager preferenceManager;
    RobotController robotController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialization();

        getModule();
        chart();

        binding.btnSelectPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                robotController.sendMessage("Message From Teacher");


            }
        });




    }

    private void initialization() {
         realm = Realm.getDefaultInstance();
         preferenceManager = new PreferenceManager(this);
        robotController = new RobotController(this);



        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (module!=null){
                    Toast.makeText(MainActivity.this, "module id" +module.getId(), Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(MainActivity.this , StartGameActivity.class);
                    intent.putExtra(Constants.ModuleNameKEY ,module.getModuleName());
                    startActivity(intent);


                }

            }
        });

        RealmResults<Module> mmmm=realm.where(Module.class).findAll();
        if (mmmm!=null){
//            Log.d("TAG", "initialization spacecrafts : "+spacecrafts.get(0).getQuestioncontent().getTitle());
            Log.d("TAG", "initialization mmmm : "+mmmm.size());
        }
        RealmResults<Level> levels=realm.where(Level.class).findAll();

        if (levels!=null){
            for (Level le :levels) {
                Log.d("TAG", "initialization le: ." + le.toString());
            }
//            Log.d("TAG", "initialization spacecrafts : "+spacecrafts.get(0).getQuestioncontent().getTitle());
            Log.d("TAG", "initialization  reee levels : "+levels.size());
//            Log.d("TAG", "initialization spacecrafts : "+spacecrafts.get(0).toString());
        }
        RealmResults<Phase> spacecrafts=realm.where(Phase.class).findAll();
        if (spacecrafts!=null){
//            Log.d("TAG", "initialization spacecrafts : "+spacecrafts.get(0).getQuestioncontent().getTitle());
            Log.d("TAG", "initialization spacecrafts : "+spacecrafts.size());
//            Log.d("TAG", "initialization spacecrafts : "+spacecrafts.get(0).toString());
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
                    break;

            }
        });
    }

    private void successCase(Module data) {
        module =data;

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // inside on execute method we are calling a method
                // to copy to real m database from our modal class.
//                realm.beginTransaction();


           realm.insertOrUpdate(data );

//                Log.d("TAG", "execute: "+m.toString());
            }
        });

        Log.d("TAG", "successCase:  " + data.toString());
        binding.btnModuleArea.setText(data.getAreaOfSpecialization());
        binding.btnModuleCurriculum.setText(data.getCurriculum());
        binding.btnModuleNumber.setText(data.getModuleNumber());
        binding.btnModuleObjective.setText(data.getObjectives());


    }

}