package com.nuwa.robot.r2022.emotionalability.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Level extends RealmObject   {

    @PrimaryKey
    private int idLevel;
    private int unitId ;
    private int moduleId ;
    private String color;
    private String image;
    private String name;
    private String objective;
    private String gameName;
    private String gameDescription;
    private RealmList<Phase> phases  ;

    public Level() {
    }

    public int getIdLevel() {
        return idLevel;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public RealmList<Phase> getPhases() {
        return phases;
    }

    public void setPhases(RealmList<Phase> phases) {
        this.phases = phases;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }


}
