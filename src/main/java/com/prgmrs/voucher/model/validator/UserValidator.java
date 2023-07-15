package com.prgmrs.voucher.model.validator;

import com.prgmrs.voucher.exception.WrongRangeFormatException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {
    public void isValidNameFormat(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(name);

        if(!matcher.matches()) {
            throw new WrongRangeFormatException("only alphabet is allowed");
        }

        if (name.length() > 255) {
            throw new WrongRangeFormatException("length must be equal to or less than 255");
        }
    }
}
