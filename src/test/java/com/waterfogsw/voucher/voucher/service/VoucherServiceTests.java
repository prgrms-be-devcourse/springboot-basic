package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTests {

    @Mock
    private VoucherRepository mockedVoucherRepository;
    private VoucherService voucherService;

    @BeforeEach
    void setup() {
        this.voucherService = new VoucherServiceImpl(mockedVoucherRepository);
    }

    @Test
    @DisplayName("바우처 생성 - fixedAmountVoucher 가 저장되고, 조회 되면 성공")
    public void createFixedAmountVoucher() throws Exception {
        // given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        Double value = 1000d;

        // when
        var voucher = voucherService.createVoucher(type, value);

        // then
        assertThat(voucher.getType(), is(VoucherType.FIXED_AMOUNT));
    }

    @Test
    @DisplayName("바우처 생성 - PercentDiscountVoucher 가 저장되고, 조회 되면 성공")
    public void createPercentDiscountVoucher() throws Exception {
        // given
        VoucherType type = VoucherType.PERCENT_DISCOUNT;
        Double value = 1000d;

        // when
        var voucher = voucherService.createVoucher(type, value);

        // then
        assertThat(voucher.getType(), is(VoucherType.PERCENT_DISCOUNT));
    }

    @Test
    @DisplayName("바우처 리스트 조회 - 모두 저장되고, 조회되면 성공")
    public void findAllVouchers() throws Exception {
        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);

        when(mockedVoucherRepository.findAll()).thenReturn(List.of(voucher1, voucher2, voucher3));

        // when
        var findVouchers = voucherService.findAll();

        // then
        assertThat(findVouchers, containsInAnyOrder(voucher1, voucher2, voucher3));
    }
}
