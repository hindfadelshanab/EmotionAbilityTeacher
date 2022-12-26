
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
    "id",
    "phoneVerifyStatus",
    "uid",
    "callerId",
    "username",
    "firstname",
    "lastname",
    "gender",
    "email",
    "uniqueId",
    "phone",
    "image",
    "address",
    "latitude",
    "longitude",
    "about",
    "otp",
    "password",
    "companyName",
    "language",
    "social_id",
    "nationalId",
    "hash",
    "is_verified",
    "reg_id",
    "notificationStatus",
    "login_type",
    "device_type",
    "created",
    "updated",
    "country_id",
    "city_id",
    "date_of_birth",
    "height",
    "weight",
    "watch_mac",
    "IosDeviceId",
    "RelativeID",
    "ring_mac",
    "shaer_phr_all",
    "usertype",
    "companytype",
    "companyid",
    "see_wellbing",
    "is_autism"
})
public class Patient {

    @JsonProperty("id")
    private String id;
    @JsonProperty("phoneVerifyStatus")
    private String phoneVerifyStatus;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("callerId")
    private String callerId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("email")
    private String email;
    @JsonProperty("uniqueId")
    private String uniqueId;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("image")
    private String image;
    @JsonProperty("address")
    private String address;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("about")
    private String about;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("password")
    private String password;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("language")
    private String language;
    @JsonProperty("social_id")
    private String socialId;
    @JsonProperty("nationalId")
    private String nationalId;
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("is_verified")
    private String isVerified;
    @JsonProperty("reg_id")
    private String regId;
    @JsonProperty("notificationStatus")
    private String notificationStatus;
    @JsonProperty("login_type")
    private String loginType;
    @JsonProperty("device_type")
    private String deviceType;
    @JsonProperty("created")
    private String created;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("country_id")
    private String countryId;
    @JsonProperty("city_id")
    private String cityId;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    @JsonProperty("height")
    private String height;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("watch_mac")
    private String watchMac;
    @JsonProperty("IosDeviceId")
    private String iosDeviceId;
    @JsonProperty("RelativeID")
    private Object relativeID;
    @JsonProperty("ring_mac")
    private String ringMac;
    @JsonProperty("shaer_phr_all")
    private String shaerPhrAll;
    @JsonProperty("usertype")
    private String usertype;
    @JsonProperty("companytype")
    private Object companytype;
    @JsonProperty("companyid")
    private Object companyid;
    @JsonProperty("see_wellbing")
    private String seeWellbing;
    @JsonProperty("is_autism")
    private String isAutism;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("phoneVerifyStatus")
    public String getPhoneVerifyStatus() {
        return phoneVerifyStatus;
    }

    @JsonProperty("phoneVerifyStatus")
    public void setPhoneVerifyStatus(String phoneVerifyStatus) {
        this.phoneVerifyStatus = phoneVerifyStatus;
    }

    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @JsonProperty("callerId")
    public String getCallerId() {
        return callerId;
    }

    @JsonProperty("callerId")
    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    @JsonProperty("uniqueId")
    public String getUniqueId() {
        return uniqueId;
    }

    @JsonProperty("uniqueId")
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("about")
    public String getAbout() {
        return about;
    }

    @JsonProperty("about")
    public void setAbout(String about) {
        this.about = about;
    }

    @JsonProperty("otp")
    public String getOtp() {
        return otp;
    }

