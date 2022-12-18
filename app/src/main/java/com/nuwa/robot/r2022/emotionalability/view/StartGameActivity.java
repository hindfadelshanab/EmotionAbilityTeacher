package com.nuwa.robot.r2022.emotionalability.view;

import static com.nuwa.robot.r2022.emotionalability.utils.Constants.LANGUAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nuwa.robot.r2022.emotionalability.R;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivitySignInBinding;
import com.nuwa.robot.r2022.emotionalability.databinding.ActivityStartGameBinding;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.utils.Constants;
import com.nuwa.robot.r2022.emotionalability.utils.LocaleHelper;
import com.nuwa.robot.r2022.emotionalability.utils.PreferenceManager;

import io.realm.Realm;
import io.realm.RealmResults;

public class StartGameActivity extends AppCompatActivity  {

    ActivityStartGameBinding binding;
    PreferenceManager preferenceManager;
    Context context;
    Resources resources;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Realm realm = Realm.getDefaultInstance();
        preferenceManager = new PreferenceManager(this);

//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.myDrawerLayout, R.string.nav_open, R.string.nav_close);
//        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
//        binding.navView.setNavigationItemSelectedListener(this);
//        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (preferenceManager.getString(LANGUAGE) != null) {
            Log.d("TAG9", "onCreate: " + preferenceManager.getString(LANGUAGE));
            if (preferenceManager.getString(LANGUAGE).equals(Constants.ENGLISH)) {

                context = LocaleHelper.setLocale(this, "en");
                resources = context.getResources();
//                binding.btnStart.setText(resources.getString(R.string.start));


            } else if (preferenceManager.getString(LANGUAGE).equals(Constants.ARABIC)) {
                context = LocaleHelper.setLocale(this, "ar");
                resources = context.getResources();
                Drawable img = resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_left_24);
                binding.btnPrevious.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                binding.btnNext.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);


            }


        }

        RealmResults<Module> mmmm=realm.where(Module.class).findAll();
        if (mmmm!=null){
//            Log.d("TAG", "initialization spacecrafts : "+spacecrafts.get(0).getQuestioncontent().getTitle());
            Log.d("TAG", "initialization mmmm : "+mmmm.size());
            Log.d("TAG", "initialization mmmm : "+mmmm.toString());
        }
        String moduleName = getIntent().getStringExtra(Constants.ModuleNameKEY);

        if (moduleName != null)
            binding.txtModuleName.setText(moduleName);

        binding.btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(StartGameActivity.this, GameLevelActivity.class);
            startActivity(intent);

        });        binding.btnPrevious.setOnClickListener(view -> {
            Intent intent = new Intent(StartGameActivity.this, MainActivity.class);
            startActivity(intent);

        });



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId()==R.id.changeLanguageAction){
//            startActivity(new Intent(this , ChangeLanguageActivity.class));
//            Toast.makeText(context, "changeLanguageAction", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, " langg :::"+ preferenceManager.getString(Constants.LANGUAGE_SIGN_IN), Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
}