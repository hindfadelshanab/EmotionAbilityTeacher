package com.nuwa.robot.r2022.emotionalability.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AnswerContent extends RealmObject {

    private int answerWay; // true/false - select - drag - select character , selectEmotionTable(5)
    private int dragBy; // size(1) - kind(2) - steps(3) -letters(4) -kind according to Function(5) - what next(6)

    private boolean isHaveImageForShow;
    private String ImageForShow;
    private RealmList<Kind>  kinds;
    private RealmList<ImageOption> imageOptions;

    protected AnswerContent(Parcel in) {
        answerWay = in.readInt();
        dragBy = in.readInt();

        isHaveImageForShow = in.readByte() != 0;
        ImageForShow = in.readString();
        in.readList(imageOptions, ImageOption.class.getClassLoader());
        in.readList(kinds, Kind.class.getClassLoader());
//        kind =  in.readParcelable(Kind.class.getClassLoader());


    }

    public AnswerContent() {
    }


    public int getDragBy() {
        return dragBy;
    }

    public void setDragBy(int dragBy) {
        this.dragBy = dragBy;
    }

    public boolean isHaveImageForShow() {
        return isHaveImageForShow;
    }

    public void setHaveImageForShow(boolean haveImageForShow) {
        isHaveImageForShow = haveImageForShow;
    }

    public String getImageForShow() {
        return ImageForShow;
    }

    public void setImageForShow(String imageForShow) {
        ImageForShow = imageForShow;
    }

    public List<Kind> getKind() {
        return kinds;
    }

    public void setKind(RealmList<Kind> kind) {
        this.kinds = kind;
    }

    public List<ImageOption> getImageOptions() {
        return imageOptions;
    }


    public void setImageOptions(RealmList<ImageOption> imageOptions) {
        this.imageOptions = imageOptions;
    }

    public int getAnswerWay() {
        return answerWay;
    }

    public void setAnswerWay(int answerWay) {
        this.answerWay = answerWay;
    }


}




