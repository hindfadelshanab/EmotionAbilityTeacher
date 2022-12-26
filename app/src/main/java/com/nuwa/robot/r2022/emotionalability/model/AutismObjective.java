
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
    "Objectives_Id",
    "Specialization_Id",
    "Autism_Objectives_Type_AR",
    "Autism_Objectives_Type_EN",
    "Display_Type",
    "Created",
    "Updated",
    "Position",
    "Status",
    "Autism_Objectives_Type"
})
public class AutismObjective {

    @JsonProperty("TimeStamp")
    private String timeStamp;
    @JsonProperty("Objectives_Id")
    private String Objectives_Id;
    @JsonProperty("Specialization_Id")
    private String Specialization_Id;
    @JsonProperty("Autism_Objectives_Type_AR")
    private String autismObjectivesTypeAR;
    @JsonProperty("Autism_Objectives_Type_EN")
    private String autismObjectivesTypeEN;
    @JsonProperty("Display_Type")
    private String displayType;
    @JsonProperty("Created")
    private String created;
    @JsonProperty("Updated")
    private String updated;
    @JsonProperty("Position")
    private String position;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Autism_Objectives_Type")
    private String Autism_Objectives_Type;
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

    @JsonProperty("Objectives_Id")
    public String getObjectives_Id() {
        return Objectives_Id;
    }

    @JsonProperty("Objectives_Id")
    public void setObjectives_Id(String objectives_Id) {
        this.Objectives_Id = objectives_Id;
    }

    @JsonProperty("Specialization_Id")
    public String getSpecialization_Id() {
        return Specialization_Id;
    }

    @JsonProperty("Specialization_Id")
    public void setSpecialization_Id(String specialization_Id) {
        this.Specialization_Id = specialization_Id;
    }

    @JsonProperty("Autism_Objectives_Type_AR")
    public String getAutismObjectivesTypeAR() {
        return autismObjectivesTypeAR;
    }

    @JsonProperty("Autism_Objectives_Type_AR")
    public void setAutismObjectivesTypeAR(String autismObjectivesTypeAR) {
        this.autismObjectivesTypeAR = autismObjectivesTypeAR;
    }

    @JsonProperty("Autism_Objectives_Type_EN")
    public String getAutismObjectivesTypeEN() {
        return autismObjectivesTypeEN;
    }

    @JsonProperty("Autism_Objectives_Type_EN")
    public void setAutismObjectivesTypeEN(String autismObjectivesTypeEN) {
        this.autismObjectivesTypeEN = autismObjectivesTypeEN;
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

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("Autism_Objectives_Type")
    public String getAutism_Objectives_Type() {
        return Autism_Objectives_Type;
    }

    @JsonProperty("Autism_Objectives_Type")
    public void setAutism_Objectives_Type(String autism_Objectives_Type) {
        this.Autism_Objectives_Type = autism_Objectives_Type;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
