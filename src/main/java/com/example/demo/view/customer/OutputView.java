package com.example.demo.view.customer;

import com.example.demo.dto.CustomerDto;
import java.util.List;

public class OutputView {

    public static final String CUSTOMER_ID_MESSAGE = "    고객 ID를 입력해주세요 >> ";
    public static final String CUSTOMER_NAME_MESSAGE = "    고객 이름을 입력해주세요 >> ";
    public static final String CUSTOMER_AGE_MESSAGE = "    고객 나이를 입력해주세요 >> ";
    public static final String CUSTOMER_CREATE_MESSAGE = "    고객이 가입되었습니다.(고객 ID : %s)";
    public static final String CUSTOMER_UPDATE_MESSAGE = "    고객명이 정상적으로 업데이트되었습니다.";
    public static final String CUSTOMER_DELETE_MESSAGE = "    고객이 정상적으로 삭제되었습니다.";
    public static final String CUSTOMER_LIST_EMPTY_MESSAGE = "가입 된 고객이 한 명도 없습니다.";
    public static final String COMMAND_OPTION_MESSAGE = "=== 고객 관리 프로그램 ===\n= <1 ~ 4> 사이 숫자를 입력해주세요 =\n1. 고객 생성\n2. 고객 리스트 읽기\n3. 고객 이름 업데이트\n4. 종료\n\n입력 : ";

    public void printCommandOptionMessage() {
        System.out.print(COMMAND_OPTION_MESSAGE);
    }


    public void printCreateMessage(CustomerDto customerDto) {
        System.out.println(String.format(CUSTOMER_CREATE_MESSAGE, customerDto.getId()));
    }

    public void printCustomerIdMessage() {
        System.out.print(CUSTOMER_ID_MESSAGE);
    }

    public void printCustomerNameMessage() {
        System.out.print(CUSTOMER_NAME_MESSAGE);
    }

    public void printCustomerAgeMessage() {
        System.out.print(CUSTOMER_AGE_MESSAGE);
    }

    public void printCustomerList(List<CustomerDto> list) {
        if (list.isEmpty()) {
            System.out.println(CUSTOMER_LIST_EMPTY_MESSAGE);
            return;
        }
        list.forEach(customerDto -> System.out.println(customerDto.formatAsString()));
    }

    public void printUpdateMessage() {
        System.out.println(CUSTOMER_UPDATE_MESSAGE);
    }

    public void printDeleteMessage() {
        System.out.println(CUSTOMER_DELETE_MESSAGE);
    }

}
