package com.example.geekbank_sms_manager;

import java.io.Serializable;

public class Sms implements Serializable {
    private String phoneNumber;
    private String messageBody;

    public Sms(String phoneNumber, String messageBody, String date) {
        this.phoneNumber = phoneNumber;
        this.messageBody = messageBody;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessageBody() {
        return messageBody;
    }
}