package org.programers.vouchermanagement.voucher.application;

import org.junit.jupiter.api.*;
import org.programers.vouchermanagement.global.exception.NoSuchEntityException;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.domain.VoucherType;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional(readOnly = true)
@SpringBootTest
class WithRepositoryVoucherServiceTest {

    private static final VoucherCreationRequest request = new VoucherCreationRequest(
            new FixedAmountPolicy(100), VoucherType.FIXED_AMOUNT);

    @Autowired
    private VoucherService voucherService;

    @Order(1)
    @Test
    void 바우처를_저장한다() {
        // given & when
        VoucherResponse result = voucherService.save(request);

        // then
        assertThat(result.getValue()).isEqualTo(100);
    }

    @Test
    void 바우처를_아이디로_조회한다() {
        // given
        VoucherResponse response = voucherService.save(request);

        // when
        VoucherResponse result = voucherService.findById(response.getId());

        // then
        assertThat(result.getId()).isEqualTo(response.getId());
    }

    @Test
    void 바우처를_모두_조회한다() {
        // given
        voucherService.save(request);
        voucherService.save(request);

        // when
        VouchersResponse result = voucherService.findAll();

        // then
        assertThat(result.getVouchers()).hasSize(2);
    }

    @Test
    void 바우처를_수정한다() {
        // given
        VoucherResponse response = voucherService.save(request);
        VoucherUpdateRequest req = new VoucherUpdateRequest(
                response.getId(), new PercentDiscountPolicy(10), VoucherType.PERCENT);

        // when
        voucherService.update(req);

        // then
        assertThat(voucherService.findById(response.getId()).getValue()).isEqualTo(10);
    }

    @Test
    void 바우처를_삭제한다() {
        // given
        VoucherResponse response = voucherService.save(request);

        // when
        voucherService.deleteById(response.getId());

        // then
        assertThatThrownBy(() -> {
            voucherService.findById(response.getId());
        })
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessage("존재하지 않는 바우처입니다.");
    }
}
