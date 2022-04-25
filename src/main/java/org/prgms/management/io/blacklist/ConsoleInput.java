package org.prgms.management.io.blacklist;

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
}
