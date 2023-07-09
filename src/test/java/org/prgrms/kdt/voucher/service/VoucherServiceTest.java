package org.prgrms.kdt.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.dao.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.dto.CreateVoucherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeEach
    void setup() {
        jdbcVoucherRepository.insert(new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0)));
        jdbcVoucherRepository.insert(new Voucher(VoucherType.FIXED, VoucherType.PERCENT.createPolicy(30.0)));
        jdbcVoucherRepository.insert(new Voucher(VoucherType.FIXED, VoucherType.PERCENT.createPolicy(70.0)));
    }

    @Test
    @DisplayName("바우처 생성 후 반환된 바우처의 amount 확인")
    void createVoucher_correctRequest_correctAmount() {
        //given
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(VoucherType.FIXED, 50.0);

        //when
        Voucher voucher = voucherService.createVoucher(createVoucherRequest);

        //then
        double resultAmount = voucher.getDiscountPolicy().getAmount();
        assertThat(resultAmount).isEqualTo(50.0);
    }

    @Test
    @DisplayName("바우처 전체 조회하여 사이즈 검증")
    void findAll_correctSize() {
        //when
        List<Voucher> vouchers = voucherService.findAll();

        //then
        int resultsize = vouchers.size();
        assertThat(resultsize).isEqualTo(3);
    }
}