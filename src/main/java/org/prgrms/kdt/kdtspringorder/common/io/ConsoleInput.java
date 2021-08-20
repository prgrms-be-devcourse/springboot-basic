package org.prgrms.kdt.kdtspringorder.common.io;

import java.util.Scanner;

public class ConsoleInput implements Input{

    private final Scanner scanner = new Scanner(System.in);

    /**
     * 콘솔 입력을 받습니다.
     * @param prompt 입력 받을 떄, 출력할 메시지
     * @return 입력한 내용
     */
    @Override
    public String input(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

}
