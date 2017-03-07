package com.firebasepractice.pravin103082.contentproviderpractice.contacts;

/**
 * Created by pravin103082 on 12-12-2016.
 */

public class ContactModel
{
    String name;
    String phoneNo;
    String phtoUri;


    public String getPhtoUri() {
        return phtoUri;
    }

    public void setPhtoUri(String phtoUri) {
        this.phtoUri = phtoUri;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