    @JsonProperty("otp")
    public void setOtp(String otp) {
        this.otp = otp;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("social_id")
    public String getSocialId() {
        return socialId;
    }

    @JsonProperty("social_id")
    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    @JsonProperty("nationalId")
    public String getNationalId() {
        return nationalId;
    }

    @JsonProperty("nationalId")
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @JsonProperty("hash")
    public String getHash() {
        return hash;
    }

    @JsonProperty("hash")
    public void setHash(String hash) {
        this.hash = hash;
    }

    @JsonProperty("is_verified")
    public String getIsVerified() {
        return isVerified;
    }

    @JsonProperty("is_verified")
    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    @JsonProperty("reg_id")
    public String getRegId() {
        return regId;
    }

    @JsonProperty("reg_id")
    public void setRegId(String regId) {
        this.regId = regId;
    }

    @JsonProperty("notificationStatus")
    public String getNotificationStatus() {
        return notificationStatus;
    }

    @JsonProperty("notificationStatus")
    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    @JsonProperty("login_type")
    public String getLoginType() {
        return loginType;
    }

    @JsonProperty("login_type")
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @JsonProperty("device_type")
    public String getDeviceType() {
        return deviceType;
    }

    @JsonProperty("device_type")
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
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

    @JsonProperty("date_of_birth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("date_of_birth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("height")
    public String getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
        this.height = height;
    }

    @JsonProperty("weight")
    public String getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(String weight) {
        this.weight = weight;
    }

    @JsonProperty("watch_mac")
    public String getWatchMac() {
        return watchMac;
    }

    @JsonProperty("watch_mac")
    public void setWatchMac(String watchMac) {
        this.watchMac = watchMac;
    }

    @JsonProperty("IosDeviceId")
    public String getIosDeviceId() {
        return iosDeviceId;
    }

    @JsonProperty("IosDeviceId")
    public void setIosDeviceId(String iosDeviceId) {
        this.iosDeviceId = iosDeviceId;
    }

    @JsonProperty("RelativeID")
    public Object getRelativeID() {
        return relativeID;
    }

    @JsonProperty("RelativeID")
    public void setRelativeID(Object relativeID) {
        this.relativeID = relativeID;
    }

    @JsonProperty("ring_mac")
    public String getRingMac() {
        return ringMac;
    }

    @JsonProperty("ring_mac")
    public void setRingMac(String ringMac) {
        this.ringMac = ringMac;
    }

    @JsonProperty("shaer_phr_all")
    public String getShaerPhrAll() {
        return shaerPhrAll;
    }

    @JsonProperty("shaer_phr_all")
    public void setShaerPhrAll(String shaerPhrAll) {
        this.shaerPhrAll = shaerPhrAll;
    }

    @JsonProperty("usertype")
    public String getUsertype() {
        return usertype;
    }

    @JsonProperty("usertype")
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @JsonProperty("companytype")
    public Object getCompanytype() {
        return companytype;
    }

    @JsonProperty("companytype")
    public void setCompanytype(Object companytype) {
        this.companytype = companytype;
    }

    @JsonProperty("companyid")
    public Object getCompanyid() {
        return companyid;
    }

    @JsonProperty("companyid")
    public void setCompanyid(Object companyid) {
        this.companyid = companyid;
    }

    @JsonProperty("see_wellbing")
    public String getSeeWellbing() {
        return seeWellbing;
    }

    @JsonProperty("see_wellbing")
    public void setSeeWellbing(String seeWellbing) {
        this.seeWellbing = seeWellbing;
    }

    @JsonProperty("is_autism")
    public String getIsAutism() {
        return isAutism;
    }

    @JsonProperty("is_autism")
    public void setIsAutism(String isAutism) {
        this.isAutism = isAutism;
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
        return "Detail{" +
                "id='" + id + '\'' +
                ", phoneVerifyStatus='" + phoneVerifyStatus + '\'' +
                ", uid='" + uid + '\'' +
                ", callerId='" + callerId + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", about='" + about + '\'' +
                ", otp='" + otp + '\'' +
                ", password='" + password + '\'' +
                ", companyName='" + companyName + '\'' +
                ", language='" + language + '\'' +
                ", socialId='" + socialId + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", hash='" + hash + '\'' +
                ", isVerified='" + isVerified + '\'' +
                ", regId='" + regId + '\'' +
                ", notificationStatus='" + notificationStatus + '\'' +
                ", loginType='" + loginType + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", countryId='" + countryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", watchMac='" + watchMac + '\'' +
                ", iosDeviceId='" + iosDeviceId + '\'' +
                ", relativeID=" + relativeID +
                ", ringMac='" + ringMac + '\'' +
                ", shaerPhrAll='" + shaerPhrAll + '\'' +
                ", usertype='" + usertype + '\'' +
                ", companytype=" + companytype +
                ", companyid=" + companyid +
                ", seeWellbing='" + seeWellbing + '\'' +
                ", isAutism='" + isAutism + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
