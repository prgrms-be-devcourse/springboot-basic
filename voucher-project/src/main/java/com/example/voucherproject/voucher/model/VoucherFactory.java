package com.example.voucherproject.voucher.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class VoucherFactory {

    public static Voucher createVoucher(VoucherType type, Long amount){
        return new Voucher(UUID.randomUUID(),type, amount,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
}