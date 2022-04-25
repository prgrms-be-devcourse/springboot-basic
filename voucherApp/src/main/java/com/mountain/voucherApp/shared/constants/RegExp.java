package com.mountain.voucherApp.shared.constants;

import java.util.regex.Pattern;

import static com.mountain.voucherApp.shared.constants.ErrorMessage.EMAIL_NOT_VALID;
import static com.mountain.voucherApp.shared.constants.Expression.EMAIL_EXP;

public enum RegExp {
    EMAIL(EMAIL_EXP, EMAIL_NOT_VALID);

    private final String expression;
    private final String errorMsg;

    RegExp(String expression, String errorMsg) {
        this.expression = expression;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean validate(String source) {
        return Pattern.matches(expression, source);
    }
}
