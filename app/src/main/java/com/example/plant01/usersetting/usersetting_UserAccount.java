package com.example.plant01.usersetting;

public class usersetting_UserAccount {
    private String idToken;
    public String userNick;
    public String userPh;
    public String userGender;
    public String userImg;
    public String userBirth;
    public String userEmail;
    public String userPassword;
    public String userPostalCode;
    public String userAdr, joinDate;

    public usersetting_UserAccount(String userNick, String userImg) {
        this.userNick = userNick;
        this.userImg = userImg;
    }

//    public usersetting_UserAccount(String userNick, String userPh, String userGender, String userImg, String userBirth, String userEmail, String userPassword, String userPostalCode, String userAdr) {
//        this.userNick = userNick;
//        this.userPh = userPh;
//        this.userGender = userGender;
//        this.userImg = userImg;
//        this.userBirth = userBirth;
//        this.userEmail = userEmail;
//        this.userPassword = userPassword;
//        this.userPostalCode = userPostalCode;
//        this.userAdr = userAdr;
//    }

    public usersetting_UserAccount(){}
//    public usersetting_UserAccount(String idToken, String userNick, String userPh, String userGender
//            , String userImg, String userBirth, String userEmail, String userPassword, String userPostalCode, String userAdr) {
//        this.idToken = idToken;
//        UserNick = userNick;
//        UserPh = userPh;
//        UserGender = userGender;
//        UserImg = userImg;
//        UserBirth = userBirth;
//        UserEmail = userEmail;
//        UserPassword = userPassword;
//        UserPostalCode = userPostalCode;
////        UserAdr = userAdr;
//    }


    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserPh() {
        return userPh;
    }

    public void setUserPh(String userPh) {
        this.userPh = userPh;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public String getUserAdr() {
        return userAdr;
    }

    public void setUserAdr(String userAdr) {
        this.userAdr = userAdr;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
}
