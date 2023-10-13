package team.marco.vouchermanagementsystem;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Console {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void printCommandManual() {
        System.out.println("""
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """);
    }

    public String readString() {
        System.out.print("> ");
        String input = readLine();
        System.out.println();

        return input;
    }

    private String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("unreadable input");
        }
    }

}
