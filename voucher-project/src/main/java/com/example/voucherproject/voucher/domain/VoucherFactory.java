package com.example.voucherproject.voucher.domain;

import com.example.voucherproject.voucher.enums.VoucherType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
public class VoucherFactory {
    public static Voucher create(VoucherType type, Long amount){
        return new Voucher(UUID.randomUUID(),type, amount,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
}