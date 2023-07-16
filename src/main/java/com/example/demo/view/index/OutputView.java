package com.example.demo.view.index;

public class OutputView {

    public static final String COMMAND_OPTION_MESSAGE = "=== 바우처, 고객 관리 프로그램 ===\n= <1 ~ 3> 사이 숫자를 입력해주세요 =\n1. 고객 관리 프로그램 실행\n2. 바우처 관리 프로그램 실행\n3. 프로그램 종료\n\n입력 : ";

    public void printCommandOptionMessage() {
        System.out.print(COMMAND_OPTION_MESSAGE);
    }
}
