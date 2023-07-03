package com.programmers.springbasic.domain.customer.view;

import lombok.Getter;

@Getter
public enum CustomerInfo {
    CUSTOMER_ID_INFO_MESSAGE("ID: "),
    CUSTOMER_NAME_INFO_MESSAGE("Name: "),
    CUSTOMER_EMAIL_INFO_MESSAGE("Email Address: ");

    String infoMessage;

    CustomerInfo(String infoMessage) {
        this.infoMessage = infoMessage;
    }
}
