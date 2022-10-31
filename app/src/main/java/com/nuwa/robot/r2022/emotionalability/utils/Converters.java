package com.nuwa.robot.r2022.emotionalability.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuwa.robot.r2022.emotionalability.model.AnswerContent;
import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.QuestionContent;
import com.nuwa.robot.r2022.emotionalability.model.Unit;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    Gson gson = new Gson();

    @TypeConverter
    public String fromUnitToGson(List<Unit> unit )  {
        return gson.toJson(unit) ;

    }

    @TypeConverter
    public List<Unit> fromGsonToUnit(String stringUnit){

        if (stringUnit == null) {
            return (null);
        }
        Type type = new TypeToken<List<Unit>>() {}.getType();
        List<Unit> unitList = gson.fromJson(stringUnit, type);
        return unitList;

    }









    @TypeConverter
    public String fromLevelsToGson(List<Level> levels )  {
        return gson.toJson(levels) ;

    }

    @TypeConverter
    public List<Level> fromGsonToLevels(String stringLevels){

        if (stringLevels == null) {
            return (null);
        }
        Type type = new TypeToken<List<Level>>() {}.getType();
        List<Level> levelList = gson.fromJson(stringLevels, type);
        return levelList;

    }










    @TypeConverter
    public String fromPhasesToGson(List<Phase> phases )  {
        return gson.toJson(phases) ;

    }

    @TypeConverter
    public List<Phase> fromGsonToPhases(String string){

        if (string == null) {
            return (null);
        }
        Type type = new TypeToken<List<Phase>>() {}.getType();
        List<Phase> phaseList = gson.fromJson(string, type);
        return phaseList;

    }



    @TypeConverter
    public String fromQuestionContentToGson(QuestionContent questionContent)  {
        return gson.toJson(questionContent) ;

    }
    @TypeConverter
    public QuestionContent fromGsonToQuestionContent( String stingQuestionContent) {
        return gson.fromJson(stingQuestionContent, QuestionContent.class);

    }







    @TypeConverter
    public String fromAnswerContentToGson(AnswerContent answerContent)  {
        return gson.toJson(answerContent) ;

    }
    @TypeConverter
    public AnswerContent fromGsonToAnswerContent( String stingAnswerContent) {
        return gson.fromJson(stingAnswerContent, AnswerContent.class);

    }
}
