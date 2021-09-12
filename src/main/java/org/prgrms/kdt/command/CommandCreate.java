package org.prgrms.kdt.command;

public class CommandCreate {

    public static String createVoucherType() {
        String voucherType;

        while (true) {
            voucherType = Input.input();
            Output.howMuchDiscountMessage(voucherType);
            if (voucherType.equals("FixedAmountVoucher") || voucherType.equals("PercentDiscountVoucher"))
                return voucherType;
        }
    }

    public static long createVoucherDiscountValue(final String voucherType) {
        long voucherDiscountValue = 0;
        boolean voucherTypeCheck = true;
        while (voucherTypeCheck) {
            if (ValueValidation.voucherTypeValidation(voucherType)) {
                boolean inputValueCheck = true;
                do {
                    final String inputValue = Input.input();
                    if (ValueValidation.numberValidation(voucherType, inputValue)) {
                        inputValueCheck = ValueValidation.rangeValidation(voucherType, inputValue);
                        voucherDiscountValue = Long.parseLong(inputValue);
                    }
                } while (inputValueCheck);
            }
            voucherTypeCheck = false;
        }
        return voucherDiscountValue;
    }
}
