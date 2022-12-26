
package com.nuwa.robot.r2022.emotionalability.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "TimeStamp",
    "Curriculum_Id",
    "Autism_Curriculum_Type_AR",
    "Autism_Curriculum_Type_EN",
    "Display_Type",
    "Created",
    "Updated",
    "Position",
    "Autism_Curriculum_Type"
})
public class AutismCurriculum {

    @JsonProperty("TimeStamp")
    private String timeStamp;
    @JsonProperty("Curriculum_Id")
    private String Curriculum_Id;
    @JsonProperty("Autism_Curriculum_Type_AR")
    private String autismCurriculumTypeAR;
    @JsonProperty("Autism_Curriculum_Type_EN")
    private String autismCurriculumTypeEN;
    @JsonProperty("Display_Type")
    private String displayType;
    @JsonProperty("Created")
    private String created;
    @JsonProperty("Updated")
    private String updated;
    @JsonProperty("Position")
    private String position;
    @JsonProperty("Autism_Curriculum_Type")
    private String Autism_Curriculum_Type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("TimeStamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    @JsonProperty("TimeStamp")
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @JsonProperty("Curriculum_Id")
    public String getCurriculum_Id() {
        return Curriculum_Id;
    }

    @JsonProperty("Curriculum_Id")
    public void setCurriculum_Id(String curriculum_Id) {
        this.Curriculum_Id = curriculum_Id;
    }

    @JsonProperty("Autism_Curriculum_Type_AR")
    public String getAutismCurriculumTypeAR() {
        return autismCurriculumTypeAR;
    }

    @JsonProperty("Autism_Curriculum_Type_AR")
    public void setAutismCurriculumTypeAR(String autismCurriculumTypeAR) {
        this.autismCurriculumTypeAR = autismCurriculumTypeAR;
    }

    @JsonProperty("Autism_Curriculum_Type_EN")
    public String getAutismCurriculumTypeEN() {
        return autismCurriculumTypeEN;
    }

    @JsonProperty("Autism_Curriculum_Type_EN")
    public void setAutismCurriculumTypeEN(String autismCurriculumTypeEN) {
        this.autismCurriculumTypeEN = autismCurriculumTypeEN;
    }

    @JsonProperty("Display_Type")
    public String getDisplayType() {
        return displayType;
    }

    @JsonProperty("Display_Type")
    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    @JsonProperty("Created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("Created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("Updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("Updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("Position")
    public String getPosition() {
        return position;
    }

    @JsonProperty("Position")
    public void setPosition(String position) {
        this.position = position;
    }

    @JsonProperty("Autism_Curriculum_Type")
    public String getAutism_Curriculum_Type() {
        return Autism_Curriculum_Type;
    }

    @JsonProperty("Autism_Curriculum_Type")
    public void setAutism_Curriculum_Type(String autism_Curriculum_Type) {
        this.Autism_Curriculum_Type = autism_Curriculum_Type;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "AutismCurriculum{" +
                "timeStamp='" + timeStamp + '\'' +
                ", curriculumId='" + Curriculum_Id + '\'' +
                ", autismCurriculumTypeAR='" + autismCurriculumTypeAR + '\'' +
                ", autismCurriculumTypeEN='" + autismCurriculumTypeEN + '\'' +
                ", displayType='" + displayType + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", position='" + position + '\'' +
                ", Autism_Curriculum_Type='" + Autism_Curriculum_Type + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
