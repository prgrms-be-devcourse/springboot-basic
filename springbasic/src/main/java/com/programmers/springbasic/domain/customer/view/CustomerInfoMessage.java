package com.programmers.springbasic.domain.customer.view;

import lombok.Getter;

@Getter
public enum CustomerInfoMessage {
    CUSTOMER_ID_INFO_MESSAGE("ID: "),
    CUSTOMER_NAME_INFO_MESSAGE("Name: "),
    CUSTOMER_EMAIL_INFO_MESSAGE("Email Address: ");

    String infoMessage;

    CustomerInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }
}
