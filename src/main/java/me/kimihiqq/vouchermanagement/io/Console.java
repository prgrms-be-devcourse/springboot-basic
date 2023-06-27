package me.kimihiqq.vouchermanagement.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Console implements Input, Output {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            log.error("Error reading input", e);
            throw new RuntimeException("Error reading input", e);
        }
    }

    @Override
    public String readLine(String prompt) {
        printLine(prompt);
        return readLine();
    }

    public long readDiscount(String prompt) {
        while (true) {
            try {
                return Long.parseLong(readLine(prompt));
            } catch (NumberFormatException e) {
                log.error("Invalid number input. Try again.", e);
            }
        }
    }

    @Override
    public void printLine(String text) {
        try {
            writer.write(text);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            log.error("Error writing input", e);
            throw new RuntimeException("Error writing input", e);
        }
    }

    @Override
    public void printInstructions() {
        printLine("=== Voucher Program ===");
        printLine("Type **exit** to exit the program.");
        printLine("Type **create** to create a new voucher.");
        printLine("Type **list** to list all vouchers.");
    }

}