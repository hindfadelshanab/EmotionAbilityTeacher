
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
    "Id",
    "TIMESTAMP",
    "Autism_Baseline_Type_AR",
    "Autism_Baseline_Type_EN",
    "Created",
    "Updated",
    "color",
    "name"
})

public class Baseline {

    @JsonProperty("Id")
    private String Id;
    @JsonProperty("TIMESTAMP")
    private String timestamp;
    @JsonProperty("Autism_Baseline_Type_AR")
    private String autismBaselineTypeAR;
    @JsonProperty("Autism_Baseline_Type_EN")
    private String autismBaselineTypeEN;
    @JsonProperty("Created")
    private String created;
    @JsonProperty("Updated")
    private Object updated;
    @JsonProperty("color")
    private String color;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Id")
    public String getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.Id = id;
    }

    @JsonProperty("TIMESTAMP")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("TIMESTAMP")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("Autism_Baseline_Type_AR")
    public String getAutismBaselineTypeAR() {
        return autismBaselineTypeAR;
    }

    @JsonProperty("Autism_Baseline_Type_AR")
    public void setAutismBaselineTypeAR(String autismBaselineTypeAR) {
        this.autismBaselineTypeAR = autismBaselineTypeAR;
    }

    @JsonProperty("Autism_Baseline_Type_EN")
    public String getAutismBaselineTypeEN() {
        return autismBaselineTypeEN;
    }

    @JsonProperty("Autism_Baseline_Type_EN")
    public void setAutismBaselineTypeEN(String autismBaselineTypeEN) {
        this.autismBaselineTypeEN = autismBaselineTypeEN;
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
    public Object getUpdated() {
        return updated;
    }

    @JsonProperty("Updated")
    public void setUpdated(Object updated) {
        this.updated = updated;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
        return "Baseline{" +
                "Id='" + Id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", autismBaselineTypeAR='" + autismBaselineTypeAR + '\'' +
                ", autismBaselineTypeEN='" + autismBaselineTypeEN + '\'' +
                ", created='" + created + '\'' +
                ", updated=" + updated +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
