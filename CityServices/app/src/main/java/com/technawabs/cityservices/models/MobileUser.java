package com.technawabs.cityservices.models;

import java.util.List;

public class MobileUser {

    private String mobile;
    private String name;
    private int age;
    private double balance;
    private double talktime;
    private double internetBalance;
    private Company company;
    private transient UserLocation[] contacts;
    private double latitude;
    private double longithude;
    private transient boolean isBalanceNeeded;
    private transient boolean isTalktimeNeeded;
    private String password;
    private String state;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTalktime() {
        return talktime;
    }

    public void setTalktime(double talktime) {
        this.talktime = talktime;
    }

    public double getInternetBalance() {
        return internetBalance;
    }

    public void setInternetBalance(double internetBalance) {
        this.internetBalance = internetBalance;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public UserLocation[] getContacts() {
        return contacts;
    }

    public void setContacts(UserLocation[] contacts) {
        this.contacts = contacts;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongithude() {
        return longithude;
    }

    public void setLongithude(double longithude) {
        this.longithude = longithude;
    }

    public boolean isBalanceNeeded() {
        return isBalanceNeeded;
    }

    public void setBalanceNeeded(boolean balanceNeeded) {
        isBalanceNeeded = balanceNeeded;
    }

    public boolean isTalktimeNeeded() {
        return isTalktimeNeeded;
    }

    public void setTalktimeNeeded(boolean talktimeNeeded) {
        isTalktimeNeeded = talktimeNeeded;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
