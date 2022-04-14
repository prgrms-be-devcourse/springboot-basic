package org.prgrms.springbootbasic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;
    VoucherService voucherService;

    @BeforeEach
    void init() {
        voucherService = new VoucherService(voucherRepository);
    }

    @DisplayName("FixedAmountVoucher 만들기 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        //when
        voucherService.createFixedAmountVoucher(10);

        //then
        verify(voucherRepository).save(any(FixedAmountVoucher.class));
    }

    @DisplayName("FixedAmountVoucher 만들기 실패")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, 100000})
    void createFixedAmountVoucherFail(int amount) {
        //given
        //when
        //then
        assertThatThrownBy(() -> voucherService.createFixedAmountVoucher(amount))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("PercentAmountVoucher 만들기 테스트")
    @Test
    void createPercentAmountVoucher() {
        //given
        //when
        voucherService.createPercentAmountVoucher(20);

        //then
        verify(voucherRepository).save(any(PercentDiscountVoucher.class));
    }

    @DisplayName("PercentAmountVoucher 만들기 실패")
    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    void createPercentAmountVoucherFail(int percent) {
        //given
        //when
        //then
        assertThatThrownBy(() -> voucherService.createPercentAmountVoucher(percent))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Voucher 전체 조회 테스트")
    @Test
    void findAll() {
        //given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        var percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        List<Voucher> vouchers = List.of(fixedAmountVoucher, percentDiscountVoucher);

        when(voucherRepository.findAll()).thenReturn(vouchers);

        //when
        var voucherList = voucherService.findAll();

        //then
        assertThat(voucherList)
            .containsExactlyInAnyOrder(fixedAmountVoucher, percentDiscountVoucher);
    }
}
