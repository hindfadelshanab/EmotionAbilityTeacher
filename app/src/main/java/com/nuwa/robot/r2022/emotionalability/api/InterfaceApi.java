package com.nuwa.robot.r2022.emotionalability.api;

import com.nuwa.robot.r2022.emotionalability.model.AutismCurriculumInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismModuleInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismObjective;
import com.nuwa.robot.r2022.emotionalability.model.AutismObjectivesInfo;
import com.nuwa.robot.r2022.emotionalability.model.AutismSpecializationInfo;
import com.nuwa.robot.r2022.emotionalability.model.BaselineResultInfo;
import com.nuwa.robot.r2022.emotionalability.model.Doctor;
import com.nuwa.robot.r2022.emotionalability.model.PatientInfo;
import com.nuwa.robot.r2022.emotionalability.model.ResultInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceApi {


    @FormUrlEncoded
    @POST("userLogin")
    Call<Doctor> userLogin(
            @Field("username") String userName,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("patientList")
    Call<PatientInfo> getPatientList(
            @Field("doctor_id") String doctor_id
    );
    @FormUrlEncoded
    @POST("get_autism_curriculum")
    Call<AutismCurriculumInfo> getAutismCurriculum(
            @Field("language") String language ,
            @Field("Display_Type") String displayType
    );

    @FormUrlEncoded
    @POST("get_autism_module")
    Call<AutismModuleInfo> getAutismModule(
            @Field("language") String language ,
            @Field("Curriculum_Id") String Curriculum_Id
    );

    @FormUrlEncoded
    @POST("get_autism_specialization")
    Call<AutismSpecializationInfo> getAutismSpecialization(
            @Field("language") String language ,
            @Field("Module_Id") String Module_Id
    );
    @FormUrlEncoded
    @POST("get_autism_objectives")
    Call<AutismObjectivesInfo> getAutismObjectives(
            @Field("language") String language ,
            @Field("Specialization_Id") String Specialization_Id
    );

    @FormUrlEncoded
    @POST("updateLanguage")
    Call<ResultInfo> updateLanguage(
            @Field("language") String language ,
            @Field("doctor_id") String doctor_id
    );
    @FormUrlEncoded
    @POST("getAutismBaseline")
    Call<BaselineResultInfo> getAutismBaseline(
            @Field("language") String language

    );

    @FormUrlEncoded
    @POST("adduserqeptabletautismanswers")
    Call<ResultInfo> sendAnswerResult(
            @Field("Patient_Id") String Patient_Id ,
            @Field("Doctor_Id") String doctor_id ,
            @Field("Curriculum_Id") String Curriculum_Id ,
            @Field("Module_Id") String Module_Id ,
            @Field("Objectives_Id") String Objectives_Id ,
            @Field("Specialization_Id") String Specialization_Id ,
            @Field("Term") String Term ,
            @Field("Date") String Date ,
            @Field("AET_Autism_Baseline_Id") String AET_Autism_Baseline_Id ,
            @Field("Answer_Value") String Answer_Value ,
            @Field("Created") String Created
    );
}
