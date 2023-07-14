package org.weekly.weekly.voucher.dto.request;

import org.weekly.weekly.ui.exception.InputValidator;

public class VoucherInfoRequest {
    private static final String SPLIT_FORMAT = ",";
    private static final int AMOUNT_NO = 0;
    private static final int EXPIRATION = 1;

    private final long amount;
    private final long expiration;


    private VoucherInfoRequest(long amount, long expiration) {
        this.amount = amount;
        this.expiration = expiration;
    }

    public static VoucherInfoRequest of(String userInput) {
        String[] inputs = userInput.split(SPLIT_FORMAT);
        checkReadVoucherException(inputs);

        return new VoucherInfoRequest(
                Long.parseLong(inputs[AMOUNT_NO].trim()),
                Long.parseLong(inputs[EXPIRATION].trim()));
    }

    public long getAmount() {
        return amount;
    }

    public long getExpiration() {
        return expiration;
    }

    private static void checkReadVoucherException(String[] inputs) {
        InputValidator.notVoucherInputSize(inputs);
        InputValidator.notNumber(inputs);
    }
}
