package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class VoucherRequest {
    private VoucherType voucherType;
    private Long amount;

    public VoucherRequest(String voucherType, String amount) {
        this.voucherType = VoucherType.of(voucherType);
        this.amount = toLong(amount);
    }

    public static Voucher of(VoucherRequest voucherRequest) {
        return Voucher.builder()
                .voucherId(UUID.randomUUID())
                .amount(voucherRequest.getAmount())
                .createdAt(LocalDateTime.now())
                .voucherType(voucherRequest.getVoucherType())
                .customerId(null)
                .build();
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
