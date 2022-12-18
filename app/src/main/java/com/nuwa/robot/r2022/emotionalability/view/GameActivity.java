package com.nuwa.robot.r2022.emotionalability.view;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nuwa.robot.r2022.emotionalability.fragment.PhaseFragment;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.adapter.QuestionAdapter;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityGameBinding;
import com.nuwa.robot.r2022.emotionalability.listener.OnMessageReciveListener;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnswered;
import com.nuwa.robot.r2022.emotionalability.model.PhaseAnsweredLiveData;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherClient;
import com.nuwa.robot.r2022.emotionalability.networking.TeacherSocketClient;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;
import com.nuwa.robot.r2022.emotionalability.utils.StateData;
import com.nuwa.robot.r2022.emotionalability.viewModel.GameViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import io.realm.Realm;

public class GameActivity extends AppCompatActivity implements TeacherSocketClient.IOnMessageListener {

    private ActivityGameBinding binding;
    private Level level;
    private QuestionAdapter questionAdapter;

    private GameViewModel gameViewModel;
    private PreferenceManager preferenceManager;
    private Realm realm;
    TeacherSocketClient teacherClient;
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        initialization();
        initConnection();
        binding.setVm(gameViewModel);

        gameViewModel.getNextButtonClickedLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                next(binding.sViewPager);
            }
        });

        gameViewModel.getPreviousButtonClickedLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                last(binding.sViewPager);
            }
        });

        gameViewModel.getPhaseCompletedLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("TAG", "onChanged: getPhaseCompletedLiveData");
                if (integer == 1)
                launchNewLevel();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        TeacherSocketClient.addIOnMessageListener(this);
    }

    @Override
    protected void onDestroy() {
        TeacherSocketClient.removeIOnMessageListener(this);

        super.onDestroy();
    }

    private void initConnection() {

        String serverIp = preferenceManager.getString(Constants.IPKEY);
        if (serverIp != null) {
            try {
                teacherClient =  new TeacherSocketClient(new URI("ws://" + serverIp + ":8887"));
                teacherClient.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialization() {
        preferenceManager = new PreferenceManager(this);
        realm = Realm.getDefaultInstance();
        gson = new Gson();
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        int levelId = getIntent().getIntExtra(Constants.LEVEL_ID_Key, 0);
        int unitId = getIntent().getIntExtra(Constants.UNIT_ID_KEY, 0);
        level = realm.where(Level.class).equalTo("idLevel", levelId).equalTo("unitId", unitId).findFirst();
        questionAdapter = new QuestionAdapter(this ,level);
        binding.sViewPager.setAdapter(questionAdapter);



    }




    public void next(ViewPager2 viewPager) {
        int position = viewPager.getCurrentItem();
        goPosition(viewPager, position + 1);


    }

    private void launchNewLevel() {
        binding.sViewPager.setVisibility(View.VISIBLE);
        binding.container.setVisibility(View.GONE);

        phaseListPosition = 0;
        gameViewModel.getNextLevel(level.getIdLevel(), level.getUnitId()).observe(this, levelStateData -> {
            if (levelStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                if (levelStateData.getData() != null) {
                    level = levelStateData.getData();
                    questionAdapter = new QuestionAdapter(this , level);
                    binding.sViewPager.setAdapter(questionAdapter);
                }
            }
        });



    }

    int phaseListPosition = 0;



    public  void last(ViewPager2 viewPager) {
        Boolean isFirst = gameViewModel.getISFirstItemInPhase().getValue().getData() ;
       if (isFirst!=null){
           if (!isFirst){
               int position = viewPager.getCurrentItem();
               goPosition(viewPager, position - 1);

           }
       }
    }

    public static void goPosition(ViewPager2 viewPager, int position) {
        Log.d("TAG", "goPosition: " + position);
        goPosition(viewPager, position, true);
    }


    public static void goPosition(ViewPager2 viewPager, int position, boolean needAnim) {

        int count = viewPager.getAdapter().getItemCount();
        if (position >= 0 && position < count) {
            viewPager.setCurrentItem(position, needAnim);
        }
    }

    @Override
    public void onMessage(String Message) {

    }

    @Override
    public void onMessage(ByteBuffer message) {
    }

    @Override
    public void onError(Exception ex) {
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
