package kdt.vouchermanagement.global.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {

    Scanner sc = new Scanner(System.in);

    @Override
    public String input() {
        return sc.nextLine();
    }
}
