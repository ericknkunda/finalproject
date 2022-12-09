package com.example.finalprojectandroidversion;

public class UserAttributesModal {
    private int userId;
    private String userNames;
    private String phoneAddress;
    private String userEmailAddress;
    private String userGender;
    private String userAgeRange;

    public UserAttributesModal() {
    }

    public UserAttributesModal(int userId, String userNames, String phoneAddress, String userEmailAddress, String userGender, String userAgeRange) {
        this.userId = userId;
        this.userNames = userNames;
        this.phoneAddress = phoneAddress;
        this.userEmailAddress = userEmailAddress;
        this.userGender = userGender;
        this.userAgeRange = userAgeRange;
    }

    public UserAttributesModal(String userNames, String phoneAddress, String userEmailAddress, String userGender, String userAgeRange) {
        this.userNames = userNames;
        this.phoneAddress = phoneAddress;
        this.userEmailAddress = userEmailAddress;
        this.userGender = userGender;
        this.userAgeRange = userAgeRange;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getPhoneAddress() {
        return phoneAddress;
    }

    public void setPhoneAddress(String phoneAddress) {
        this.phoneAddress = phoneAddress;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAgeRange() {
        return userAgeRange;
    }

    public void setUserAgeRange(String userAgeRange) {
        this.userAgeRange = userAgeRange;
    }
}
