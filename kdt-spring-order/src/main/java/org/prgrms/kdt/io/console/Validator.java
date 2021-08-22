package org.prgrms.kdt.io.console;

import org.prgrms.kdt.domain.voucher.VoucherType;

import java.util.Arrays;

public class Validator {
    public boolean isInValidCommand(String line) {
        return Arrays.stream(Command.values())
                .filter(c -> c != Command.CONTINUE)
                .filter(c -> c.getValue().equals(line))
                .findFirst()
                .isEmpty();
    }

    public boolean isInvalidValue(long voucherValue) {
        return voucherValue < 0;
    }

    public boolean isInValidType(int voucherType) {
        int limit = VoucherType.totalTypes();
        return voucherType < 0 || voucherType >= limit;
    }

    public boolean isNotDigitArray(String[] strArray) {
        if (isEmpty(strArray))
            return true;

        for (String str : strArray) {
            for (int i = 0; i < str.length(); i++) {
                if (isNotDigit(str.charAt(i)))
                    return true;
            }
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
