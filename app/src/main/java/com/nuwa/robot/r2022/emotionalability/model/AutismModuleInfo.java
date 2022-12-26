
package com.nuwa.robot.r2022.emotionalability.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "success",
    "message",
    "details"
})
public class AutismModuleInfo {

    @JsonProperty("success")
    private String success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("details")
    private List<AutismModule> details = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("success")
    public String getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(String success) {
        this.success = success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("details")
    public List<AutismModule> getDetails() {
        return details;
    }

    @JsonProperty("details")
    public void setDetails(List<AutismModule> details) {
        this.details = details;
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
        return "AutismModuleInfo{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", details=" + details +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
