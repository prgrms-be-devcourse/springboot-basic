package com.programmers.part1.io;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * UI 계층에서 필요한 기본 메시지를 설정하여 사용하였습니다.
 * */
public class Message {

    private Message() {}

    public static final String START_PROMPT = """
            === Voucher Program===
            Type exit to exit the program.
            Type create to create a new Voucher.
            Type list to list all vouchers.
            Type blacklist to list all vouchers.
            """ ;
    public static final String VOUCHER_PROMPT = """
                ===바우처의 타입 선택하시오===
                1. 고정 금액 할인
                2. 비율 금액 할인
                """;
    public static final String AMOUNT_PROMPT = "할인 금액이나 비율 값을 입력하시오.\n";
    public static final String CREATE_SUCCESS = "바우처를 생성 완료했습니다\n";
    public static final String EXIT = "프로그램을 종료합니다.\n";


}
