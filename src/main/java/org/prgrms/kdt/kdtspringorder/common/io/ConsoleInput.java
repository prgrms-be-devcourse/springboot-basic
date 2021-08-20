package org.prgrms.kdt.kdtspringorder.common.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * 콘솔 입력을 담당합니다.
 */
@Component
public class ConsoleInput implements Input{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

}
