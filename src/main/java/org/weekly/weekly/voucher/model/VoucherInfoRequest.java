package org.weekly.weekly.voucher.model;

import org.weekly.weekly.ui.exception.ReadException;

public class VoucherInfoRequest {
    private static final String SPLIT_FORMAT = ",";
    private static final int AMOUNT_NO = 0;
    private static final int EXPIRATION = 1;

    private final String amount;
    private final String expiration;


    public VoucherInfoRequest(String userInput) {
        String[] inputs = userInput.split(userInput);
        checkReadVoucherException(inputs);

        this.amount = inputs[AMOUNT_NO];
        this.expiration = inputs[EXPIRATION];
    }

    private void checkReadVoucherException(String[] inputs) {
        ReadException.notVoucherInputSize(inputs);
        ReadException.notVoucherInputFormat(inputs);
    }

    public String getAmount() {
        return amount;
    }

    public String getExpiration() {
        return expiration;
    }
}
