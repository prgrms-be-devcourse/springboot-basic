package prgms.spring_week1.io;

import java.util.Scanner;

public class Input {
    private final Scanner sc = new Scanner(System.in);

    public String input() {
        return sc.nextLine();
    }
}
