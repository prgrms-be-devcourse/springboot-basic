package com.prgrms.voucher_manage.console;

import com.prgrms.voucher_manage.exception.InvalidInputException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class InputUtil {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String getStringInput() throws IOException {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank()) throw new InvalidInputException("Empty input is not allowed.");
        return value;
    }

    public Long getLongInput() throws Exception {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank()) throw new InvalidInputException("Empty input is not allowed.");
        return Long.valueOf(value);
    }
}
