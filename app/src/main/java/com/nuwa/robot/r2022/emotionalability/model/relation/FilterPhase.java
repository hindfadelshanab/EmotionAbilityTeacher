package com.nuwa.robot.r2022.emotionalability.model.relation;

import androidx.room.Embedded;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.Unit;

public class FilterPhase {

    @Embedded
    public Unit unit ;

    @Embedded
    public Level level ;

    @Embedded
    public Phase phase ;
}
