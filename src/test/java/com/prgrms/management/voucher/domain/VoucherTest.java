package com.prgrms.management.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.NoSuchElementException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherTest {
    VoucherRequest fixedRequest;
    VoucherRequest percentRequest;

    @BeforeAll
    void setup() {
        fixedRequest = new VoucherRequest("fixed", "1000");
        percentRequest = new VoucherRequest("percent", "90");
    }

    @Test
    void FIXED_Customer_객체_생성() {
        //when
        Voucher voucher = new Voucher(fixedRequest);
        //then
        Assertions.assertNotNull(voucher.getVoucherId());
    }

    @Test
    void PERCENT_Customer_객체_생성() {
        //when
        Voucher voucher = new Voucher(percentRequest);
        //then
        Assertions.assertNotNull(voucher.getVoucherId());
    }

    @Test
    void 잘못된_Customer_객체_생성() {
        //then
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        new Voucher(new VoucherRequest("MACBOOK", "90000")))
                .isInstanceOf(NoSuchElementException.class);
    }
}