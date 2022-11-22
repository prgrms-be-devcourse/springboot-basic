package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

@SpringBootTest
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("입력된 바우처 타입과 할인 요금을 적용하여 바우처를 생성을 검증하라")
    void create() {
        //given
        Long discount = 100L;
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;

        //when
        String createVoucherId = voucherService.create(voucherType, discount);

        //then
        Voucher voucher = voucherRepository.findById(createVoucherId).get();
        assertThat(voucher, isA(Voucher.class));
    }

    @Test
    void findVouchers() {
        //given

        //when
        String findVoucherId = voucherService.findVouchers();

        //then
        assertThat("989a671c-5d5a-4ae2-aadb-f493831b5e14", is(findVoucherId));
    }
}