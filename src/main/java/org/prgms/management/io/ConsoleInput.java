package org.prgms.management.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String getInput(String text) {
        System.out.print(text);

        return this.sc.nextLine();
    }

    @Override
    public Integer getInputInteger(String text) {
        System.out.print(text);

        String input = this.sc.nextLine();

        if (!input.matches("\\d+")) {
            logger.error("잘못된 숫자를 입력했습니다. {}", input);
            return 0;
        }

        return Integer.parseInt(input);
    }
}
