
package com.nuwa.robot.r2022.emotionalability.model;

import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.realm.RealmObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "Doctor_Name",
    "gender",
    "email",
    "phone",
    "image",
    "country_id",
    "city_id",
    "language",
    "Username"
})
public class Details extends RealmObject  {

    @PrimaryKey
    @JsonProperty("id")
    private String id;
    @JsonProperty("Doctor_Name")
    private String Doctor_Name;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("image")
    private String image;
    @JsonProperty("country_id")
    private String countryId;
    @JsonProperty("city_id")
    private String cityId;
    @JsonProperty("language")
    private String language;
    @JsonProperty("Username")
    private String username;



    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("Doctor_Name")
    public String getDoctor_Name() {
        return Doctor_Name;
    }

    @JsonProperty("Doctor_Name")
    public void setDoctor_Name(String doctor_Name) {
        this.Doctor_Name = doctor_Name;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("country_id")
    public String getCountryId() {
        return countryId;
    }

    @JsonProperty("country_id")
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @JsonProperty("city_id")
    public String getCityId() {
        return cityId;
    }

    @JsonProperty("city_id")
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("Username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("Username")
    public void setUsername(String username) {
        this.username = username;
    }



    @Override
    public String toString() {
        return "Details{" +
                "id='" + id + '\'' +
                ", doctorName='" + Doctor_Name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                ", countryId='" + countryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", language='" + language + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
