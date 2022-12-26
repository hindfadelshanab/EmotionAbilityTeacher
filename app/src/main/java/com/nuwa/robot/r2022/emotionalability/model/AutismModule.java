
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
    "Module_Id",
    "Curriculum_Id",
    "Autism_Module_Type_AR",
    "Autism_Module_Type_EN",
    "Display_Type",
    "Created",
    "Updated",
    "Position",
    "Status",
    "Autism_Module_Type"
})
public class AutismModule {

    @JsonProperty("TimeStamp")
    private String timeStamp;
    @JsonProperty("Module_Id")
    private String Module_Id;
    @JsonProperty("Curriculum_Id")
    private String Curriculum_Id;
    @JsonProperty("Autism_Module_Type_AR")
    private String Autism_Module_Type_AR;
    @JsonProperty("Autism_Module_Type_EN")
    private String autismModuleTypeEN;
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
    @JsonProperty("Autism_Module_Type")
    private String Autism_Module_Type;
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

    @JsonProperty("Module_Id")
    public String getModule_Id() {
        return Module_Id;
    }

    @JsonProperty("Module_Id")
    public void setModule_Id(String module_Id) {
        this.Module_Id = module_Id;
    }

    @JsonProperty("Curriculum_Id")
    public String getCurriculumId() {
        return Curriculum_Id;
    }

    @JsonProperty("Curriculum_Id")
    public void setCurriculumId(String curriculumId) {
        this.Curriculum_Id = curriculumId;
    }

    @JsonProperty("Autism_Module_Type_AR")
    public String getAutism_Module_Type_AR() {
        return Autism_Module_Type_AR;
    }

    @JsonProperty("Autism_Module_Type_AR")
    public void setAutism_Module_Type_AR(String autism_Module_Type_AR) {
        this.Autism_Module_Type_AR = autism_Module_Type_AR;
    }

    @JsonProperty("Autism_Module_Type_EN")
    public String getAutismModuleTypeEN() {
        return autismModuleTypeEN;
    }

    @JsonProperty("Autism_Module_Type_EN")
    public void setAutismModuleTypeEN(String autismModuleTypeEN) {
        this.autismModuleTypeEN = autismModuleTypeEN;
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

    @JsonProperty("Autism_Module_Type")
    public String getAutism_Module_Type() {
        return Autism_Module_Type;
    }

    @JsonProperty("Autism_Module_Type")
    public void setAutism_Module_Type(String autism_Module_Type) {
        this.Autism_Module_Type = autism_Module_Type;
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
