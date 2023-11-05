package com.prgrms.voucher_manage.console.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import static com.prgrms.voucher_manage.exception.ErrorMessage.EMPTY_INPUT_NOT_ALLOWED;
import static com.prgrms.voucher_manage.exception.ErrorMessage.INVALID_UUID_FORMAT;

@Component
public class InputUtil {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String getStringInput() throws IOException {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank())
            throw new IllegalArgumentException(EMPTY_INPUT_NOT_ALLOWED.getMessage());
        return value;
    }

    public Long getLongInput() throws Exception {
        System.out.print("> ");
        String value = br.readLine().strip();
        if (value.isBlank())
            throw new IllegalArgumentException(EMPTY_INPUT_NOT_ALLOWED.getMessage());
        return Long.valueOf(value);
    }

    public UUID getUUIDInput() {
        try {
            return UUID.fromString(getStringInput());
        } catch (Exception e) {
            throw new RuntimeException(INVALID_UUID_FORMAT.getMessage());
        }
    }
}
