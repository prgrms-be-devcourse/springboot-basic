package com.prgmrs.voucher.model.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    public boolean isValidNameFormat(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(name);

        boolean isValidFormat = matcher.matches();
        boolean isValidLength = name.length() <= 255;

        return isValidFormat && isValidLength;
    }
}
