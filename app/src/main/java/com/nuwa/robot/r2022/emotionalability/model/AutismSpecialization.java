
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
    "Specialization_Id",
    "Module_Id",
    "Autism_Specialization_Type_AR",
    "Autism_Specialization_Type_EN",
    "Display_Type",
    "Created",
    "Updated",
    "Position",
    "Status",
    "Autism_Specialization_Type"
})
public class AutismSpecialization {

    @JsonProperty("TimeStamp")
    private String timeStamp;
    @JsonProperty("Specialization_Id")
    private String Specialization_Id;
    @JsonProperty("Module_Id")
    private String Module_Id;
    @JsonProperty("Autism_Specialization_Type_AR")
    private String autismSpecializationTypeAR;
    @JsonProperty("Autism_Specialization_Type_EN")
    private String autismSpecializationTypeEN;
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
    @JsonProperty("Autism_Specialization_Type")
    private String Autism_Specialization_Type;
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

    @JsonProperty("Specialization_Id")
    public String getSpecialization_Id() {
        return Specialization_Id;
    }

    @JsonProperty("Specialization_Id")
    public void setSpecialization_Id(String specialization_Id) {
        this.Specialization_Id = specialization_Id;
    }

    @JsonProperty("Module_Id")
    public String getModule_Id() {
        return Module_Id;
    }

    @JsonProperty("Module_Id")
    public void setModule_Id(String module_Id) {
        this.Module_Id = module_Id;
    }

    @JsonProperty("Autism_Specialization_Type_AR")
    public String getAutismSpecializationTypeAR() {
        return autismSpecializationTypeAR;
    }

    @JsonProperty("Autism_Specialization_Type_AR")
    public void setAutismSpecializationTypeAR(String autismSpecializationTypeAR) {
        this.autismSpecializationTypeAR = autismSpecializationTypeAR;
    }

    @JsonProperty("Autism_Specialization_Type_EN")
    public String getAutismSpecializationTypeEN() {
        return autismSpecializationTypeEN;
    }

    @JsonProperty("Autism_Specialization_Type_EN")
    public void setAutismSpecializationTypeEN(String autismSpecializationTypeEN) {
        this.autismSpecializationTypeEN = autismSpecializationTypeEN;
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

    @JsonProperty("Autism_Specialization_Type")
    public String getAutism_Specialization_Type() {
        return Autism_Specialization_Type;
    }

    @JsonProperty("Autism_Specialization_Type")
    public void setAutism_Specialization_Type(String autism_Specialization_Type) {
        this.Autism_Specialization_Type = autism_Specialization_Type;
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
        return "AutismSpecialization{" +
                "timeStamp='" + timeStamp + '\'' +
                ", Specialization_Id='" + Specialization_Id + '\'' +
                ", Module_Id='" + Module_Id + '\'' +
                ", autismSpecializationTypeAR='" + autismSpecializationTypeAR + '\'' +
                ", autismSpecializationTypeEN='" + autismSpecializationTypeEN + '\'' +
                ", displayType='" + displayType + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", position='" + position + '\'' +
                ", status='" + status + '\'' +
                ", Autism_Specialization_Type='" + Autism_Specialization_Type + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
