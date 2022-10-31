package com.nuwa.robot.r2022.emotionalability.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.Unit;

import java.util.List;

public class LevelWithPhases {


    @Embedded
    public Level level;
    @Relation(
            parentColumn = "idLevel",
            entityColumn = "levelId")
    public List<Phase> phases;

}
