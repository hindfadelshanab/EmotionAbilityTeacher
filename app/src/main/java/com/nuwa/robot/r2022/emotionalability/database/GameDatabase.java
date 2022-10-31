package com.nuwa.robot.r2022.emotionalability.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.Unit;
import com.nuwa.robot.r2022.emotionalability.utils.Converters;

//@TypeConverters(Converters.class)

public abstract class GameDatabase  {

//    public abstract GameDao gameDao();
//
//    private static GameDatabase instance;
//
//    public static synchronized GameDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                    GameDatabase.class, "game_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
//
//        }
//        return instance;
//
//    }


}
