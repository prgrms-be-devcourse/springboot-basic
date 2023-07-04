package prgms.spring_week1.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    Scanner sc = new Scanner(System.in);

    public String input() {
        return sc.nextLine();
    }
}
