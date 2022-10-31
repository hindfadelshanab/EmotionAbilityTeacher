package com.nuwa.robot.r2022.emotionalability.model;


import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Module  extends RealmObject {

    @PrimaryKey
    private int id ;

    private String moduleName ;
    private String moduleNumber ;
    private String curriculum  ;
    private String areaOfSpecialization;
    private String objectives   ;


    private RealmList<Unit> units ;






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(String moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getAreaOfSpecialization() {
        return areaOfSpecialization;
    }

    public void setAreaOfSpecialization(String areaOfSpecialization) {
        this.areaOfSpecialization = areaOfSpecialization;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public RealmList<Unit> getUnits() {
        return units;
    }

    public void setUnits(RealmList<Unit> units) {
        this.units = units;
    }
//    public List<Unit> getUnits() {
//        return units;
//    }
//
//    public void setUnits(RealmList<Unit> units) {
//        this.units = units;
//    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", moduleNumber='" + moduleNumber + '\'' +
                ", curriculum='" + curriculum + '\'' +
                ", areaOfSpecialization='" + areaOfSpecialization + '\'' +
                ", objectives='" + objectives + '\'' +
                ", units=" + units +
                '}';
    }
}
