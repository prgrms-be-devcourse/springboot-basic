package kdt.vouchermanagement.global.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {

    Scanner sc = new Scanner(System.in);

    @Override
    public String menuInput() {
        return sc.nextLine();
    }

    @Override
    public int valueInput() {
        return sc.nextInt();
    }
}
