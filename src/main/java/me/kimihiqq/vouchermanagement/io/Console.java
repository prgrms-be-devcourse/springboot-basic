package me.kimihiqq.vouchermanagement.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.option.ConsoleOption;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.EnumSet;

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

    public long readDiscount(String prompt) {
        printLine(prompt);
        while (true) {
            try {
                long discount = Long.parseLong(readLine());
                if (discount < 0) {
                    throw new IllegalArgumentException("Discount amount cannot be negative.");
                }
                if (discount > 100) {
                    throw new IllegalArgumentException("Discount rate cannot be greater than 100.");
                }
                return discount;
            } catch (NumberFormatException e) {
                printLine("Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                printLine(e.getMessage() + " Please try again.");
            }
        }
    }

    public <E extends Enum<E> & ConsoleOption> E promptUserChoice(Class<E> enumType) {
        Arrays.stream(enumType.getEnumConstants())
                .forEach(option -> printLine(option.getKey() + ": " + option.getDescription()));

        int userChoice = Integer.parseInt(readLine());
        return EnumSet.allOf(enumType).stream()
                .filter(option -> option.getKey() == userChoice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Option: " + userChoice));
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
}