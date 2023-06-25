package org.weekly.weekly.voucher.model;

import org.weekly.weekly.ui.exception.ReadException;

import java.util.Arrays;

public class VoucherInfoRequest {
    private static final String SPLIT_FORMAT = ",";
    private static final int AMOUNT_NO = 0;
    private static final int EXPIRATION = 1;

    private final String amount;
    private final String expiration;


    private VoucherInfoRequest(String amount, String expiration) {
        this.amount = amount;
        this.expiration = expiration;
    }

    public static VoucherInfoRequest of(String userInput) {
        String[] inputs = userInput.split(SPLIT_FORMAT);
        checkReadVoucherException(inputs);

        return new VoucherInfoRequest(inputs[AMOUNT_NO].trim(), inputs[EXPIRATION].trim());
    }

    public String getAmount() {
        return amount;
    }

    public String getExpiration() {
        return expiration;
    }

    private static void checkReadVoucherException(String[] inputs) {
        ReadException.notVoucherInputSize(inputs);
        ReadException.notVoucherInputFormat(inputs);
    }


}
