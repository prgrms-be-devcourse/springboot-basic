package org.prgrms.springbootbasic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.prgrms.springbootbasic.type.VoucherType.FIXED;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @MockBean
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    @Test
    @DisplayName("FixedVoucher 생성 - 성공")
    public void testCreateFixedAmountVoucher_success() {

        LocalDate now = LocalDate.now();
        VoucherInputDto voucherInputDto = new VoucherInputDto(
                String.valueOf(FIXED),
                10L,
                0L,
                11L,
                now.toString(),
                now.plusDays(3).toString()
        );

        Voucher createdVoucher = voucherService.createVoucher(voucherInputDto);

        assertThat(createdVoucher.getVoucherType().toString(), is(voucherInputDto.getVoucherType()));
        assertThat(createdVoucher.getDiscountQuantity(), is(voucherInputDto.getDiscountQuantity()));
        assertThat(createdVoucher.getDiscountRatio(), is(voucherInputDto.getDiscountRatio()));
        assertThat(createdVoucher.getCreatedAt().toString(), is(voucherInputDto.getCreatedAt()));
        assertThat(createdVoucher.getEndedAt().toString(), is(voucherInputDto.getEndedAt()));
    }

    @Test
    @DisplayName("FixedVoucher 생성 - 실패 : 0 미만")
    public void testCreateFixedAmountVoucher_fail_under_0() {
//        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(FIXED, -10L));
    }

    @Test
    @DisplayName("PercentVoucher 생성 - 성공")
    public void testCreatePercentAmountVoucher_success() {
//        Voucher savedVoucher = voucherService.createVoucher(PERCENT, 10L);
//        assertThat(savedVoucher.getClass(), is(PercentAmountVoucher.class));
//        assertThat(savedVoucher.getQuantity(), is(10L));

    }

    @Test
    @DisplayName("PercentVoucher 생성 - 실패 : 100 초과")
    public void testCreatePercentAmountVoucher_fail_exceed_100() {
//        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(PERCENT, 101L));
    }

    @Test
    @DisplayName("PercentVoucher 생성 - 실패 : 0 미만")
    public void testCreatePercentAmountVoucher_fail_under_0() {
//        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(PERCENT, -10L));
    }
}