package org.prgrms.kdt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.dao.entity.voucher.VoucherFactory;
import org.prgrms.kdt.dao.entity.voucher.VoucherType;
import org.prgrms.kdt.dao.repository.voucher.MemoryVoucherRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Component
class VoucherServiceTest {

    private static final VoucherService voucherService = new VoucherService(new MemoryVoucherRepository(), new VoucherFactory());
    private static final String FIXED_AMOUNT_VOUCHER = "1";
    private static final String PERCENT_DISCOUNT_VOUCHER = "2";
    private static final String BLANK = " ";

    @BeforeEach
    void clear() {
        voucherService.clear();
    }

    @Test
    @DisplayName("바우처를 여러 개 만들었을 때, 반환 받는 바우처의 개수가 동일한지 검증")
    void 반환되는_바우처_갯수확인() throws IOException {
        // given
        voucherService.create(FIXED_AMOUNT_VOUCHER, "1000");
        voucherService.create(FIXED_AMOUNT_VOUCHER, "2000");
        voucherService.create(FIXED_AMOUNT_VOUCHER, "3000");
        // when
        int size = voucherService.getAllVouchers().size();

        // then
        assertThat(size, is(3));
    }

    @Test
    @DisplayName("생성한 바우처의 id 값과 저장된 바우처의 id 값이 같은 지 비교")
    void 바우처_id비교() throws IOException {
        // given
        Voucher voucher = voucherService.create(FIXED_AMOUNT_VOUCHER, "1000");

        // when
        UUID voucherId = voucherService.getAllVouchers().get(0).getVoucherId();

        // then
        assertThat(voucherId, is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("존재하지 않는 Voucher 형식을 넣으면 예외 발생")
    void 존재하지_않는_바우처_형식넣기() {
        assertThrows(NoSuchElementException.class, () -> voucherService.create(BLANK, "1000"));
    }

    @Test
    @DisplayName("요청한 바우처 형식 FixedAmountVoucher와 생성된 바우처의 형식이 같은지 확인")
    void FixedAmountVoucher_요청형식_생성바우처형식_비교() {
        // given
        String discountValue = "1000";

        // when
        Voucher newVoucher = voucherService.create(FIXED_AMOUNT_VOUCHER, discountValue);

        // then
        assertThat(newVoucher.getVoucherType(), is(VoucherType.getStringClassName(FIXED_AMOUNT_VOUCHER)));
    }

    @Test
    @DisplayName("요청한 바우처 형식 PercentDiscountVoucher와 생성된 바우처의 형식이 같은지 확인")
    void PercentDiscountVoucher_요청형식_생성바우처형식_비교() {
        // given
        String discountValue = "50";

        // when
        Voucher newVoucher = voucherService.create(PERCENT_DISCOUNT_VOUCHER, discountValue);

        // then
        assertThat(newVoucher.getVoucherType(), is(VoucherType.getStringClassName(PERCENT_DISCOUNT_VOUCHER)));
    }
}