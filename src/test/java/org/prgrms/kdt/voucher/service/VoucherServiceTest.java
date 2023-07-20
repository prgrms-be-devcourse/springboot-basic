package org.prgrms.kdt.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.dao.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.VoucherResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.dto.ServiceCreateVoucherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeEach
    void setup() {
        jdbcVoucherRepository.insert(new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0), LocalDateTime.now()));
        jdbcVoucherRepository.insert(new Voucher(UUID.randomUUID(), VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(35.0), LocalDateTime.now()));
        jdbcVoucherRepository.insert(new Voucher(UUID.randomUUID(), VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(70.0), LocalDateTime.now()));
    }

    @Test
    @DisplayName("바우처 생성 후 반환된 바우처의 amount 확인")
    void createVoucher_correctRequest_correctAmount() {
        //given
        ServiceCreateVoucherRequest request = new ServiceCreateVoucherRequest(VoucherType.FIXED, 50.0);

        //when
        VoucherResponse voucher = voucherService.createVoucher(request);

        //then
        double resultAmount = voucher.amount();
        assertThat(resultAmount).isEqualTo(50.0);
    }

    @Test
    @DisplayName("바우처 전체 조회하여 사이즈 검증")
    void findAll_correctSize() {
        //when
        VoucherResponses vouchers = voucherService.findAll();

        //then
        int resultsize = vouchers.vouchers().size();
        assertThat(resultsize).isEqualTo(3);
    }
}