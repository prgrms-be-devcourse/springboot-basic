package com.weeklyMission.voucher.repository;

import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.PercentDiscountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import com.weeklyMission.exception.IncorrectInputException;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherFactory {
    Fixed("fixed"){
        @Override
        public Voucher makeVoucher(UUID id, Integer amount) {
            return new FixedAmountVoucher(id, amount);
        }
    },
    Percent("percent"){
        @Override
        public Voucher makeVoucher(UUID id, Integer amount) {
            return new PercentDiscountVoucher(id, amount);
        }
    };

    private final String type;

    VoucherFactory(String input){
        this.type = input;
    }

    public static VoucherFactory of(String input){
        return Arrays.stream(values())
            .filter(type -> type.isEquals(input))
            .findFirst()
            .orElseThrow(() -> new IncorrectInputException("type", input, "지원하지 않는 voucher."));
    }

    private boolean isEquals(String input){
        return this.type.equals(input);
    }

    public abstract Voucher makeVoucher(UUID id, Integer amount);


}
