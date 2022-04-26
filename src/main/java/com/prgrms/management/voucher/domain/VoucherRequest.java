package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class VoucherRequest {
    private VoucherType voucherType;
    private Long amount;
    private UUID customerId;

    public VoucherRequest(String voucherType, String amount) {
        this.voucherType = VoucherType.of(voucherType);
        this.amount = toLong(amount);
    }

    public Voucher create() {
        return this.voucherType.create(this.voucherType, this.amount, this.customerId);
    }

    private long toLong(String inputAmount) {
        long amount = 0L;
        try {
            amount = Long.parseLong(inputAmount);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ErrorMessageType.INCORRECT_NUMBER_FORMAT.getMessage());
        }
        return amount;
    }
}
