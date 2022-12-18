package com.nuwa.robot.r2022.emotionalability.model;

import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.nuwa.robot.r2022.emotionalability.utils.StateData;

import org.parceler.Parcels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Phase extends RealmObject {


    @PrimaryKey
    private int id;

    private int levelId;
    private int unitId;
    private int moduleId;
    private QuestionContent questioncontent;
    private AnswerContent answerContent;
    private int point;
    private String response;
    private boolean isAnswered;

    public Phase() {
    }

    public Phase(int id, int levelId, int unitId, int moduleId,
                 QuestionContent questioncontent,
                 AnswerContent answerContent,
                 int point, String response, boolean isAnswered) {
        this.id = id;
        this.levelId = levelId;
        this.unitId = unitId;
        this.moduleId = moduleId;
        this.questioncontent = questioncontent;
        this.answerContent = answerContent;
        this.point = point;
        this.response = response;
        this.isAnswered = isAnswered;
    }
//    public Phase(int id, int levelId, int unitId,
//                 QuestionContent questioncontent, AnswerContent answerContent, int point, String response, boolean isAnswered) {
//        this.id = id;
//        this.levelId = levelId;
//        this.unitId = unitId;
//        this.questioncontent = questioncontent;
//        this.answerContent = answerContent;
//        this.point = point;
//        this.response = response;
//        this.isAnswered = isAnswered;
//    }


    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuestionContent getQuestioncontent() {
        return questioncontent;
    }

    public void setQuestioncontent(QuestionContent questioncontent) {
        this.questioncontent = questioncontent;
    }

    public AnswerContent getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(AnswerContent answerContent) {
        this.answerContent = answerContent;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "id=" + id +
                ", levelId=" + levelId +
                ", unitId=" + unitId +
                ", questioncontent=" + questioncontent +
                ", answerContent=" + answerContent +
                ", point=" + point +
                ", response='" + response + '\'' +
                ", isAnswered=" + isAnswered +
                '}';
    }
//    public int getUnitId() {
//        return unitId;
//    }
//
//    public void setUnitId(int unitId) {
//        this.unitId = unitId;
//    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }


    public Phase clone() {
        return new Phase(id, levelId, unitId,moduleId, questioncontent, answerContent, point, response, isAnswered);

    }

}

