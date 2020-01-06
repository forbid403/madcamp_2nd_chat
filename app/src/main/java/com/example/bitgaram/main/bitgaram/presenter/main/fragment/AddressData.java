package com.example.bitgaram.main.bitgaram.presenter.main.fragment;

public class AddressData {
    public String name;
    public String phonenum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public AddressData(String name, String phonenum) {
        this.name = name;
        this.phonenum = phonenum;
    }
}
