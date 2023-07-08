package com.prgmrs.voucher.model.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {
    public boolean isValidNameFormat(String name) {
        Pattern pattern = Pattern.compile("^[-+]?\\d+$");
        return pattern.matcher(name).matches();
    }
}
