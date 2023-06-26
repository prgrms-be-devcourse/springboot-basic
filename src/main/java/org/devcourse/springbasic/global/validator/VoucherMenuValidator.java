package org.devcourse.springbasic.global.validator;

import org.devcourse.springbasic.global.util.RegexPattern;
import java.util.regex.Matcher;

public class VoucherMenuValidator implements Validator<String> {

    @Override
    public boolean validate(String valueToVerify) {

        Matcher matcher = RegexPattern.NOT_INPUT_MENU_PATTERN
                .matcher(valueToVerify);
        return matcher.find();
    }
}
