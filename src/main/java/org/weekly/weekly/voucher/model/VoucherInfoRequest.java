package org.weekly.weekly.voucher.model;

import org.weekly.weekly.ui.exception.ReadException;

public class VoucherInfoRequest {
    private static final String SPLIT_FORMAT = ",";
    private final String amount;
    private final String expiratioin;

    public VoucherInfoRequest(String userInput) {
        String[] inputs = userInput.split(userInput);
        checkReadVoucherException(inputs);

        this.amount = inputs[0];
        this.expiratioin = inputs[1];
    }

    private void checkReadVoucherException(String[] inputs) {
        ReadException.notVoucherInputSize(inputs);
        ReadException.notVoucherInputFormat(inputs);
    }

    public String getAmount() {
        return amount;
    }

    public String getExpiratioin() {
        return expiratioin;
    }
}
