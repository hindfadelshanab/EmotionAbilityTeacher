package com.nuwa.robot.r2022.emotionalability.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Unit;

import java.util.List;

public class UnitWithLevels {

    @Embedded
    public Unit unit;
    @Relation(
            parentColumn = "id",
            entityColumn = "unitId")
    public List<Level> levels;
}
