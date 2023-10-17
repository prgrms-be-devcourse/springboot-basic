package com.weeklyMission.client;

import com.weeklyMission.console.ConsoleIO;
import com.weeklyMission.domain.FixedAmountVoucher;
import com.weeklyMission.domain.PercentDiscountVoucher;
import com.weeklyMission.domain.Voucher;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    Fixed("fixed"){
        @Override
        public Voucher giveVoucher(ConsoleIO consoleIO) {
            return new FixedAmountVoucher(UUID.randomUUID(), consoleIO.printAmountCommand());
        }
    },
    Percent("percent"){
        @Override
        public Voucher giveVoucher(ConsoleIO consoleIO) {
            return new PercentDiscountVoucher(UUID.randomUUID(), consoleIO.printAmountCommand());
        }
    };

    private final String type;

    VoucherType(String type){
        this.type = type;
    }

    public static VoucherType of(String input){
        return Arrays.stream(values())
            .filter(type -> type.isEquals(input))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("잘못된 입력입니다."));
    }

    private boolean isEquals(String input){
        return this.type.equals(input);
    }

    public abstract Voucher giveVoucher(ConsoleIO consoleIO);
}
