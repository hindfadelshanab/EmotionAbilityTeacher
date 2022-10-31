package com.nuwa.robot.r2022.emotionalability.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.relation.FilterPhase;

import java.util.List;

@Dao
public interface GameDao {
//
//     @Insert(onConflict = OnConflictStrategy.REPLACE)
//     void insertModule(Module module);
//
//     @Query("SELECT * FROM module WHERE id =:id")
//     Module getModule(int id);
//
//     @Transaction
//     @Query("SELECT * FROM  Phase WHERE id =:id ")
//     Phase getPhase(int id );
//
//
//     @Query("SELECT * FROM   Level JOIN Phase ON level.idLevel = phase.levelId WHERE id =:id")
//     Phase getFiltered(int id );

//     @Insert(onConflict = OnConflictStrategy.REPLACE)
//     void  insertPhase(Phase phase);
}
