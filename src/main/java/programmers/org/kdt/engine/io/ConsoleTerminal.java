package programmers.org.kdt.engine.io;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleTerminal extends ConsoleIO {
    private final Scanner scanner;

    public ConsoleTerminal() {
        scanner = new Scanner(System.in);
    }

    //input
    @Override
    public String input(String command) {
        System.out.println(command);
        return scanner.nextLine();
    }

    //output
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
