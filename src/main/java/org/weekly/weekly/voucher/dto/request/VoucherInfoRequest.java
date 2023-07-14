package org.weekly.weekly.voucher.dto.request;

import org.weekly.weekly.ui.exception.InputValidator;

public class VoucherInfoRequest {
    private static final String SPLIT_FORMAT = ",";
    private static final int AMOUNT_NO = 0;
    private static final int EXPIRATION = 1;

    private long amount;
    private long expiration;

    public VoucherInfoRequest() {}

    public VoucherInfoRequest(long amount, long expiration) {
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

    private static void checkReadVoucherException(String[] inputs) {
        InputValidator.notVoucherInputSize(inputs);
        InputValidator.notNumber(inputs);
    }

    public long getAmount() {
        return amount;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
