package org.prgrms.kdt.io.console;

import org.prgrms.kdt.domain.voucher.VoucherType;

import java.util.Arrays;

public class Validator {

    public boolean isNotDigit(String[] strArray) {
        if (isEmpty(strArray))
            return true;

        for (String str : strArray) {
            if (isNotDigit(str)) return true;
        }

        return false;
    }

    private boolean isNotDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (isNotDigit(str.charAt(i)))
                return true;
        }
        return false;
    }

    private boolean isNotDigit(char ch) {
        return !Character.isDigit(ch);
    }

    private boolean isEmpty(String[] line) {
        return line.length != 2;
    }
}
