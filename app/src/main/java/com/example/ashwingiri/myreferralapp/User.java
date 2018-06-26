package com.example.ashwingiri.myreferralapp;

import java.util.ArrayList;

/**
 * Created by Ashwin Giri on 6/25/2018.
 */

class User {

    String name;
    String Email;
    String referral_code;
    int balance;
    ArrayList<String> referred_user;

    public User() {
    }

    public User(String name, String email, String referral_code, int balance, ArrayList<String> referred_user) {
        this.name = name;
        Email = email;
        this.referral_code = referral_code;
        this.balance = balance;
        this.referred_user = referred_user;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<String> getReferred_user() {
        return referred_user;
    }

    public void setReferred_user(ArrayList<String> referred_user) {
        this.referred_user = referred_user;
    }
}
