package com.nuwa.robot.r2022.emotionalability.model;

import org.parceler.Parcel;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

public class QuestionContent  extends   RealmObject  {
     String title ;
     String image ;
     String faceExpression ;
     String bodyMotion ;

    public QuestionContent() {
    }

    public QuestionContent(String title, String image, String faceExpression, String bodyMotion) {
        this.title = title;
        this.image = image;
        this.faceExpression = faceExpression;
        this.bodyMotion = bodyMotion;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFaceExpression() {
        return faceExpression;
    }

    public void setFaceExpression(String faceExpression) {
        this.faceExpression = faceExpression;
    }

    public String getBodyMotion() {
        return bodyMotion;
    }

    public void setBodyMotion(String bodyMotion) {
        this.bodyMotion = bodyMotion;
    }

    @Override
    public String toString() {
        return "QuestionContent{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", faceExpression='" + faceExpression + '\'' +
                ", bodyMotion='" + bodyMotion + '\'' +
                '}';
    }
}
