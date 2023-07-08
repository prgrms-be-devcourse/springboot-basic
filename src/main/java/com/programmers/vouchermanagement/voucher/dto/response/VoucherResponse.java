package com.programmers.vouchermanagement.voucher.dto.response;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherResponse {

    private final UUID id;
    private final DiscountType type;
    private final int amount;

    public VoucherResponse(Voucher voucher) {
        this.id = voucher.getId();
        this.type = voucher.getDiscountPolicy().getType();
        this.amount = voucher.getDiscountPolicy().getAmount();
    }
}
