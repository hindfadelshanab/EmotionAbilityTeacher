package com.nuwa.robot.r2022.emotionalability.utils;

import java.util.ArrayList;
import java.util.List;

public class ReaderGame <T>{


    List<T> list = new ArrayList();
    List<T> phaseList = new ArrayList();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    public void addToList(T t){
        list.add(t);
    }

    public List<T> getPhaseList() {
        return phaseList;
    }

    public void setPhaseList(List<T> phaseList) {
        this.phaseList = phaseList;
    }
    public  void addToPhaseList(T t){
        phaseList.add(t);

    }
}
