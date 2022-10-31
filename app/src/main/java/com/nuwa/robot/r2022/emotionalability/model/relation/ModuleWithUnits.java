package com.nuwa.robot.r2022.emotionalability.model.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.nuwa.robot.r2022.emotionalability.model.Module;
import com.nuwa.robot.r2022.emotionalability.model.Unit;

import java.util.List;

public class ModuleWithUnits {

    @Embedded
    public Module module;
    @Relation(
            parentColumn = "id",
            entityColumn = "moduleId")
    public List<Unit> unitList;
}
