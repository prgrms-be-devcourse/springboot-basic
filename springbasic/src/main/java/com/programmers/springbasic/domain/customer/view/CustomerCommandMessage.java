package com.programmers.springbasic.domain.customer.view;

import lombok.Getter;

@Getter
public enum CustomerCommandMessage {
    CUSTOMER_CREATE_MESSAGE("Let's start to create new Customer!"),
    CUSTOMER_CREATE_NAME_MESSAGE("input name"),
    CUSTOMER_CREATE_EMAIL_MESSAGE("input email"),
    CUSTOMER_CREATE_FINISHED_MESSAGE("Customer created!"),
    CUSTOMER_LIST_MESSAGE("Here is Customer Info List!"),
    CUSTOMER_RAED_ID_MESSAGE("찾고자 하는 고객의 id를 입력해주세요."),
    CUSTOMER_DELETE_ID_MESSAGE("제거하고자 하는 고객의 id를 입력해주세요."),
    CUSTOMER_DELETE_FINISHED_MESSAGE("Customer Successfully Removed!"),
    CUSTOMER_EXIT_MESSAGE("Go back to Main Menu!");

    String message;

    CustomerCommandMessage(String message) {
        this.message = message;
    }
}
