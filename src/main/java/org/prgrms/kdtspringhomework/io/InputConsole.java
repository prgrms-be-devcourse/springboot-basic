package org.prgrms.kdtspringhomework.io;

import java.util.Scanner;

public class InputConsole implements Input {
    private final Scanner sc = new Scanner(System.in);

    //사용자 입력값 받기
    @Override
    public String receiveUserInput() {
        return sc.nextLine();
    }
}
