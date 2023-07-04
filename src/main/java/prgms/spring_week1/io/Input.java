package prgms.spring_week1.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    Scanner sc = new Scanner(System.in);

    public String input() {
        return sc.nextLine();
    }
}
