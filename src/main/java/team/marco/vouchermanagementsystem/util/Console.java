package team.marco.vouchermanagementsystem.util;

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

    public int readInt() {
        try {
            return Integer.parseInt(readString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력하실 수 있습니다.");
        }
    }

    public int readInt(String prompt) {
        System.out.println(prompt + "\n");
        return readInt();
    }

    private String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("unreadable input");
        }
    }

    public void printVoucherTypes() {
        System.out.println("""
                Type 0 to create fixed amount voucher.
                Type 1 to create percent discount voucher.
                """);
    }

    public void printError(Exception e) {
        if(e instanceof NumberFormatException) {
            System.out.println("숫자를 입력해 주세요.");
        } else {
            System.out.println(e.getMessage());
        }
    }
}
