package com.prgrms.voucher_manage.util;

import com.prgrms.voucher_manage.exception.EmptyInputException;
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
        if (value.isBlank()) throw new EmptyInputException();
        return value;
    }

    public Long getLongInput() throws Exception {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank()) throw new EmptyInputException();
        return Long.valueOf(value);
    }
}
