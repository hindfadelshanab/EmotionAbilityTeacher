package com.nuwa.robot.r2022.emotionalability.model;

import androidx.room.Entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Unit extends RealmObject {

    @PrimaryKey
    private int idUnit  ;

    private int moduleId ;
    private String unitName ;
    private RealmList<Level> levels ;

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(int idUnit) {
        this.idUnit = idUnit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public RealmList<Level> getLevels() {
        return levels;
    }

    public void setLevels(RealmList<Level> levels) {
        this.levels = levels;
    }
//    public List<Level> getLevels() {
//        return levels;
//    }
//
//    public void setLevels(List<Level> levels) {
//        this.levels = levels;
//    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + idUnit +
                ", unitName='" + unitName + '\'' +
                ", levels=" + levels +
                '}';
    }
}
